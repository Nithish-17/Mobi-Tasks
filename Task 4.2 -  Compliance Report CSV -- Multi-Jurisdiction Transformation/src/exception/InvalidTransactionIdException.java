package exception;

public class InvalidTransactionIdException extends ComplianceException {
    public InvalidTransactionIdException(String message) {
        super(message);
    }
}
