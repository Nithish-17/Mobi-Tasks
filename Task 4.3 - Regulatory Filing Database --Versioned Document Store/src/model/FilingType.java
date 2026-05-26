package model;


public enum FilingType {

    CRAR("Capital Adequacy Ratio Report"),
    NPA("Non-Performing Assets Report"),
    AML("Anti-Money Laundering Compliance"),
    KYC("Know Your Customer Compliance"),
    BASEL("Basel Regulatory Compliance"),
    LIQUIDITY("Liquidity Coverage Report"),
    RISK("Risk Assessment Filing"),
    AUDIT("Internal Audit Filing"),
    FRAUD("Fraud Monitoring Report"),
    CYBER("Cybersecurity Compliance Report");

    private final String description;

    FilingType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}