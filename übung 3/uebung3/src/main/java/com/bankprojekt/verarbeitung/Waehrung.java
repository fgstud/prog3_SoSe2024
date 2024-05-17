package com.bankprojekt.verarbeitung;

public enum Waehrung {
    EUR(1.0f), ESCUDO(109.8269f), DOBRA(24304.7429f), FRANC(490.92f);

    /**
     * umtauschwert der Waehrung in Euro
     */
    private double umtauschwert;

    public double getUmtauschwert() {
        return this.umtauschwert;
    }

    /**
     * rechnet betrag in Waehrung um
     * @param betrag
     * @return betrag in waehrung
     */
    public double euroInWaehrungUmrechnen(double betrag) {
        return this.umtauschwert * betrag;
    }

    /**
     * Rechnet betrag in Euro um
     * @param betrag
     * @return Betrag in euro
     */
    public double waehrungInEuroUmrechnen(double betrag) {
        return betrag / this.umtauschwert;
    }

    /**
     * Setzt die aktuelle Waehrung
     * @param umtauschwert
     */
    private Waehrung(float umtauschwert) {
        this.umtauschwert = umtauschwert;
    }
}
