package model;

public class Filing {

    private String filingId;
    private String entity;
    private String type;
    private String period;
    private double amount;
    private String status;

    public Filing(String filingId, String entity, String type, String period, double amount, String status) {
        this.filingId = filingId;
        this.entity = entity;
        this.type = type;
        this.period = period;
        this.amount = amount;
        this.status = status;
    }

    @Override
    public String toString() {

        return filingId + " | " +
                entity + " | " +
                type + " | " +
                period + " | " +
                amount + " | " +
                status;
    }
}