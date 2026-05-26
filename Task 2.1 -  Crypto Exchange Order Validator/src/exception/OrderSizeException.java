package exception;

public class OrderSizeException extends CryptoOrderException{
    public OrderSizeException(String message) {
        super(message);
    }
}
