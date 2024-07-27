package bankprojekt.verarbeitung;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Eine Aktie, die ständig ihren Kurs verändert
 *
 * @author Doro
 */
public class Aktie {

	private static Map<String, Aktie> alleAktien = new HashMap<>();
	private String wkn;
	private double kurs;
	private Lock aktieLock = new ReentrantLock();
	private Condition condition = aktieLock.newCondition();

	/**
	 * gibt die Aktie mit der gewünschten Wertpapierkennnummer zurück
	 *
	 * @param wkn Wertpapierkennnummer
	 * @return Aktie mit der angegebenen Wertpapierkennnummer oder null, wenn es diese WKN
	 * nicht gibt.
	 */
	public static Aktie getAktie(String wkn) {
		return alleAktien.get(wkn);
	}

	/**
	 * erstellt eine neu Aktie mit den angegebenen Werten
	 *
	 * @param wkn Wertpapierkennnummer
	 * @param k   aktueller Kurs
	 * @throws IllegalArgumentException wenn einer der Parameter null bzw. negativ ist
	 *                                  oder es eine Aktie mit dieser WKN bereits gibt
	 */
	public Aktie(String wkn, double k) {
		if (wkn == null || k <= 0 || alleAktien.containsKey(wkn))
			throw new IllegalArgumentException();
		this.wkn = wkn;
		this.kurs = k;
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		Aktie currentAktie = this;
		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				updateValue(wkn);
			}
		}, 0, 1, TimeUnit.SECONDS);

		//thread der kurs ändert
		alleAktien.put(wkn, this);
	}

	/**
	 * Wertpapierkennnummer
	 *
	 * @return WKN der Aktie
	 */
	public String getWkn() {
		return wkn;
	}

	/**
	 * aktueller Kurs
	 *
	 * @return Kurs der Aktie
	 */
	public double getKurs() {
		return kurs;
	}

	private void updateValue(String wkn) {
		this.kurs += new Random().nextDouble(7) - 3;
		if (this.kurs < 0) {
			this.kurs = 0;
		}
		System.out.println(wkn + " neuer wert: " + this.kurs);
		aktieLock.lock();
		alleAktien.put(wkn, this);
		condition.signalAll();
		aktieLock.unlock();
	}

	/**
	 * wartet darauf das der Preis sinkt
	 *
	 * @param targetValue Preis
	 * @throws InterruptedException
	 */
	public void warteAufBuyPreis(double targetValue) throws InterruptedException {
		aktieLock.lock();
		try {
			while (this.kurs > targetValue) {
				System.out.println("waiting..." + targetValue);
				condition.await();
			}
		} finally {
			System.out.println("found..." + targetValue);
			aktieLock.unlock();
		}
	}

	/**
	 * Wartet darauf das der Preis ueber den targetValue steight
	 * @param targetValue gewuenschter Preis
	 * @throws InterruptedException
	 */

	public void warteAufSellPreis(double targetValue) throws InterruptedException {
		aktieLock.lock();
		try {
			while (this.kurs < targetValue) {
				condition.await();
			}
		} finally {
			aktieLock.unlock();
		}
	}
}
