package util;

import model.Entity;
import model.Filing;
import model.FilingType;
import model.Status;

import java.io.*;
import java.time.LocalDate;
import java.util.UUID;

public class Helper {
    public static String generateFilingId(Entity entity, FilingType filingType) {

        return entity +
                "_" +
                filingType +
                "_" +
                UUID.randomUUID().toString().substring(0, 8);
    }

    public static String convertToRecord(Filing f) {

        return f.getFilingId() + "|" +
                f.getVersion() + "|" +
                f.getEntityId() + "|" +
                f.getFilingType() + "|" +
                f.getPeriod() + "|" +
                f.getFiledDate() + "|" +
                f.getStatus() + "|" +
                f.getSupersededBy();
    }

    public static void updateIndex(String filingId) throws IOException {

        int lineNumber = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(FilePaths.FILING_DB))) {

            while (reader.readLine() != null) {

                lineNumber++;
            }
        }

        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(FilePaths.FILING_ID_INDEX,true)))) {

            printWriter.println(filingId + "=" + (lineNumber - 1));
            //lineNumber - 1 because already inserted the new filing so it counts extra
        }
    }


    public static Filing findByFilingId(String filingId) throws IOException {

        int targetLine = -1;

        try (BufferedReader reader = new BufferedReader(new FileReader(FilePaths.FILING_ID_INDEX))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("=");

                if (parts[0].equals(filingId)) {

                    targetLine = Integer.parseInt(parts[1]);

                    break;
                }
            }
        }

        if (targetLine == -1) {

            return null;
        }


        try (BufferedReader reader = new BufferedReader(new FileReader(FilePaths.FILING_DB))) {

            String line;

            int currentLine = 0;

            while ((line = reader.readLine()) != null) {

                if (currentLine == targetLine) {

                    String[] parts = line.split("\\|", -1);

                    Filing filing = new Filing();

                    filing.setFilingId(parts[0]);

                    filing.setVersion(Integer.parseInt(parts[1]));

                    filing.setEntityId(Entity.valueOf(parts[2]));

                    filing.setFilingType(FilingType.valueOf(parts[3]));

                    filing.setPeriod(parts[4]);

                    filing.setFiledDate(LocalDate.parse(parts[5]));

                    filing.setStatus(Status.valueOf(parts[6]));

                    filing.setSupersededBy(parts[7]);

                    return filing;
                }

                currentLine++;
            }
        }

        return null;
    }
}
