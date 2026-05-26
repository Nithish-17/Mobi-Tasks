package exception;

public class TokenExpiredException extends BankAPIException {
    public TokenExpiredException(String message) {
        super(message);
    }
}