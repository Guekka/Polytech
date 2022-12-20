import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MinTermTest {
    @Test
    void test1() {
        var input = 5;
        var count = 4;

        var minTerm = new MinTerm(input, count);
        var expected = List.of(MinTermState.UNCHECKED, MinTermState.CHECKED, MinTermState.UNCHECKED, MinTermState.CHECKED);

        assertEquals(expected, minTerm.data());
    }

    @Test
    void test2() {
        var input = 16;
        var count = 3;

        assertThrows(IllegalArgumentException.class, () -> new MinTerm(input, count));
    }

    @Test
    void test3() {
        var input = 0;
        var count = 3;

        var minTerm = new MinTerm(input, count);
        var expected = List.of(MinTermState.UNCHECKED, MinTermState.UNCHECKED, MinTermState.UNCHECKED);

        assertEquals(expected, minTerm.data());
    }

    @Test
    void countUnchecked() {
        var input = 5;
        var count = 4;

        var minTerm = new MinTerm(input, count);
        var expected = 2;

        assertEquals(expected, minTerm.countUnchecked());
    }
}