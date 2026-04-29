package exception;

public class InvalidWalletException extends CryptoOrderException{
    public InvalidWalletException(String message) {
        super(message);
    }
}
