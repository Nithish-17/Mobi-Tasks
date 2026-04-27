public class StopLossOrder extends Order{
    String type;
    double stopPrice;
    @Override
    boolean canFill(double marketPrice) {
        if(type.equals("BUY")){
            if(marketPrice >= stopPrice){
//                System.out.println("warning stock purchased to avoid loss");
                return true;
            }
            return false;
        }
        else{
            if(marketPrice <= stopPrice){
//                System.out.println("warning stock sold to avoid loss");
                return true;
            }
            return false;
        }
    }
    public StopLossOrder(String orderId, String stockSymbol, int quantity, double stopPrice, String type) {
        super(orderId, stockSymbol, quantity);
        this.stopPrice = stopPrice;
        this.type = type.toUpperCase();
    }
    @Override
    public String toString() {
        return "StopLossOrder" + getStockSymbol() + " " + type + " " + getQuantity();
    }
}
