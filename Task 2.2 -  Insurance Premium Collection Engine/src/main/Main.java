package main;

import controller.PolicyController;
import model.Policy;
import model.StatusState;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Policy> policies = new ArrayList<>();
        PolicyController controller = new PolicyController();
        policies.add(new Policy("P01", "C001", 12000, LocalDate.parse("2026-05-04"), null, StatusState.ACTIVE,LocalDate.parse("2028-10-17"),true));
        policies.add(new Policy("P02", "C002", 8500, LocalDate.parse("2026-05-04"), null,StatusState.ACTIVE,LocalDate.parse("2028-10-17"),false));
        policies.add(new Policy("P03", "C003", 9500, LocalDate.parse("2026-04-25"), null, StatusState.ACTIVE,LocalDate.parse("2028-10-17"),true));
        policies.add(new Policy("P04", "C004", 11000, LocalDate.parse("2026-04-15"), LocalDate.parse("2026-04-25"), StatusState.GRACE,LocalDate.parse("2028-10-17"),true));
        policies.add(new Policy("P05", "C005", 11500, LocalDate.parse("2026-03-30"), LocalDate.parse("2026-04-09"), StatusState.GRACE,LocalDate.parse("2028-10-17"),true));
        policies.add(new Policy("P06", "C006", 7000, LocalDate.parse("2026-04-30"), LocalDate.parse("2026-05-10"), StatusState.GRACE,LocalDate.parse("2028-10-17"),true));
        policies.add(new Policy("P07", "C007", 7200, LocalDate.parse("2026-04-30"), LocalDate.parse("2026-05-10"), StatusState.GRACE,LocalDate.parse("2028-10-17"),true));
        policies.add(new Policy("P08", "C008", 5000, LocalDate.parse("2026-04-28"), null, StatusState.ACTIVE,LocalDate.parse("2028-10-17"),true));
        policies.add(new Policy("P09", "C009", 7500, LocalDate.parse("2026-04-30"), null, StatusState.ACTIVE,LocalDate.parse("2028-10-17"),true));

        controller.processBulk(policies);
        controller.generateCollectionReport();

    }
}
