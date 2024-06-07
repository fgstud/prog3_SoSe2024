package com.prog3.bankprojekt.verarbeitung;

public class KontoNichtLeerException extends Exception {
    /**
     * wird geworfen wenn das Konto beim loeschen nicht leer ist.
     * @param kontonummer des zu loeschenden kontos
     */
    public KontoNichtLeerException(long kontonummer)
    {
        super("Konto ist nicht leer: " + kontonummer);
    }
}
