abstract class Order {
    // -- ask why private
    private final String orderId;
    private final String stockSymbol;
    private final int quantity;
    private double pricePerShare;
    private String status = "OPEN";
    private double executionPrice;

    public String getOrderId() {
        return orderId;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePerShare() {
        return pricePerShare;
    }

    public String getStatus() {
        return status;
    }

    public double getExecutionPrice() {
        return executionPrice;
    }

    abstract boolean canFill(double marketPrice);
    void fill(double marketPrice){
        status = "FILLED";
        executionPrice = marketPrice;
    }

    public Order(String orderId, String stockSymbol, int quantity) {
        this.orderId = orderId;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
    }
}
