package Zensurenspiegel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Zensurenspiegel {
    Collection<Integer> noten = new ArrayList<Integer>();
    int highestNote;

    public Zensurenspiegel(int highestNote) {
        if (highestNote <= 0) {
            throw new IllegalArgumentException("Hoechste Note muss mindestens 1 sein!");
        }
        this.highestNote = highestNote;
    }

    public void run() {
        int number;
        double notenTotal= 0;
        boolean continueLoop =true;
        Scanner scanner = new Scanner(System.in);
        do {
            {
                System.out.println("Note: ");
                number = scanner.nextInt();
                noten.add(number);
            }
        } while (number != highestNote + 1);
        for (int note: noten
             ) {
            notenTotal += note;
        }
        double average = notenTotal / noten.size();
        System.out.println("The average of all notes is: " + average);
    }
}
