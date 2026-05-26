package service;

import exception.*;
import model.Entity;
import model.Filing;
import model.FilingType;
import model.Status;
import util.FilePaths;
import util.Helper;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class VersionedFilingDBImplementation implements VersionedFilingDB {


    @Override
    public void submitFiling(Filing f) throws RegulatoryException,IOException{

        Filing currentVersion = getCurrentVersion(f.getEntityId().name() + "_" + f.getFilingType().name()); // if it is null then it is first active report

        if (currentVersion != null) {

            amendFiling(currentVersion.getFilingId(), f);

            return;

        }

        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(FilePaths.FILING_DB, true)))) {

            f.setStatus(Status.ACTIVE);
            f.setFilingId(Helper.generateFilingId(f.getEntityId(), f.getFilingType()));
            f.setVersion(1);
            f.setFiledDate(LocalDate.now());
            f.setSupersededBy("");
            printWriter.println(Helper.convertToRecord(f));

            printWriter.flush();
            Helper.updateIndex(f.getFilingId()); //indexing

        } catch (IOException e) {
            throw e;
        }

    }

    @Override
    public void amendFiling(String filingId, Filing amended) throws RegulatoryException, IOException {

        List<String> updatedRecords = new ArrayList<>();

        Filing existingFiling = Helper.findByFilingId(filingId);

        if (existingFiling == null) {

            throw new FilingNotFoundException("Filing not Found for " + filingId);
        }

        String newFilingId = Helper.generateFilingId(amended.getEntityId(), amended.getFilingType());

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FilePaths.FILING_DB))) {

            String line;

            while ((line = bufferedReader.readLine()) != null) {

                String[] parts = line.split("\\|", -1);
                String currentId = parts[0];

                if (currentId.equals(filingId)) {

                    parts[6] = Status.SUPERSEDED.name();
                    parts[7] = newFilingId;

                    line = String.join("|", parts);

                    amended.setFilingId(newFilingId);
                    amended.setVersion(existingFiling.getVersion() + 1);
                    amended.setFiledDate(LocalDate.now());
                    amended.setStatus(Status.ACTIVE);
                    amended.setSupersededBy("");

                }


                updatedRecords.add(line);

            }

            updatedRecords.add(Helper.convertToRecord(amended));

            try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(FilePaths.FILING_DB)))) {

                for (String updatedRecord : updatedRecords) {

                    printWriter.println(updatedRecord);
                }

                printWriter.flush();
                Helper.updateIndex(amended.getFilingId());

            }

        }

    }

    @Override
    public Filing getCurrentVersion(String baseId) throws RegulatoryException {

        ValidateInputs.validateBaseId(baseId);

        try (BufferedReader reader = new BufferedReader(new FileReader(FilePaths.FILING_DB))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|", -1);

                String currentBaseId = parts[2] + "_" + parts[3];

                String supersededBy = parts[7];

                if (baseId.equals(currentBaseId) && supersededBy.isEmpty()) {

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

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Filing> getFilingHistory(String baseId) throws RegulatoryException {

        ValidateInputs.validateBaseId(baseId);

        List<Filing> filingHistory = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FilePaths.FILING_DB))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|", -1);

                String currentBaseId = parts[2] + "_" + parts[3];

                if (baseId.equals(currentBaseId)) {

                    Filing filing = new Filing();

                    filing.setFilingId(parts[0]);

                    filing.setVersion(
                            Integer.parseInt(parts[1]));

                    filing.setEntityId(
                            Entity.valueOf(parts[2]));

                    filing.setFilingType(
                            FilingType.valueOf(parts[3]));

                    filing.setPeriod(parts[4]);

                    filing.setFiledDate(
                            LocalDate.parse(parts[5]));

                    filing.setStatus(
                            Status.valueOf(parts[6]));

                    filing.setSupersededBy(parts[7]);

                    filingHistory.add(filing);
                }
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        filingHistory.sort(
                Comparator.comparingInt(
                        Filing::getVersion));

        return filingHistory;

    }

    @Override
    public List<Filing> searchByPeriodAndType(String period, String type) throws RegulatoryException{

        FilingType filingType;

        try {

            filingType =
                    FilingType.valueOf(type);

        }
        catch (IllegalArgumentException e) {

            throw new RegulatoryException(
                    "Invalid filing type");
        }

        List<Filing> matchingFilings =
                new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(
                     new FileReader(FilePaths.FILING_DB))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts =
                        line.split("\\|", -1);

                String currentPeriod =
                        parts[4];

                FilingType currentType =
                        FilingType.valueOf(parts[3]);

                if (period.equals(currentPeriod)
                        && filingType == currentType) {

                    Filing filing = new Filing();

                    filing.setFilingId(parts[0]);

                    filing.setVersion(
                            Integer.parseInt(parts[1]));

                    filing.setEntityId(
                            Entity.valueOf(parts[2]));

                    filing.setFilingType(currentType);

                    filing.setPeriod(parts[4]);

                    filing.setFiledDate(
                            LocalDate.parse(parts[5]));

                    filing.setStatus(
                            Status.valueOf(parts[6]));

                    filing.setSupersededBy(parts[7]);

                    matchingFilings.add(filing);
                }
            }

        }
        catch (IOException e) {

            e.printStackTrace();
        }

        return matchingFilings;

    }

    @Override
    public void generateAuditTrail(String entityId) throws RegulatoryException,IOException{

        try {

            Entity.valueOf(entityId);

        }
        catch (IllegalArgumentException e) {

            throw new RegulatoryException("Invalid Entity Id" +  entityId);
        }

        List<Filing> auditFilings = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FilePaths.FILING_DB))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|", -1);

                Entity currentEntity = Entity.valueOf(parts[2]);

                if (currentEntity.name().equals(entityId)) {

                    Filing filing = new Filing();

                    filing.setFilingId(parts[0]);

                    filing.setVersion(Integer.parseInt(parts[1]));

                    filing.setEntityId(currentEntity);

                    filing.setFilingType(FilingType.valueOf(parts[3]));

                    filing.setPeriod(parts[4]);

                    filing.setFiledDate(LocalDate.parse(parts[5]));

                    filing.setStatus(Status.valueOf(parts[6]));

                    filing.setSupersededBy(parts[7]);

                    auditFilings.add(filing);
                }
            }

        }
        catch (IOException e) {

            e.printStackTrace();
        }

        auditFilings.sort(Comparator.comparing(Filing::getFilingType).thenComparingInt(Filing::getVersion));

        String auditFileName = FilePaths.auditFile(entityId);

        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(auditFileName)))) {

            for (Filing filing: auditFilings) {

                printWriter.println();

                printWriter.println("FILING ID      : " + filing.getFilingId());

                printWriter.println("VERSION        : " + filing.getVersion());

                printWriter.println("FILING TYPE    : " + filing.getFilingType());

                printWriter.println("PERIOD         : " + filing.getPeriod());

                printWriter.println("FILED DATE     : " + filing.getFiledDate());

                printWriter.println("STATUS         : " + filing.getStatus());

                printWriter.println("SUPERSEDED BY  : " + filing.getSupersededBy());
            }

        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }

}
