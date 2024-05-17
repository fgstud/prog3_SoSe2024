package com.prog3.bankprojekt.verarbeitung;

//import com.google.common.primitives.Doubles;

import static com.prog3.bankprojekt.verarbeitung.Waehrung.EUR;
//Abkürzung des Klassennamens ist jetzt erlaubt

/**
 * stellt ein allgemeines Bank-Konto dar
 */
public abstract class Konto implements Comparable<Konto>
{
	//Methode ist IGITTIGITT!
	//Trenne Verarbeitung von Ein- und Ausgabe!
	public void aufDerKonsoleAusgeben()
	{
		System.out.println(this.toString());
	}
	/** 
	 * der Kontoinhaber
	 */
	private Kunde inhaber;

	/**
	 * die Kontonummer
	 */
	private final long nummer;

	/**
	 * der aktuelle Kontostand
	 */
	private double kontostand;

	/**
	 * die aktuelle Waehrung
	 */
	private Waehrung waehrung;

	/**
	 * setzt den aktuellen Kontostand
	 * @param kontostand neuer Kontostand
	 */
	protected void setKontostand(double kontostand) {
		this.kontostand = kontostand;
	}

	/**
	 * Wenn das Konto gesperrt ist (gesperrt = true), können keine Aktionen daran mehr vorgenommen werden,
	 * die zum Schaden des Kontoinhabers wären (abheben, Inhaberwechsel)
	 */
	private boolean gesperrt;

	/**
	 * Setzt die beiden Eigenschaften kontoinhaber und kontonummer auf die angegebenen Werte,
	 * der anfängliche Kontostand wird auf 0 gesetzt.
	 *
	 * @param inhaber der Inhaber
	 * @param kontonummer die gewünschte Kontonummer
	 * @throws IllegalArgumentException wenn der inhaber null ist
	 */
	public Konto(Kunde inhaber, long kontonummer) {
		if(inhaber == null)
			throw new IllegalArgumentException("Inhaber darf nicht null sein!");
		this.inhaber = inhaber;
		this.nummer = kontonummer;
		this.kontostand = 0;
		this.gesperrt = false;
		this.waehrung = EUR;
	}
	
	/**
	 * setzt alle Eigenschaften des Kontos auf Standardwerte
	 */
	public Konto() {
		this(Kunde.MUSTERMANN, 1234567);
	}

	/**
	 * liefert den Kontoinhaber zurück
	 * @return   der Inhaber
	 */
	public final Kunde getInhaber() {
		return this.inhaber;
	}
	
	/**
	 * setzt den Kontoinhaber
	 * @param kinh   neuer Kontoinhaber
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn kinh null ist
	 */
	public final void setInhaber(Kunde kinh) throws GesperrtException{
		if (kinh == null)
			throw new IllegalArgumentException("Der Inhaber darf nicht null sein!");
		if(this.gesperrt)
			throw new GesperrtException(this.nummer);        
		this.inhaber = kinh;

	}
	
	/**
	 * liefert den aktuellen Kontostand
	 * @return   Kontostand
	 */
	public double getKontostand() {
		return kontostand;
	}

	/**
	 * liefert die Kontonummer zurück
	 * @return   Kontonummer
	 */
	public final long getKontonummer() {
		return nummer;
	}

	/**
	 * liefert zurück, ob das Konto gesperrt ist oder nicht
	 * @return true, wenn das Konto gesperrt ist
	 */
	public boolean isGesperrt() { //Achtung: nicht get... bei booleschen Werten
		return gesperrt;
	}
	
	/**
	 * Erhöht den Kontostand um den eingezahlten Betrag.
	 *
	 * @param betrag double
	 * @throws IllegalArgumentException wenn der betrag negativ ist 
	 */
	public void einzahlen(double betrag) {
		if (betrag < 0 ||!Double.isFinite(betrag)) {
			throw new IllegalArgumentException("Falscher Betrag");
		}
		setKontostand(getKontostand() + betrag);
	}


	/**
	 *  Erhöht den Kontostand um den eingezahlten Betrag in der entsprechenden Waehrung
	 * @param betrag double
	 * @param w Waehrung
	 * @throws IllegalArgumentException
	 */
	public void einzahlen(double betrag, Waehrung w) {
		if (betrag < 0 ||!Double.isFinite(betrag)) {
			throw new IllegalArgumentException("Falscher Betrag");
		}
		if (this.waehrung == w) {
			setKontostand(getKontostand() + betrag);
		} else if (w.equals(EUR)) {
			setKontostand(getKontostand() + this.waehrung.euroInWaehrungUmrechnen(betrag));
		} else {
			double kontostandInEUR = this.waehrung.waehrungInEuroUmrechnen(getKontostand());
			double betragInEUR = w.waehrungInEuroUmrechnen(betrag);
			double addBetragOfKontostandAndBetragInEUR = kontostandInEUR + betragInEUR;
			setKontostand(this.waehrung.euroInWaehrungUmrechnen(addBetragOfKontostandAndBetragInEUR));
		}

	}

	/**
	 * Wechselt die Waehrung des aktuellen Kontos
	 * @param neu Waehrung
	 */
	public void waehrungswechsel(Waehrung neu) {
		Double kontostandInEUR = this.waehrung.waehrungInEuroUmrechnen(this.kontostand);
		this.kontostand = neu.euroInWaehrungUmrechnen(kontostandInEUR);
		this.waehrung = neu;
	}

	/**
	 * Verringert den Kontostand um betrag in waehrung
	 *
	 * @param betrag double
	 * @param waehrung Waehrung
	 * @return true wenn Abheben erfolgeich
	 * @throws GesperrtException wenn Konto gesperrt
	 * @throws IllegalArgumentException Wenn der Betrag netatigv ist
	 */
	public boolean abheben(double betrag, Waehrung waehrung) throws GesperrtException{
		if (this.gesperrt) {
			throw new GesperrtException(this.nummer);
		}
		if (betrag <= 0 || !Double.isFinite(betrag)) {
			throw new IllegalArgumentException("Falscher Betrag");
		}

		if (this.waehrung == waehrung) {
			setKontostand(getKontostand() - betrag);
			return true;
		} else if (waehrung.equals(EUR)) {
			setKontostand(getKontostand() - this.waehrung.euroInWaehrungUmrechnen(betrag));
		}
		double kontostandInEUR = this.waehrung.waehrungInEuroUmrechnen(getKontostand());
		double betragInEUR = waehrung.waehrungInEuroUmrechnen(betrag);
		double diffBetweenKontostandAndBetragInEUR = kontostandInEUR - betragInEUR;
		setKontostand(this.waehrung.euroInWaehrungUmrechnen(diffBetweenKontostandAndBetragInEUR));
		return true;
	}

	@Override
	public String toString() {
		String ausgabe;
		ausgabe = "Kontonummer: " + this.getKontonummerFormatiert()
				+ System.getProperty("line.separator");
		ausgabe += "Inhaber: " + this.inhaber;
		ausgabe += "Aktueller Kontostand: " + getKontostandFormatiert() + " ";
		ausgabe += this.getGesperrtText() + System.getProperty("line.separator");
		return ausgabe;
	}

	/**
	 * Mit dieser Methode wird der geforderte Betrag vom Konto abgehoben, wenn es nicht gesperrt ist
	 * und die speziellen Abheberegeln des jeweiligen Kontotyps die Abhebung erlauben
	 *
	 * @param betrag double
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn der betrag negativ oder unendlich oder NaN ist 
	 * @return true, wenn die Abhebung geklappt hat, 
	 * 		   false, wenn sie abgelehnt wurde
	 */
	public abstract boolean abheben(double betrag) 
								throws GesperrtException;
	
	/**
	 * sperrt das Konto, Aktionen zum Schaden des Benutzers sind nicht mehr möglich.
	 */
	public void sperren() {
		this.gesperrt = true;
	}

	/**
	 * entsperrt das Konto, alle Kontoaktionen sind wieder möglich.
	 */
	public void entsperren() {
		this.gesperrt = false;
	}
	
	
	/**
	 * liefert eine String-Ausgabe, wenn das Konto gesperrt ist
	 * @return "GESPERRT", wenn das Konto gesperrt ist, ansonsten ""
	 */
	public String getGesperrtText()
	{
		if (this.gesperrt)
		{
			return "GESPERRT";
		}
		else
		{
			return "";
		}
	}
	
	/**
	 * liefert die ordentlich formatierte Kontonummer
	 * @return auf 10 Stellen formatierte Kontonummer
	 */
	public String getKontonummerFormatiert()
	{
		return String.format("%10d", this.nummer);
	}
	
	/**
	 * liefert den ordentlich formatierten Kontostand
	 * @return formatierter Kontostand mit 2 Nachkommastellen und Währungssymbol
	 */
	public String getKontostandFormatiert()
	{
		return String.format("%10.2f %2$s" , this.getKontostand(), this.waehrung);
	}
//Ändern Sie die Methode getKontostandFormatiert() in Konto so ab, dass immer
//die aktuelle Währung angezeigt wird.
	/**
	 * liefert die aktuelle Waehrung
	 * @return die Waehrung
	 */
	public Waehrung getAktuelleWaehrung() {
		return this.waehrung;
	}


	/**
	 * Vergleich von this mit other; Zwei Konten gelten als gleich,
	 * wen sie die gleiche Kontonummer haben
	 * @param other das Vergleichskonto
	 * @return true, wenn beide Konten die gleiche Nummer haben
	 */
	@Override
	public boolean equals(Object other)
	{
		if(this == other)
			return true;
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		if(this.nummer == ((Konto)other).nummer)
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return 31 + (int) (this.nummer ^ (this.nummer >>> 32));
	}

	@Override
	public int compareTo(Konto other)
	{
		if(other.getKontonummer() > this.getKontonummer())
			return -1;
		if(other.getKontonummer() < this.getKontonummer())
			return 1;
		return 0;
	}
}
