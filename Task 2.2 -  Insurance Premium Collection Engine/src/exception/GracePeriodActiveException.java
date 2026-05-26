package exception;

import java.time.LocalDate;

public class GracePeriodActiveException extends PremiumCollectionException {
    private LocalDate graceEndDate;
    public GracePeriodActiveException(String message, LocalDate graceEndDate) {
        super(message);
        this.graceEndDate = graceEndDate;
    }

    public LocalDate getGraceEndDate() {
        return graceEndDate;
    }
}
