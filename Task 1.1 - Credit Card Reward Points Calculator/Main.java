import java.util.Scanner;

class AmountValidation extends RuntimeException{
    public AmountValidation(String message){
        super(message);
    }
}

public class Main {

    enum CardType{
        CLASSIC,
        GOLD,
        PLATINUM;
    }

    enum PurchaseCategory{
        DINING,
        TRAVEL,
        GROCERY,
        OTHER;
    }

    static int rate(CardType card,PurchaseCategory category){
        switch (card){
            case CLASSIC -> {
                switch (category){
                    case DINING : return 2;
                    case TRAVEL : return 3;
                    case GROCERY: return 1;
                    case OTHER  : return 1;
                }
            }
            case GOLD -> {
                switch (category){
                    case DINING : return 4;
                    case TRAVEL : return 5;
                    case GROCERY: return 2;
                    case OTHER  : return 2;
                }
            }
            case PLATINUM -> {
                switch (category){
                    case DINING : return 8;
                    case TRAVEL : return 10;
                    case GROCERY: return 4;
                    case OTHER  : return 3;
                }
            }
        }
        return 0;
    }

    static void main() {
        CardType card;
        PurchaseCategory category;
        double spendingAmount;
        int runningTotal;
        int newTotal;
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Card type : ");
            card = CardType.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Category : ");
            category = PurchaseCategory.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Spend amount : ");
            spendingAmount = scanner.nextDouble();
            if (spendingAmount <= 0)
                throw new AmountValidation("Amount is 0");
            System.out.print("Running total : ");
            runningTotal = scanner.nextInt();
        }
        catch (IllegalArgumentException e){
            System.out.println("card type or category not found try again");
            return;
        }
        catch (AmountValidation e){
            System.out.println(e.getMessage());
            return;
        }
        int pointsEarned = (int)(spendingAmount/100) * rate(card,category);
        System.out.println("Points earned : " + pointsEarned);
        newTotal = runningTotal + pointsEarned;
        System.out.println("New total : " + newTotal);

        if(newTotal > 5000){
            newTotal = 5000;
            System.out.println("Warning: monthly cap reached : 5000");
        }
        else
            System.out.println("Warning: Approaching monthly cal of 5000 points");
    }
}
