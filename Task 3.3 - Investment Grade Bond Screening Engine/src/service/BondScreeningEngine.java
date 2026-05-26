package service;

import exception.BondException;
import model.Bond;

import java.util.List;

public interface BondScreeningEngine {
    void loadBond(Bond b) throws BondException;
    List<Bond> screenBonds(String minRating, double minYield, double maxYield, double maxDuration) throws BondException;
    void addToWatchlist(String isin) throws BondException;
    void removeFromWatchlist(String isin) throws BondException;
    List<Bond>  getWatchlistSnapshot();
    List<Bond> getTopYieldingBonds(int n, String sector);
    void generateScreeningReport(List<Bond> screened) throws BondException;

}
