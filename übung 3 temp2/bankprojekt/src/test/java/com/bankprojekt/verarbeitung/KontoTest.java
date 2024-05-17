package com.bankprojekt.verarbeitung;

import com.prog3.bankprojekt.verarbeitung.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class KontoTest {

    /**
     * Tests Waehrungswechsel
     */
    @Test
    protected void waehrungswechsel() {
        Kunde ich = new Kunde("Dorothea", "Hubrich", "zuhause", LocalDate.parse("1976-07-13"));
        Girokonto konto = new Girokonto(ich, 1234, 1000.0);
        konto.waehrungswechsel(Waehrung.DOBRA);
        Assertions.assertEquals(konto.getAktuelleWaehrung(),Waehrung.DOBRA);
    }

    /**
     * tests abheben
     * @throws GesperrtException
     */
    @Test
    protected void abheben() throws GesperrtException {
        Kunde ich = new Kunde("Dorothea", "Hubrich", "zuhause", LocalDate.parse("1976-07-13"));
        Konto konto = new Girokonto(ich, 1234, 1000.0);
        konto.einzahlen(100);
        konto.abheben(50);

        Assertions.assertEquals(konto.getKontostand(),50);
    }
}