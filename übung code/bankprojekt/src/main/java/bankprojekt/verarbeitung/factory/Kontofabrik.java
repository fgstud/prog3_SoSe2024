package bankprojekt.verarbeitung.factory;

import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;

/**
 * Durch das Einsetzen der Fabrik koennen zukuenftig Kontoarten hinzugefuegt werden ohne eine aenderung an der Bankklasse
 * vornehmen zu muessen.
 */
public interface Kontofabrik {

    /**
     * erstellt ein Konto
     * @param inhaber der Inhaber
     * @param letzteKontonummer Die letzte verwendete Kontonummer
     * @return ein Konto
     */
    Konto kontoErstellen(Kunde inhaber, long letzteKontonummer);
}