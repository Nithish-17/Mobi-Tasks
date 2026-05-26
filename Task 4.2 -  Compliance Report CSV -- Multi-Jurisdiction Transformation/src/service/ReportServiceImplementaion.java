package service;

import exception.*;
import model.Country;
import model.Currency;
import model.JurisdictionSummary;
import model.ReportData;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ReportServiceImplementaion implements ReportService {

    private Map<String, JurisdictionSummary> summaries =
            new HashMap<>();

    private int unclassifiedCount = 0;

    public ReportServiceImplementaion() {
        summaries.put("RBI",new JurisdictionSummary());
        summaries.put("FCA",new JurisdictionSummary());
        summaries.put("MAS",new JurisdictionSummary());
    }

    @Override
    public void processTransaction(Properties rates, BufferedReader inputReader, PrintWriter rbiWriter, PrintWriter fcaWriter, PrintWriter masWriter, PrintWriter unclassifiedWriter, BufferedWriter reportWriter) throws IOException, ComplianceException {


        String line;

        while((line = inputReader.readLine()) != null){


            //data fields
            String txnId;
            double amount;
            String currency;
            String fromCountry;
            String toCountry;
            String purpose;
            String date;

            //storing data from line
            String[] data = line.split(",");

            boolean validate  = validateData(data,unclassifiedWriter);

            if(validate){

                txnId = data[0];
                amount = Double.parseDouble(data[1]);
                currency = data[2];
                fromCountry = data[3];
                toCountry = data[4];
                purpose = data[5];
                date = data[6];

                ReportData reportData = new ReportData(txnId,amount, Currency.valueOf(currency),Country.valueOf(fromCountry),Country.valueOf(toCountry),purpose,LocalDate.parse(date));

                if(reportData.getToCountry().equals(Country.IN)){
                    double inrAmount = reportData.getAmount() * Double.parseDouble(rates.getProperty("INR"));
                    rbiWriter.printf("%s,%.2f,%s,%s%n",txnId,inrAmount,purpose,date);
                    summaries.get("RBI").addTransactionId(txnId,inrAmount);
                }

                if(reportData.getFromCountry().equals(Country.GB) || reportData.getToCountry().equals(Country.GB)){
                    double gbpAmount = reportData.getAmount() * Double.parseDouble(rates.getProperty("GBP"));
                    fcaWriter.printf("%s,%.2f,%s,%s,%s%n",txnId,gbpAmount,fromCountry,toCountry,date);
                    summaries.get("FCA").addTransactionId(txnId,gbpAmount);
                }

                if(reportData.getFromCountry().equals(Country.SG) || reportData.getToCountry().equals(Country.SG)){
                    double sgdAmount = reportData.getAmount() * Double.parseDouble(rates.getProperty("SGD"));
                    masWriter.printf("%s,%.2f,%s,%s%n",txnId,sgdAmount,purpose,date);
                    summaries.get("MAS").addTransactionId(txnId,sgdAmount);
                }
            }
        }
    }

    @Override
    public boolean validateData(String[] data, PrintWriter unclassifiedWriter) {
        try{

            if(data.length != 7)
                throw new InvalidDataException("INVALID DATA EXCEPTION " + Arrays.toString(data) + " error : data count mismatch");


            if(!data[0].matches("T\\d+"))
                throw new InvalidTransactionIdException("INVALID TRANSACTION ID EXCEPTION " + Arrays.toString(data) + " error : transaction id mismatch");


            if(Double.parseDouble(data[1]) < 0)
                throw new InvalidAmountException("INVALID AMOUNT EXCEPTION " + Arrays.toString(data) + " error : amount cannot be negative");


            boolean checkCurrency = false;
            for(Currency c : Currency.values()){
                if(c.name().equals(data[2])){
                    checkCurrency = true;
                    break;
                }
            }
            if(!checkCurrency)
                throw new InvalidCurrencyException("INVALID CURRENCY EXCEPTION " + Arrays.toString(data) + " error : currency mismatch");


            try{
                Country.valueOf(data[3]);
                Country.valueOf(data[4]);
            }
            catch (IllegalArgumentException e){
                unclassifiedCount++;
                throw new InvalidCountryException(
                        "INVALID COUNTRY EXCEPTION "
                                + Arrays.toString(data)
                                + " error : country code mismatch"
                );
            }

            if(LocalDate.parse(data[6]).isAfter(LocalDate.now())){
                throw new InvalidDateException("INVALID DATE EXCEPTION" +  Arrays.toString(data) + " error : date cannot be after now");
            }

            return true;
        }

        catch (InvalidDataException | InvalidCurrencyException | InvalidTransactionIdException | InvalidDateException | InvalidAmountException | DateTimeParseException e) {
            System.out.println(e.getMessage());;
        }

        catch (InvalidCountryException e){
            unclassifiedWriter.println(e.getMessage());
        }

        return false;
    }

    @Override
    public void processSummary() {
        for(String jurisdiction : summaries.keySet()){

            JurisdictionSummary summary = summaries.get(jurisdiction);

            System.out.printf(
                    "%s_report.csv : %d rows (%s)%n",
                    jurisdiction.toLowerCase(),
                    summary.getCount(),
                    String.join(
                            ", ",
                            summary.getTransactionIds()
                    )
            );
        }

        System.out.printf("Unclassified : %d%n",unclassifiedCount);

        System.out.printf("Jurisdiction totals: ");

        for(String jurisdiction : summaries.keySet()){

            JurisdictionSummary summary = summaries.get(jurisdiction);

            System.out.printf("%s=%.2f ",jurisdiction,summary.getTotal());
        }

        System.out.println();
    }
}

