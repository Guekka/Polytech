package ads.poo2.lab4.heaps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.LookupOp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/*
    * Test class for BinaryHeap
    * @TODO avoid to enforce the way to manage the heap (left or right child)
 */
class BinaryHeapTest {

    Logger LOGGER4Tests = Logger.getLogger(BinaryHeapTest.class.getName());

    BinaryHeap<Integer> binaryHeap;
    BinaryHeap<String> binaryHeapString;


    @BeforeEach
    void setUp() {
        LOGGER4Tests.setLevel(Level.OFF);
        binaryHeap = new BinaryHeap<Integer>(10, Comparator.naturalOrder());
    }

    /**
     * Initialize the heap with strings : A B C D E
     */
    void setBinaryHeapString() {
        binaryHeapString = new BinaryHeap<>(new String[]{"A", "B", "C", "D", "E"}, Comparator.reverseOrder());
    }

    /**
     *
     */
    public void initHeap() {
        binaryHeap = new BinaryHeap<>(3, 7, 5, 1, 9);
    }

    /**
     * Test the heap from the lesson
     */
    @Test
    void testHeapFromLesson() throws EmptyHeapException {
        LOGGER4Tests.log(Level.INFO, "********************* testHeapFromLesson");
        binaryHeap = new BinaryHeap<>(new Integer[]{12, 5, 11, 3, 10, 2, 9, 4, 8, 1, 7, 6}, Comparator.reverseOrder());
        LOGGER4Tests.info(Arrays.toString(binaryHeap.getArray()));
        LOGGER4Tests.info(binaryHeap.toStringByLevels());
        assertEquals(1, binaryHeap.extreme());
        assertEquals(12, binaryHeap.getArray()[7]);
        assertEquals(9, binaryHeap.getArray()[6]);
        assertTrue(binaryHeap.isLeaf(9));
        assertTrue(binaryHeap.isLeaf(10));
        assertTrue(binaryHeap.isLeaf(11));
        assertFalse(binaryHeap.isLeaf(6));
    }


    @Test
    void testSize() {
        assertEquals(0, binaryHeap.size());
        setBinaryHeapString();
        assertEquals(5, binaryHeapString.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(binaryHeap.isEmpty());
        setBinaryHeapString();
        assertFalse(binaryHeapString.isEmpty());
    }

    @Test
    void testExtreme() throws EmptyHeapException {
        setBinaryHeapString();
        assertThrows(EmptyHeapException.class, () -> binaryHeap.extreme());
        assertEquals("A", binaryHeapString.extreme());
        initHeap();
        assertEquals(9, binaryHeap.extreme());
        binaryHeapString = new BinaryHeap<>(new String[]{"A", "B", "C", "D", "E"}, Comparator.naturalOrder());
        assertEquals("E", binaryHeapString.extreme());
    }

    //@Test
    /**
     * Test the inverseExtreme method But this method is not asked in the lab
     */
    /*void testInverseExtreme() throws EmptyHeapException {
        setBinaryHeapString();
        assertEquals("E", binaryHeapString.inverseExtreme());
        binaryHeapString.deleteExtreme();
        assertEquals("E", binaryHeapString.inverseExtreme());
        binaryHeapString.deleteExtreme();
        assertEquals("E", binaryHeapString.inverseExtreme());
        binaryHeapString.deleteExtreme();
        assertEquals("E", binaryHeapString.inverseExtreme());
        binaryHeapString.deleteExtreme();
        assertEquals("E", binaryHeapString.inverseExtreme());
        binaryHeapString.deleteExtreme();
        assertThrows(EmptyHeapException.class, () -> binaryHeapString.inverseExtreme());
        initHeap();
        assertEquals(1, binaryHeap.inverseExtreme());
        binaryHeap.deleteExtreme();
        assertEquals(1, binaryHeap.inverseExtreme());
    }
     */


    @Test
    void testConstructors()  {
        setBinaryHeapString();
        assertEquals(5, binaryHeapString.size());
        assertEquals("A", binaryHeapString.getArray()[0]);
        //assertArrayEquals(new String[]{"A", "B", "C", "D", "E"}, binaryHeapString.getArray());
    }

    @Test
    void testInsert() throws FullHeapException {
        binaryHeap.add(1);
        assertEquals(1, binaryHeap.size());
        binaryHeap.add(5);
        assertEquals(2, binaryHeap.size());
        binaryHeap.add(2);
        assertEquals(3, binaryHeap.size());
        binaryHeap.add(10);
        assertEquals(4, binaryHeap.size());
        binaryHeap.add(7);
        //Remove the following line if you don't order the heap in the same way
        assertArrayEquals(new Integer[]{10, 7, 2, 1, 5, null, null, null, null, null}, binaryHeap.getArray());
    }

    @Test
    void testDelete() {
        setBinaryHeapString();
        LOGGER4Tests.info(binaryHeapString.toStringByLevels());
        binaryHeapString.delete("C");
        testHeapEndIsFree(binaryHeap);
        binaryHeapString.delete("D");
        binaryHeapString.delete("E");
        testHeapEndIsFree(binaryHeap);
        assertArrayEquals(new String[]{"A", "B", null, null, null}, binaryHeapString.getArray());
    }


    @Test
     void testDeleteExtreme() throws EmptyHeapException {
        initHeap();
        LOGGER4Tests.info("before delete" + Arrays.toString(binaryHeap.getArray()));
        LOGGER4Tests.info(binaryHeap.toStringByLevels());
        assertEquals(Integer.valueOf(9), binaryHeap.deleteExtreme());
        testHeapEndIsFree(binaryHeap);
        assertEquals(Integer.valueOf(7), binaryHeap.deleteExtreme());
        testHeapEndIsFree(binaryHeap);
        assertEquals(Integer.valueOf(5), binaryHeap.deleteExtreme());
        testHeapEndIsFree(binaryHeap);
        assertEquals(Integer.valueOf(3), binaryHeap.deleteExtreme());
        testHeapEndIsFree(binaryHeap);
        assertEquals(Integer.valueOf(1), binaryHeap.deleteExtreme());
        testHeapEndIsFree(binaryHeap);
        assertTrue(binaryHeap.isEmpty());
    }

    /**
     * Test if the end of the table contains null
     * If not, you keep deleted elements in the table, and it's not good for the memory management
     * @param binaryHeap the heap to test
     */
    private void testHeapEndIsFree(BinaryHeap<?> binaryHeap) {
        LOGGER4Tests.info(binaryHeap.toStringByLevels());
        LOGGER4Tests.info("insure the end of the table contains null : " + Arrays.toString(binaryHeap.getArray()));
        assertNull(binaryHeap.getArray()[binaryHeap.size()]);
    }

    @Test
     void testDeleteAll()  {
        binaryHeap = new BinaryHeap<>(3, 7, 5, 1, 9,0,9,7,9);
        binaryHeap.deleteAll(9);
        testHeapEndIsFree(binaryHeap);
        assertArrayEquals(
                        new Integer[]{7, 7, 5, 1, 3, 0, null, null,null}, binaryHeap.getArray());
    }

    @Test
     void testDeleteExtremeWithComparator() throws FullHeapException, EmptyHeapException {
        Comparator<Integer> c = (e1, e2) -> e2 - e1;
        BinaryHeap<Integer> heapWithComparator = new BinaryHeap<>(10, c);
        heapWithComparator.add(3);
        heapWithComparator.add(7);
        heapWithComparator.add(5);
        heapWithComparator.add(1);
        heapWithComparator.add(9);
        assertEquals(1, heapWithComparator.deleteExtreme());
        assertEquals(3, heapWithComparator.deleteExtreme());
        assertEquals(5, heapWithComparator.deleteExtreme());
        assertEquals(7, heapWithComparator.deleteExtreme());
        assertEquals(9, heapWithComparator.deleteExtreme());
        assertTrue(heapWithComparator.isEmpty());
    }


    /* --------------------- Question 1 --------------------- */
    @Test
    void testFirstQuestionPartOne1() throws FullHeapException {
        Comparator<Integer> c = (e1, e2) -> e2 - e1;
        BinaryHeap<Integer> heapQ1 = new BinaryHeap<>(15, c);
        LOGGER4Tests.setLevel(Level.INFO);
        BinaryHeap.LOGGER.setLevel(Level.INFO);
        List<Integer> list = Arrays.asList(10, 12, 1, 14, 6, 5, 8, 15, 3, 9, 7, 4, 11, 13, 2);
        for (Integer i : list) {
            LOGGER4Tests.info("insert " + i);
            heapQ1.add(i);
        }
        System.out.println(heapQ1.toStringByLevels());
        System.out.println(Arrays.toString(heapQ1.getArray()));
        assertArrayEquals(new Integer[]{1, 3, 2, 6, 7, 5, 4, 15, 14, 12, 9, 10, 11, 13, 8}, heapQ1.getArray());
    }

    @Test
    void testFirstQuestionPartOne2() throws FullHeapException {
        Comparator<Integer> c = (e1, e2) -> e2 - e1;
        LOGGER4Tests.setLevel(Level.INFO);
        BinaryHeap.LOGGER.setLevel(Level.FINE);
        LOGGER4Tests.info("create heap with array");
        Integer[] array = new Integer[]{10, 12, 1, 14, 6, 5, 8, 15, 3, 9, 7, 4, 11, 13, 2};
        BinaryHeap<Integer> heapQ2 = new BinaryHeap<>(array);//,c);
        System.out.println(heapQ2.toStringByLevels());
        assertArrayEquals(new Integer[]{15, 14, 13, 12, 9, 11, 8, 10, 3, 6, 7, 4, 5, 1, 2}, heapQ2.getArray());

    }


    @Test
    void testFirstQuestionPartOne3() throws FullHeapException, EmptyHeapException {
        Comparator<Integer> c = (e1, e2) -> e2 - e1;
        LOGGER4Tests.setLevel(Level.INFO);
        LOGGER4Tests.info("create heap with array");
        Integer[] array = new Integer[]{10, 12, 1, 14, 6, 5, 8, 15, 3, 9, 7, 4, 11, 13, 2};
        BinaryHeap<Integer> heapQ3 = new BinaryHeap<>(array,c);
        BinaryHeap.LOGGER.setLevel(Level.FINE);
        LOGGER4Tests.info("Delete extreme");
        Integer i = heapQ3.deleteExtreme();
        assertEquals(1, i);
        System.out.println(heapQ3.toStringByLevels());

    }

    @Test
    void testFirstQuestionPartOne4() throws FullHeapException, EmptyHeapException {
        Comparator<Integer> c = (e1, e2) -> e2 - e1;
        LOGGER4Tests.setLevel(Level.INFO);
        Integer[] array = new Integer[]{10, 12, 1, 14, 6, 5, 8, 15, 3, 9, 7, 4, 11, 13, 2};
        BinaryHeap<Integer> heapQ3 = new BinaryHeap<>(array,c);
        System.out.println(heapQ3.toStringByLevels());
        BinaryHeap.LOGGER.setLevel(Level.FINE);
        LOGGER4Tests.info("Find extreme inverse");
        Integer i = heapQ3.inverseExtreme();
        assertEquals(15, i);

    }
}


