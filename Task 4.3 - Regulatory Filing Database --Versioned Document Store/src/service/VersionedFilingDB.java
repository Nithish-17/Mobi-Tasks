package service;

import exception.RegulatoryException;
import model.Filing;

import java.io.IOException;
import java.util.List;

public interface VersionedFilingDB {
    void submitFiling(Filing f) throws RegulatoryException, IOException;
    void amendFiling(String filingId, Filing amended) throws RegulatoryException, IOException;
    Filing getCurrentVersion(String baseId) throws RegulatoryException;
    List<Filing> getFilingHistory(String baseId) throws RegulatoryException;
    List<Filing> searchByPeriodAndType(String period, String type) throws RegulatoryException;
    void generateAuditTrail(String entityId) throws RegulatoryException, IOException;
}
