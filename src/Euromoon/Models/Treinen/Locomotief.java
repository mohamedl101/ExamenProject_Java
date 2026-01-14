package Euromoon.Models.Treinen;

/** Elke locomotief heeft een verschillend aantal wagons. Ze hebben sowieso plaats voor 80
 * personen en zijn slechts zichtbaar in eerste of tweede klasse.
 */
public class Locomotief {
    private String type;
    private int maxWagons;
    private int eersteKlassePlaats;
    private int tweedeKlassePlaats;


    // Constructor Locomotief
    public Locomotief(String type, int maxWagons) {
        this.type = type;
        this.maxWagons = maxWagons;
        this.eersteKlassePlaats = eersteKlassePlaats;
        this.tweedeKlassePlaats = tweedeKlassePlaats;
    }

    // Getters
    public String getType() {return type;}
    public int getMaxWagons() {return maxWagons;}
    public int getEersteKlassePlaats() {return eersteKlassePlaats;}
    public int getTweedeKlassePlaats() {return tweedeKlassePlaats;}

    @Override
    public String toString() {
        return type + " (Maximum Wagons: " + maxWagons + ", Zitplaatsen: " + eersteKlassePlaats
                + " (1ste Klasse)/ " + tweedeKlassePlaats + "(2de Klasse)";
    }

}

class Wagon {
    private String type;  // eerste of tweedeklasse
    private int plaatsen;

    // Constructor Wagon
    public Wagon(String type, int plaatsen) {
        this.type = type;
        this.plaatsen = plaatsen;
    }

    // Getters
    public String getType() {return type;}
    public int getPlaatsen() {return plaatsen;}

    @Override
    public String toString() {
        return type + " Wagon: " + plaatsen + " zitplaatsen";
    }
}