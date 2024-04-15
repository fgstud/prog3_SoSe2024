package sorters;

import bruch.Bruch;

import java.util.Comparator;

public class SortByDiff implements Comparator<Bruch> {
    @Override
    public int compare(Bruch o1, Bruch o2) {
        int diffO1 = o1.getDenominator() - o1.getNominator();
        int diffO2 = o2.getDenominator() - o1.getNominator();
        if (diffO1 < diffO2) {
            return 1;
        } else if (diffO1 > diffO2) {
            return -1;
        }
        return 0;
    }
}
