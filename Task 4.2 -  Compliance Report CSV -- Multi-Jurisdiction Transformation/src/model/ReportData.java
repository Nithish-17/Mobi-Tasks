package model;

import java.time.LocalDate;

public class ReportData {
    private String txnId;
    private double amount;
    private Currency currency;
    private Country fromCountry;
    private Country toCountry;
    private String purpose;
    private LocalDate date;

    public ReportData(String txnId, double amount, Currency currency, Country fromCountry, Country toCountry, String purpose, LocalDate date) {
        this.txnId = txnId;
        this.amount = amount;
        this.currency = currency;
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
        this.purpose = purpose;
        this.date = date;
    }

    public String getTxnId() {
        return txnId;
    }

    public double getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Country getFromCountry() {
        return fromCountry;
    }

    public Country getToCountry() {
        return toCountry;
    }

    public String getPurpose() {
        return purpose;
    }

    public LocalDate getDate() {
        return date;
    }


}
