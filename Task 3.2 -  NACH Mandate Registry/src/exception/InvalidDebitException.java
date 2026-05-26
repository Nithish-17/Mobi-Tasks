package exception;

public class InvalidDebitException extends NACHException {
    public InvalidDebitException(String message) {
        super(message);
    }
}
