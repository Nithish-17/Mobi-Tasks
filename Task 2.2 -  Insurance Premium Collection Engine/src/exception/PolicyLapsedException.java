package exception;

import java.time.LocalDate;

public class PolicyLapsedException extends PremiumCollectionException{
    private LocalDate lapseDate;
    public PolicyLapsedException(String message, LocalDate lapseDate) {
        super(message);
        this.lapseDate = lapseDate;
    }

    public LocalDate getLapseDate() {
        return lapseDate;
    }
}
