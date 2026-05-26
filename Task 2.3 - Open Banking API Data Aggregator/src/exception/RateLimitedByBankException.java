package exception;

public class RateLimitedByBankException extends BankAPIException {
    int retryAfterSeconds;

    public RateLimitedByBankException(String message, int seconds) {
        super(message);
        this.retryAfterSeconds = seconds;
    }

    public int getRetryAfterSeconds() {
        return retryAfterSeconds;
    }
}
