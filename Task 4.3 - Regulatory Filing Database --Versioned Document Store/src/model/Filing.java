package model;

import java.time.LocalDate;

public class Filing {

    private String filingId;
    private int version;
    private Entity entityId;
    private FilingType filingType;
    private String period;
    private LocalDate filedDate;
    private Status status;
    private String supersededBy;


    public Filing(){

    }
    public Filing (Entity entityId, FilingType filingType, String period) {
        this.entityId = entityId;
        this.filingType = filingType;
        this.period = period;
    }


    public String getFilingId() {
        return filingId;
    }

    public int getVersion() {
        return version;
    }

    public Entity getEntityId() {
        return entityId;
    }

    public FilingType getFilingType() {
        return filingType;
    }

    public String getPeriod() {
        return period;
    }

    public LocalDate getFiledDate() {
        return filedDate;
    }

    public Status getStatus() {
        return status;
    }

    public String getSupersededBy() {
        return supersededBy;
    }


    public void setFilingId(String filingId) {
        this.filingId = filingId;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setEntityId(Entity entityId) {
        this.entityId = entityId;
    }

    public void setFilingType(FilingType filingType) {
        this.filingType = filingType;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setFiledDate(LocalDate filedDate) {
        this.filedDate = filedDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSupersededBy(String supersededBy) {
        this.supersededBy = supersededBy;
    }

    @Override
    public String toString() {

        return "Filing{" +
                "filingId='" + filingId + '\'' +
                ", version=" + version +
                ", entityId=" + entityId +
                ", filingType=" + filingType +
                ", period='" + period + '\'' +
                ", filedDate=" + filedDate +
                ", status=" + status +
                ", supersededBy='" + supersededBy + '\'' +
                '}';
    }
}
