package model;

public enum Entity {
    HDFC("HDFC Bank"),
    SBI("State Bank of India"),
    ICICI("ICICI Bank"),
    AXIS("Axis Bank"),
    KOTAK("Kotak Mahindra Bank"),
    PNB("Punjab National Bank"),
    CANARA("Canara Bank"),
    BOB("Bank of Baroda"),
    YESBANK("Yes Bank"),
    INDUSIND("IndusInd Bank");

    private final String fullName;

    Entity(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
