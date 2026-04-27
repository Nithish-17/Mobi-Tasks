import java.util.ArrayList;
import java.util.List;
public class OrderBook {
    List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    void processOrders(double marketPrice) {
        int count = 0;
        System.out.println("@ price " + marketPrice + ":");
        for (Order o :orders) {
           if(o.getStatus().equals("OPEN") &&  o.canFill(marketPrice)){
                o.fill(marketPrice);
                count++;
                System.out.println(count +"(" + o + ")" + " -> FILLED");
            }
        }
    }

    void unFilled(){
        int unFilledCount = 0;
        for(Order o : orders)
            if(o.getStatus().equals("OPEN"))
                unFilledCount++;
        System.out.println("Remaining OPEN : " + unFilledCount + " orders");
    }

}
