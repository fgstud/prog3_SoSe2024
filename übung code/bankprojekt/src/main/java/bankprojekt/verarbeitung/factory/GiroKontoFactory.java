package bankprojekt.verarbeitung.factory;

import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;

public class GiroKontoFactory implements Kontofabrik{
    private double dispo = 0;

    public GiroKontoFactory(double dispo) {
        this.dispo = dispo;
    }

    public GiroKontoFactory() {
        this.dispo = 0;
    }

    @Override
    public Konto kontoErstellen(Kunde inhaber, long letzteKontonummer) {
        return new Girokonto(inhaber, letzteKontonummer, dispo);
    }

    public void setDispo(double dispo) {
        this.dispo = dispo;
    }


}
