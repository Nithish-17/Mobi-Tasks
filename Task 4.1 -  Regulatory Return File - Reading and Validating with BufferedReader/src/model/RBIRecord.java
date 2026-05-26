package model;
public class RBIRecord {
    private String bankCode;
    private ReturnType returnType;
    private String period;
    private double amountCr;
    private double amountDr;
    private double balance;
    private Status status;
    public RBIRecord(String bankCode,
                     ReturnType returnType,
                     String period,
                     double amountCr,
                     double amountDr,
                     double balance,
                     Status status) {
        this.bankCode = bankCode;
        this.returnType = returnType;
        this.period = period;
        this.amountCr = amountCr;
        this.amountDr = amountDr;
        this.balance = balance;
        this.status = status;
    }
    public String getBankCode() {
        return bankCode;
    }
    public ReturnType getReturnType() {
        return returnType;
    }
    public String getPeriod() {
        return period;
    }
    public double getAmountCr() {
        return amountCr;
    }
    public double getAmountDr() {
        return amountDr;
    }
    public double getBalance() {
        return balance;
    }
    public Status getStatus() {
        return status;
    }
}