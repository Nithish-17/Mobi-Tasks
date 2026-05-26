package util;

import model.Filing;

import java.util.List;

public class Output {

    public static void printCurrentVersion(Filing f){
        System.out.println("Current " + f.getEntityId() + "_" + f.getFilingType() + " : " + f.getVersion() + " " + f.getFilingId());
    }

    public static void printHistory(List<Filing> filings){

        for(Filing f : filings){
            System.out.print(f.getVersion() + " " + f.getStatus() + " | ");
        }

    }

    public static void printFiling(List<Filing> filings){
        for(Filing f : filings){
            System.out.println(f);
        }
    }

}
