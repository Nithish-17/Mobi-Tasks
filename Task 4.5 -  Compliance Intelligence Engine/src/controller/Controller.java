package controller;

import model.ComplainceDossier;
import service.ComplianceIntelligenceEngineImpl;
import util.FilePaths;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Controller {
    public void run(){

        ComplianceIntelligenceEngineImpl complianceService = new ComplianceIntelligenceEngineImpl();

        try
        {
            complianceService.buildCrossFileIndex();

            ComplainceDossier dossier = complianceService.getEntityDossier("HDFC");
            System.out.println(dossier);

            Map<String, List<String>> searchResults =complianceService.searchAllFiles("SHELL_COMPANY_INC|OFFSHORE_GROUP");

            for(Map.Entry<String, List<String>> entry : searchResults.entrySet()) {

                System.out.println(entry.getKey() + " -> " + entry.getValue().size() + " matches");

            }

            List<String> exposures = complianceService.detectUnreportedExposures(10000000);

            for(String exposure : exposures) {

                System.out.println(exposure);
            }

            complianceService.generateRegulatoryResponse("HDFC","QUERY-REF-001",FilePaths.getOutputFilePath("regulatory_response.txt"));

            complianceService.archiveToComplianceVault(Arrays.asList("HDFC","SBI","ICICI"),FilePaths.getOutputFilePath("vault.db"));
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
