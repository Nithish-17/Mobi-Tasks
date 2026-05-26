package model;

import java.time.LocalDate;

public class AccountSummary {
    String bankCode;
    String accountId;
    String accountType;
    double balance;
    LocalDate lastUpdated;

    public AccountSummary(String bankCode, String accountId, String accountType, double balance) {
        this.bankCode = bankCode;
        this.accountId = accountId;
        this.accountType = accountType;
        this.balance = balance;
        this.lastUpdated = LocalDate.now();
    }

    public double getBalance() {
        return balance;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getAccountId() {
        return accountId;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }


    @Override
    public String toString() {
        return bankCode + " - " + accountId + " - " + accountType + " - " + balance;
    }
}