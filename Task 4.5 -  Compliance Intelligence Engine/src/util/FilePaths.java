package util;

import java.io.File;
import java.util.List;

public class FilePaths {

    private static String BASE_PATH = "Task 4.5 -  Compliance Intelligence Engine/";

    public static final String FILINGS_DB = BASE_PATH + "data/filings.db";

    public static final String TRANSACTIONS_DB = BASE_PATH + "data/transactions.db";

    public static final String WATCHLIST_DB = BASE_PATH + "data/watchlist.db";

    public static final String EXPOSURES_DB = BASE_PATH + "data/exposures.db";

    public static final String OUTPUT_DIR = BASE_PATH + "output/";

    public static String getFilePath(String fileName) {
        return BASE_PATH + "data/" +fileName;
    }

    public static String getOutputFilePath(String fileName) {
        return OUTPUT_DIR + fileName;
    }

    public static List<File> getAllFiles() {
        File filingsFile = new File(FilePaths.FILINGS_DB);
        File transactionsFile = new File(FilePaths.TRANSACTIONS_DB);
        File watchlistFile = new File(FilePaths.WATCHLIST_DB);
        File exposuresFile = new File(FilePaths.EXPOSURES_DB);

        return List.of(filingsFile, transactionsFile, watchlistFile, exposuresFile);
    }

    public static File getFile(String fileName) {
        return new File(BASE_PATH + "data/" + fileName);
    }
}
