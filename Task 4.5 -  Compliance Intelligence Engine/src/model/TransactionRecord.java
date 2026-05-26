package model;

public class TransactionRecord {

    private String txnId;
    private String entity;
    private String counterparty;
    private double amount;
    private String date;
    private String purpose;

    public TransactionRecord(String txnId, String entity, String counterparty, double amount, String date, String purpose) {

        this.txnId = txnId;
        this.entity = entity;
        this.counterparty = counterparty;
        this.amount = amount;
        this.date = date;
        this.purpose = purpose;
    }

    @Override
    public String toString() {

        return txnId + " | " +
                entity + " | " +
                counterparty + " | " +
                amount + " | " +
                date + " | " +
                purpose;
    }
}