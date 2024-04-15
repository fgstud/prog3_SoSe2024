import bruch.Bruch;
import sorters.SortByDiff;
import sorters.SortByValue;

import java.util.Arrays;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        Bruch a = new Bruch(1, 2);
        Bruch b = new Bruch(3, 5);
        Bruch c = new Bruch(4, 7);
        Bruch[] bruchliste = {b, a, c};



        for (Bruch bruch : bruchliste) {
            System.out.print(bruch.toString());
        }
        System.out.println();
        Arrays.sort(bruchliste, new SortByValue());

        for (Bruch bruch : bruchliste) {
            System.out.print(bruch.toString());
        }
        System.out.println();
        Arrays.sort(bruchliste, new SortByDiff());
        for (Bruch bruch : bruchliste) {
            System.out.print(bruch.toString());
        }
    }

}


