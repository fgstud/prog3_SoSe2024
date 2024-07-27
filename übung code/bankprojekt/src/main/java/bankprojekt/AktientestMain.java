package bankprojekt;

import bankprojekt.verarbeitung.Aktie;
import bankprojekt.verarbeitung.Aktienkonto;
import bankprojekt.verarbeitung.Kunde;

import java.util.concurrent.ExecutionException;

public class AktientestMain {
    /**
     * Main methode zum testen der Aktien
     * @param args
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Aktienkonto testKonto = new Aktienkonto(new Kunde(), 100);
        testKonto.einzahlen(4000);
        Thread.sleep(100);
        new Aktie("GOFF", 5);
        new Aktie("EVIL_SUNZ", 30);
        new Aktie("SNAKEBITEZ", 3);

        double kaufeGoffs = -1.0;

        while (kaufeGoffs == -1.0) {
            kaufeGoffs = testKonto.kaufauftrag("GOFF", 3, 7).get();
        }

        kaufeGoffs = -1.0;
        while (kaufeGoffs == -1) {
            kaufeGoffs = testKonto.kaufauftrag("GOFF", 5, 10).get();
        }
        double kaufeSnakebitez = -1.0;
        while (kaufeSnakebitez == -1.0) {
            kaufeSnakebitez = testKonto.kaufauftrag("SNAKEBITEZ",3, 10).get();
        }
        double kaufeEvilSunz = -1.0;
        while (kaufeEvilSunz == -1) {
            kaufeEvilSunz = testKonto.kaufauftrag("EVIL_SUNZ",25, 1.5).get();
        }
        testKonto.verkaufauftrag("GOFF", 15);
        testKonto.verkaufauftrag("SNAKEBITEZ", 20);
        testKonto.verkaufauftrag("EVIL_SUNZ", 1.7);
        kaufeGoffs = -1.0;
        kaufeEvilSunz = -1.0;
        kaufeSnakebitez = -1.0;

        while (kaufeGoffs == -1.0 || kaufeEvilSunz == -1.0 || kaufeSnakebitez == -1.0) {
            kaufeEvilSunz = testKonto.kaufauftrag("EVIL_SUNZ",25, 1.5).get();
            kaufeSnakebitez = testKonto.kaufauftrag("SNAKEBITEZ",3, 10).get();
            kaufeGoffs = testKonto.kaufauftrag("GOFF", 1, 14).get();
        }
        testKonto.verkaufauftrag("GOFF", 15);
        testKonto.verkaufauftrag("SNAKEBITEZ", 20);
        testKonto.verkaufauftrag("EVIL_SUNZ", 2);


    }
}
