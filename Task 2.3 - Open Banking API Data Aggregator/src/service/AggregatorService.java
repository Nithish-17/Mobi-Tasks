package service;

import exception.*;
import model.AccountSummary;
import model.ApiStatus;
import model.StatusInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AggregatorService {

    private List<AccountSummary> allSuccessAccounts = new ArrayList<>();
    private List<String> degradedBanks = new ArrayList<>();
    private int successCount = 0;
    private int failureCount = 0;

    private boolean refresh(String bank){
        return new Random().nextBoolean();
    }

    private void partialDataProcessor(String bank,List<AccountSummary> accounts, Map<String, List<String>> BanksWithaccounts) throws PartialDataException {
        List<AccountSummary> fetched = new ArrayList<>();
        List<String> failed = new ArrayList<>();
        for(AccountSummary account : accounts){
            if(account.getBankCode().equals(bank)) {
                fetched.add(account);
                allSuccessAccounts.add(account);
            }
        }
        List<String> allAccounts = BanksWithaccounts.get(bank);
        for(String accId :  allAccounts){
            boolean found = false;
            for(AccountSummary acc :  fetched){
                if(acc.getAccountId().equals(accId)){
                    found = true;
                    break;
                }
            }
            if(!found){
                failed.add(accId);
            }

        }
        throw new PartialDataException(bank + ": PartialData - " + fetched.size() + " fetched" + failed.size() + " failed " + failed.toString(),fetched,failed);
    }

    private void verifyApi(String bank, List<AccountSummary> accounts, Map<String, StatusInfo> statuses, Map<String, List<String>> BanksWithaccounts) throws BankAPIException {

        if(statuses.get(bank).status() == ApiStatus.RATE_LIMIT_EXCEEDED)
            throw new RateLimitedByBankException(bank + ": RateLimited 3s -> ",3);

        if(statuses.get(bank).status() == ApiStatus.TOKEN_EXPIRED)
            throw new TokenExpiredException(bank + ": Token Expired -> ");

        if(statuses.get(bank).status() == ApiStatus.SCHEMA_CHANGED)
            throw new SchemaChangedException(bank + ": Schema Changed -> skip (degraded)");

        if(statuses.get(bank).status() == ApiStatus.PARTIAL_DATA)
            partialDataProcessor(bank,accounts,BanksWithaccounts);

    }

    public void aggregate(String bank, List<AccountSummary> accounts, Map<String, StatusInfo> statuses, Map<String, List<String>> BanksWithaccounts) throws BankAPIException {
        try{
            verifyApi(bank,accounts,statuses,BanksWithaccounts);
            successCount++;
            for(AccountSummary account : accounts){
                if(account.getBankCode().equals(bank)){
                    allSuccessAccounts.add(account);
                }
            }
        }
        catch (TokenExpiredException e){
                boolean refreshed = refresh(bank);
                if(refreshed) {
                    successCount++;
                    throw new BankAPIException(e.getMessage() + "refreshed SUCCESS - " + statuses.get(bank).totalAccounts());
                }
                failureCount++;
                throw new BankAPIException(e.getMessage() + "refreshed fail - FAILED");

        }
        catch (RateLimitedByBankException e){
            try {
                Thread.sleep(e.getRetryAfterSeconds() * 1000);
                verifyApi(bank,accounts,statuses,BanksWithaccounts);
            }
            catch (Exception e1) {
                throw new BankAPIException(e.getMessage() + "FAILED");
            }
        }
        catch (PartialDataException  e){
            failureCount++;
            throw e;
        }
        catch (SchemaChangedException e){
            degradedBanks.add(bank);
            failureCount++;
            throw e;
        }
        if(failureCount > statuses.size() / 2)
            throw new AggregationException("Majority banks failed");
    }

    public List<String> getDegradedBanks() {
        return degradedBanks;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public List<AccountSummary> getAllSuccessAccounts() {
        return allSuccessAccounts;
    }
}
