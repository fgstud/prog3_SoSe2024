package bankprojekt.verarbeitung;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Aktienkonto extends Konto {
    private double kontostand;
    private Map<String, Integer> aktienDepot;

    /**
     * Abheben methode
     * @param betrag double betrag der abgehoben wird
     * @return erfolg
     * @throws GesperrtException
     */
    @Override
    public boolean abheben(double betrag) throws GesperrtException {
        if (betrag <= kontostand) {
            kontostand -= betrag;
            return true;
        }
        return false;
    }

    @Override
    protected void bereiteAbhebenNach(double betrag) {

    }

    @Override
    protected boolean darfAbheben(double betrag) {
        return false;
    }

    @Override
    protected void bereiteAbhebenVor() {

    }


    /**
     * Konstruktor fuer das Konto
     * @param inhaber
     * @param kontonummer
     */
    public Aktienkonto(Kunde inhaber, long kontonummer) {
        super(inhaber, kontonummer);
        this.aktienDepot = new HashMap<String, Integer>();
        this.kontostand = 0;
    }

    /**
     * erhoeht kontostand um betrag
     * @param betrag double betrag
     */
    public void einzahlen(double betrag) {
        this.kontostand += betrag;
    }



    /**
     * erstellt einen Kaufauftrag fuer eine Anzahl einer Aktie mit einem Hoechstpreis zu dem eingekauft werden soll
     * @param wkn Kennung der Aktie
     * @param anzahl anyahl
     * @param hoechstpreis hoechstpreis
     * @return gesammte Kosten
     * @throws InterruptedException
     */
    public Future<Double> kaufauftrag(String wkn, int anzahl, double
            hoechstpreis) throws InterruptedException {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);


        Callable<Double> task = () -> {
            try {
                return kaufeAktie(wkn,anzahl,hoechstpreis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        return service.submit(task);
    }

    /**
     * erstellt einen Kaufauftrag fuer eine Aktie mit einem Mindestpreis zu dem verkauft werden soll
     * @param wkn Kennung der Aktie
     * @param minimalpreis
     * @return gesamterloes
     */
    public Future<Double> verkaufauftrag(String wkn, double minimalpreis) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);


        Callable<Double> task = () -> {
            try {
                return verkaufeAktie(wkn,minimalpreis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        return service.submit(task);

    }

    /**
     * erstellt einen Kaufauftrag fuer eine Anzahl einer Aktie mit einem Hoechstpreis zu dem eingekauft werden soll
     * @param wkn Kennung der Aktie
     * @param anzahl
     * @param hoechstpreis
     * @return Wert als Double
     * @throws InterruptedException
     */
    private Double kaufeAktie(String wkn, int anzahl, double hoechstpreis) throws InterruptedException {
        Aktie aktie = Aktie.getAktie(wkn);
        System.out.println("wanna buy: " + aktie.toString());
        if (aktie != null) {
            aktie.warteAufBuyPreis(hoechstpreis);
            double gesamtPreis = anzahl * aktie.getKurs();
            if (gesamtPreis < kontostand) {
                kontostand -= gesamtPreis;
                aktienDepot.put(wkn, anzahl);
                System.out.println("BOUGHT - " +aktie.getWkn() + " gekauft bei: [" + aktie.getKurs() + "] neuer Kontostand ist: [" + kontostand+ "]");
                return gesamtPreis;
            }
        }
        System.out.println("pleite");
        return 0.0;
    }

    /**
     * erstellt einen Kaufauftrag fuer eine Aktie mit einem Mindestpreis zu dem verkauft werden soll
     * @param wkn Kennung der Aktie
     * @param minimalpreis
     * @return Wert als Double
     * @throws InterruptedException
     */
    private Double verkaufeAktie(String wkn,double minimalpreis) throws InterruptedException {
        Aktie aktie = Aktie.getAktie(wkn);
        System.out.println("wanna buy: " + aktie.toString());
        if (aktie != null && aktienDepot.containsKey(wkn)) {
            aktie.warteAufSellPreis(minimalpreis);
            double gesamtPreis = aktienDepot.get(wkn) * aktie.getKurs();
            kontostand += gesamtPreis;
            System.out.println("SOLD - " + aktie.getWkn() + " verkauft bei: [" + aktie.getKurs() + "] neuer Kontostand ist: [" + kontostand+ "]");
            return gesamtPreis;
        }
        System.out.println("nope");
        return 0.0;
    }
}

