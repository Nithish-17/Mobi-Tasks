package exception;

public class BankAPIException extends Exception {
    public BankAPIException(String msg) {
        super(msg);
    }
}