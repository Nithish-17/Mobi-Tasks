abstract class Loan {
    private final String loanId;
    private final String borrowerName;
    private final int principal;
    private String distributedDate = "N/A";
    private LoanStatus status = LoanStatus.APPLIED;

    //abstract methods-----------------------------------------
    abstract double calculateMonthlyInstalment();
    abstract double calculatePenalty(int daysOverdue);
    abstract String getLoanType();
    //---------------------------------------------------------

   //getters---------------------------------------------------
    public String getLoanId() {
        return loanId;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public double getPrincipal() {
        return principal;
    }

    public String getDistributedDate() {
        return distributedDate;
    }

    public LoanStatus getStatus() {
        return status;
    }
    //------------------------------------------------------------

    //setter to change the state of the loan----------------------
    public void setStatus(LoanStatus status) {
        this.status = status;
    }
    //------------------------------------------------------------

    public Loan(String loanId, String borrowerName, int principal) {
        this.loanId = loanId;
        this.borrowerName = borrowerName;
        this.principal = principal;
    }
}
