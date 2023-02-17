import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryManagerTest {
    @Test
    void t1() {
        //Merge categories as given in blog
        List<Minterm> list = getBlogMinterms();
        System.out.println(list);
        int nbBits = 4;
        CategoryManager manager = new CategoryManager(list, nbBits);
        List<Minterm> res = manager.mergeCategories();
        System.out.println(res);
        assertEquals(16, res.size());
        assertFalse(manager.isLastTurn());
    }

    private List<Minterm> getBlogMinterms() {
        // [0000, 0001, 0010, 0100, 0110, 1000, 1001, 1100, 1101, 1110, 1111]
        return createMintermList(0, 1, 2, 4, 6, 8, 9, 12, 13, 14, 15);
    }

    @Test
    void t2() {
        int nbBits = 3;
        List<Minterm> list = createMintermList(3, 0, 1, 2, 3, 4, 7);
        CategoryManager manager = new CategoryManager(list, nbBits);
        MintermCategory category = manager.getCategory(1);
        assertEquals(3, category.size());
        assertEquals(1, manager.getCategory(3).size());
        List<Minterm> res = manager.mergeCategories();
        assertEquals(6, res.size());
        System.out.println(res);
        assertTrue(res.contains(new Minterm(0, 1, -1)));
        assertTrue(res.contains(new Minterm(0, 0, -1)));
        assertTrue(res.contains(new Minterm(-1, 1, 1)));
        assertFalse(manager.isLastTurn());
    }

    private List<Minterm> createMintermList(int... vals) {
        int bitCount = Arrays.stream(vals).max().stream().map(Minterm::numberOfBitsNeeded).findFirst().orElse(0);
        return Arrays.stream(vals).mapToObj(val -> new Minterm(val, bitCount)).toList();
    }
}