/*
package data;

import model.RiskCategory;
import model.RiskFactor;
import model.UnderwritingApplication;

import java.util.Arrays;
import java.util.List;

public class MockData {
    public static List<RiskFactor> getAllFactors() {
        return Arrays.asList(

                // HEALTH
                new RiskFactor("RF001", "Smoking", RiskCategory.HEALTH, 2.5, 9),
                new RiskFactor("RF002", "Obesity", RiskCategory.HEALTH, 2.2, 8),
                new RiskFactor("RF003", "Diabetes", RiskCategory.HEALTH, 2.0, 7),
                new RiskFactor("RF004", "Heart Disease History", RiskCategory.HEALTH, 3.0, 10),
                new RiskFactor("RF005", "Regular Exercise", RiskCategory.HEALTH, 1.5, 2),

                // LIFESTYLE
                new RiskFactor("RF006", "Alcohol Consumption", RiskCategory.LIFESTYLE, 1.8, 7),
                new RiskFactor("RF007", "Extreme Sports", RiskCategory.LIFESTYLE, 2.0, 8),
                new RiskFactor("RF008", "Poor Sleep Pattern", RiskCategory.LIFESTYLE, 1.3, 6),
                new RiskFactor("RF009", "Healthy Diet", RiskCategory.LIFESTYLE, 1.0, 2),
                new RiskFactor("RF010", "Frequent International Travel", RiskCategory.LIFESTYLE, 1.4, 5),

                // OCCUPATION
                new RiskFactor("RF011", "Construction Worker", RiskCategory.OCCUPATION, 2.5, 9),
                new RiskFactor("RF012", "Pilot", RiskCategory.OCCUPATION, 2.2, 8),
                new RiskFactor("RF013", "Software Engineer", RiskCategory.OCCUPATION, 1.0, 3),
                new RiskFactor("RF014", "Mining Worker", RiskCategory.OCCUPATION, 2.8, 10),
                new RiskFactor("RF015", "Office Employee", RiskCategory.OCCUPATION, 0.8, 2),

                // FINANCIAL
                new RiskFactor("RF016", "Low Credit Score", RiskCategory.FINANCIAL, 1.8, 8),
                new RiskFactor("RF017", "Stable Income", RiskCategory.FINANCIAL, 1.0, 2),
                new RiskFactor("RF018", "High Debt", RiskCategory.FINANCIAL, 1.7, 7),
                new RiskFactor("RF019", "Multiple Existing Loans", RiskCategory.FINANCIAL, 1.5, 6),
                new RiskFactor("RF020", "Good Savings History", RiskCategory.FINANCIAL, 0.9, 2)
        );
    }
        public static List<UnderwritingApplication> getApplications() {
            return Arrays.asList(
                    new UnderwritingApplication("A001", "Raj", 30, Arrays.asList(new RiskFactor("F101", "Smoking", RiskCategory.LIFESTYLE, 3.0, 8))),
                    new UnderwritingApplication("A002", "Kumar", 42, Arrays.asList(new RiskFactor("F102", "Alcohol", RiskCategory.LIFESTYLE, 2.0, 6))),
                    new UnderwritingApplication("A003", "Arun", 50, Arrays.asList(new RiskFactor("F103", "Diabetes", RiskCategory.HEALTH, 4.0, 7))),
                    new UnderwritingApplication("A004", "David", 38, Arrays.asList(new RiskFactor("F104", "Smoking", RiskCategory.LIFESTYLE, 3.0, 7), new RiskFactor("F105", "Loan Default", RiskCategory.FINANCIAL, 2.5, 5))),
                    new UnderwritingApplication("A005", "Vikram", 45, Arrays.asList(new RiskFactor("F106", "Pilot Job", RiskCategory.OCCUPATION, 3.5, 6))),
                    new UnderwritingApplication("A006", "Ravi", 55, Arrays.asList(new RiskFactor("F107", "Heart Disease", RiskCategory.HEALTH, 5.0, 9))),
                    new UnderwritingApplication("A007", "Ajay", 29, Arrays.asList(new RiskFactor("F108", "Alcohol", RiskCategory.LIFESTYLE, 2.0, 5), new RiskFactor("F109", "Smoking", RiskCategory.LIFESTYLE, 3.0, 6))),
                    new UnderwritingApplication("A008", "Manoj", 61, Arrays.asList(new RiskFactor("F110", "Diabetes", RiskCategory.HEALTH, 4.0, 8), new RiskFactor("F111", "Heart Disease", RiskCategory.HEALTH, 5.0, 8))),
                    new UnderwritingApplication("A009", "John", 49, Arrays.asList(new RiskFactor("F112", "Loan Default", RiskCategory.FINANCIAL, 2.5, 7), new RiskFactor("F113", "Smoking", RiskCategory.LIFESTYLE, 3.0, 8))),
                    new UnderwritingApplication("A010", "Karthik", 63, Arrays.asList(new RiskFactor("F114", "Heart Disease", RiskCategory.HEALTH, 5.0, 9), new RiskFactor("F115", "Pilot Job", RiskCategory.OCCUPATION, 3.5, 8))),
                    new UnderwritingApplication("A011", "Bala", 35, Arrays.asList(new RiskFactor("F116", "Alcohol", RiskCategory.LIFESTYLE, 2.0, 4))),
                    new UnderwritingApplication("A012", "Suresh", 58, Arrays.asList(new RiskFactor("F117", "Heart Disease", RiskCategory.HEALTH, 5.0, 10), new RiskFactor("F118", "Smoking", RiskCategory.LIFESTYLE, 3.0, 9), new RiskFactor("F119", "Loan Default",RiskCategory.FINANCIAL, 2.5, 8)))
            );
    }
}
*/
