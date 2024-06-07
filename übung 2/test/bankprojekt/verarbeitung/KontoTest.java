package bankprojekt.verarbeitung;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class KontoTest {

    @Test
    void getKontostandFormatiert() {
        Kunde ich = new Kunde("Dorothea", "Hubrich", "zuhause", LocalDate.parse("1976-07-13"));
        Konto konto = new Girokonto(ich, 1234, 1000.0);
        konto.einzahlen(100);
        konto.waehrungswechsel(Waehrung.ESCUDO);
        Assertions.assertEquals("0.0 ESCUDO", konto.getKontostandFormatiert());
    }
}