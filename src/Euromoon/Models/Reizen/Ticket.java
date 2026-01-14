package Euromoon.Models.Reizen;

import java.time.LocalDateTime;
import java.time.LocalTime;

import Euromoon.Models.Personen.Passagier;

/** Een Ticket bevat een passagier en klasse (1e of 2e) met de datum van aankoop */
public class Ticket {
    private final Passagier passagier;
    private final String klasse;
    private final LocalDateTime gekochtOp;

    // Constructor
    public Ticket(Passagier passagier, String klasse) {
        if (!"1e klasse".equals(klasse) && !"2e klasse".equals(klasse)) {
            throw new IllegalArgumentException("Klasse moet 1e of 2e klasse zijn");
        }
        this.passagier = passagier;
        this.klasse = klasse;
        this.gekochtOp = LocalDateTime.now();
    }

    // Getters
    public Passagier getPassagier() {return passagier;}
    public String getKlasse() {return klasse;}
    public LocalDateTime getGekochtOp() {return gekochtOp;}

    @Override
    public String toString() {
        return passagier + ", " + klasse + ", " + gekochtOp;
    }
}
