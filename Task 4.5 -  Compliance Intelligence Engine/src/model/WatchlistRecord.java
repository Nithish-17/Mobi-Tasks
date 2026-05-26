package model;

public class WatchlistRecord {

    private String entityId;
    private String riskLevel;
    private String sanctionDate;
    private String authority;

    public WatchlistRecord(String entityId, String riskLevel, String sanctionDate, String authority) {

        this.entityId = entityId;
        this.riskLevel = riskLevel;
        this.sanctionDate = sanctionDate;
        this.authority = authority;
    }

    @Override
    public String toString() {

        return entityId + " | " +
                riskLevel + " | " +
                sanctionDate + " | " +
                authority;
    }
}