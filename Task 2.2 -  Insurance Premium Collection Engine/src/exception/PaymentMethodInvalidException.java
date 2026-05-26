package exception;

public class PaymentMethodInvalidException extends PremiumCollectionException{
    public PaymentMethodInvalidException(String message) {
        super(message);
    }
}
