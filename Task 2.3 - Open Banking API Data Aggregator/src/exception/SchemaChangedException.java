package exception;

public class SchemaChangedException extends BankAPIException {
    public SchemaChangedException(String message) {
        super(message);
    }
}