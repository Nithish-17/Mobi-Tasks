import java.util.Scanner;

public class Main {
    static void main() {
        int option;
        String orderId;
        String stockSymbol;
        int quantity;
        double limit;
        String type;
        Scanner scanner = new Scanner(System.in);
        OrderBook ob = new OrderBook();
        while (true){
            System.out.print("Enter 1 to enter Market Order : \n" +
                    "Enter 2 to enter Limit Order : \n" +
                    "Enter 3 to enter StopLossOrder : \n" +
                    "Enter 4 to process order : \n" +
                    "Enter option : ");

            option = scanner.nextInt();
            scanner.nextLine();
            if(option == 4) break;
            switch (option){
                case 1 -> {
                    OrderInput in = new OrderInput();
                    in.getInput(scanner,option);
                    if(Validator.getValidation(in,option)) continue;
                    ob.addOrder(new MarketOrder(in.orderId, in.stockSymbol, in.quantity, in.type));
                }
                case 2 -> {
                    OrderInput in = new OrderInput();
                    in.getInput(scanner,option);
                    if(Validator.getValidation(in,option)) continue;
                    ob.addOrder(new LimitOrder(in.orderId, in.stockSymbol, in.quantity,in.limit, in.type));
                }
                case 3 -> {
                    OrderInput in = new OrderInput();
                    in.getInput(scanner,option);
                    if(Validator.getValidation(in,option)) continue;
                    ob.addOrder(new StopLossOrder(in.orderId, in.stockSymbol, in.quantity,in.limit, in.type));
                }
                default -> System.out.println("Wrong option retry ");
            }
        }

        while (true) {
            System.out.print("Enter 1 to process order with current market value : \n" +
                    "Enter 2 to exit : \n"+
                    "Enter option : ");
            option = scanner.nextInt();
            if(option == 2) break;
            if (option == 1) {
                System.out.println("Enter the market price : ");
                double marketPrice = scanner.nextDouble();
                ob.processOrders(marketPrice);
            } else {
                System.out.println("Wrong option retry : ");
            }
        }
        ob.unFilled();
    }
}
