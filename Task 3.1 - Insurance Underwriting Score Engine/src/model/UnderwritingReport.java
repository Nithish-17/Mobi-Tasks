package model;

import java.util.Map;

public class UnderwritingReport {
    private int standard;
    private int loaded;
    private int declined;
    private Map<String,Double> averageScore;
    public UnderwritingReport(int standard, int loaded, int declined, Map<String,Double> averageScore) {
        this.standard = standard;
        this.loaded = loaded;
        this.declined = declined;
        this.averageScore = averageScore;
    }

    public int getStandard() {
        return standard;
    }

    public int getLoaded() {
        return loaded;
    }

    public int getDeclined() {
        return declined;
    }

    public Map<String, Double> getAverageScore() {
        return averageScore;
    }
}
