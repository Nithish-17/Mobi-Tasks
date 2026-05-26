package exception;

public class DuplicateMandateException extends NACHException {
    public DuplicateMandateException(String message) {
        super(message);
    }
}
