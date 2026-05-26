package model;

public class MT940Transaction {

    private String valueDate;
    private String bookingDate;
    private char debitCredit;
    private double amount;
    private String reference;

    public MT940Transaction(
            String valueDate,
            String bookingDate,
            char debitCredit,
            double amount,
            String reference
    ) {
        this.valueDate = valueDate;
        this.bookingDate = bookingDate;
        this.debitCredit = debitCredit;
        this.amount = amount;
        this.reference = reference;
    }

    public String getValueDate() {
        return valueDate;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public char getDebitCredit() {
        return debitCredit;
    }

    public double getAmount() {
        return amount;
    }

    public String getReference() {
        return reference;
    }

    @Override
    public String toString() {
        return valueDate + "," +
                bookingDate + "," +
                debitCredit + "," +
                amount + "," +
                reference;
    }
}