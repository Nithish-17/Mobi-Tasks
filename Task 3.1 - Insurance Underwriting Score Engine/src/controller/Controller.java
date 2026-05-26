package controller;

import data.ApplicationDbWriter;
import data.DatabaseReader;
import java.util.Map;
import java.util.PriorityQueue;

import model.UnderwritingApplication;
import model.UnderwritingReport;
import service.UnderWritingEngineImplementation;

public class Controller {

    UnderWritingEngineImplementation engine = new UnderWritingEngineImplementation();

    public void run() {
        engine.registerFactors(DatabaseReader.getAllFactors());

        for(UnderwritingApplication app : DatabaseReader.getApplications()) {
            engine.submitApplications(app);
            ApplicationDbWriter.saveApplication(app);
            System.out.printf("%s: score=%d -> %s\n",app.getApplicationId(),app.getTotalScore(),app.getDecision());
        }
        System.out.println();
        PriorityQueue<UnderwritingApplication> reviewQueue = engine.getReviewQueue();
        System.out.print("Review Queue (highest risk first): ");
        for(UnderwritingApplication app : reviewQueue){
            System.out.printf("%s(%s) | ",app.getApplicationId(),app.getTotalScore());
        }
        System.out.println("\n");

        UnderwritingReport report = engine.makeReport();
        System.out.println("Underwriting Report:");
        System.out.printf("Score 0-39  (standard) : %d applications \n",report.getStandard());
        System.out.printf("Score 40-69 (loaded)   : %d applications \n",report.getLoaded());
        System.out.printf("Score 70+   (declined) : %d applications \n",report.getDeclined());
        System.out.println();

        for(Map.Entry<String,Double> entry : report.getAverageScore().entrySet()){
            System.out.printf("average score of %-15s : %.2f%n", entry.getKey(),entry.getValue());
        }
    }


}
