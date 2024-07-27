package bankprojekt.verarbeitung.factory;

import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.Sparbuch;

public class SparbuchFactory implements Kontofabrik {

    @Override
    public Konto kontoErstellen(Kunde inhaber, long letzteKontonummer) {
        return new Sparbuch(inhaber, letzteKontonummer);
    }
}
