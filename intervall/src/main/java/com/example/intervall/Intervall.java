package com.example.intervall;

import java.util.Date;
import java.sql.Time;

/**
 * Represents an Intervall
 * @param <T>
 */
public class Intervall<T extends Comparable> {
    /**
     * untere Grenze des Intervalls
     */
    private final T untere;

    /**
     * obere Grenze des intervalls
     */
    private final T obere;

    /**
     * Konstruktor
     * @param untereGrenze
     * @param obereGrenze
     */
    public Intervall(T untereGrenze, T obereGrenze){
        this.untere = untereGrenze;
        this.obere = obereGrenze;
    }

    /**
     * Gibt untere zurueck
     * @return
     */
    public T getUntere() {
        return untere;
    }

    /**
     * gibt obere zurueck
     * @return
     */
    public T getObere() {
        return obere;
    }

    /**
     * gibt zurueck ob der Intervall leer ist
     * @return boolean
     */
    public boolean isLeer() {
        if (this.untere.compareTo(this.obere) > 0) {
            return true;
        }
        return false;
    }

    /**
     * gibt zurueck ob ein Wert im Intervall enthalten ist.
     * @param wert
     * @return boolean
     * @param <T1>
     */
    public <T1 extends Comparable<T>> boolean enthaelt(T1 wert){
        if (wert.compareTo(this.untere) > 0 && wert.compareTo(this.obere) < 0) {
            return true;
        }
        return false;
    }

    /**
     * gibt intervall mit Schnittmenge mit eingabeintervall anderes zurueck
     * @param anderes eingabeintervall
     * @return intervall
     * @param <T2>
     */
    public <T2 extends Comparable<T>> Intervall<T> schnitt(Intervall<T2> anderes) {
        T schnittUntere;
        T schnittObere;
        if (anderes.getUntere().compareTo(this.untere) >= 0) {
            schnittUntere = (T) anderes.getUntere();
        } else {
            schnittUntere = this.untere;
        }
        if (anderes.getObere().compareTo(this.obere) <= 0) {
            schnittObere = (T) anderes.getObere();
        } else {
            schnittObere = this.obere;
        }
        return new Intervall<>(schnittUntere, schnittObere);
    }
}
