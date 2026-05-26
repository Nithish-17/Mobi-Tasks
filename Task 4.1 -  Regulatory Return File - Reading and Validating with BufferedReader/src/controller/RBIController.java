package controller;

import exception.RegulatoryException;
import service.RBIValidationService;
import service.RBIValidationServiceImplementation;
import util.FilePaths;

import java.io.*;

public class RBIController {
    public void run(){
        try(
                BufferedReader br = new BufferedReader(new FileReader(FilePaths.INPUT_FILE));
                PrintWriter errorWriter = new PrintWriter(new BufferedWriter(new FileWriter(FilePaths.ERROR_FILE)));
                PrintWriter summaryWriter = new PrintWriter(new BufferedWriter(new FileWriter(FilePaths.SUMMARY_FILE))){}
        ){
                RBIValidationServiceImplementation  rbiValidationService = new RBIValidationServiceImplementation();
                rbiValidationService.validateReturns(br,errorWriter);
                rbiValidationService.reportSummary(summaryWriter);
        }
        catch (IOException e) {
            System.out.println("Error opening file");
        }
    }
}
