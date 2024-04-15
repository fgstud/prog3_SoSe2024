package sorters;

import bruch.Bruch;
import java.util.Comparator;

public class SortByValue implements Comparator<Bruch> {
    @Override
    public int compare(Bruch o1, Bruch o2) {
        return o1.compareTo(o2);
    }
}
