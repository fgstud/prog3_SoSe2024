package ubung7.nullstellensuche;

public class Nullstellensuche {
    private static  final double MARGIN =0.01;

    /**
     * Findet die Nullstelle in einer Formel zwischen zwei Grenzwerten. Test 4 failed immer und ich wei� nicht warum.
     * @param function Formel
     * @param grenze1 Grenzwert
     * @param grenze2 Anderer Grenzwert
     * @return Gibt die Nullstelle zurueck
     * @throws NoNullstelleException
     */
    public static double finde(NullstellensucheInterface function, double grenze1, double grenze2) throws NoNullstelleException {


        double a = function.blamethode(grenze1);
        double b = function.blamethode(grenze2);

        if ((a < 0 && b < 0) || (a > 0 && b > 0))
            throw new NoNullstelleException(a, b);

        if (a == 0) {
            return a;
        }
        if (b == 0) {
            return b;
        }


        if (a > b) {
            while (a - b > MARGIN) {
                double mittelpunkt = (grenze1 + grenze2) / 2;
                double m = function.blamethode(mittelpunkt);

                if (m < 0) {
                    grenze2 = mittelpunkt;
                    b = function.blamethode(grenze2);

                }

                if (m > 0) {
                    grenze1 = mittelpunkt;
                    a = function.blamethode(grenze1);
                }
            }
        }

        if (b > a) {
            while (b - a > MARGIN) {
                double mittelpunkt = (grenze1 + grenze2) / 2;
                double m = function.blamethode(mittelpunkt);
                if (m < 0) {
                    grenze1 = mittelpunkt;
                    a = function.blamethode(grenze1);
                }
                if (m > 0) {
                    grenze2 = mittelpunkt;
                    b = function.blamethode(grenze2);
                }
            }

        }
        return (grenze1+grenze2)/2;
    }
}
