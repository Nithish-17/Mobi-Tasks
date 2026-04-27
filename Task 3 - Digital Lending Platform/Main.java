public class Main {
    static void main() {
        LoanPortfolio portfolio = new LoanPortfolio();

        portfolio.addLoan(new PersonalLoan("PL01","Arjun",200000,12.5,24));
        portfolio.addLoan(new HomeLoan("HL01","Priya", 5000000,8.5,240));
        portfolio.addLoan(new EducationLoan("EL01","Rohan",800000,7.0,true));
        portfolio.markOverdue("PL01",15);
        portfolio.generateStatement();
    }
    
}
