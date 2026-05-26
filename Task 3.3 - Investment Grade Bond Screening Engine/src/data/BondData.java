package data;

import model.Bond;
import model.Rating;
import model.Sector;

import java.util.ArrayList;
import java.util.List;

public class BondData {

    public static List<Bond> generateBonds() {

        List<Bond> bonds = new ArrayList<>();


        // =====================================================
        // BANKING
        // =====================================================

        bonds.add(new Bond("IN0001", "SBI_2029",
                Sector.BANKING, Rating.AA,
                9.85, 4.5,
                1000, 50000000));

        bonds.add(new Bond("IN0002", "HDFC_2027",
                Sector.BANKING, Rating.AAA,
                9.40, 3.8,
                1000, 42000000));

        bonds.add(new Bond("IN0003", "ICICI_2030",
                Sector.BANKING, Rating.A,
                8.95, 5.2,
                1000, 38000000));

        bonds.add(new Bond("IN0004", "AXIS_2028",
                Sector.BANKING, Rating.BBB,
                8.50, 4.1,
                1000, 27000000));

        bonds.add(new Bond("IN0005", "PNB_2031",
                Sector.BANKING, Rating.BB,
                10.20, 6.5,
                1000, 19000000));


        // =====================================================
        // GOV
        // =====================================================

        bonds.add(new Bond("IN0006", "GOI_2028",
                Sector.GOV, Rating.AAA,
                7.10, 3.5,
                1000, 90000000));

        bonds.add(new Bond("IN0007", "GOI_2032",
                Sector.GOV, Rating.AA,
                7.45, 5.5,
                1000, 85000000));

        bonds.add(new Bond("IN0008", "TNBOND_2029",
                Sector.GOV, Rating.A,
                7.90, 4.7,
                1000, 32000000));

        bonds.add(new Bond("IN0009", "KRBOND_2031",
                Sector.GOV, Rating.BBB,
                8.25, 6.2,
                1000, 25000000));

        bonds.add(new Bond("IN0010", "APBOND_2033",
                Sector.GOV, Rating.BB,
                9.10, 7.4,
                1000, 17000000));


        // =====================================================
        // INFRA
        // =====================================================

        bonds.add(new Bond("IN0011", "NHAI_2030",
                Sector.INFRA, Rating.AA,
                8.10, 4.9,
                1000, 46000000));

        bonds.add(new Bond("IN0012", "LNT_2029",
                Sector.INFRA, Rating.A,
                8.45, 5.1,
                1000, 41000000));

        bonds.add(new Bond("IN0013", "GMR_2032",
                Sector.INFRA, Rating.BBB,
                9.35, 6.7,
                1000, 28000000));

        bonds.add(new Bond("IN0014", "ADANI_2034",
                Sector.INFRA, Rating.BB,
                10.75, 8.5,
                1000, 22000000));

        bonds.add(new Bond("IN0015", "IRB_2028",
                Sector.INFRA, Rating.A,
                7.95, 4.0,
                1000, 26000000));


        // =====================================================
        // CORPORATE
        // =====================================================

        bonds.add(new Bond("IN0016", "TATA_2029",
                Sector.CORPORATE, Rating.AAA,
                7.85, 3.9,
                1000, 51000000));

        bonds.add(new Bond("IN0017", "RELIANCE_2031",
                Sector.CORPORATE, Rating.AA,
                8.30, 5.4,
                1000, 72000000));

        bonds.add(new Bond("IN0018", "INFY_2028",
                Sector.CORPORATE, Rating.A,
                7.65, 3.6,
                1000, 34000000));

        bonds.add(new Bond("IN0019", "WIPRO_2030",
                Sector.CORPORATE, Rating.BBB,
                8.90, 5.9,
                1000, 29000000));

        bonds.add(new Bond("IN0020", "VEDANTA_2033",
                Sector.CORPORATE, Rating.BB,
                11.20, 7.8,
                1000, 21000000));


        // =====================================================
        // EXTRA BONDS
        // =====================================================

        bonds.add(new Bond("IN0021", "BOB_2030",
                Sector.BANKING, Rating.A,
                8.75, 5.0,
                1000, 24000000));

        bonds.add(new Bond("IN0022", "CANARA_2029",
                Sector.BANKING, Rating.BBB,
                8.95, 4.8,
                1000, 20000000));

        bonds.add(new Bond("IN0023", "GOI_2035",
                Sector.GOV, Rating.AAA,
                7.30, 6.1,
                1000, 95000000));

        bonds.add(new Bond("IN0024", "NHAI_2035",
                Sector.INFRA, Rating.AA,
                8.60, 6.3,
                1000, 31000000));

        bonds.add(new Bond("IN0025", "TATASTEEL_2032",
                Sector.CORPORATE, Rating.BBB,
                9.25, 6.0,
                1000, 28000000));

        bonds.add(new Bond("IN0026", "JSW_2031",
                Sector.CORPORATE, Rating.A,
                8.15, 5.3,
                1000, 26000000));

        bonds.add(new Bond("IN0027", "POWERGRID_2030",
                Sector.INFRA, Rating.AAA,
                7.80, 4.2,
                1000, 47000000));

        bonds.add(new Bond("IN0028", "YESBANK_2032",
                Sector.BANKING, Rating.BB,
                10.90, 7.1,
                1000, 16000000));

        bonds.add(new Bond("IN0029", "ONGC_2029",
                Sector.CORPORATE, Rating.AA,
                8.05, 4.4,
                1000, 39000000));

        bonds.add(new Bond("IN0030", "METRO_2031",
                Sector.INFRA, Rating.BBB,
                9.05, 5.7,
                1000, 23000000));

        return bonds;
    }
}
