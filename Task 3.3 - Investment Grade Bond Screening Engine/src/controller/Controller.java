package controller;

import data.BondData;
import model.Bond;
import model.Sector;
import service.BondScreeningEngine;
import service.BondScreeningEngineImplementation;

import java.util.List;

public class Controller {

    public void run() {

        BondScreeningEngineImplementation engine =new BondScreeningEngineImplementation();

        List<Bond> bonds = BondData.generateBonds();
        for (Bond bond : bonds) {
            try {
                engine.loadBond(bond);
            }
            catch (Exception e) {
                System.out.println("Failed To Load Bond : "+ e.getMessage());
            }
        }

        try {
            List<Bond> screen1 = engine.screenBonds("A", 7, 9, 5);
            System.out.printf("\nScreen 1 (A-rated, 7-9%%, dur<=5): %d bonds\n", screen1.size());
            engine.generateScreeningReport(screen1);
        }

        catch (Exception e) {
            System.out.println("Screen 1 Failed : " + e.getMessage());
        }

        try {
            List<Bond> screen2 = engine.screenBonds("BBB",9,12, 8);
            System.out.printf("\nScreen 2 (BBB-rated, 9-12%%, dur<=8): %d bonds\n",screen2.size());
            engine.generateScreeningReport(screen2);
        }

        catch (Exception e) {
            System.out.println("Screen 2 Failed : "+ e.getMessage());
        }


        String[] watchlistIsins = {
                "IN0001",
                "IN0002",
                "IN0011",
                "IN0016",
                "IN0021",
                "IN0024",
                "IN0029",
        };

        for (String isin : watchlistIsins) {
            try {
                engine.addToWatchlist(isin);
            }

            catch (Exception e) {
                System.out.println("Watchlist Add Failed : " + e.getMessage());
            }
        }

        try {

            System.out.println("\n========== WATCHLIST ==========");
            List<Bond> watchlist = engine.getWatchlistSnapshot();
            for (Bond bond : watchlist) {
                System.out.println(bond.getIsin());
            }

        }

        catch (Exception e) {
            System.out.println("Watchlist Snapshot Failed : "+ e.getMessage());
        }
        int toplimitedBankCount = 3;
        Sector sector = Sector.BANKING;
        System.out.println("\nTop " + toplimitedBankCount + " " + sector.name() + " bonds by yeilds : ");
        List<Bond> topNBonds = engine.getTopYieldingBonds(toplimitedBankCount,sector.name());
        for (Bond bond : topNBonds) {
            System.out.println(bond);
        }
    }
}