package Zensurenspiegel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Zensurenspiegel {
    Collection<Integer> Noten = new ArrayList<Integer>();
    int highestNote;

    public Zensurenspiegel(int highestNote) {
        if (highestNote <= 0) {
            throw new IllegalArgumentException("Hoechste Note muss mindestens 1 sein!");
        }
        this.highestNote = highestNote;
    }

    public void run() {
        int number = 0;
        Scanner scanner = new Scanner(System.in);
        while (number != highestNote+1) {
            System.out.println("Note: ");
            number = scanner.nextInt();

        }
    }
}
