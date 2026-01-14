package Euromoon.Models.Treinen;

/** Elke locomotief bestaat uit een type model, met elke een verschillend aantal wagons
 * die ze kunnen dragen.
 */
public class Locomotief {
    private String type;
    private int maxWagons;

    // Constructor Locomotief
    public Locomotief(String type, int maxWagons) {
        this.type = type;
        this.maxWagons = maxWagons;
    }

    // Getters
    public String getType() {return type;}
    public int getMaxWagons() {return maxWagons;}

    @Override
    public String toString() {
        return type + " (Maximum Wagons: " + maxWagons;
    }

}

/** Een wagon kan eerste of tweede klasse zijn en bestaan sowieso ALTIJD uit 80 zitplaatsen. */
class Wagon {
    private String type; // 1ste of 2e klasse
    private final int plaatsen = 80; // ALTIJD 80 zitplaatsen

    // Constructor Wagon
    public Wagon(String type) {
        if (!(type.equals("1e klasse") || type.equals("2e klasse")))
            throw new IllegalArgumentException("Type kan alleen 1e of 2e klasse zijn");
        this.type = type;
    }

    // Getters
    public String getType() {return type;}
    public int getPlaatsen() {return plaatsen;}

    @Override
    public String toString() {
        return type + " Wagon: " + plaatsen + " zitplaatsen";
    }
}