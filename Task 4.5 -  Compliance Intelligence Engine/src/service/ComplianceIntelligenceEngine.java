package service;

import model.ComplainceDossier;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ComplianceIntelligenceEngine {

    void buildCrossFileIndex() throws Exception;

    ComplainceDossier getEntityDossier(String entityId);

    Map<String, List<String>> searchAllFiles(String regex) throws IOException;

    List<String> detectUnreportedExposures(double threshold) throws IOException;

    void generateRegulatoryResponse(String entityId,
                                    String queryRef,
                                    String outputPath) throws IOException;

    void archiveToComplianceVault(List<String> entityIds,
                                  String vaultPath) throws IOException;
}