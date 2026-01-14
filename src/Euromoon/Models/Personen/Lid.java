package Euromoon.Models.Personen;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public abstract class Lid extends Persoon{
    private List<String> certificaties;

    public Lid(String naam, String achternaam, String rijkregisternummer, LocalDate geboortedatum) {
        super(naam, achternaam, rijkregisternummer, geboortedatum);
        this.certificaties = new ArrayList<>();
    }

    public void addCertificaties(String certificatie){
        certificaties.add(certificatie);
    }

    public List<String> getCertificaties(){
        return certificaties;
    }

}
