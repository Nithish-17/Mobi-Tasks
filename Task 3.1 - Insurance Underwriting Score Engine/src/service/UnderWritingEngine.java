package service;

import model.RiskFactor;
import model.UnderwritingApplication;
import model.UnderwritingReport;

import java.util.List;

public interface UnderWritingEngine {
    void registerFactors(List<RiskFactor> factors);
    void submitApplications(UnderwritingApplication app);
    List<UnderwritingApplication>  getHighRiskApplications();
    UnderwritingReport makeReport();



}
