package exception;

public class MandateAlreadyRevokedException extends NACHException {
    public MandateAlreadyRevokedException(String message) {
        super(message);
    }
}
