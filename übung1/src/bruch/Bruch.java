package bruch;

public class Bruch implements Comparable<Bruch>{

    private int nominator;
    private int denominator;

    public Bruch(int nominator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Nenner darf nicht 0 sein!");
        }
        this.nominator = nominator;
        this.denominator = denominator;
    }

    public int getNominator() {
        return nominator;
    }

    public int getDenominator() {
        return denominator;
    }


    @Override
    public int compareTo(Bruch b) {
        return Double.compare(this.ausrechnen(), b.ausrechnen());
    }

    @Override
    public String toString() {
        return "[" + nominator +
                "/" + denominator + "]";
    }

    public Bruch multiplizieren(Bruch bruch) {
        Bruch ausgabe = new Bruch(this.nominator * bruch.nominator, this.denominator * bruch.denominator);
        ausgabe.kuerzen();
        return ausgabe;
    }

    public double ausrechnen() {
        return Double.valueOf(this.nominator) / Double.valueOf(this.denominator);
    }

    public void kuerzen() {
        int ggt = findGgtEuclid(this.nominator, this.denominator);
        this.nominator = this.nominator / ggt;
        this.denominator = this.denominator / ggt;
    }

    public Bruch kehrwert() {
        return new Bruch(this.denominator, this.nominator);
    }

    public Bruch dividieren(Bruch b) {
        return multiplizieren(b.kehrwert());
    }

    private int findGgtEuclid(int a, int b) {
        int temp;
        while (b != 0) {
            temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}

