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

    public boolean isBestuurder() {
        if (certificaties == null) return false;
        for (String c : certificaties) {
            if (c == null) continue;
            String s = c.trim().toLowerCase();
            if (s.equals("rijbewijs b1 (personenvervoer)") ||
                    s.contains("rijbewijs b1") ||
                    s.contains("personenvervoer") ||
                    s.contains("bestuurder")) {
                return true;
            }
        }
        return false;
    }

    public boolean isSteward() {
        if (certificaties == null) return false;
        for (String c : certificaties) {
            if (c == null) continue;
            String s = c.trim().toLowerCase();
            if (s.equals("toerisme") ||
                    s.contains("steward") ||
                    s.contains("gastvrijheid")) {
                return true;
            }
        }
        return false;
    }

}
