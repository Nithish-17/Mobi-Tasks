package model;

import java.util.ArrayList;
import java.util.List;

public class JurisdictionSummary {
    private int count;
    private double total;
    private List<String> transactionIds = new ArrayList<>();

    public void addTransactionId(String txnId, double amount) {
        transactionIds.add(txnId);
        total += amount;
        count++;
    }

    public int getCount() {
        return count;
    }

    public double getTotal() {
        return total;
    }

    public List<String> getTransactionIds() {
        return transactionIds;
    }
}
