package util;

public class FilePaths {

    private static final String BASE_PATH = "Task 4.3 - Regulatory Filing Database --Versioned Document Store/files/";

    public static final String FILING_DB = BASE_PATH + "filings.db";
    public static final String FILING_ID_INDEX = BASE_PATH + "filing_id_index.idx";

    public static String auditFile(String entityId) {
        return BASE_PATH + "entity_" + entityId + "_audit.txt";
    }
}
