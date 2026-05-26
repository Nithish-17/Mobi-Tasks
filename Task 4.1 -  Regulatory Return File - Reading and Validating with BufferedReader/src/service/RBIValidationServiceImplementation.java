package service;

import exception.*;
import model.RBIRecord;
import model.ReturnType;
import model.Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class RBIValidationServiceImplementation implements RBIValidationService {
    int totalRecords = 0;
    int passedRecords = 0;
    int failedRecords = 0;

    int submittedCount = 0;
    int pendingCount = 0;

    double totalBalance = 0;

    @Override
    public void validateReturns(BufferedReader br, PrintWriter errorWriter) throws IOException{
        String line;
        while((line = br.readLine()) != null){
            totalRecords++;

            try {
                String[] data =  line.split("\\|");

                if(data.length != 7){
                    throw new InvalidDataCountException(line);
                }

                boolean checkReturnType = false;
                for(ReturnType returnType : ReturnType.values()){
                    if(returnType.name().equals(data[1])) {
                        checkReturnType = true;
                        break;
                    }
                }
                if(!checkReturnType){
                    throw new InvalidReturnTypeException(line);
                }

                boolean checkStatus = false;
                for(Status status : Status.values()){
                    if(status.name().equals(data[6])){
                        checkStatus = true;
                        break;
                    }
                }
                if(!checkStatus){
                    throw new InvalidStatusException(line);
                }

                RBIRecord record = new RBIRecord(data[0], ReturnType.valueOf(data[1]),data[2],Double.parseDouble(data[3]),Double.parseDouble(data[4]),Double.parseDouble(data[5]), Status.valueOf(data[6]));

                if(record.getAmountCr() < 0 || record.getAmountDr() < 0 || record.getBalance() < 0)
                    throw new InvalidCurrencyException(line);

                if(record.getStatus().equals(Status.PENDING)) pendingCount++;

                else if(record.getStatus().equals(Status.SUBMITTED)) submittedCount++;

                double exceptedBalance = Math.abs(record.getAmountCr() - record.getAmountDr());

                if(Math.abs(exceptedBalance - record.getBalance()) > 0){
                    throw new BalanceMismatchException(line);
                }

                totalBalance += record.getBalance();
                passedRecords++;

            }

            catch (InvalidDataCountException e) {
                failedRecords++;
                errorWriter.println("INVALID DATA COUNT ERROR " + line + " reason invalid data count");
            }

            catch (InvalidReturnTypeException e)
            {
                failedRecords++;
                errorWriter.println("INVALID RETURN TYPE ERROR " + line + " reason invalid return type");
            }

            catch (InvalidStatusException e)
            {
                failedRecords++;
                errorWriter.println("INVALID STATUS ERROR " + line + " reason invalid status");
            }

            catch (InvalidCurrencyException e){
                failedRecords++;
                errorWriter.println("INVALID CURRENCY ERROR " + line + " reason invalid or negative currency");
            }

            catch (BalanceMismatchException e)
            {
                failedRecords++;
                errorWriter.println(line + " INVALID BALANCE ERROR " + line + " reason invalid balance");
            }

        }

    }

    @Override
    public void reportSummary(PrintWriter summaryWriter) {
        summaryWriter.println("TOTAL RECORDS: " + totalRecords);
        summaryWriter.println("PASSED RECORDS: " + passedRecords);
        summaryWriter.println("FAILED RECORDS: " + failedRecords);
        summaryWriter.println("SUBMITTED RECORDS: " + submittedCount);
        summaryWriter.println("PENDING RECORDS: " + pendingCount);
        summaryWriter.println("TOTAL BALANCE: " + totalBalance);
    }


}
