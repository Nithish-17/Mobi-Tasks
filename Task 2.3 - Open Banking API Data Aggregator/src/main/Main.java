package main;

import controller.AccountController;
import model.AccountSummary;
import model.ApiStatus;
import model.StatusInfo;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        AccountController controller = new AccountController();

        List<String> banks = List.of("SBI","HDFC", "ICICI", "AXIS", "KOTAK", "PNB", "BOB", "CITI");

        List<AccountSummary> successAccounts = List.of(
                new AccountSummary("SBI","S001","savings",10000),
                new AccountSummary("SBI","S002","savings",15000),
                new AccountSummary("SBI","S003","current",1000),
                new AccountSummary("SBI","S004","current",20000),
                new AccountSummary("HDFC","H001","savings",11000), // expired then success
                new AccountSummary("HDFC","H002","savings",3000),
                new AccountSummary("HDFC","H003","current",50000),
                new AccountSummary("KOTAK","K001","savings",10000), // partial fetch
                new AccountSummary("KOTAK","K002","current",20000),
                new AccountSummary("KOTAK","K003","savings",30000),
                new AccountSummary("CITI","C001","current",10000),
                new AccountSummary("CITI","C002","savings",20000)
        );

        Map<String, StatusInfo> statuses = Map.of(
                "SBI", new StatusInfo(ApiStatus.SUCCESS,4),
                "HDFC", new StatusInfo(ApiStatus.TOKEN_EXPIRED, 3),
                "ICICI", new StatusInfo(ApiStatus.RATE_LIMIT_EXCEEDED,1),
                "AXIS", new StatusInfo(ApiStatus.SCHEMA_CHANGED,3),
                "KOTAK", new StatusInfo(ApiStatus.PARTIAL_DATA,4),
                "PNB", new StatusInfo(ApiStatus.TOKEN_EXPIRED,3),
                "BOB", new StatusInfo(ApiStatus.RATE_LIMIT_EXCEEDED, 3),
                "CITI", new StatusInfo(ApiStatus.SUCCESS,2)
        );

        Map<String, List<String>> BanksWithaccounts = Map.of(
                "SBI", List.of("S001","S002","S003","S004"),
                "HDFC", List.of("H001","H002","H003"),
                "ICICI", List.of("I001"),
                "AXIX", List.of("A001","A002","A003"),
                "KOTAK", List.of("K001","K002","K003","K004"),
                "PNB", List.of("POO1","P002","P003"),
                "BOB", List.of("BOO1","B002","B003"),
                "CITI", List.of("C001","C002")
        );
        controller.processBanks(banks, successAccounts, statuses, BanksWithaccounts);
        controller.generateConsidatedView(statuses.size(),successAccounts.size(),successAccounts);
        controller.getDegradedBanks();

    }
}
