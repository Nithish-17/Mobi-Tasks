public class MarketOrder extends Order {
    String type;
    @Override
    boolean canFill(double marketPrice) {
        return true;
    }

    public MarketOrder(String orderId, String stockSymbol, int quantity, String type) {
        super(orderId, stockSymbol, quantity);
        this.type = type.toUpperCase();
    }


    @Override
    public String toString() {
        return "MarketOrder " + getStockSymbol() + " " + type + " " + getQuantity();
    }
}
