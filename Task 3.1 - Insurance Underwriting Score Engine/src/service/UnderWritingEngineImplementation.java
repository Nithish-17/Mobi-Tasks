package service;

import model.RiskFactor;
import model.UnderwritingApplication;
import model.UnderwritingReport;
import exception.*;
import java.util.*;

public class UnderWritingEngineImplementation implements UnderWritingEngine {

    HashMap<String, List<RiskFactor>> factorsByCategory; //category wise mapping of factors

    LinkedHashMap<String, UnderwritingApplication> applications; //to maintain the application by insertion order

    TreeMap<Integer, List<UnderwritingApplication>> byScore; //score wise application sorting

    HashMap<String, Double> categoryWeightMap; //category wise total weight contribution

    PriorityQueue<UnderwritingApplication> reviewQueue; //highest risk first


    public UnderWritingEngineImplementation() {
        factorsByCategory = new HashMap<>();
        applications = new LinkedHashMap<>();
        byScore = new TreeMap<>();
        categoryWeightMap = new HashMap<>();
        reviewQueue = new PriorityQueue<>((a, b) -> Integer.compare(b.getTotalScore(), a.getTotalScore()));
    }

    @Override
    public void registerFactors(List<RiskFactor> factors) {
        for (RiskFactor factor : factors) {
            //factorsByCategory and categoryWeightMap
            factorsByCategory.computeIfAbsent(factor.getCategory().name(), k -> new ArrayList<>()).add(factor);
            categoryWeightMap.put(factor.getCategory().name(), categoryWeightMap.getOrDefault(factor.getCategory().name(), 0.0) + factor.getWeight());
        }
    }

    @Override
    public void submitApplications(UnderwritingApplication app) {
        validateApplication(app);
        int score = computeScore(app);
        app.setTotalScore(score);
        applications.put(app.getApplicationId(), app);
        byScore.computeIfAbsent(score, k -> new ArrayList<>()).add(app);
        reviewQueue.offer(app);
        approveWithLoading(app.getApplicationId());

    }

    private int computeScore(UnderwritingApplication app) {
        double totalScore = 0;
        for (RiskFactor factor : app.getFactors()) {
            totalScore += factor.getWeight() * factor.getScore();
        }
        return (int) totalScore;
    }

    private static final int STANDARD_LIMIT = 40;
    private static final int DECLINE_LIMIT = 70;

    private void approveWithLoading(String appId) {
        UnderwritingApplication app =
                applications.get(appId);

        int score = app.getTotalScore();

        if (score < STANDARD_LIMIT) {
            app.setDecision(
                    "APPROVED (Standard Rate)"
            );
        } else if (score < DECLINE_LIMIT) {
            app.setDecision(
                    "APPROVED (+25% Loading)"
            );

        } else {
            app.setDecision(
                    "DECLINED (Score >= 70, High Risk)"
            );
        }
    }

    @Override
    public List<UnderwritingApplication> getHighRiskApplications() {
        List<UnderwritingApplication> highRiskApplications = new ArrayList<>();

        NavigableMap<Integer, List<UnderwritingApplication>> tail = byScore.tailMap(70, true);

        for (List<UnderwritingApplication> apps : tail.values()) {
            highRiskApplications.addAll(apps);
        }

        return highRiskApplications;

    }


    public PriorityQueue<UnderwritingApplication> getReviewQueue() {
        return new PriorityQueue<>(reviewQueue); // internal queue should not be modified
    }


    @Override
    public UnderwritingReport makeReport() {
        int standard = 0;
        int loaded = 0;
        int declined = 0;
        Map<String, Integer> totalScores = new HashMap<>();
        Map<String, Integer> totalRisk = new HashMap<>();
        for (UnderwritingApplication app : applications.values()) {
            if (app.getTotalScore() < 40) {
                standard++;
            } else if (app.getTotalScore() < 70) {
                loaded++;
            } else
                declined++;
        }
        for (UnderwritingApplication app : applications.values()) {
            List<RiskFactor> riskFactors = app.getFactors();
            for (RiskFactor factor : riskFactors) {
                totalScores.put(factor.getCategory().name(), totalScores.getOrDefault(factor.getCategory().name(), 0) + factor.getScore());
                totalRisk.put(factor.getCategory().name(), totalRisk.getOrDefault(factor.getCategory().name(), 0) + 1);
            }
        }

        Map<String, Double> averageScores = new HashMap<>();
        for (String category : totalScores.keySet()) {
            double average = (double) totalScores.get(category) / totalRisk.get(category);
            averageScores.put(category, average);
        }

        return new UnderwritingReport(standard, loaded, declined, averageScores);

    }

    private void validateApplication(UnderwritingApplication application) {
        if (applications.containsKey(application.getApplicationId())) {
            throw new DuplicateApplicationException(
                    "Duplicate Application ID: " +
                            application.getApplicationId()
            );
        }

        if (application == null) {
            throw new InvalidApplicationException("Application cannot be null");
        }

        if (application.getApplicationId() == null
                || application.getApplicationId().isBlank()) {
            throw new InvalidApplicationException("Invalid Application ID");
        }

        if (application.getApplicationName() == null
                || application.getApplicationName().isBlank()) {
            throw new InvalidApplicationException("Invalid Applicant Name");
        }

        if (application.getAge() <= 0) {
            throw new InvalidApplicationException("Invalid Applicant Age");
        }

        if (application.getFactors() == null
                || application.getFactors().isEmpty()) {
            throw new InvalidApplicationException(
                    "Application must contain risk factors"
            );
        }
    }

    private void validateFactors(List<RiskFactor> factors) {
        for (RiskFactor factor : factors) {
            if (factor.getWeight() <= 0) {
                throw new InvalidRiskFactorException("Weight cannot be negative");
            }
            if (factor.getScore() < 0 || factor.getScore() > 10) {
                throw new InvalidRiskFactorException("Score must be between 0 and 10");
            }
        }
    }
}
