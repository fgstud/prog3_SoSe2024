package com.prog3.bankprojekt.verwaltung;

import com.prog3.bankprojekt.verarbeitung.*;

import java.util.*;

public class Bank {
    private final long bankleitzahl;
    private HashMap<Long, Konto> konten;
    private long currentKontoNr;

    /**
     * @param bankleitzahl
     */
    public Bank(long bankleitzahl) {
        this.bankleitzahl = bankleitzahl;
        this.konten = new HashMap<>();
        this.currentKontoNr = 0;
    }

    /**
     * @return
     */
    public long getBankleitzahl() {
        return this.bankleitzahl;
    }

    /**
     * @param inhaber
     * @return
     */
    public long girokontoErstellen(Kunde inhaber) {
        Konto temp = new Girokonto(inhaber, currentKontoNr, 0);
        this.konten.put(currentKontoNr, temp);
        return currentKontoNr++;
    }

    /**
     * @param inhaber
     * @return
     */
    public long sparbuchErstellen(Kunde inhaber) {
        Konto temp = new Sparbuch(inhaber, currentKontoNr);
        this.konten.put(currentKontoNr, temp);
        return currentKontoNr++;
    }

    /**
     * @return
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
     * @return
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
     * @param von
     * @param betrag
     * @return
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

    public boolean kontoLoeschen(long nummer) throws KontoNichtLeerException, IllegalArgumentException {
        verifyIfKontoExists(nummer);
        if (konten.get(nummer).getKontostand() > 0) {
            throw new KontoNichtLeerException(nummer);
        }
        konten.remove(nummer);
        return true;
    }

    public double getKontostand(long nummer) throws IllegalArgumentException {
        verifyIfKontoExists(nummer);
        return konten.get(nummer).getKontostand();
    }

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
}
