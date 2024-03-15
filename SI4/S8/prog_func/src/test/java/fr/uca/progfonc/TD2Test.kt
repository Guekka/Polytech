package fr.uca.progfonc

import fr.uca.progfonc.TD1.sort
import org.junit.Assert
import org.junit.Test

class TD2Test {
    @Test
    fun testMapEmptyList() {
        Assert.assertNull(TD2.map({ obj: Any -> obj.hashCode() }, null))
    }

    @Test
    fun testMapInitial() {
        val list = Lst("Hello", Lst("world", null))
        val expectedResult = Lst('H', Lst('w', null))
        Assert.assertEquals(expectedResult, TD2.map({ s: String -> s[0] }, list))
    }

    @Test
    fun testMapSign() {
        val list = Lst(12, Lst(0, Lst(-24, null)))
        val expectedResult = Lst(1, Lst(0, Lst(-1, null)))
        Assert.assertEquals(expectedResult, TD2.map({ i: Int? ->
            Integer.signum(
                i!!
            )
        }, list))
    }

    @Test
    fun testSquare() {
        val list = Lst(2, Lst(0, Lst(-4, null)))
        val expectedResult = Lst(4, Lst(0, Lst(16, null)))
        Assert.assertEquals(expectedResult, TD2.squares(list))
    }

    @Test
    fun testSizeOfStrings() {
        val list = Lst("Hello", Lst("world!", null))
        val expectedResult = Lst(5, Lst(6, null))
        Assert.assertEquals(expectedResult, TD2.sizeOfStrings(list))
    }

    @Test
    fun testFilterWithEmptyList() {
        val emptyList: Lst<Int>? = null
        val filteredList = TD2.filter({ i: Int -> i % 2 == 0 }, emptyList)
        Assert.assertNull(filteredList)
    }

    @Test
    fun testFilterWithSingleElementList() {
        val singleElementList = Lst(1, null)
        val filteredList = TD2.filter({ i: Int -> i % 2 == 0 }, singleElementList)
        Assert.assertNull(filteredList)
    }

    @Test
    fun testFilterWithMultipleElements() {
        val multipleElementsList = Lst(1, Lst(2, Lst(3, Lst(4, null))))
        val filteredList = TD2.filter({ i: Int -> i % 2 == 0 }, multipleElementsList)
        Assert.assertEquals(Lst(2, Lst(4, null)), filteredList)
    }

    @Test
    fun testLowersWithMixedCaseElements() {
        val multipleElementsList = Lst("abc", Lst("Def", Lst("ghi", null)))
        val lowersList = TD2.lowers(multipleElementsList)
        Assert.assertEquals(Lst("abc", Lst("ghi", null)), lowersList)
    }

    @Test
    fun testCountWithEmptyList() {
        val emptyList: Lst<Int>? = null
        val count = TD2.count({ i: Int -> i % 2 == 0 }, emptyList)
        Assert.assertEquals(0, count.toLong())
    }

    @Test
    fun testCountWithSingleElementList() {
        val singleElementList = Lst(1, null)
        val count = TD2.count({ i: Int -> i % 2 == 0 }, singleElementList)
        Assert.assertEquals(0, count.toLong())
    }

    @Test
    fun testCountWithMultipleElements() {
        val multipleElementsList = Lst(1, Lst(2, Lst(3, Lst(4, null))))
        val count = TD2.count({ i: Int -> i % 2 == 0 }, multipleElementsList)
        Assert.assertEquals(2, count.toLong())
    }

    @Test
    fun testNbPositives() {
        val multipleElementsList = Lst(-1, Lst(0, Lst(1, Lst(2, null))))
        val count = TD2.nbPositives(multipleElementsList)
        Assert.assertEquals(3, count.toLong())
    }

    @Test
    fun testReduceWithEmptyList() {
        val emptyList: Lst<Int>? = null
        val sum = TD2.reduce({ i: Int, j: Int -> i + j }, emptyList, 0)
        Assert.assertEquals(0, sum!!.toLong())
    }

    @Test
    fun testReduceWithSingleElementList() {
        val singleElementList = Lst(1, null)
        val sum = TD2.reduce({ i: Int, j: Int -> i + j }, singleElementList, 0)
        Assert.assertEquals(1, sum!!.toLong())
    }

    @Test
    fun testReduceWithMultipleElements() {
        val multipleElementsList = Lst(1, Lst(2, Lst(3, Lst(4, null))))
        val sum = TD2.reduce({ i: Int, j: Int -> i + j }, multipleElementsList, 0)
        Assert.assertEquals(10, sum!!.toLong())
    }

    @Test
    fun testReduceWithAllElements2() {
        val multipleElementsList = Lst(2, Lst(4, Lst(6, Lst(8, null))))
        val product = TD2.reduce({ i: Int, j: Int -> i * j }, multipleElementsList, 1)
        Assert.assertEquals(384, product!!.toLong())
    }

    @Test
    fun testReduceSizes() {
        val multipleElementsList = Lst("AbCD", Lst("ef", Lst("ghIjk", null)))
        val product = TD2.reduce({ s: String, x: Int -> s.length + x }, multipleElementsList, 0)
        Assert.assertEquals(11, product!!.toLong())
    }

    @Test
    fun testSum() {
        val multipleElementsList = Lst(1, Lst(2, Lst(3, Lst(4, null))))
        val sum = TD2.sum(multipleElementsList)
        Assert.assertEquals(10, sum!!.toLong())
    }

    @Test
    fun testMin() {
        val multipleElementsList = Lst(1, Lst(-2, Lst(-3, Lst(4, null))))
        val result = TD2.min(multipleElementsList)
        Assert.assertEquals(-3, result!!.toLong())
    }

    @Test
    fun testMinFirst() {
        val multipleElementsList = Lst(1, Lst(2, Lst(3, Lst(4, null))))
        val result = TD2.min(multipleElementsList)
        Assert.assertEquals(1, result!!.toLong())
    }

    @Test
    fun testSumLengthLowers() {
        val multipleElementsList = Lst("abc", Lst("ADef", Lst("ghi", null)))
        val result = TD2.sumLengthLowers(multipleElementsList)
        Assert.assertEquals(6, result.toLong())
    }

    @Test
    fun testReprInteger() {
        val multipleElementsList = Lst(1, Lst(-2, Lst(-3, Lst(4, null))))
        val result = TD2.repr(multipleElementsList)
        Assert.assertEquals("(1 -2 -3 4 )", result)
    }

    @Test
    fun testReprString() {
        val multipleElementsList = Lst("abc", Lst("Def", Lst("ghi", null)))
        val result = TD2.repr(multipleElementsList)
        Assert.assertEquals("(abc Def ghi )", result)
    }

    @Test
    fun testConcat() {
        val multipleElementsList = Lst(
            Lst(1, Lst(2, Lst(3, null))), Lst(
                Lst(-1, Lst(-2, Lst(-3, null))), Lst(Lst(0, null), null)
            )
        )
        val expected = Lst(1, Lst(2, Lst(3, Lst(-1, Lst(-2, Lst(-3, Lst(0, null)))))))
        Assert.assertEquals(expected, TD2.concat(multipleElementsList))
    }

    @Test
    fun testToSet() {
        val multipleElementsList = Lst(1, Lst(2, Lst(1, Lst(3, null))))
        val expected = Lst(1, Lst(2, Lst(3, null)))
        Assert.assertEquals(expected, sort(TD2.toSet(multipleElementsList)))
    }

    @Test
    fun testUnion() {
        val multipleElementsList = Lst(3, Lst(2, Lst(1, null)))
        val otherList = Lst(4, Lst(2, Lst(0, null)))
        val expected = Lst(0, Lst(1, Lst(2, Lst(3, Lst(4, null)))))
        Assert.assertEquals(expected, sort(TD2.union(multipleElementsList, otherList)))
    }

    @Test
    fun testEqualsTo() {
        Assert.assertTrue(TD2.equalsTo("ABC").test("ABC"))
        Assert.assertFalse(TD2.equalsTo("ABC").test("abc"))
    }

    @Test
    fun testBetween() {
        Assert.assertTrue(TD2.between(5, 17).test(12))
        Assert.assertTrue(TD2.between(5, 17).test(5))
        Assert.assertFalse(TD2.between(5, 17).test(30))
    }

    @Test
    fun testCountOccurence() {
        val multipleElementsList = Lst(1, Lst(2, Lst(1, Lst(3, null))))
        Assert.assertEquals(2, TD2.countOccurence(multipleElementsList, 1).toLong())
        Assert.assertEquals(0, TD2.countOccurence(multipleElementsList, 4).toLong())
    }
}
