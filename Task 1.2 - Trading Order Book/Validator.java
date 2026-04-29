public class Validator {
    public static boolean getValidation(OrderInput in, int option){
        boolean flag = false;
        if(in.quantity <= 0) {
            System.out.println("please enter valid quantity");
            flag = true;
        }
        if(!in.type.equals("BUY") && !in.type.equals("SELL")){
            System.out.println("please enter valid type BUY OR SELL");
            flag = true;
        }

        if(option == 2 || option == 3){
            if(in.limit <= 0){
                System.out.println("Enter valid positive limit");
                flag = true;
            }
        }
        return flag;
    }
}
