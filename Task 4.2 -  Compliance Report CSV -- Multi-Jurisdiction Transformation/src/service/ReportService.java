package service;

import exception.ComplianceException;

import java.io.*;
import java.util.Properties;

public interface ReportService {
    void processTransaction(Properties rates, BufferedReader inputReader, PrintWriter rbiWriter, PrintWriter fcaWriter, PrintWriter masWriter, PrintWriter unclassifiedWriter, BufferedWriter reportWriter) throws IOException, ComplianceException;
    boolean validateData(String[] data, PrintWriter unclassifiedWriter) ;
    void processSummary();
}
