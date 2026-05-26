public class HomeLoan extends Loan{
    double interest;
    int years;
    @Override
    double calculateMonthlyInstalment() {
        double P = getPrincipal();                 // principal
        double r = interest / 12 / 100;            // monthly interest rate
        int n = years * 12;                        // total months

        double emiValue = (P * r * Math.pow(1 + r, n)) /
                (Math.pow(1 + r, n) - 1);

        // store as int if needed
        return (int) Math.round(emiValue);
    }

    @Override
    double calculatePenalty(int daysOverdue) {
        return (int)(0.005 * getPrincipal() * daysOverdue / 100);
    }

    @Override
    String getLoanType() {
        return "HomeLoan";
    }

    public HomeLoan(String loanId, String borrowerName, int principal, double interest, int years) {
        super(loanId, borrowerName, principal);
        this.interest = interest;
        this.years = years;
    }
}
