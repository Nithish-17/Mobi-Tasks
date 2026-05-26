package model;

import java.time.LocalDate;

public class Policy {
    private final String policyId;
    private final String customerId;
    private final double premiumAmount;
    private LocalDate dueDate;
    private LocalDate graceEndDate;
    private StatusState status;
    private LocalDate expiryDate;
    private boolean bankApproved;

    public Policy(String policyId, String customerId, double premiumAmount, LocalDate dueDate, LocalDate graceEndDate, StatusState status, LocalDate expiryDate, boolean bankApproved) {
        this.policyId = policyId;
        this.customerId = customerId;
        this.premiumAmount = premiumAmount;
        this.dueDate = dueDate;
        this.graceEndDate = graceEndDate;
        this.status = status;
        this.expiryDate = expiryDate;
        this.bankApproved = bankApproved;
    }


    public void setStatus(StatusState status) {
        this.status = status;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setGraceEndDate(LocalDate graceEndDate) {
        this.graceEndDate = graceEndDate;
    }

    public String getPolicyId() {
        return policyId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getPremiumAmount() {
        return premiumAmount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getGraceEndDate() {
        return graceEndDate;
    }

    public StatusState getStatus() {
        return status;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public boolean isBankApproved() {
        return bankApproved;
    }
}

