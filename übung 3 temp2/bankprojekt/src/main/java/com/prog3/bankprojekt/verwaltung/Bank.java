package com.prog3.bankprojekt.verwaltung;

import com.prog3.bankprojekt.verarbeitung.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Bank {
    private final long bankleitzahl;
    private HashMap<Long, Konto> konten;
    private long currentKontoNr;

    /**
     * @param bankleitzahl der bank
     */
    public Bank(long bankleitzahl) {
        this.bankleitzahl = bankleitzahl;
        this.konten = new HashMap<>();
        this.currentKontoNr = 0;
    }

    /**
     * @return bankleitzahl der bank
     */
    public long getBankleitzahl() {
        return this.bankleitzahl;
    }

    /**
     * @param inhaber des Kontos
     * @return erstellte Kontonachricht
     */
    public long girokontoErstellen(Kunde inhaber) {
        Konto temp = new Girokonto(inhaber, currentKontoNr, 0);
        this.konten.put(currentKontoNr, temp);
        return currentKontoNr++;
    }

    /**
     * @param inhaber des Kontos
     * @return erstellte Kontonummer
     */
    public long sparbuchErstellen(Kunde inhaber) {
        Konto temp = new Sparbuch(inhaber, currentKontoNr);
        this.konten.put(currentKontoNr, temp);
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
        return konten.get(von).abheben(betrag);
    }

    /**
     * @param auf
     * @param betrag
     * @throws IllegalArgumentException
     */
    public void geldEinzahlen(long auf, double betrag) throws IllegalArgumentException {
        verifyIfKontoExists(auf);
        konten.get(auf).einzahlen(betrag);
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

    /**
     * liefert eine Liste aller freien Kontonummern, die im vergebenen Bereich liegen. es geht um die Kontonummern,
     * die dazwischen liegen und für die es gerade kein Konto gibt, z.B. weil es gelöscht wurde.
     *
     * @return eine Liste aller bisher nicht verwendeten Kontonummern
     */
    List<Long> getKontonummernLuecken() {
        return LongStream.range(0, this.currentKontoNr + 1)
                .filter(kontonummer -> konten.containsKey(kontonummer) == false)
                .boxed()
                .collect(Collectors.toList());
    }
}