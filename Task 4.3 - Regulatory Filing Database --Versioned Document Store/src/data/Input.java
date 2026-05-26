package data;

import model.RawInput;

import java.util.List;


public class Input {

    public static List<RawInput> getRawInputs(){

        return List.of(

                new RawInput("HDFC", "CRAR", "Q1-2024"),
                new RawInput("HDFC", "NPA", "Q1-2024"),
                new RawInput("SBI", "AML", "Q1-2024"),
                new RawInput("ICICI", "CYBER", "Q2-2024"),
                new RawInput("AXIS", "RISK", "Q2-2024"),
                new RawInput("HDFC", "CRAR", "Q1-2024"),   // Amendment
                new RawInput("SBI", "AML", "Q1-2024"),     // Amendment
                new RawInput("ICICI", "CYBER", "Q2-2024"), // Amendment
                new RawInput("HDFC", "NPA", "Q1-2024"),    // Amendment
                new RawInput("AXIS", "RISK", "Q2-2024")    // Amendment

        );

    }

}
