package model;

import jdk.jfr.Category;

public class RiskFactor {
    private String factorId;
    private String factorName;
    private RiskCategory category;
    private double weight;
    private int score;

    public RiskFactor(String factorId, String factorName, RiskCategory category, double weight, int score) {
        this.factorId = factorId;
        this.factorName = factorName;
        this.category = category;
        this.weight = weight;
        this.score = score;
    }

    public String getFactorId() {
        return factorId;
    }

    public String getFactorName() {
        return factorName;
    }

    public RiskCategory getCategory() {
        return category;
    }

    public double getWeight() {
        return weight;
    }

    public int getScore() {
        return score;
    }
}
