package model;

public class RawInput {
    String entityId;
    String filingType;
    String period;


    public RawInput(String entityId, String filingType, String period) {
        this.entityId = entityId;
        this.filingType = filingType;
        this.period = period;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getFilingType() {
        return filingType;
    }

    public String getPeriod() {
        return period;
    }
}
