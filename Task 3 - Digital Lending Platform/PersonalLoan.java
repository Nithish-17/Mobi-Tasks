public class PersonalLoan extends Loan{
    double interest;
    int years;
    @Override
    double calculateMonthlyInstalment() {
        int totalInterest = (int) (getPrincipal() * years * interest);
        int totalAmount = (int) (getPrincipal() + totalInterest);
        return totalAmount / years;
    }

    @Override
    double calculatePenalty(int daysOverdue) {
        return (int)(0.02 * calculateMonthlyInstalment() * daysOverdue);
    }

    @Override
    String getLoanType() {
        return "PersonalLoan";
    }

    public PersonalLoan(String loanId, String borrowerName, int principal, double interest, int years) {
        super(loanId, borrowerName, principal);
        this.interest = interest;
        this.years = years;
    }
}
