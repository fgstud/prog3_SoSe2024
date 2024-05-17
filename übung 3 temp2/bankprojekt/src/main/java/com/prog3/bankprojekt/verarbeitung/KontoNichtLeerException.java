package com.prog3.bankprojekt.verarbeitung;

public class KontoNichtLeerException extends Exception {
    public KontoNichtLeerException(long kontonummer)
    {
        super("Konto ist nicht leer: " + kontonummer);
    }
}
