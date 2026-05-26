package exception;

public class InvalidRiskFactorException extends RuntimeException{
    public InvalidRiskFactorException(String message) {
        super(message);
    }
}
