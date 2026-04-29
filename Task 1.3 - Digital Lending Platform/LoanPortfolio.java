import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoanPortfolio {
    List<Loan> loans = new ArrayList<>();
    HashMap<String,Integer> overdueMap = new HashMap<>();

    void addLoan(Loan loan){
        loans.add(loan);
        loan.calculateMonthlyInstalment();
    }

    void markOverdue(String loanId, int days){
        for(Loan loan : loans)
            if(loan.getLoanId().equals(loanId)) {
                loan.setStatus(LoanStatus.OVERDUE);
                overdueMap.put(loanId,days);
                break;
            }
    }
    void generateStatement(){
        System.out.printf("%-6s %-8s %12s %12s %10s\n","LoanId","Type","EMI","Status","Penalty");
        for(Loan loan : loans) {
            int emi = (int) loan.calculateMonthlyInstalment();
            int penalty = 0;
            if (loan.getStatus().equals(LoanStatus.OVERDUE)) {
                int days = overdueMap.get(loan.getLoanId());
                penalty = (int) loan.calculatePenalty(days);
            }
            System.out.printf("%-6s %-15s Rs.%-8d %-10s Rs.%-8d\n", loan.getLoanId(),loan.getLoanType(),emi,loan.getStatus(),penalty);
        }
        System.out.println("Total Outstanding: Rs." + getTotalOutstanding());
    }
    int getTotalOutstanding(){
        int total = 0;
        for(Loan loan : loans){
            if(loan.getStatus().equals(LoanStatus.ACTIVE) || loan.getStatus().equals(LoanStatus.OVERDUE))
                total += (int) loan.getPrincipal();
        }
        return total;
    }







}
