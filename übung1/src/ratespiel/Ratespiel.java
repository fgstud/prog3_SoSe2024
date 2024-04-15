package ratespiel;

import com.sun.org.apache.xpath.internal.functions.FuncFalse;

import java.util.Scanner;

public class Ratespiel {
    private int number;
    String regExValidInput = "[1-100]"; // TODO: Fix this regex
    public Ratespiel() {
        this.number = (int) Math.round(Math.random() * 99 + 1);
    }

    public void run() {
        boolean correctGuess = false;
        String userinput;
        int counter = 0;

        while (!correctGuess) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Your guess: ");
            userinput = scanner.next();
            if (!userinput.matches(regExValidInput)) {
                System.out.println("Bitte Zahl zwischen 1 und 100 eingeben");
            } else if (this.number < Integer.parseInt(userinput)) {
                System.out.println("Die gesuchte Zahl ist kleiner als " + userinput);
            } else if (this.number > Integer.parseInt(userinput)) {
                System.out.println("Die gesuchte Zahl ist groesser als " + userinput);
            } else {
                correctGuess = true;
            }
            counter++;
        }
        System.out.println("Anzahl der Versuche: " + counter);
    }
}