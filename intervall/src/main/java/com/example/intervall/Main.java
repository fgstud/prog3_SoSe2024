package com.example.intervall;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Main class
 */
public class Main {

    /**
     * main method
     * @param args unused
     */
    public static void main(String[] args) {
        Intervall<String> i1 = new Intervall<>("A", "G");
        Intervall<String> i2 = new Intervall<>("C", "Z");
        Boolean test = i1.enthaelt("D");
        System.out.println(test);
        Intervall<String> testIntervall = i1.schnitt(i2);
        System.out.println(testIntervall.getUntere() + "  " + testIntervall.getObere());
        System.out.println(i1.isLeer());
        Intervall<String> i3 = new Intervall<>("z", "a");
        System.out.println(i3.isLeer());

        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.MAY, 10, 10, 00, 0);
        Date date1 = calendar.getTime();
        calendar.set(2023, Calendar.MAY, 10, 11, 00, 0);
        Date date2 = calendar.getTime();
        Intervall<Date> iDate = new Intervall<>(date1, date2);

        Time time1 = Time.valueOf("10:29:30");
        Time time2 = Time.valueOf("10:40:30");
        Intervall<Time> iTime = new Intervall<>(time1, time2);

        Intervall<Date> dateIntervall = iDate.schnitt(iTime);
        System.out.println(dateIntervall.getUntere() + " " + dateIntervall.getObere());

        Timestamp timestamp1 = Timestamp.valueOf("2023-05-10 10:20:00");
        Timestamp timestamp2 = Timestamp.valueOf("2023-05-10 12:20:00");
        Intervall<Timestamp> iTimestamp = new Intervall<>(timestamp1, timestamp2);

        Intervall<Date> timestampIntervall = iDate.schnitt(iTimestamp);
        System.out.println(timestampIntervall.getUntere()+" "+ timestampIntervall.getObere());
    }
}