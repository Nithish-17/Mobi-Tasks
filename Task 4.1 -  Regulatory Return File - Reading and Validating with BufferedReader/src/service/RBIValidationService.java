package service;

import exception.RegulatoryException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public interface RBIValidationService {
    void validateReturns(BufferedReader br, PrintWriter errorWriter) throws IOException;
    void reportSummary(PrintWriter summaryWriter);
}
