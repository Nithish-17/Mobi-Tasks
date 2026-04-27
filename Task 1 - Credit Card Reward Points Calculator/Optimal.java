import java.util.Scanner;

public class Optimal {

    enum CardType{
        CLASSIC(2,3,1,1),
        GOLD(4,5,2,2),
        PLATINUM(8,10,4,3);

        final int dining;
        final int tarvel;
        final int grocery;
        final int other;
        CardType(int dining, int travel, int grocery, int other){
            this.dining = dining;
            this.tarvel = travel;
            this.grocery = grocery;
            this.other = other;
        }
        int getRate(PurchaseCategory category){
            return switch (category) {
                case DINING -> dining;
                case TRAVEL -> tarvel;
                case GROCERY -> grocery;
                case OTHER -> other;
            };
        }
    }
    enum PurchaseCategory{
        DINING,
        TRAVEL,
        GROCERY,
        OTHER;
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
        int pointsEarned = (int)(spendingAmount/100) * card.getRate(category);
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
