package service;

import exception.MT940ParseException;
import model.MT940Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MT940ParserServiceImpl implements MT940ParserService {


    List<MT940Transaction> txns = new ArrayList<>();

    public List<MT940Transaction> getTransactions(){
        return txns;
    };


    @Override
    public void parseFile(String path) throws IOException, MT940ParseException {

        BufferedReader br = new BufferedReader(new FileReader(path));

        StringBuilder sb = new StringBuilder();

        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }

        br.close();

        String content = sb.toString();

        String[] messages = content.split("-}");

        for (String message : messages) {

            if (message.trim().isEmpty()) {
                continue;
            }

             txns.addAll(extractTransactions(message));

        }
    }

    @Override
    public List<MT940Transaction> extractTransactions(String message) throws MT940ParseException {

        List<MT940Transaction> transactions = new ArrayList<>();

        Pattern pattern = Pattern.compile(":61:([\\S\\s]+?)(?=:\\d{2}[A-Z]?:|$)",Pattern.MULTILINE);

        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {

            String txnLine = matcher.group(1).trim();

            try {

                String valueDate = txnLine.substring(0, 6);

                String bookingDate = txnLine.substring(6, 12);

                char dc = txnLine.charAt(12);

                int amountStart = 13;

                int amountEnd = amountStart;

                while (amountEnd < txnLine.length()  &&
                                (Character.isDigit(txnLine.charAt(amountEnd)) ||
                                        txnLine.charAt(amountEnd) == ',')
                ) {
                    amountEnd++;
                }

                String amtStr = txnLine.substring(amountStart, amountEnd);

                double amount = parseAmount(amtStr);

                String reference = txnLine.substring(amountEnd);

                MT940Transaction txn =
                        new MT940Transaction(
                                valueDate,
                                bookingDate,
                                dc,
                                amount,
                                reference
                        );

                transactions.add(txn);

            } catch (Exception e) {

                throw new MT940ParseException(
                        "Error parsing transaction line: " + txnLine
                );
            }
        }

        return transactions;
    }

    @Override
    public double parseAmount(String amtStr) {

        amtStr = amtStr.replace(",", ".");

        return Double.parseDouble(amtStr);
    }

    @Override
    public void writeNormalisedCSV(List<MT940Transaction> txns,String path) throws IOException {

        PrintWriter pw = new PrintWriter(new FileWriter(path));

        pw.println("ValueDate,BookingDate,DebitCredit,Amount,Reference");

        for (MT940Transaction txn : txns) {

            pw.println(
                    txn.getValueDate() + "," +
                            txn.getBookingDate() + "," +
                            txn.getDebitCredit() + "," +
                            txn.getAmount() + "," +
                            "\"" + txn.getReference() + "\""
            );
        }

        pw.close();

    }

    @Override
    public void writeBinaryArchive(List<MT940Transaction> txns,String binPath) throws IOException {

        DataOutputStream dos = new DataOutputStream(new FileOutputStream(binPath));

        for (MT940Transaction txn : txns) {

            dos.writeUTF(txn.getValueDate());

            dos.writeUTF(txn.getBookingDate());

            dos.writeByte(txn.getDebitCredit());

            dos.writeDouble(txn.getAmount());

            dos.writeUTF(txn.getReference());
        }

        dos.close();

    }

    @Override
    public void verifyBinaryRoundTrip(String csvPath,String binPath) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(csvPath));

        br.readLine();

        int csvCount = 0;

        double csvSum = 0;

        String line;

        while ((line = br.readLine()) != null) {

            String[] parts = line.split(",");

            csvSum += Double.parseDouble(parts[3]);

            csvCount++;
        }

        br.close();

        DataInputStream dis = new DataInputStream(new FileInputStream(binPath));

        int binCount = 0;

        double binSum = 0;

        try {

            while (true) {

                dis.readUTF();

                dis.readUTF();

                dis.readByte();

                double amount = dis.readDouble();

                dis.readUTF();

                binSum += amount;

                binCount++;
            }

        } catch (EOFException ignored) {

        }

        dis.close();

        if (csvCount == binCount &&
                Math.abs(csvSum - binSum) < 0.001) {

            System.out.println(
                    "Round-trip OK: " +
                            csvCount +
                            " records | CSV sum = binary sum = Rs."
                            + String.format("%.2f", csvSum)
            );

        } else {

            System.out.println("Round-trip verification failed");
        }
    }
}
