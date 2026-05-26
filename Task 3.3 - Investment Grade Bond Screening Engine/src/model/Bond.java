package model;

public class Bond {
    private String isin;
    private String issuerName;
    private Sector sector;
    private Rating rating;

    private double yieldToMaturity;
    private double duration;
    private double faceValue;
    private double outstandingAmount;


    public Bond(String isin, String issuerName, Sector sector, Rating rating, double yieldToMaturity, double duration, double faceValue, double outstandingAmount) {
        this.isin = isin;
        this.issuerName = issuerName;
        this.sector = sector;
        this.rating = rating;
        this.yieldToMaturity = yieldToMaturity;
        this.duration = duration;
        this.faceValue = faceValue;
        this.outstandingAmount = outstandingAmount;
    }

    public String getIsin() {
        return isin;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public Sector getSector() {
        return sector;
    }

    public Rating getRating() {
        return rating;
    }

    public double getYieldToMaturity() {
        return yieldToMaturity;
    }

    public double getDuration() {
        return duration;
    }

    public double getFaceValue() {
        return faceValue;
    }

    public double getOutstandingAmount() {
        return outstandingAmount;
    }
    @Override
    public String toString() {
        return issuerName + " :" + yieldToMaturity + "% YTM";
    }
}
