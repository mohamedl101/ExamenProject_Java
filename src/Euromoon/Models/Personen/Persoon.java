package Euromoon.Models.Personen;

import java.time.LocalDate;

public abstract class Persoon {

    private String naam;
    private String achternaam;
    private String rijkregisternummer;
    private LocalDate geboortedatum;

    /** Constructor Persoon */
    protected Persoon(String naam, String achternaam, String rijkregisternummer, LocalDate geboortedatum) {
        this.naam = naam;
        this.achternaam = achternaam;
        this.rijkregisternummer = rijkregisternummer;
        this.geboortedatum = geboortedatum;
    }

    /** Getters */
    public String getNaam() {return naam; }
    public String getAchternaam() {return achternaam; }
    public String getRijkregisternummer() {return rijkregisternummer; }
    public LocalDate getGeboortedatum() {return geboortedatum; }

    @Override
    public String toString() {
        return naam + " " + achternaam + " (" + rijkregisternummer + ") " + geboortedatum;
    }

}


