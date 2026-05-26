public class EducationLoan extends Loan{
    boolean flag;
    double interest;
    @Override
    double calculateMonthlyInstalment() {
        return 0;
    }

    @Override
    double calculatePenalty(int daysOverdue) {
        return 0;
    }

    @Override
    String getLoanType() {
        return "EducationalLoan";
    }
    public EducationLoan(String loanId, String borrowerName, int principal, double interest, boolean flag) {
        super(loanId, borrowerName, principal);
        this.interest = interest;
        this.flag = flag;
    }
}
