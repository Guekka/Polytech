import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MintermCategoryTest {
    @Test
    void test() {
        //Merge Categories of only one elements
        MintermCategory m0Class = new MintermCategory();
        Minterm m0 = new Minterm(0,4);
        m0Class.add(m0);
        MintermCategory m1Class = new MintermCategory();
        Minterm m1 = new Minterm(1,4);
        m1Class.add(m1);
        List<Minterm> res = m1Class.merge(m0Class);
        assertTrue(res.contains(new Minterm(0, 0, 0, -1)));
        assertTrue(m0.isMarked());
        assertTrue(m1.isMarked());
        Collection<Integer> combinations = res.get(0).getCombinations();
        assertTrue(combinations.contains(0));
        assertTrue( combinations.contains(1));
    }
}
