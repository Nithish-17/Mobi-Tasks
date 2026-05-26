package exception;

public class PremiumMismatchException extends PremiumCollectionException{
    private double dueAmount;
    private double collectedAmount;
    public PremiumMismatchException(String message, double dueAmount, double collectedAmount) {
        super(message);
        this.dueAmount = dueAmount;
        this.collectedAmount = collectedAmount;
    }

    public double getDueAmount() {
        return dueAmount;
    }

    public double getCollectedAmount() {
        return collectedAmount;
    }
}
