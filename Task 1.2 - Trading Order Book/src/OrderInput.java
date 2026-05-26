import java.util.Scanner;

public class OrderInput {
    String orderId;
    String stockSymbol;
    int quantity;
    double limit;
    String type;
    public void getInput(Scanner scanner,int option){
        System.out.print("Enter orderId : ");
        orderId = scanner.nextLine();
        System.out.print("Enter stockSymbol : ");
        stockSymbol = scanner.nextLine();
        System.out.print("Enter quantity : ");
        quantity = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter type (BUY or SELL) : ");
        type = scanner.nextLine().toUpperCase();
        if(option == 2 || option == 3) {
            System.out.print("Enter stock limit( 1 for marked order) : ");
            limit = scanner.nextDouble();
        }
    }

}
