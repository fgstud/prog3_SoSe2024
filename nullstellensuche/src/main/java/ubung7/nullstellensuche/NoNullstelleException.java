package ubung7.nullstellensuche;

/**
 * tritt bei einem sch�digenden Zugriff auf ein gesperrtes Konto auf
 * @author Doro
 *
 */
@SuppressWarnings("serial")
public class NoNullstelleException extends Exception {

    /**
     * Keine Nullstelle vorhanden
     * @param a Ein Grenzwert
     * @param b Ein Grenzwert
     */
    public NoNullstelleException(double a, double b)
    {
        super("Keine Nullstelle zwischen " + a + " und: " + b);
    }

}
