package data;

import model.NACHDebitFrequency;
import model.NACHMandate;
import java.util.ArrayList;
import java.util.List;

public class MandateData {
    public static List<NACHMandate> getMandates() {

        List<NACHMandate> mandates = new ArrayList<>();

        for (int i = 1; i <= 15; i++) {
            double maxDebitAmount = i <= 3 ? 10000 : 3000;
            String endDate = (i == 6 || i == 7)
                    ? "2024-06-15"
                    : "2027-06-15";
            mandates.add(
                    new NACHMandate(
                            String.format("UMRN%03d",i),
                            "CUS" + i,
                            "ACC" + ((i % 5) + 1),
                            "MIFR00" + i,
                            maxDebitAmount,
                            (i % 2 == 0) ? NACHDebitFrequency.MONTHLY : NACHDebitFrequency.QUARTERLY,
                            "2026-01-01",
                            endDate
                            )
                    );
        }
        return mandates;
    }
}
