package Euromoon.Models.Treinen;

import java.util.List;
import java.util.ArrayList;

/** Een trein bestaat uit een locomotief en lijst wagons. Locomotieven kunnen afhankelijk van
 * type slechts een bepaald aantal wagons dragen. Een wagon heeft altijd 80 zitplaatsen en
 * is ofwel 1e of 2e klasse */

public class Treinen {
    private Locomotief locomotief;
    private List<Wagon> wagons;

    // Constructor
    public Treinen(Locomotief locomotief) {
        this.locomotief = locomotief;
        this.wagons = new ArrayList<>();
    }

    public Locomotief getLocomotief() {
        return locomotief;
    }

    public void setLocomotief(Locomotief locomotief) {
        this.locomotief = locomotief;
    }

    // Wagonnen toevoegen aan trein
    public boolean addWagon(Wagon wagon) {
        if (wagons.size() < locomotief.getMaxWagons()) {
            wagons.add(wagon);
            return true;
        } else {
            System.out.println("Maximum aantal wagons bereikt voor deze locomotief");
            return false;
        }
    }

    /** Berekening aantal zitplaatsen 1e/2e klasse. Voor elke wagon dat voldoet aan
     * het type, worden er 80 zitplaatsen toegevoegd.
     * @return
     */

    // Aantal zitplaatsen 1e klasse
    public int aantalZitplaatsen1eKlasse() {
        int plaatsen = 0;
        for (Wagon wagon : wagons) {
            if ("1e klasse".equals(wagon.getType())) {
                plaatsen += wagon.getPlaatsen();
            }
        }
        return plaatsen;
        }

    // Aantal zitplaatsen 2e klasse
    public int aantalZitplaatsen2eKlasse() {
        int plaatsen = 0;
        for (Wagon wagon : wagons) {
            if ("2e klasse".equals(wagon.getType())) {
                plaatsen += wagon.getPlaatsen();
            }
        }
        return plaatsen;
    }

    // Totaal aantal plaatsen (ALLE WAGONS)
    public int totaalZitplaatsen() {
        int plaatsen = 0;
        for (Wagon wagon : wagons) {
            plaatsen += wagon.getPlaatsen();
        }
        return plaatsen;
    }

    @Override
    public String toString() {
        return "Trein: locomotief= " + locomotief.toString() + ", wagons=" + wagons.size();
    }
}
