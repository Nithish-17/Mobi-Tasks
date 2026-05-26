package controller;

import data.Input;
import exception.RegulatoryException;
import model.Filing;
import model.RawInput;
import service.ValidateInputs;
import service.VersionedFilingDBImplementation;
import util.Output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    VersionedFilingDBImplementation implementationService = new VersionedFilingDBImplementation();

    public void run(){

            List<RawInput> inputs = Input.getRawInputs();

            List<Filing> validatedInputs = ValidateInputs.validate(inputs);

            for(Filing f : validatedInputs){

                try {
                    implementationService.submitFiling(f);
                }


                catch(RegulatoryException | IOException e){
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }

            }

            try {
                Output.printCurrentVersion(implementationService.getCurrentVersion("HDFC_NPA"));
                Output.printHistory(implementationService.getFilingHistory("HDFC_NPA"));
                Output.printFiling(implementationService.searchByPeriodAndType("Q1-2024", "NPA"));
                implementationService.generateAuditTrail("HDFC");
            }

            catch(RegulatoryException | IOException e){

                System.out.println(e.getMessage());
            }

    }
}
