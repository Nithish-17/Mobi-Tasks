package main;
import model.CryptoOrder;
import processor.OrderProcessor;
import java.util.ArrayList;
import java.util.List;
public class Main {
     static void main() {
        List<CryptoOrder> orders = new ArrayList<>();
        orders.add(new CryptoOrder("O1", "0xABC", "BTC/USDT", "BUY", 0.01, 30000));
        orders.add(new CryptoOrder("O2", "ABC", "ETH/USDT", "BUY", 0.01, 2000));
        orders.add(new CryptoOrder("O3", "0x123", "ETH/USDT", "BUY", 0.0005, 2000));
        orders.add(new CryptoOrder("O4", "0x123", "ETH/USDT", "BUY", 5, 2000));
        orders.add(new CryptoOrder("O5", "0x123", "ETH/USDT", "BUY", 0.01, 2000));
        orders.add(new CryptoOrder("O6", "0x123", "ETH/USDT", "SELL", 1, 2000));
        orders.add(new CryptoOrder("O7", "0xABC", "ETH/USDT", "BUY", 0.02, 2000));
        orders.add(new CryptoOrder("O8", "0x999", "ETH/USDT", "BUY", 1, 2000));
        OrderProcessor processor = new OrderProcessor();
        processor.processOrders(orders);
    }
}
