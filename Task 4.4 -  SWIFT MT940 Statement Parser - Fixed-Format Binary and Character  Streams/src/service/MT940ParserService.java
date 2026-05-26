package service;

import exception.MT940ParseException;
import model.MT940Transaction;

import java.io.IOException;
import java.util.List;

public interface MT940ParserService {

    void parseFile(String path) throws IOException, MT940ParseException;

    List<MT940Transaction> extractTransactions(String message) throws MT940ParseException;

    double parseAmount(String amtStr);

    void writeNormalisedCSV(List<MT940Transaction> txns,String path) throws IOException;

    void writeBinaryArchive(List<MT940Transaction> txns,String binPath) throws IOException;

    void verifyBinaryRoundTrip(String csvPath,String binPath) throws IOException;
}