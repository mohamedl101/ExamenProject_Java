package Euromoon.Models.Personen;

import java.time.LocalDate;

public class Passagier extends Persoon{

    /** Constructor Passagier */
    public Passagier(String naam, String achternaam, String rijkregisternummer, LocalDate geboortedatum) {
        super(naam, achternaam, rijkregisternummer, geboortedatum);
    }
}
