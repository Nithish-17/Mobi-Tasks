package model;

import java.util.List;

public class UnderwritingApplication {
    private String applicationId;
    private String applicationName;
    private int age;
    private List<RiskFactor> factors;
    private int totalScore;
    private String decision;

    public String getApplicationId() {
        return applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public int getAge() {
        return age;
    }

    public List<RiskFactor> getFactors() {
        return factors;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public String getDecision() {
        return decision;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void     setDecision(String decision) {
        this.decision = decision;
    }

    public UnderwritingApplication(String applicationId, String applicationName, int age, List<RiskFactor> factors) {
        this.applicationId = applicationId;
        this.applicationName = applicationName;
        this.age = age;
        this.factors = factors;
    }
}