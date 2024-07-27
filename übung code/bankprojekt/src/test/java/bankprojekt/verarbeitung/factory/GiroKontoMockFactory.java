package bankprojekt.verarbeitung.factory;

import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import org.mockito.Mockito;

public class GiroKontoMockFactory implements Kontofabrik{
    private double dispo = 0;



    @Override
    public Konto kontoErstellen(Kunde inhaber, long letzteKontonummer) {
        return Mockito.mock(Girokonto.class);
    }

    public void setDispo(double dispo) {
        this.dispo = dispo;
    }


}
