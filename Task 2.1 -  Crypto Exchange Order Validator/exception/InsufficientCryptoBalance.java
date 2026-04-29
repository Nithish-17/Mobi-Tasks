package exception;

public class InsufficientCryptoBalance extends CryptoOrderException{
    private final String symbol;
    private final double requiredAmount;
    private final double avaliableAmount;
    public InsufficientCryptoBalance(String message, String symbol, double requiredAmount, double avaliableAmount) {
        super(message);
        this.symbol = symbol;
        this.requiredAmount = requiredAmount;
        this.avaliableAmount = avaliableAmount;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getRequiredAmount() {
        return requiredAmount;
    }

    public double getAvaliableAmount() {
        return avaliableAmount;
    }
}
