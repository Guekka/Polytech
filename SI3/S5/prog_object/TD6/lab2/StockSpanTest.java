package lab2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockSpanTest {

    int[] prices = {100,80,60,70,60,75,85,120};
    int[] expectedPan = {1, 1, 1, 2, 1, 4, 6, 8};

    @Test
    void naiveTest() {
        int[] resultingPan = StockSpan.naive(prices);
        assertArrayEquals(expectedPan,resultingPan);
    }

    @Test
    void smart() {
        int[] resultingPan = StockSpan.smart(prices);
        assertArrayEquals(expectedPan,resultingPan);
    }
}