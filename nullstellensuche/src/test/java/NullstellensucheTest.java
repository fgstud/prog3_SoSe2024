import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ubung7.nullstellensuche.NoNullstelleException;
import ubung7.nullstellensuche.Nullstellensuche;

import static org.junit.jupiter.api.Assertions.*;

class NullstellensucheTest {

    @Test
    void test1() throws NoNullstelleException {
        Assertions.assertEquals(2.5, Nullstellensuche.finde((x -> x * 2 - 5), -100, 100), 0.01);
    }
    @Test
    void test2() throws NoNullstelleException {
        Assertions.assertEquals(Math.log(7)/3, Nullstellensuche.finde((x -> (float) (Math.pow(Math.E, 3 * x) - 7)), -10, 10), 0.01);
    }

    @Test
    void test3() throws NoNullstelleException {
        Assertions.assertEquals(5, Nullstellensuche.finde((x -> (5 - x) / (x * 3 + 2)), 0, 100), 0.01);
    }

    @Test
    void test4() throws NoNullstelleException{
        Assertions.assertThrows(NoNullstelleException.class, () -> Nullstellensuche.finde((x -> x * x + 1), 0, 100));
    }
}