package model;

public class ExposureRecord {

    private String entity;
    private String counterparty;
    private double totalExposure;
    private String asOfDate;

    public ExposureRecord(String entity, String counterparty, double totalExposure, String asOfDate) {

        this.entity = entity;
        this.counterparty = counterparty;
        this.totalExposure = totalExposure;
        this.asOfDate = asOfDate;
    }

    @Override
    public String toString() {

        return entity + " | " +
                counterparty + " | " +
                totalExposure + " | " +
                asOfDate;
    }
}