package com.bankprojekt.spielereien;

import com.bankprojekt.verarbeitung.*;

import java.time.LocalDate;

/**
 * spielt ein wenig mit den Währungsmethoden des Bankprojektes herum
 * @author Doro
 *
 */
public class Waehrungsspielereien {

	/**
	 * ein kleines Programm mit 2 Konten in verschiedenen Währungen
	 * @param args wird nicht verwendet
	 */
	public static void main(String[] args)  {
		try {
			Kunde ich = new Kunde("Dorothea", "Hubrich", "zuhause", LocalDate.parse("1976-07-13"));
			Konto meinKonto = new Girokonto(ich, 1234, 1000.0);
			
			meinKonto.waehrungswechsel(Waehrung.ESCUDO);
			System.out.println("Nach Währungswechsel nach Escudos: " + meinKonto);
			meinKonto.einzahlen(1000, Waehrung.EUR);
			System.out.println("1000 EUR eingezahlt: " + meinKonto);
			meinKonto.einzahlen(100_000, Waehrung.FRANC);
			System.out.println("100.000 Franc eingezahlt: " + meinKonto);
			
			meinKonto.waehrungswechsel(Waehrung.DOBRA);
			System.out.println("Nach Währungswechsel nach Dobra: " + meinKonto);
			boolean hatGeklappt;
			hatGeklappt = meinKonto.abheben(1000);
			System.out.println("1000 Dobra abgehoben: " + hatGeklappt + System.lineSeparator() + meinKonto);
			hatGeklappt = meinKonto.abheben(1000, Waehrung.EUR);
			System.out.println("1000 EUR abgehoben: " + hatGeklappt + System.lineSeparator() + meinKonto);
			hatGeklappt = meinKonto.abheben(10_000, Waehrung.FRANC);
			System.out.println("10.000 Franc abgehoben: " + hatGeklappt + System.lineSeparator() + meinKonto);
			
			meinKonto = new Sparbuch(ich, 9876);
			meinKonto.waehrungswechsel(Waehrung.ESCUDO);
			System.out.println("Nach Währungswechsel nach Escudo: " + meinKonto);
			meinKonto.einzahlen(1000, Waehrung.EUR);
			System.out.println("1000 EUR eingezahlt: " + meinKonto);
			meinKonto.einzahlen(100_000, Waehrung.FRANC);
			System.out.println("100.000 Franc eingezahlt: " + meinKonto);
			
			meinKonto.waehrungswechsel(Waehrung.DOBRA);
			System.out.println("Nach Währungswechsel nach Dobra: " + meinKonto);
			hatGeklappt = meinKonto.abheben(1000);
			System.out.println("1000 MKD abgehoben: " + hatGeklappt + System.lineSeparator() + meinKonto);
			hatGeklappt = meinKonto.abheben(1000, Waehrung.EUR);
			System.out.println("1000 EUR abgehoben: " + hatGeklappt + System.lineSeparator() + meinKonto);
			hatGeklappt = meinKonto.abheben(1000, Waehrung.FRANC);
			System.out.println("1000 Franc abgehoben: " + hatGeklappt + System.lineSeparator() + meinKonto);
		} catch (GesperrtException e)
		{}  //nichts tun, tritt hier nicht auf
	}

}
