package model;
public class CryptoOrder {

    private final String orderId;
    private final String walletAddress;
    private final String tradingPair;
    private final String side; // BUY / SELL
    private final double quantity;
    private final double pricePerUnit;

    public CryptoOrder(String orderId, String walletAddress, String tradingPair,
                       String side, double quantity, double pricePerUnit) {
        this.orderId = orderId;
        this.walletAddress = walletAddress;
        this.tradingPair = tradingPair;
        this.side = side;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public String getTradingPair() {
        return tradingPair;
    }

    public String getSide() {
        return side;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }
}
