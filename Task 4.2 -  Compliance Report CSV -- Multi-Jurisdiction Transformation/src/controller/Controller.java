package controller;

import exception.ComplianceException;
import service.ReportServiceImplementaion;
import util.FilePaths;

import java.io.*;
import java.util.Properties;

public class Controller {

    public void run(){

        ReportServiceImplementaion reportService = new ReportServiceImplementaion();

        Properties rates = new Properties();

        try(
                FileInputStream fis = new FileInputStream(FilePaths.RATE_PROPERTY);
                BufferedReader inputReader = new BufferedReader(new FileReader(FilePaths.INPUT_CSV));
                PrintWriter rbiWriter = new PrintWriter(new BufferedWriter(new FileWriter(FilePaths.RBI_CSV)));
                PrintWriter fcaWriter = new PrintWriter(new BufferedWriter(new FileWriter(FilePaths.FCA_CSV)));
                PrintWriter masWriter = new PrintWriter(new BufferedWriter(new FileWriter(FilePaths.MAS_CSV)));
                PrintWriter unclassifiedWriter = new PrintWriter(new BufferedWriter(new FileWriter(FilePaths.UNCLASSIFIED_CSV)));
                BufferedWriter reportWriter = new BufferedWriter(new FileWriter(FilePaths.JURISDICTION_SUMMARY));
            )

        {
            rates.load(fis);
            reportService.processTransaction(rates,inputReader,rbiWriter,fcaWriter,masWriter,unclassifiedWriter,reportWriter);
            reportService.processSummary();
        }

        catch (ComplianceException e){
            System.out.println(e.getMessage());
        }
        
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
