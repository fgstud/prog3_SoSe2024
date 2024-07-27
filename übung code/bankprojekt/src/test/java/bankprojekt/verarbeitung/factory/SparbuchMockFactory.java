package bankprojekt.verarbeitung.factory;

import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Sparbuch;
import org.mockito.Mockito;

public class SparbuchMockFactory implements Kontofabrik{
    @Override
    public Konto kontoErstellen(Kunde inhaber, long letzteKontonummer) {
        return Mockito.mock(Sparbuch.class);
    }
}
