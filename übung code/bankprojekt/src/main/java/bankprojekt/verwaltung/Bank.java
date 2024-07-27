package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.*;
import bankprojekt.verarbeitung.factory.Kontofabrik;
import bankprojekt.verarbeitung.observer.KontoEventHandler;
import bankprojekt.verarbeitung.observer.KontoEventImpl;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Bank implements Cloneable{
    private final long bankleitzahl;
    private HashMap<Long, Konto> konten;
    private long currentKontoNr;
    private KontoEventHandler kontoEventHandler;

    /**
     * @param bankleitzahl der bank
     */
    public Bank(long bankleitzahl) {
        this.bankleitzahl = bankleitzahl;
        this.konten = new HashMap<>();
        this.currentKontoNr = 0;
        this.kontoEventHandler = new KontoEventHandler();
    }

    /**
     * @return bankleitzahl der bank
     */
    public long getBankleitzahl() {
        return this.bankleitzahl;
    }

    public long kontoErstellen(Kontofabrik k, Kunde inhaber) {
        konten.put(currentKontoNr, k.kontoErstellen(inhaber,currentKontoNr));
        kontoEventHandler.subscribe(currentKontoNr, new KontoEventImpl());
        return currentKontoNr++;
    }

    /**
     * @param inhaber des Kontos
     * @return erstellte Kontonachricht
     */
    public long girokontoErstellen(Kunde inhaber) {
        Konto temp = new Girokonto(inhaber, currentKontoNr, 0);
        this.konten.put(currentKontoNr, temp);
        kontoEventHandler.subscribe(currentKontoNr, new KontoEventImpl());
        return currentKontoNr++;
    }

    /**
     * @param inhaber des Kontos
     * @return erstellte Kontonummer
     */
    public long sparbuchErstellen(Kunde inhaber) {
        Konto temp = new Sparbuch(inhaber, currentKontoNr);
        this.konten.put(currentKontoNr, temp);
        kontoEventHandler.subscribe(currentKontoNr, new KontoEventImpl());
        return currentKontoNr++;
    }


    /**
     * @return ein String der alle kontonummern mit Kontostand enthaelt
     */
    public String getAlleKonten() {
        String temp = "";
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Long, Konto> entry : this.konten.entrySet()
        ) {
            stringBuilder.append(entry.getValue().getKontonummer());
            stringBuilder.append(':');
            stringBuilder.append(entry.getValue().getKontostand());
            stringBuilder.append(entry.getValue().getAktuelleWaehrung());
            stringBuilder.append(" | ");
        }
        return stringBuilder.toString();
    }

    /**
     * @return Liste mit allen Kontonummer
     */
    public List<Long> getAlleKontonummern() {
        List<Long> kontonummern = new ArrayList<>();
        for (Map.Entry<Long, Konto> entry : this.konten.entrySet()
        ) {
            kontonummern.add(entry.getKey());
        }
        return kontonummern;
    }

    /**
     * @param von    Kontonummer des Kontos von dem Abgehoben wird
     * @param betrag der abgehoben werden soll
     * @return erfolg des abhebens
     * @throws GesperrtException
     */
    public boolean geldAbheben(long von, double betrag) throws GesperrtException, IllegalArgumentException {
        verifyIfKontoExists(von);
        boolean success = konten.get(von).abheben(betrag);
        if (success) {
            kontoEventHandler.update(von, "geld abgehoben: " + betrag);
        }
        return success;
    }

    /**
     * @param auf
     * @param betrag
     * @throws IllegalArgumentException
     */
    public void geldEinzahlen(long auf, double betrag) throws IllegalArgumentException {
        verifyIfKontoExists(auf);
        try {
            konten.get(auf).einzahlen(betrag);
            kontoEventHandler.update(auf, "geld eingezahlt: " + betrag);
        }catch (IllegalArgumentException e) {
            kontoEventHandler.update(auf, "Illegale Transaktion. Invalide einzahlung: " + betrag);
            throw e;
        }
    }

    /**
     * @param nummer
     * @return
     * @throws KontoNichtLeerException
     * @throws IllegalArgumentException
     */
    public boolean kontoLoeschen(long nummer) throws KontoNichtLeerException, IllegalArgumentException {
        verifyIfKontoExists(nummer);
        if (konten.get(nummer).getKontostand() > 0) {
            throw new KontoNichtLeerException(nummer);
        }
        konten.remove(nummer);
        kontoEventHandler.update(nummer, "Konto gelöscht.");
        return true;
    }

    /**
     * @param nummer
     * @return
     * @throws IllegalArgumentException
     */
    public double getKontostand(long nummer) throws IllegalArgumentException {
        verifyIfKontoExists(nummer);
        return konten.get(nummer).getKontostand();
    }

    /**
     * @param vonKontonr
     * @param nachKontonr
     * @param betrag
     * @param verwendungszweck
     * @return
     * @throws IllegalArgumentException
     * @throws GesperrtException
     */
    public boolean geldUeberweisen(long vonKontonr, long nachKontonr, double betrag, String verwendungszweck) throws IllegalArgumentException, GesperrtException {
        verifyIfKontoExists(vonKontonr);
        verifyIfKontoExists(nachKontonr);
        if (vonKontonr != nachKontonr) {
            boolean success;
            //todo check waehrung oder refactore Konto -> check Waehrung bei überweisung

            if (konten.get(vonKontonr) instanceof Girokonto && konten.get(nachKontonr) instanceof Girokonto) {
                Girokonto vonKonto = (Girokonto) konten.get(vonKontonr);
                success = vonKonto.ueberweisungAbsenden(betrag, konten.get(nachKontonr).getInhaber().getVorname() + konten.get(nachKontonr).getInhaber().getNachname(), nachKontonr, this.bankleitzahl, verwendungszweck);

                Girokonto nachkonto = (Girokonto) konten.get(nachKontonr);
                double kontostandAlt = nachkonto.getKontostand();
                if (nachkonto.isGesperrt()) {
                    throw new GesperrtException(nachKontonr);
                }
                nachkonto.ueberweisungEmpfangen(betrag, konten.get(vonKontonr).getInhaber().getVorname() + konten.get(vonKontonr).getInhaber().getNachname(), vonKontonr, this.bankleitzahl, verwendungszweck);

                double kontostandNew = konten.get(nachKontonr).getKontostand();
                if (success) {
                    success = kontostandAlt < kontostandNew;
                }
                if (success) {
                    konten.put(nachKontonr, nachkonto);
                    konten.put(vonKontonr, vonKonto);

                    kontoEventHandler.update(vonKontonr, betrag + " ueberwiesen an " + nachKontonr);
                    kontoEventHandler.update(nachKontonr, betrag + " empfangen von " + vonKontonr);
                }

            } else {
                throw new IllegalArgumentException("Sparbuecher koennen keine Ueberweisungen taetigen oder empfangen.");
            }
            return success;
        }
        throw new IllegalArgumentException("Kontonummern duerfen nicht identisch sein!");
    }

    private void verifyIfKontoExists(long kontonr) {
        if (!this.konten.containsKey(kontonr)) {
            throw new IllegalArgumentException("Konto existiert nicht: " + kontonr);
        }
    }

    /**
     * die Methode sperrt alle Konten, deren Kontostand im Minus ist.
     */
    void pleitegeierSperren() {
        konten.values().stream()
                .filter(konto -> konto.getKontostand() < 0)
                .forEach(konto -> konto.sperren());
    }

    /**
     * Die Methode liefert eine Liste aller Kunden, die auf einem Konto einen Konto-stand haben, der mindestens minimum beträgt.
     *
     * @param minimum
     * @return
     */

    List<Kunde> getKundenMitVollemKonto(double minimum) {
        return konten.values().stream()
                .filter(konto -> konto.getKontostand() >= minimum)
                .map(konto -> konto.getInhaber())
                .collect(Collectors.toList());
    }

    /**
     * liefert die Namen und Geburtstage aller Kunden der Bank. Doppelte Namen sol-len dabei aussortiert werden. Sortieren Sie die Liste nach dem Geburtsdatum.
     *
     * @return alle Kunden mit ihren Geburtsdaten, vom Ältesten zum Jüngsten sortiert.
     */
    String getKundengeburtstage() {

        return konten.values().stream()
                .map(konto -> konto.getInhaber())
                .distinct()
                .sorted(Comparator.comparing(kunde -> kunde.getGeburtstag()))
                .map(kunde -> kunde.getName() + "|" +kunde.getGeburtstag())
                .reduce("",(endString, dasVonVorherJeweiligerKundeSieheEineZeileWeiterOben) -> endString + System.lineSeparator() + dasVonVorherJeweiligerKundeSieheEineZeileWeiterOben);
    }


    @Override
    public Bank clone() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bank clonedBank = new Bank(bankleitzahl);
        clonedBank.currentKontoNr = this.currentKontoNr;
        konten.values().stream().forEach(konto -> {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(konto);
                oos.flush();
                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                ObjectInputStream ois = new ObjectInputStream(bais);
                Konto clonedKonto = (Konto) ois.readObject();
                clonedBank.konten.put(clonedKonto.getKontonummer(), clonedKonto);
           } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
          }
        });
        return clonedBank;
    }
}