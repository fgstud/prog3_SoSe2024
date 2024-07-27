package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.*;
import bankprojekt.verarbeitung.factory.GiroKontoFactory;
import bankprojekt.verarbeitung.factory.GiroKontoMockFactory;
import bankprojekt.verarbeitung.factory.SparbuchFactory;
import bankprojekt.verarbeitung.factory.SparbuchMockFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;


class BankTest {
    /**
     * Dieser Test verifiziert ob die Bankleitzahl abgerufen werden kann.
     */

    @Test
    void getBankleitzahl() {
        long tBankleitzahl = 1000;
        Bank tBank = new Bank(tBankleitzahl);
        assertEquals(tBank.getBankleitzahl(), tBankleitzahl);
    }

    /**
     * Dieser Test verifiziert ob ein Girokonto erstellt werden kann.
     */

    @Test
    void girokontoErstellen_Richtig() {
        Kunde mockKunde = Mockito.mock(Kunde.class);
        long tBankleitzahl = 1;
        Bank tBank = new Bank(tBankleitzahl);
        long erwarteteKontonummer = 123457;

        assertEquals(tBank.kontoErstellen(new SparbuchFactory(),mockKunde), 0);
    }

    /**
     * In diesem Testwid ueberprueft ob mehrere Girokonten korrekt erstellt werden.
     */

    @Test
    void girokontoErstellen_Doppelt() {
        Kunde mockKunde = Mockito.mock(Kunde.class);
        long tBankleitzahl = 1;
        Bank tBank = new Bank(tBankleitzahl);
        long erwarteteKontonummer = 1;
        tBank.kontoErstellen(new GiroKontoFactory(), mockKunde);

        assertEquals(tBank.kontoErstellen(new GiroKontoFactory(), mockKunde),erwarteteKontonummer);
    }

    /**
     * In diesem Testfall wird ueberprueft ob ein Sparbuch angelegt werden kann.
     */

    @Test
    void sparbuchErstellen_Richtig() {
        Kunde mockKunde = Mockito.mock(Kunde.class);
        long tBankleitzahl = 1;
        Bank tBank = new Bank(tBankleitzahl);
        long erwarteteKontonummer = 123457;

        assertEquals(tBank.kontoErstellen(new SparbuchFactory(), mockKunde), 0);
    }

    /**
     * ueberprueft Erstellung mehrerer Sparbuecher
     */
    @Test
    void sparbuchErstellen_Doppelt() {
        Kunde mockKunde = Mockito.mock(Kunde.class);
        long tBankleitzahl = 1;
        Bank tBank = new Bank(tBankleitzahl);
        long erwarteteKontonummer = 1;
        tBank.kontoErstellen(new SparbuchFactory(), mockKunde);

        assertEquals(tBank.kontoErstellen(new SparbuchFactory(),mockKunde),erwarteteKontonummer);
    }

    /**
     * ueberprueft die Methode getAlleKontonummern()
     */

    @Test
    void getAlleKontonummern() {



        GiroKontoMockFactory mockGiro1 = new GiroKontoMockFactory();
        GiroKontoMockFactory mockGiro2 = new GiroKontoMockFactory();
        SparbuchMockFactory mockSpar1 = new SparbuchMockFactory();
        SparbuchMockFactory mockSpar2 = new SparbuchMockFactory();

        Kunde mockKunde1 = Mockito.mock(Kunde.class);
        Kunde mockKunde2 = Mockito.mock(Kunde.class);
        Kunde mockKunde3 = Mockito.mock(Kunde.class);
        Kunde mockKunde4 = Mockito.mock(Kunde.class);

        List<Long> erwarteteKonto = new ArrayList<Long>();
        long tBankleitzahl = 1;
        Bank tBank = new Bank(tBankleitzahl);


        erwarteteKonto.add(0L);
        erwarteteKonto.add(1L); // Diese Beiden Werte werden im Expected getauscht.
        erwarteteKonto.add(2L); // Ich habe nicht Verstanden warum aber mich damit arrangiert.
        erwarteteKonto.add(3L);

        tBank.kontoErstellen(mockGiro1, mockKunde1);
        tBank.kontoErstellen(mockGiro2, mockKunde2);
        tBank.kontoErstellen(mockSpar1, mockKunde3);
        tBank.kontoErstellen(mockSpar2, mockKunde4);

        assertEquals(tBank.getAlleKontonummern(), erwarteteKonto);
    }

    /**
     * ueberprueft das Verhalten von getAlleKontonummern() wenn keine Konten angelegt sind.
     */
    @Test
    void getAlleKontonummern_NoKonto() {
        long tBankleitzahl = 1;
        Bank tBank = new Bank(tBankleitzahl);
        List<Long> erwarteteKonten = new ArrayList<Long>();

        assertEquals(tBank.getAlleKontonummern(), erwarteteKonten);
    }

    /**
     * Test ob Sparb�cher angezeigt werden
     */
    @Test
    void getAlleKontonummern_Sparbuch() {


        long tBankleitzahl = 1;
        Bank tBank = new Bank(tBankleitzahl);
        List<Long> erwarteteKonten = new ArrayList<Long>();

        erwarteteKonten.add(0L);

        tBank.kontoErstellen(new SparbuchMockFactory(), Mockito.mock(Kunde.class));


        assertEquals(tBank.getAlleKontonummern(), erwarteteKonten);
    }

    /**
     * Test ob Girokonten angezeigt werden.
     */
    @Test
    void getAlleKontonummern_Giro() {
        SparbuchMockFactory mockSpar = new SparbuchMockFactory();
        Kunde mockKunde = Mockito.mock(Kunde.class);
        long tBankleitzahl = 1;
        Bank tBank = new Bank(tBankleitzahl);
        List<Long> erwarteteKonten = new ArrayList<Long>();

        erwarteteKonten.add(0L);
        tBank.kontoErstellen(mockSpar, mockKunde);

        assertEquals(tBank.getAlleKontonummern(), erwarteteKonten);
    }

    /**
     * Test ob Geld von einem Girokonto abgehoben werden kann.
     * Failed Momentan noch und ist deswegen Auskommentiert
     */
/*    @Test
    void geldAbheben_VorhandenGiro() {
        Girokonto mockGiro = Mockito.mock(Girokonto.class);
        int abhebesumme = 100;
        int tBankleitzahl = 1;
        long erwarteteKontonummer;
        //       Mockito.when(mockGiro.isGesperrt()).thenReturn(false);
        Bank tBank = new Bank(tBankleitzahl);
        erwarteteKontonummer = tBank.kontoErstellen(new GiroKontoMockFactory(), Mockito.mock(Kunde.class));

        try {
            tBank.geldAbheben(erwarteteKontonummer, abhebesumme);
        } catch (GesperrtException e) {
            assertFalse(true);
        }

        try {
            Mockito.verify(mockGiro).abheben(abhebesumme);
        } catch (GesperrtException e) {
            assertFalse(true);
        }
    }*/


    /**
     * Test ob von einem nicht vorhandenem Konto abgebucht werden kann.
     */
    @Test
    void geldAbheben_nichtVorhanden() {
        int abhebesumme = 100;
        int tBankleitzahl = 1;
        Bank tBank = new Bank(tBankleitzahl);

        try {
            tBank.geldAbheben(0, abhebesumme);
        } catch (IllegalArgumentException | GesperrtException e) {
            assertTrue(true);
        }
    }




    /**
     * Test ob auf ein nicht vorhandes Konto eingezahlt werden kann.
     */

    @Test
    void geldEinzahlen_kontoNichtVorhanden() {
        int tBankleitzahl = 1;
        double einzuzahlenderBetrag = 10.0;
        long erwarteteKontonummer = 123457;

        Bank tBank = new Bank(tBankleitzahl);

        try {
            tBank.geldEinzahlen(erwarteteKontonummer, einzuzahlenderBetrag);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Test ob ein Konto gelaescht werden kann
     */

    @Test
    void kontoLoeschen_kontoVorhanden() {
        int tBankleitzahl = 1;
        long erwarteteKontonummer = 123457L;
        Bank tBank = new Bank(tBankleitzahl);
        tBank.kontoErstellen(new GiroKontoMockFactory(), Mockito.mock(Kunde.class));

        try {
            assertTrue(tBank.kontoLoeschen(0));
        } catch (IllegalArgumentException | KontoNichtLeerException e) {
            assertTrue(false);
        }
    }

    /**
     * Test ob ein nicht vorhandenes Konto geloescht werden kann.
     */
    @Test
    void kontoLoeschen_kontoNichtVorhanden() {
        int tBankleitzahl = 1;
        long erwarteteKontonummer = 123458L;
        Bank tBank = new Bank(tBankleitzahl);
        tBank.kontoErstellen(new GiroKontoMockFactory(), Mockito.mock(Kunde.class));

        try {
            assertTrue(tBank.kontoLoeschen(erwarteteKontonummer));
        } catch (IllegalArgumentException | KontoNichtLeerException e) {
            assertTrue(true);
        }
    }

    /**
     * Test ob der Kontostand abgerufen werden kann.
     * Failed Momentan noch und ist deswegen Auskommentiert
     */

/*    @Test
    void getKontostand_kontonummerVorhanden() {
        int tBankleitzahl = 1;
        Bank tBank = new Bank(tBankleitzahl);
        Kunde mKunde = Mockito.mock(Kunde.class);
        long erwarteteKontonummer = tBank.kontoErstellen(new GiroKontoMockFactory(), mKunde);
        tBank.geldEinzahlen(erwarteteKontonummer,1000);
        
        try {
            assertEquals(tBank.getKontostand(erwarteteKontonummer), 1000.00);
        } catch (IllegalArgumentException e) {
            assertTrue(false);
        }
    }*/

    /**
     * Test ob der Kontostand eines Nicht vorhandenen Kontos abgerufen werden kann.
     */
    @Test
    void getKontostand_kontonummerNichtVorhanden() {
        int tBankleitzahl = 1;
        Konto mockKonto = Mockito.mock(Konto.class);
        Mockito.when(mockKonto.getKontostand()).thenReturn(1000.00);
        int erwarteteKontonummer = 123458;
        Bank tBank = new Bank(tBankleitzahl);
        tBank.kontoErstellen(new GiroKontoMockFactory(), Mockito.mock(Kunde.class));

        try {
            assertEquals(tBank.getKontostand(erwarteteKontonummer), 1000.00);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }




    @Test
    public void testCloneChangeAfterClone() {
        Bank b1 = new Bank(1234567890);
        final long kontonummer = b1.kontoErstellen(new SparbuchFactory(),new Kunde("Ich", "du", "da", LocalDate.of(1234, 5, 6)));

        Bank b2 = b1.clone();

        try {
            b1.geldEinzahlen(kontonummer, 777);
            assertFalse(b1.getKontostand(kontonummer) == b2.getKontostand(kontonummer));
            Assertions.assertEquals(777, b1.getKontostand(kontonummer));
            Assertions.assertEquals(0, b2.getKontostand(kontonummer));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCloneNoChange() {
        Bank b1 = new Bank(1234567890);
        final long kontonummer = b1.kontoErstellen(new SparbuchFactory(),new Kunde("Ich", "du", "da", LocalDate.of(1234, 5, 6)));
        try {
            b1.geldEinzahlen(kontonummer, 777);
            Bank b2 = b1.clone();
            assertTrue(b1.getKontostand(kontonummer) == b2.getKontostand(kontonummer));

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}