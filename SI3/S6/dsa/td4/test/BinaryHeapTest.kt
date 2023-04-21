import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

internal class BinaryHeapTest {
    var LOGGER4Tests: Logger = Logger.getLogger(BinaryHeapTest::class.java.name)
    var binaryHeap: BinaryHeap<Int> = BinaryHeap(0)
    var binaryHeapString: BinaryHeap<String> = BinaryHeap(0)

    @BeforeEach
    fun setUp() {
        LOGGER4Tests.level = Level.OFF
        binaryHeap = BinaryHeap(10, Comparator.naturalOrder())
    }

    /**
     * Initialize the heap with strings : A B C D E
     */
    fun setBinaryHeapString() {
        binaryHeapString = BinaryHeap(arrayOf("A", "B", "C", "D", "E"), Comparator.reverseOrder())
    }

    /**
     *
     */
    fun initHeap() {
        binaryHeap = BinaryHeap(arrayOf(3, 7, 5, 1, 9))
    }

    /**
     * Test the heap from the lesson
     */
    @Test
    @Throws(EmptyHeapException::class)
    fun testHeapFromLesson() {
        LOGGER4Tests.log(Level.INFO, "********************* testHeapFromLesson")
        binaryHeap = BinaryHeap(arrayOf(12, 5, 11, 3, 10, 2, 9, 4, 8, 1, 7, 6), Comparator.reverseOrder())
        LOGGER4Tests.info(binaryHeap.getArray().toString())
        LOGGER4Tests.info(binaryHeap.toStringByLevels())
        Assertions.assertEquals(1, binaryHeap.extreme())
        Assertions.assertEquals(12, binaryHeap.getArray()[7])
        Assertions.assertEquals(9, binaryHeap.getArray()[6])
        Assertions.assertTrue(binaryHeap.isLeaf(9))
        Assertions.assertTrue(binaryHeap.isLeaf(10))
        Assertions.assertTrue(binaryHeap.isLeaf(11))
        Assertions.assertFalse(binaryHeap.isLeaf(6))
    }

    @Test
    fun testSize() {
        Assertions.assertEquals(0, binaryHeap.size())
        setBinaryHeapString()
        Assertions.assertEquals(5, binaryHeapString.size())
    }

    @Test
    fun testIsEmpty() {
        Assertions.assertTrue(binaryHeap.isEmpty())
        setBinaryHeapString()
        Assertions.assertFalse(binaryHeapString.isEmpty())
    }

    @Test
    @Throws(EmptyHeapException::class)
    fun testExtreme() {
        setBinaryHeapString()
        Assertions.assertThrows(EmptyHeapException::class.java) { binaryHeap.extreme() }
        Assertions.assertEquals("A", binaryHeapString.extreme())
        initHeap()
        Assertions.assertEquals(9, binaryHeap.extreme())
        binaryHeapString = BinaryHeap(arrayOf("A", "B", "C", "D", "E"), Comparator.naturalOrder())
        Assertions.assertEquals("E", binaryHeapString.extreme())
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
    fun testConstructors() {
        setBinaryHeapString()
        Assertions.assertEquals(5, binaryHeapString.size())
        Assertions.assertEquals("A", binaryHeapString.getArray()[0])
        //assertEquals(new String[]{"A", "B", "C", "D", "E"}, binaryHeapString.getArray());
    }

    @Test
    @Throws(FullHeapException::class)
    fun testInsert() {
        binaryHeap.add(1)
        Assertions.assertEquals(1, binaryHeap.size())
        binaryHeap.add(5)
        Assertions.assertEquals(2, binaryHeap.size())
        binaryHeap.add(2)
        Assertions.assertEquals(3, binaryHeap.size())
        binaryHeap.add(10)
        Assertions.assertEquals(4, binaryHeap.size())
        binaryHeap.add(7)
        //Remove the following line if you don't order the heap in the same way
        Assertions.assertEquals(listOf(10, 7, 2, 1, 5), binaryHeap.getArray())
    }

    @Test
    fun testDelete() {
        setBinaryHeapString()
        LOGGER4Tests.info(binaryHeapString.toStringByLevels())
        binaryHeapString.delete("C")
        testHeapEndIsFree(binaryHeap)
        binaryHeapString.delete("D")
        binaryHeapString.delete("E")
        testHeapEndIsFree(binaryHeap)
        Assertions.assertEquals(listOf("A", "B"), binaryHeapString.getArray())
    }

    @Test
    @Throws(EmptyHeapException::class)
    fun testDeleteExtreme() {
        initHeap()
        LOGGER4Tests.info("before delete" + binaryHeap.getArray().toString())
        LOGGER4Tests.info(binaryHeap.toStringByLevels())
        Assertions.assertEquals(Integer.valueOf(9), binaryHeap.deleteExtreme())
        testHeapEndIsFree(binaryHeap)
        Assertions.assertEquals(Integer.valueOf(7), binaryHeap.deleteExtreme())
        testHeapEndIsFree(binaryHeap)
        Assertions.assertEquals(Integer.valueOf(5), binaryHeap.deleteExtreme())
        testHeapEndIsFree(binaryHeap)
        Assertions.assertEquals(Integer.valueOf(3), binaryHeap.deleteExtreme())
        testHeapEndIsFree(binaryHeap)
        Assertions.assertEquals(Integer.valueOf(1), binaryHeap.deleteExtreme())
        testHeapEndIsFree(binaryHeap)
        Assertions.assertTrue(binaryHeap.isEmpty())
    }

    /**
     * Test if the end of the table contains null
     * If not, you keep deleted elements in the table, and it's not good for the memory management
     * @param binaryHeap the heap to test
     */
    private fun testHeapEndIsFree(binaryHeap: BinaryHeap<*>) {
        LOGGER4Tests.info(binaryHeap.toStringByLevels())
        LOGGER4Tests.info("insure the end of the table contains null : " + binaryHeap.getArray().toString())
    }

    @Test
    fun testDeleteAll() {
        binaryHeap = BinaryHeap(arrayOf(3, 7, 5, 1, 9, 0, 9, 7, 9))
        binaryHeap.deleteAll(9)
        testHeapEndIsFree(binaryHeap)
        Assertions.assertEquals(listOf(7, 7, 5, 1, 3, 0), binaryHeap.getArray())
    }

    @Test
    @Throws(FullHeapException::class, EmptyHeapException::class)
    fun testDeleteExtremeWithComparator() {
        val c = Comparator { e1: Int, e2: Int -> e2 - e1 }
        val heapWithComparator: BinaryHeap<Int> = BinaryHeap<Int>(10, c)
        heapWithComparator.add(3)
        heapWithComparator.add(7)
        heapWithComparator.add(5)
        heapWithComparator.add(1)
        heapWithComparator.add(9)
        Assertions.assertEquals(1, heapWithComparator.deleteExtreme())
        Assertions.assertEquals(3, heapWithComparator.deleteExtreme())
        Assertions.assertEquals(5, heapWithComparator.deleteExtreme())
        Assertions.assertEquals(7, heapWithComparator.deleteExtreme())
        Assertions.assertEquals(9, heapWithComparator.deleteExtreme())
        Assertions.assertTrue(heapWithComparator.isEmpty())
    }

    /* --------------------- Question 1 --------------------- */
    @Test
    @Throws(FullHeapException::class)
    fun testFirstQuestionPartOne1() {
        val c = Comparator { e1: Int, e2: Int -> e2 - e1 }
        val heapQ1: BinaryHeap<Int> = BinaryHeap<Int>(15, c)
        LOGGER4Tests.level = Level.INFO
        val list = mutableListOf(10, 12, 1, 14, 6, 5, 8, 15, 3, 9, 7, 4, 11, 13, 2)
        for (i in list) {
            LOGGER4Tests.info("insert $i")
            heapQ1.add(i)
        }
        println(heapQ1.toStringByLevels())
        println(heapQ1.getArray().toString())
        Assertions.assertEquals(listOf(1, 3, 2, 6, 7, 5, 4, 15, 14, 12, 9, 10, 11, 13, 8), heapQ1.getArray())
    }

    @Test
    @Throws(FullHeapException::class)
    fun testFirstQuestionPartOne2() {
        val c = Comparator { e1: Int, e2: Int -> e2 - e1 }
        LOGGER4Tests.level = Level.INFO
        LOGGER4Tests.info("create heap with array")
        val array = arrayOf(10, 12, 1, 14, 6, 5, 8, 15, 3, 9, 7, 4, 11, 13, 2)
        val heapQ2: BinaryHeap<Int> = BinaryHeap(array) //,c);
        println(heapQ2.toStringByLevels())
        Assertions.assertEquals(listOf(15, 14, 13, 12, 9, 11, 8, 10, 3, 6, 7, 4, 5, 1, 2), heapQ2.getArray())
    }

    @Test
    @Throws(FullHeapException::class, EmptyHeapException::class)
    fun testFirstQuestionPartOne3() {
        val c = Comparator { e1: Int, e2: Int -> e2 - e1 }
        LOGGER4Tests.level = Level.INFO
        LOGGER4Tests.info("create heap with array")
        val array = arrayOf(10, 12, 1, 14, 6, 5, 8, 15, 3, 9, 7, 4, 11, 13, 2)
        val heapQ3: BinaryHeap<Int> = BinaryHeap(array, c)
        LOGGER4Tests.info("Delete extreme")
        val i = heapQ3.deleteExtreme()
        Assertions.assertEquals(1, i)
        println(heapQ3.toStringByLevels())
    }

    @Test
    @Throws(FullHeapException::class, EmptyHeapException::class)
    fun testFirstQuestionPartOne4() {
        val c = Comparator { e1: Int, e2: Int -> e2 - e1 }
        LOGGER4Tests.level = Level.INFO
        val array = arrayOf(10, 12, 1, 14, 6, 5, 8, 15, 3, 9, 7, 4, 11, 13, 2)
        val heapQ3: BinaryHeap<Int> = BinaryHeap(array, c)
        println(heapQ3.toStringByLevels())
        LOGGER4Tests.info("Find extreme inverse")
        val i = heapQ3.inverseExtreme()
        Assertions.assertEquals(15, i)
    }
}
