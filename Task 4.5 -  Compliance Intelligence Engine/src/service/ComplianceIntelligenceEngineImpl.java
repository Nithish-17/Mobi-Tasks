package service;

import exception.CrossFileIndexException;
import exception.DataIntegrityException;
import model.ComplainceDossier;
import util.FilePaths;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplianceIntelligenceEngineImpl implements ComplianceIntelligenceEngine {

    Map<String, HashMap<String, List<Long>>> crossFileIndex = new HashMap<>();

    public void buildCrossFileIndex() throws CrossFileIndexException, IOException {

        List<File> files = FilePaths.getAllFiles();

        for (File file : files) {

            if (!file.exists()) throw new CrossFileIndexException("File not found ", file.getPath());

            try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {

                HashMap<String, List<Long>> indexMap = new HashMap<>();
                String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));

                int lines_read = 0;

                while (true) {

                    long offset = raf.getFilePointer();
                    String line = raf.readLine();

                    if (line == null) break;

                    lines_read++;
                    String[] parts = line.split("\\|");
                    String entity;

                    switch (fileName) {
                        case "filings":
                        case "transactions":
                            entity = parts[1];
                            break;
                        case "watchlist":
                        case "exposures":
                            entity = parts[0];
                            break;

                        default:
                            continue;

                    }

                    indexMap.computeIfAbsent(entity, k -> new ArrayList<>()).add(offset);

                }

                int indexCount = 0;
                for (List<Long> offsets : indexMap.values()) {

                    indexCount += offsets.size();

                }

                if (indexCount != lines_read) {

                    throw new DataIntegrityException("Record count mismatch in " + fileName);

                }
                crossFileIndex.put(fileName, indexMap);
            }

        }

        for (HashMap<String, List<Long>> indexMap : crossFileIndex.values()) {
            System.out.println(indexMap.toString());
        }

    }

    public ComplainceDossier getEntityDossier(String entityId) {

        ComplainceDossier dossier = new ComplainceDossier();

        for (Map.Entry<String, HashMap<String, List<Long>>> entry : crossFileIndex.entrySet()) {

            String fileName = entry.getKey();
            HashMap<String, List<Long>> indexMap = entry.getValue();

            List<Long> offsets = indexMap.get(entityId);

            if (offsets == null)
                continue;

            File file = new File(FilePaths.getFilePath(fileName + ".db"));

            if (!file.exists()) continue;

            try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {

                for (Long offset : offsets) {

                    randomAccessFile.seek(offset);
                    String line = randomAccessFile.readLine();

                    dossier.addRecord(fileName, line);
                }

            } catch (IOException e) {

                e.printStackTrace();
            }

        }

        return dossier;
    }

    public Map<String, List<String>> searchAllFiles(String regex) throws IOException {

        Map<String, List<String>> searchedResults = new HashMap<>();

        List<File> files = FilePaths.getAllFiles();

        Pattern pattern = Pattern.compile(regex);

        for (File file : files) {

            if (!file.exists()) continue;

            List<String> matchedLines = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                String line;

                while ((line = reader.readLine()) != null) {

                    Matcher matcher = pattern.matcher(line);

                    if (matcher.find()) {
                        matchedLines.add(line);
                    }
                }
            }

            searchedResults.put(file.getName(), matchedLines);
        }
        return searchedResults;
    }

    public List<String> detectUnreportedExposures(double threshold) throws IOException {

        List<String> unreportedExposures = new ArrayList<>();
        Map<String, Double> transactionSums = new HashMap<>();

        File transactionsFile = FilePaths.getFile("transactions.db");

        if (transactionsFile.exists()) {

            try (BufferedReader reader = new BufferedReader(new FileReader(transactionsFile))) {

                String line;

                while ((line = reader.readLine()) != null) {

                    String[] parts = line.split("\\|");

                    String entity = parts[1];

                    String counterparty = parts[2];

                    double amount = Double.parseDouble(parts[3]);

                    String key = entity + "|" + counterparty;

                    transactionSums.merge(key, amount, Double::sum);
                }

            }
        }

        File exposureFile = FilePaths.getFile("exposures.db");

        if (exposureFile.exists()) {

            try (BufferedReader reader = new BufferedReader(new FileReader(exposureFile))) {

                String line;
                while ((line = reader.readLine()) != null) {

                    String[] parts = line.split("\\|");

                    String entity = parts[0];

                    String counterparty = parts[1];

                    double exposure = Double.parseDouble(parts[2]);

                    String key = entity + "|" + counterparty;

                    double transactionTotal = transactionSums.getOrDefault(key, 0.0);

                    if (transactionTotal > exposure && transactionTotal > threshold) {

                        unreportedExposures.add("Entity: " + entity + ", Counterparty: " + counterparty + ", Transactions: " + transactionTotal + ", Reported Exposure: " + exposure);

                    }

                }
            }
        }

        return unreportedExposures;
    }

    public void generateRegulatoryResponse(String entityId,String queryRef,String outputPath) throws IOException {

        ComplainceDossier dossier = getEntityDossier(entityId);

        File outputFile = new File(outputPath);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            writer.write("====================================");
            writer.newLine();

            writer.write("      REGULATORY RESPONSE LETTER");
            writer.newLine();

            writer.write("====================================");
            writer.newLine();
            writer.newLine();

            writer.write("Query Reference : " + queryRef);
            writer.newLine();

            writer.write("Entity           : " + entityId);
            writer.newLine();

            writer.write("Generated On     : " + java.time.LocalDate.now());
            writer.newLine();
            writer.newLine();

            int section = 1;

            for(Map.Entry<String, List<String>> entry : dossier.getRecords().entrySet()) {

                writer.write("------------------------------------");
                writer.newLine();

                writer.write(section + ". "+ entry.getKey().toUpperCase());
                writer.newLine();

                writer.write("------------------------------------");
                writer.newLine();

                for(String record : entry.getValue()) {

                    writer.write(record);
                    writer.newLine();
                }

                writer.newLine();

                section++;
            }
        }
    }

    public void archiveToComplianceVault(List<String> entityIds, String vaultPath) throws IOException {

        File vaultFile = new File(vaultPath);

        try(FileOutputStream fos = new FileOutputStream(vaultFile)) {

            byte[] buffer = new byte[4096];

            for(String entityId : entityIds) {

                ComplainceDossier dossier = getEntityDossier(entityId);

                for(Map.Entry<String, List<String>> entry : dossier.getRecords().entrySet()) {

                    String sourceName = entry.getKey();

                    List<String> records = entry.getValue();

                    for(String record : records) {

                        byte[] data = (sourceName + " | " + record + System.lineSeparator()).getBytes();

                        try(ByteArrayInputStream bis = new ByteArrayInputStream(data)) {

                            int bytesRead;

                            while((bytesRead = bis.read(buffer)) != -1) {

                                fos.write(buffer,0,bytesRead);
                            }
                        }
                    }
                }
            }
        }
    }

}
