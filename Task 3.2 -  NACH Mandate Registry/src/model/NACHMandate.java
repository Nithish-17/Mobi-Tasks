package model;

public class NACHMandate {

    private String umrn;
    private String customerId;
    private String bankAccountId;
    private String destinationMIFR;
    private double maxDebitAmount;
    private NACHDebitFrequency debitFrequency;
    private String startDate;
    private String endDate;
    private NACHStatus status;

    public NACHMandate(String umrn,
                       String customerId,
                       String bankAccountId,
                       String destinationMIFR,
                       double maxDebitAmount,
                       NACHDebitFrequency debitFrequency,
                       String startDate,
                       String endDate) {

        this.umrn = umrn;
        this.customerId = customerId;
        this.bankAccountId = bankAccountId;
        this.destinationMIFR = destinationMIFR;
        this.maxDebitAmount = maxDebitAmount;
        this.debitFrequency = debitFrequency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = NACHStatus.PENDING;
    }

    public String getUmrn() {
        return umrn;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public String getDestinationMIFR() {
        return destinationMIFR;
    }

    public double getMaxDebitAmount() {
        return maxDebitAmount;
    }

    public NACHDebitFrequency getDebitFrequency() {
        return debitFrequency;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public NACHStatus getStatus() {
        return status;
    }

    public void setStatus(NACHStatus status) {
        this.status = status;
    }

}