package service;

import exception.*;
import model.Bond;

import java.util.*;

public class BondScreeningEngineImplementation implements BondScreeningEngine {
    HashMap<String, Bond> universe;
    HashMap<String, List<Bond>> bySector;
    TreeMap<Double, List<Bond>> byYield;
    LinkedHashSet<String> watchlist;
    HashMap<String, Integer> ratingRank;

    public BondScreeningEngineImplementation() {
        universe = new HashMap<>();
        bySector = new HashMap<>();
        byYield = new TreeMap<>();
        ratingRank = new HashMap<>();
        watchlist = new LinkedHashSet<>();
        ratingRank = new HashMap<>();

        ratingRank.put("AAA", 1);
        ratingRank.put("AA", 2);
        ratingRank.put("A", 3);
        ratingRank.put("BBB", 4);
        ratingRank.put("BB", 5);
    }


    @Override
    public void loadBond(Bond b) throws BondException {
        if(b == null)
            throw new BondNotFoundExcpetion(String.format("%s not found", b.getIsin()));
        if(universe.containsKey(b.getIsin())){
            throw new DuplicateBondException(String.format("%s is already found", b.getIsin()));
        }
        String isin = b.getIsin();
        universe.put(isin, b);
        bySector.computeIfAbsent(b.getSector().name(), k -> new ArrayList<>()).add(b);
        byYield.computeIfAbsent(b.getYieldToMaturity(), k -> new ArrayList<>()).add(b);
    }

    @Override
    public List<Bond> screenBonds(String minRating, double minYield, double maxYield, double maxDuration) throws BondException {

        if (minRating == null || minRating.isBlank()) {
            throw new RatingException("Rating cannot be null or empty");
        }

        if (!ratingRank.containsKey(minRating)) {
            throw new RatingException("Invalid Rating : " + minRating);
        }

        if (minYield < 0 || maxYield < 0) {
            throw new YeildException("Yield cannot be negative");
        }

        if (minYield > maxYield) {
            throw new YeildException("Minimum yield cannot be greater than maximum yield");
        }

        if (maxDuration < 0) {
            throw new DurationException("Duration cannot be negative");
        }

        List<Bond> screenedBonds = new ArrayList<>(); //result
        int requiredRank = ratingRank.get(minRating);
        for(Bond b : universe.values()){
            if(b == null) continue;
            if(!ratingRank.containsKey(b.getRating().name())) continue;
            int bondRating = ratingRank.get(b.getRating().name());
            boolean ratingCheck = bondRating <= requiredRank;
            boolean yieldCheck = b.getYieldToMaturity() <= maxYield &&
                                 b.getYieldToMaturity() >= minYield;
            boolean durationCheck = b.getDuration() <= maxDuration;

            if(ratingCheck && yieldCheck && durationCheck){
                screenedBonds.add(b);
            }
        }
        return screenedBonds;
    }

    @Override
    public void addToWatchlist(String isin) throws BondException{
        if(universe.containsKey(isin))
            watchlist.add(isin);
        else
            throw new BondNotFoundExcpetion(String.format("%s not found", isin));
    }

    @Override
    public void removeFromWatchlist(String isin) throws BondException {
        if(watchlist.contains(isin))
            watchlist.remove(isin);
        else
            throw new BondNotFoundExcpetion(String.format("%s not found", isin));
    }

    @Override
    public List<Bond> getWatchlistSnapshot() {
        List<Bond> watchlistBonds = new ArrayList<>();
        for(String isin : watchlist){
            Bond b = universe.get(isin);
            if(b != null)
                watchlistBonds.add(b);
        }
        return watchlistBonds;
    }

    @Override
    public List<Bond> getTopYieldingBonds(int n, String sector) {
        List<Bond> topYieldBonds = new ArrayList<>(); //result
        for(Map.Entry<Double, List<Bond>> entry : byYield.descendingMap().entrySet()){
            for(Bond b : entry.getValue()){
                if(b.getSector().name().equals(sector)){
                    topYieldBonds.add(b);

                    if(topYieldBonds.size() >= n)
                        return topYieldBonds;
                }
            }
        }
        return topYieldBonds;
    }

    @Override
    public void generateScreeningReport(List<Bond> screenedBonds) throws BondException{
        if(screenedBonds == null)
            throw new BondNotFoundExcpetion("Screened bonds cannot be null");
        if(screenedBonds.isEmpty()){
            System.out.println("Screened bonds is empty");
            return;
        }

        Map<String,List<Bond>> bySector = new HashMap<>();
        for(Bond bond : screenedBonds){
            if (bond == null)
                continue;
            bySector.computeIfAbsent(bond.getSector().name(), k -> new ArrayList<>()).add(bond);

        }

        for(Map.Entry<String,List<Bond>> entry : bySector.entrySet()){
            String sector = entry.getKey();
            List<Bond> bonds = entry.getValue();
            double averageYeilds = 0.0;

            for(Bond bond : bonds){
                averageYeilds += bond.getYieldToMaturity();
            }
            averageYeilds /= bonds.size();

            System.out.printf("%s: %d bonds average yield %.2f%%\n", sector, bonds.size(), averageYeilds);
        }




    }
}
