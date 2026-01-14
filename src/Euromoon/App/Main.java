package Euromoon.App;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import Euromoon.Models.Personen.Passagier;
import Euromoon.Models.Personen.Lid;
import Euromoon.Models.Treinen.Locomotief;
import Euromoon.Models.Treinen.Treinen;
import Euromoon.Models.Reizen.Reizen;
import Euromoon.Models.Reizen.Ticket;

/**
 * Minimale CLI main die gebruikmaakt van jouw klasse-namen (Reizen, Treinen, etc.).
 * Vervang exact dit bestand in src/Euromoon/App/Main.java
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Passagier> passagiers = new ArrayList<>();
        List<Treinen> treinen = new ArrayList<>();
        List<Reizen> reizen = new ArrayList<>();

        while (true) {
            System.out.println("\n==== Euromoon CLI ====");
            System.out.println("1) Registreren passagier");
            System.out.println("2) Aanmaken reis");
            System.out.println("3) Trein koppelen aan reis");
            System.out.println("4) Ticket verkopen aan passagier");
            System.out.println("5) Afdrukken boardinglijst");
            System.out.println("0) Exit");
            System.out.print("Kies optie: ");
            String keuze = scanner.nextLine().trim();

            try {
                switch (keuze) {
                    case "1" -> registreerPassagier(passagiers);
                    case "2" -> {
                        Reizen r = maakReisInteractive();
                        if (r != null) {
                            reizen.add(r);
                            System.out.println("Reis opgeslagen (index=" + (reizen.size()-1) + ")");
                        }
                    }
                    case "3" -> koppelTreinInteractive(reizen, treinen);
                    case "4" -> verkoopTicketInteractive(reizen, passagiers);
                    case "5" -> printBoardingListInteractive(reizen);
                    case "0" -> {
                        System.out.println("Bye.");
                        return;
                    }
                    default -> System.out.println("Onbekende optie.");
                }
            } catch (Exception e) {
                System.out.println("Fout: " + e.getMessage());
            }
        }
    }

    private static void registreerPassagier(List<Passagier> passagiers) {
        System.out.println("\n-- Registreren passagier --");
        System.out.print("Voornaam: ");
        String naam = scanner.nextLine().trim();
        System.out.print("Achternaam: ");
        String achternaam = scanner.nextLine().trim();
        System.out.print("Rijksregisternummer: ");
        String rrn = scanner.nextLine().trim();
        System.out.print("Geboortedatum (YYYY-MM-DD): ");
        String dd = scanner.nextLine().trim();
        LocalDate geboortedatum;
        try {
            geboortedatum = LocalDate.parse(dd);
        } catch (DateTimeParseException e) {
            System.out.println("Ongeldige datum. Registratie afgebroken.");
            return;
        }
        Passagier p = new Passagier(naam, achternaam, rrn, geboortedatum);
        passagiers.add(p);
        System.out.println("Passagier geregistreerd (index=" + (passagiers.size()-1) + "): " + p);
    }

    private static Reizen maakReisInteractive() {
        System.out.println("\n-- Aanmaken reis --");
        System.out.print("Origin (bv. Brussel): ");
        String origin = scanner.nextLine().trim();
        System.out.print("Destination (bv. Parijs): ");
        String destination = scanner.nextLine().trim();
        System.out.print("Vertrek (YYYY-MM-DDTHH:MM, bv. 2026-03-05T12:30): ");
        String ts = scanner.nextLine().trim();
        LocalDateTime vertrek;
        try {
            vertrek = LocalDateTime.parse(ts);
        } catch (DateTimeParseException e) {
            System.out.println("Ongeldige datum/tijd. Reis niet aangemaakt.");
            return null;
        }
        return new Reizen(origin, destination, vertrek);
    }

    private static void koppelTreinInteractive(List<Reizen> reizen, List<Treinen> treinen) {
        System.out.println("\n-- Trein koppelen aan reis --");
        if (reizen.isEmpty()) {
            System.out.println("Geen reizen beschikbaar. Maak eerst een reis.");
            return;
        }
        printReizen(reizen);
        System.out.print("Kies reis index: ");
        int ri = safeReadIndex(reizen.size());
        if (ri < 0) return;
        Reizen reis = reizen.get(ri);

        // Maak of hergebruik trein
        System.out.print("Wil je een nieuwe trein maken? (j/n): ");
        String nieuw = scanner.nextLine().trim().toLowerCase();
        Treinen trein;
        if ("j".equals(nieuw) || "y".equals(nieuw)) {
            System.out.print("Locomotief type (bv. Class 373): ");
            String type = scanner.nextLine().trim();
            System.out.print("Max wagons voor deze locomotief (bv. 12): ");
            int max = safeReadInt();
            Locomotief loco = new Locomotief(type, max);
            trein = new Treinen(loco);

            // voeg wagons (via helper in Treinen zodat Main geen toegang tot package-private Wagon nodig heeft)
            System.out.print("Hoeveel wagons wil je toevoegen nu? ");
            int aantal = safeReadInt();
            for (int i = 0; i < aantal; i++) {
                System.out.print("Wagon " + (i+1) + " type (1e klasse / 2e klasse): ");
                String wtype = scanner.nextLine().trim();
                boolean added = trein.addWagonByType(wtype);
                if (!added) {
                    System.out.println("Wagon kon niet toegevoegd worden (max of ongeldig).");
                    break;
                }
            }
            treinen.add(trein);
            System.out.println("Nieuwe trein gemaakt en opgeslagen (index " + (treinen.size()-1) + ").");
        } else {
            if (treinen.isEmpty()) {
                System.out.println("Geen bestaande treinen. Maak er eerst een nieuwe.");
                return;
            }
            printTreinen(treinen);
            System.out.print("Kies trein index: ");
            int ti = safeReadIndex(treinen.size());
            if (ti < 0) return;
            trein = treinen.get(ti);
        }

        // crew toevoegen
        System.out.print("Wil je crew toevoegen voor deze reis? (j/n): ");
        String addCrew = scanner.nextLine().trim().toLowerCase();
        if ("j".equals(addCrew) || "y".equals(addCrew)) {
            System.out.print("Hoeveel crewleden wil je toevoegen? ");
            int c = safeReadInt();
            for (int i = 0; i < c; i++) {
                System.out.println("-- crewlid " + (i+1) + " --");
                System.out.print("Voornaam: "); String vn = scanner.nextLine().trim();
                System.out.print("Achternaam: "); String an = scanner.nextLine().trim();
                System.out.print("Rijksregisternummer: "); String rrn = scanner.nextLine().trim();
                System.out.print("Geboortedatum (YYYY-MM-DD): "); String dd = scanner.nextLine().trim();
                LocalDate gd;
                try { gd = LocalDate.parse(dd); } catch (DateTimeParseException e) {
                    System.out.println("Ongeldige datum, sla crewlid over.");
                    continue;
                }
                // concrete crew object
                Lid pl = new Lid(vn, an, rrn, gd);
                System.out.print("Aantal certificaties voor dit lid: ");
                int certs = safeReadInt();
                for (int j = 0; j < certs; j++) {
                    System.out.print("Certificatie " + (j+1) + ": ");
                    String cert = scanner.nextLine().trim();
                    pl.addCertificaties(cert);
                }
                reis.voegTreinLid(pl); // methode bestaat in Reizen.java (compatibel met jouw naamkeuze)
                System.out.println("Crewlid toegevoegd.");
            }
        }

        reis.koppelTrein(trein);
        System.out.println("Trein gekoppeld aan reis.");
        if (reis.voldoetPersoneelsLid()) {
            System.out.println("Crewvereisten zijn voldaan.");
        } else {
            System.out.println("Let op: crewvereisten zijn NIET voldaan (min. 1 bestuurder en 3 stewards).");
        }
    }

    private static void verkoopTicketInteractive(List<Reizen> reizen, List<Passagier> passagiers) {
        System.out.println("\n-- Ticket verkopen --");
        if (passagiers.isEmpty()) {
            System.out.println("Geen passagiers geregistreerd.");
            return;
        }
        printPassagiers(passagiers);
        System.out.print("Kies passagier index: ");
        int pi = safeReadIndex(passagiers.size());
        if (pi < 0) return;
        Passagier p = passagiers.get(pi);

        if (reizen.isEmpty()) {
            System.out.println("Geen reizen beschikbaar.");
            return;
        }
        printReizen(reizen);
        System.out.print("Kies reis index: ");
        int ri = safeReadIndex(reizen.size());
        if (ri < 0) return;
        Reizen reis = reizen.get(ri);

        System.out.print("Klasse (1e klasse / 2e klasse): ");
        String klasse = scanner.nextLine().trim();
        try {
            Ticket t = reis.verkoopTicket(p, klasse);
            System.out.println("Ticket verkocht: " + t);
        } catch (Exception e) {
            System.out.println("Kan ticket niet verkopen: " + e.getMessage());
        }
    }

    private static void printBoardingListInteractive(List<Reizen> reizen) {
        System.out.println("\n-- Afdrukken boardinglijst --");
        if (reizen.isEmpty()) {
            System.out.println("Geen reizen beschikbaar.");
            return;
        }
        printReizen(reizen);
        System.out.print("Kies reis index: ");
        int ri = safeReadIndex(reizen.size());
        if (ri < 0) return;
        Reizen reis = reizen.get(ri);
        try {
            String bestand = reis.printBoardingList();
            System.out.println("Boardinglijst geschreven naar: " + bestand);
        } catch (Exception e) {
            System.out.println("Fout bij schrijven boardinglijst: " + e.getMessage());
        }
    }

    // helpers
    private static int safeReadIndex(int size) {
        String line = scanner.nextLine().trim();
        try {
            int idx = Integer.parseInt(line);
            if (idx < 0 || idx >= size) {
                System.out.println("Index buiten bereik.");
                return -1;
            }
            return idx;
        } catch (NumberFormatException e) {
            System.out.println("Ongeldige index.");
            return -1;
        }
    }

    private static int safeReadInt() {
        String line = scanner.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Ongeldig getal, gebruik 0.");
            return 0;
        }
    }

    private static void printPassagiers(List<Passagier> ps) {
        System.out.println("Passagiers:");
        for (int i = 0; i < ps.size(); i++) {
            System.out.println(i + ") " + ps.get(i));
        }
    }

    private static void printReizen(List<Reizen> rs) {
        System.out.println("Reizen:");
        for (int i = 0; i < rs.size(); i++) {
            System.out.println(i + ") " + rs.get(i));
        }
    }

    private static void printTreinen(List<Treinen> ts) {
        System.out.println("Treinen:");
        for (int i = 0; i < ts.size(); i++) {
            System.out.println(i + ") " + ts.get(i));
        }
    }
}