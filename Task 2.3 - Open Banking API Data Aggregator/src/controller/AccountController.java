package controller;

import exception.AggregationException;
import exception.BankAPIException;
import model.AccountSummary;
import model.StatusInfo;
import service.AggregatorService;

import java.util.List;
import java.util.Map;

public class AccountController {
    AggregatorService service = new AggregatorService();

    public void processBanks(List<String> banks, List<AccountSummary> accounts, Map<String, StatusInfo> statuses, Map<String, List<String>> BanksWithaccounts) {
        for(String bank : banks){
            try{
                service.aggregate(bank, accounts, statuses, BanksWithaccounts);
                int count = statuses.get(bank).totalAccounts();
                System.out.println(bank + "SUCCESS - " +  count + " accounts fetched" );
            }
            catch(BankAPIException | AggregationException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void generateConsidatedView(int threshold, int totalAccounts,List<AccountSummary> successAccounts){
        
        double totalBalance = 0;
        for(AccountSummary account : successAccounts){
            totalBalance +=  account.getBalance();
        }
        
        System.out.printf(
                "failureCount=%d, threshold=%d: continuing%n",
                service.getFailureCount(), threshold
        );
        
        System.out.printf(
                "Consolidated: %d accounts | Total balance: Rs.%,.0f%n",
                totalAccounts, totalBalance
        );
    }

    public void getDegradedBanks(){
        System.out.println("Degraded banks " + service.getDegradedBanks());
    }


}
