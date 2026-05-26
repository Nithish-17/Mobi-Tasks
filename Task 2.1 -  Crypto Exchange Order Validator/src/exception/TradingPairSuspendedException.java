package exception;

public class TradingPairSuspendedException extends CryptoOrderException{
    private final String paircode;
    private final String resumeTime;
    public TradingPairSuspendedException(String message, String pairCode, String resumeTime) {
        super(message);
        this.paircode = pairCode;
        this.resumeTime = resumeTime;
    }

    public String getPaircode() {
        return paircode;
    }

    public String getResumeTime() {
        return resumeTime;
    }
}
