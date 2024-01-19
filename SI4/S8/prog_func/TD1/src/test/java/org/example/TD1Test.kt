package org.example

import org.junit.Assert
import org.junit.Test

class TD1Test {
    @Test
    fun testLengthWithEmptyList() {
        val emptyList: Lst<Any>? = null
        Assert.assertEquals(0, TD1.length(emptyList).toLong())
    }

    @Test
    fun testLengthWithNonEmptyList() {
        val nonEmptyList = Lst(1, Lst(2, Lst<Any>(3, null)))
        Assert.assertEquals(3, TD1.length(nonEmptyList).toLong())
    }

    @Test
    fun testMemberWithValueInList() {
        val list = Lst(1, Lst(2, Lst<Any>(3, null)))
        Assert.assertTrue(TD1.member(2, list))
    }

    @Test
    fun testMemberWithValueNotInList() {
        val list = Lst(1, Lst(2, Lst<Any>(3, null)))
        Assert.assertFalse(TD1.member(4, list))
    }

    @Test
    fun testAppendWithEmptyLists() {
        Assert.assertNull(TD1.append<Any>(null, null))
    }

    @Test
    fun testAppendWithNonEmptyLists() {
        val list1 = Lst(1, Lst<Any>(2, null))
        val list2 = Lst(3, Lst<Any>(4, null))
        val expected = Lst(1, Lst(2, Lst(3, Lst<Any>(4, null))))
        Assert.assertEquals(expected, TD1.append(list1, list2))
    }

    @Test
    fun testSumWithEmptyList() {
        Assert.assertEquals(0, TD1.sum(null).toLong())
    }

    @Test
    fun testSumWithNonEmptyList() {
        val nonEmptyList = Lst(1, Lst(2, Lst(3, null)))
        Assert.assertEquals(6, TD1.sum(nonEmptyList).toLong())
    }

    @Test
    fun testRemoveWithValueInList() {
        val list = Lst(1, Lst(2, Lst<Any>(3, null)))
        val expected = Lst(1, Lst<Any>(3, null))
        Assert.assertEquals(expected, TD1.remove(2, list))
    }

    @Test
    fun testRemoveWithValueNotInList() {
        val list = Lst(1, Lst(2, Lst<Any>(3, null)))
        Assert.assertEquals(list, TD1.remove(4, list))
    }

    @Test
    fun testRemoveAllWithValueInList() {
        val list = Lst(1, Lst(2, Lst<Any>(1, null)))
        val expected = Lst<Any>(2, null)
        Assert.assertEquals(expected, TD1.removeAll(1, list))
    }

    @Test
    fun testRemoveAllWithValueNotInList() {
        val list = Lst(1, Lst(2, Lst<Any>(3, null)))
        Assert.assertEquals(list, TD1.remove(4, list))
    }

    @Test
    fun testFizzBuzzWithValidInput() {
        val expected = Lst(
            "2",
            Lst(
                "Fizz", Lst(
                    "4", Lst(
                        "Buzz", Lst(
                            "Fizz", Lst(
                                "7",
                                Lst(
                                    "8", Lst(
                                        "Fizz", Lst(
                                            "Buzz", Lst(
                                                "11", Lst(
                                                    "Fizz",
                                                    Lst(
                                                        "13", Lst(
                                                            "14", Lst(
                                                                "FizzBuzz", Lst(
                                                                    "16", Lst(
                                                                        "17",
                                                                        Lst("Fizz", Lst("19", Lst("Buzz", null)))
                                                                    )
                                                                )
                                                            )
                                                        )
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
        Assert.assertEquals(expected, TD1.fizzbuzz(2, 21))
    }

    @Test
    fun testFromArrayWithEmptyArray() {
        val emptyArray = arrayOfNulls<Any>(0)
        Assert.assertNull(TD1.fromArray(emptyArray))
    }

    @Test
    fun testFromArrayWithNonEmptyArray() {
        val nonEmptyArray = arrayOf<Any>(1, 2, 3)
        val expected = Lst(1, Lst(2, Lst<Any>(3, null)))
        Assert.assertEquals(expected, TD1.fromArray(nonEmptyArray))
    }

    @Test
    fun testReverseWithEmptyList() {
        Assert.assertNull(TD1.reverse<Any>(null))
    }

    @Test
    fun testReverseWithNonEmptyList() {
        val lst = Lst(1, Lst(2, Lst(3, null)))
        val reversedLst = TD1.reverse(lst)
        Assert.assertEquals(Lst(3, Lst(2, Lst(1, null))), reversedLst)
    }

    @Test
    fun testInsertWithEmptyList() {
        val insertedLst = TD1.insert(1, null)
        Assert.assertEquals(Lst(1, null), insertedLst)
    }

    @Test
    fun testInsertWithNonEmptyList() {
        val lst = Lst(1, Lst(2, Lst(4, null)))
        val insertedLst = TD1.insert(3, lst)
        Assert.assertEquals(Lst(1, Lst(2, Lst(3, Lst(4, null)))), insertedLst)
    }

    @Test
    fun testSortWithEmptyList() {
        val emptyList: Lst<Int>? = null
        Assert.assertNull(TD1.sort(emptyList))
    }

    @Test
    fun testSortWithNonEmptyList() {
        val lst = Lst(4, Lst(1, Lst(3, null)))
        val insertedLst = TD1.sort(lst)
        Assert.assertEquals(Lst(1, Lst(3, Lst(4, null))), insertedLst)
    }

    // Test indexOf method
    @Test
    fun testIndexOf() {
        // Create a list with three elements
        val l = Lst(1, Lst(2, Lst(3, null)))

        // Test indexOf when value is present in list
        var index = TD1.indexOf(2, l)
        Assert.assertEquals(1, index.toLong())

        // Test indexOf when value is not present in list
        index = TD1.indexOf(4, l)
        Assert.assertEquals(-1, index.toLong())
    }

    // Test take method
    @Test
    fun testTake() {
        // Create a list with five elements
        val l = Lst(1, Lst(2, Lst(3, Lst(4, Lst(5, null)))))

        // Test taking first three elements of list
        val firstThree = TD1.take(3, l)
        val expected = Lst(1, Lst(2, Lst(3, null)))
        Assert.assertEquals(expected, firstThree)

        Assert.assertEquals(expected, TD1.take(5, expected))

        // Test taking first zero elements of list
        val noElements = TD1.take(0, l)
        Assert.assertNull(noElements)
    }

    // Test unique method
    @Test
    fun testUnique() {
        // Create a list with four elements, two of which are duplicates
        val l = Lst(1, Lst(2, Lst(2, Lst(1, null))))

        // Test getting unique elements from list
        val unique = TD1.unique(l)
        val expected = Lst(1, Lst(2, null))
        val other = Lst(2, Lst(1, null))
        Assert.assertTrue(expected == unique || other == unique)
    }

    @Test
    fun testHas() {
        val l = Lst(
            Pair("a", 1),
            Lst(Pair("b", 2), null)
        )
        Assert.assertTrue(TD1.has(l, "a"))
        Assert.assertTrue(TD1.has(l, "b"))
        Assert.assertFalse(TD1.has(l, "c"))
    }

    @Test
    fun testGet() {
        val l = Lst(
            Pair("a", 1),
            Lst(Pair("b", 2), null)
        )
        Assert.assertEquals(1, TD1.get(l, "a")!!.toLong())
        Assert.assertEquals(2, TD1.get(l, "b")!!.toLong())
        Assert.assertNull(TD1.get(l, "c"))
    }

    @Test
    fun testSet() {
        var l: Lst<Pair<String, Int>> = Lst(
            Pair("a", 1),
            Lst(Pair("b", 2), null)
        )
        l = TD1.set(l, "a", 3)
        Assert.assertEquals(3, TD1.get(l, "a")!!.toLong())
        l = TD1.set(l, "c", 4)
        Assert.assertEquals(4, TD1.get(l, "c")!!.toLong())
    }

    @Test
    fun testMaxOne() {
        val list = Lst(5, null)
        Assert.assertEquals(5, TD1.max(list))
    }

    @Test
    fun testMaxFirst() {
        val list = Lst(10, Lst(2, Lst(3, null)))
        Assert.assertEquals(10, TD1.max(list))
    }

    @Test
    fun testMaxLast() {
        val list = Lst(10, Lst(2, Lst(30, null)))
        Assert.assertEquals(30, TD1.max(list))
    }

    @Test
    fun testMaxIn() {
        val list = Lst(10, Lst(200, Lst(30, null)))
        Assert.assertEquals(200, TD1.max(list))
    }
}
