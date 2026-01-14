package Euromoon.Models.Reizen;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.BufferedWriter;

import Euromoon.Models.Treinen.Treinen;
import Euromoon.Models.Personen.Lid;
import Euromoon.Models.Personen.Passagier;

public class Reizen {
    private final String vertrek;
    private final String aankomst;
    private final LocalDateTime vertrekTijd;

    private Treinen treinen;
    private final List<Lid> Lid = new ArrayList<>();
    private final List<Ticket> ticket = new ArrayList<>();

    //Constructor
    public Reizen(String vertrek, String aankomst, LocalDateTime vertrekTijd) {
        this.vertrek = vertrek;
        this.aankomst = aankomst;
        this.vertrekTijd = vertrekTijd;
    }

    // Getters
    public String getVertrek() {return vertrek;}
    public String getAankomst() {return aankomst;}
    public LocalDateTime getVertrekTijd() {return vertrekTijd;}

    public void koppelTrein(Treinen treinen) {
        this.treinen = treinen;
    }

    public void voegTreinLid(Lid lid) {
        Lid.add(lid);
    }

    public List<Lid> getLid() {return new ArrayList<>(Lid);}

    // Checkt of er minimaal 1 bestuurder en 3 stewards zijn
    public boolean voldoetPersoneelsLid() {
        int bestuurders = 0;
        int stewards = 0;

        for (Lid l : Lid) {
            if (l == null) continue;
            if (l.isBestuurder()) bestuurders++;
            if (l.isSteward()) stewards++;
            if (bestuurders >= 1 && stewards >= 3) return true;
        }
        return bestuurders >= 1 && stewards >= 3;
    }

    /**
     * Verkoopt een ticket voor deze reis voor een specifieke klasse.
     */
    public synchronized Ticket verkoopTicket(Passagier passagier, String klasse) {
        if (passagier == null) throw new IllegalArgumentException("Er moet minstens 1 passagier zijn");
        if (!"1e klasse".equals(klasse) && !"2e klasse".equals(klasse))
            throw new IllegalArgumentException("Klasse moet '1e klasse' of '2e klasse' zijn");

        int beschikbare = beschikbarePlaatsen(klasse);
        if (beschikbare <= 0) {
            throw new IllegalStateException("Geen plaatsen meer beschikbaar in " + klasse);
        }
        Ticket t = new Ticket(passagier, klasse);
        ticket.add(t);
        return t;
    }

    /**
     * Berekent hoeveel plaatsen er nog beschikbaar zijn in een bepaalde klasse.
     */
    public int beschikbarePlaatsen(String klasse) {
        int totaalInTrein;
        if ("1e klasse".equals(klasse)) {
            totaalInTrein = treinen.aantalZitplaatsen1eKlasse();
        } else {
            totaalInTrein = treinen.aantalZitplaatsen2eKlasse();
        }

        int verkocht = 0;
        for (Ticket t : ticket) {
            if (t == null) continue;
            if (klasse.equals(t.getKlasse())) verkocht++;
        }

        return totaalInTrein - verkocht;
    }

    public List<Ticket> getTickets() { return new ArrayList<>(ticket); }
    public int totaalVerkochteTickets() { return ticket.size(); }

    /**
     * Boardinglijst die .txt bestand kan sturen.
     */
    public String printBoardingList() throws IOException {
        String safeOrigin = vertrek.replaceAll("\\s+", "_");
        String safeDest = aankomst.replaceAll("\\s+", "_");

        // Timestamp Formaat
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String ts = vertrekTijd.format(fmt);
        String tsSafe = ts.replace(":", "-");

        String fileName = String.format("%s_%s_%s.txt", safeOrigin, safeDest, tsSafe);

        List<String> lines = new ArrayList<>();
        lines.add("Boardinglijst voor reis: " + vertrek + " -> " + aankomst + " vertrek: " + vertrekTijd);
        lines.add("Trein: " + (treinen == null ? "niet gekoppeld" : treinen.toString()));
        lines.add("Aantal tickets: " + ticket.size());
        lines.add("");
        lines.add("Passagiers:");
        for (Ticket t : ticket) {
            if (t == null) continue;
            Passagier p = t.getPassagier();
            String passagierInfo = String.format("%s %s | RR: %s | geboortedatum: %s | klasse: %s",
                    p.getNaam(), p.getAchternaam(), p.getRijkregisternummer(), p.getGeboortedatum(), t.getKlasse());
            lines.add(passagierInfo);
        }

        Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8);
        return fileName;
    }

    @Override
    public String toString() {
        return vertrek + " -> " + aankomst + " (" + vertrekTijd + ")";
    }
}