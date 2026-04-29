public class LimitOrder extends Order {
    double limitPrice;
    String type;
    @Override
    boolean canFill(double marketPrice) {
        if(type.equals("BUY"))
            return marketPrice <= limitPrice;
        else
            return marketPrice >= limitPrice;

    }
    public LimitOrder(String orderId, String stockSymbol, int quantity,double limitPrice, String type) {
        super(orderId, stockSymbol, quantity);
        this.limitPrice = limitPrice;
        this.type = type.toUpperCase();
    }
    @Override
    public String toString() {
        return "LimitOrder" + getStockSymbol() + " " + type + " " + getQuantity();
    }
}
