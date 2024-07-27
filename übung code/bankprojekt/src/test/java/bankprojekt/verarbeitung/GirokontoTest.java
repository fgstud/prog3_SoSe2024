package bankprojekt.verarbeitung;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GirokontoTest {
        Girokonto gk;
        Kunde k = new Kunde("Vorname", "Nachname", "Somewhere", LocalDate.parse("2020-01-01"));
        long nummer = 17;
        double dispo = 1000;

        @Test
        void konstruktorTest() {
            gk = new Girokonto(k, nummer, dispo);
            assertEquals(gk.getKontostand(), 0);
            assertEquals(gk.getKontostandFormatiert(), "      0.00 EUR");
            assertFalse(gk.isGesperrt());
            assertEquals(gk.getGesperrtText(), "");
            assertEquals(gk.getInhaber(), k);
            assertEquals(gk.getDispo(), dispo);
            assertEquals(gk.getKontonummer(), nummer);
            assertEquals(gk.getKontonummerFormatiert(), "        17");
        }

        @Test
        void einzahlenTest() {
            gk = new Girokonto();
            gk.einzahlen(100);

            assertEquals(gk.getKontostand(), 100);
            assertEquals(gk.getKontostandFormatiert(), "    100.00 EUR");
            assertFalse(gk.isGesperrt());
            assertEquals(gk.getGesperrtText(), "");
            assertEquals(gk.getInhaber(), Kunde.MUSTERMANN);
        }

        @Test
        void einZahlenNegativTest() {
            gk = new Girokonto();
            try {
                gk.einzahlen(-100);
                fail("Keine Exception!");
            } catch (IllegalArgumentException e) {
            }
            assertEquals(gk.getKontostand(), 0);
            assertEquals(gk.getKontostandFormatiert(), "      0.00 EUR");
            assertFalse(gk.isGesperrt());
            assertEquals(gk.getGesperrtText(), "");
            assertEquals(gk.getInhaber(), Kunde.MUSTERMANN);
        }

        @Test
        void einzahlenNaNTest(){
            gk= new Girokonto();
            try {
                gk.einzahlen(Double.NaN);
                fail("Keine Exception!");
            } catch(IllegalArgumentException e) {
            }
            assertEquals(gk.getKontostand(), 0);
            assertEquals(gk.getKontostandFormatiert(), "      0.00 EUR");
            assertFalse(gk.isGesperrt());
            assertEquals(gk.getGesperrtText(), "");
            assertEquals(gk.getInhaber(), Kunde.MUSTERMANN);
        }

        @Test
        void abhebenInDenDispoTest() throws GesperrtException {
            boolean geklappt;
            gk= new Girokonto(k, nummer, dispo);
            gk.einzahlen(100);geklappt= gk.abheben(50 + dispo);
            assertTrue(geklappt);

            assertEquals(gk.getKontostand(), 50 -dispo);
            assertFalse(gk.isGesperrt());
            assertEquals(gk.getGesperrtText(), "");
            assertEquals(gk.getInhaber(), k);
            assertEquals(gk.getDispo(), dispo);
        }

        @Test
        void abhebenImDispoTest() throws GesperrtException{
            boolean geklappt;
            gk= new Girokonto(k, nummer, dispo);
            gk.einzahlen(100);
            geklappt= gk.abheben(50 + dispo);
            assertTrue(geklappt);
            geklappt= gk.abheben(20);
            assertTrue(geklappt);
            assertEquals(gk.getKontostand(), 30 -dispo);
            assertFalse(gk.isGesperrt());
            assertEquals(gk.getGesperrtText(), "");
            assertEquals(gk.getInhaber(), k);
            assertEquals(gk.getDispo(), dispo);
        }

        @Test
        void abhebenGenauImDispoTest() throws GesperrtException{
            boolean geklappt;
            gk= new Girokonto(k, nummer, dispo);
            gk.einzahlen(100);
            geklappt= gk.abheben(100 + dispo);
            assertTrue(geklappt);
            assertEquals(gk.getKontostand(), -dispo);
            assertFalse(gk.isGesperrt());
            assertEquals(gk.getGesperrtText(), "");
            assertEquals(gk.getInhaber(), k);
            assertEquals(gk.getDispo(), dispo);
        }

        @Test
        void abhebenUeberDispoTest() throws GesperrtException{
            boolean geklappt;
            gk= new Girokonto(k, nummer, dispo);
            gk.einzahlen(100);
            geklappt= gk.abheben(500 + dispo);
            assertFalse(geklappt);
            assertEquals(gk.getKontostand(), 100);
            assertFalse(gk.isGesperrt());
            assertEquals(gk.getGesperrtText(), "");
            assertEquals(gk.getInhaber(), k);
            assertEquals(gk.getDispo(), dispo);
        }

}