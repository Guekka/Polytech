package ads.lab1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecursionTest {

    @Test
    void binaryTest() {
        String [] binary = {"000","001","010","011","100","101","110","111"};
        String[] res = Recursion.binary(3).toArray(new String[0]);
        assertArrayEquals(binary,res);
    }

    @Test
    void words() {
        String [] words = {"AABBB", "ABABB", "ABBAB", "ABBBA", "BAABB", "BABAB", "BABBA", "BBAAB", "BBABA", "BBBAA"};
        String[] res = Recursion.words(2,3).toArray(new String[0]);
        assertArrayEquals(words,res);
    }

    @Test
    void permutations() {
        String [] permutations = { "(1,2,3)", "(1,3,2)", "(2,1,3)", "(2,3,1)", "(3,1,2)", "(3,2,1)"};
        String[] res = Recursion.permutations(3).toArray(new String[0]);;
        assertArrayEquals(permutations,res);

    }


    @Test
    void sum() {
        int[ ] a = new int[]{3, 5, 7, 11};
        assertTrue(Recursion.sum(a,21));
        assertFalse(Recursion.sum(a,13));
    }
}