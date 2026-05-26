package exception;

import model.AccountSummary;
import java.util.List;

public class PartialDataException extends BankAPIException {
    List<AccountSummary> fetched;
    List<String> failedIds;

    public PartialDataException(String message,List<AccountSummary> fetched, List<String> failedIds) {
        super(message);
        this.fetched = fetched;
        this.failedIds = failedIds;
    }

    public List<AccountSummary> getFetched() {
        return fetched;
    }

    public List<String> getFailedIds() {
        return failedIds;
    }
}
