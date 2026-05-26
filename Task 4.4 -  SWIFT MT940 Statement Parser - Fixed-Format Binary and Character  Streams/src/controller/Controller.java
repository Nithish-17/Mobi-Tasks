package controller;

import service.MT940ParserService;
import service.MT940ParserServiceImpl;
import util.FileUtil;

public class Controller {

    public void run() {

        try {

            MT940ParserServiceImpl service = new MT940ParserServiceImpl();

            service.parseFile(FileUtil.INPUT_FILE);

            service.writeNormalisedCSV(
                    service.getTransactions(),
                    FileUtil.CSV_FILE
            );

            service.writeBinaryArchive(
                    service.getTransactions(),
                    FileUtil.BINARY_FILE
            );

            service.verifyBinaryRoundTrip(
                    FileUtil.CSV_FILE,
                    FileUtil.BINARY_FILE
            );

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}