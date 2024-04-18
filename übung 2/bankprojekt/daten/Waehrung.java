package daten;

public enum Waehrung {
    EUR(1.0f), ESCUDO(109.8269f), DOBRA(24304.7429f), FRANC(490.92f);

    private double umtauschwert;

    public double getUmtauschwert() {
        return this.umtauschwert;
    }

    public double euroInWaehrungUmrechnen(double betrag) {
        return this.umtauschwert * betrag;
    }

    public double waehrungInEuroUmrechnen(double betrag) {
        return this.umtauschwert / betrag;
    }

    private Waehrung(float umtauschwert) {
        this.umtauschwert = umtauschwert;
    }
}
