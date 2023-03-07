package ads.poo2.lab3.bstWithInnerClass;
import ads.poo2.lab3.RankedBSTInterface;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RankedBSTWithInnerclassTest {
        @Test
        public void testIsEmpty() {
            RankedBSTInterface<Integer> bst = new RankedBSTWithInnerClass<>();
            assertTrue(bst.isEmpty());
        }

        @Test
        public void testMakeEmpty() {
            RankedBSTInterface<Integer> bst = new RankedBSTWithInnerClass<>();
            bst.insert(5);
            assertFalse(bst.isEmpty());
            bst.makeEmpty();
            assertTrue(bst.isEmpty());
        }

        @Test
        public void testSize() {
            RankedBSTInterface<Integer> bst = new RankedBSTWithInnerClass<>();
            bst.insert(5);
            bst.insert(3);
            bst.insert(8);
            assertEquals(3, bst.getSize());
        }

    @Test
    void testRankWhenNotPresent() {
        RankedBSTInterface<Integer> bst = new RankedBSTWithInnerClass<>();
        bst.insert(5);
        bst.insert(3);
        bst.insert(8);
        assertEquals(0, bst.rank(1));
    }
          @Test
         void testRank() {
              RankedBSTInterface<Integer> bst = new RankedBSTWithInnerClass<>();
            bst.insert(5);
            bst.insert(3);
            bst.insert(8);
            bst.insert(1);
            bst.insert(4);
            bst.insert(7);
            bst.insert(10);
              assertEquals(4, bst.rank(5));
              assertEquals(1, bst.rank(1));
              assertEquals(7, bst.rank(10));
              assertEquals(3, bst.rank(4));
        }

        @Test
        public void testElement() {
            RankedBSTInterface<Integer> bst = new RankedBSTWithInnerClass<>();
            bst.insert(5);
            assertEquals(5, bst.elementInRank(1));
            bst.insert(3);
            bst.insert(8);
            bst.insert(1);
            bst.insert(4);
            bst.insert(7);
            bst.insert(10);
            assertEquals(5, bst.elementInRank(4));
            assertEquals(1, bst.elementInRank(1));
            assertEquals(10, bst.elementInRank(7));
            assertEquals(4, bst.elementInRank(3));
            assertEquals(7,bst.elementInRank(5));
        }

        @Test
        public void testContains() {
            RankedBSTInterface<Integer> bst = new RankedBSTWithInnerClass<>();
            bst.insert(5);
            bst.insert(3);
            bst.insert(8);
            assertTrue(bst.contains(5));
            assertFalse(bst.contains(2));
        }

        @Test
        public void testInsert() {
            RankedBSTInterface<Integer> bst = new RankedBSTWithInnerClass<>();
            bst.insert(5);
            bst.insert(3);
            bst.insert(8);
            assertTrue(bst.contains(5));
            assertTrue(bst.contains(3));
            assertTrue(bst.contains(8));
            assertFalse(bst.contains(2));
        }

        @Test
        public void testRemove() {
            RankedBSTInterface<Integer> bst = new RankedBSTWithInnerClass<>();
            bst.insert(5);
            bst.insert(3);
            bst.insert(8);
            bst.remove(5);
            assertFalse(bst.contains(5));
            assertTrue(bst.contains(3));
            assertTrue(bst.contains(8));
        }



    @Test
    void mainTest() {
        RankedBSTWithInnerClass.main(new String[0]);
    }
}