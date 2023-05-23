package graphs.roadApplication

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SparseMatrixTest {
    private var matrix = SparseMatrix<Int>()

    @BeforeEach
    fun setUp() {
        matrix = SparseMatrix()
    }

    @Test
    fun valueIsSet() {
        matrix[18, 0] = 1
        assertEquals(1, matrix[18, 0])
    }

    @Test
    fun isSparse() {
        matrix[0, 0] = 1
        matrix[10, 10] = 2
        matrix[20, 20] = 3

        assertEquals(3, matrix.count())
    }

    @Test
    fun isCorrectlyMappedInto1D() {
        matrix[0, 5] = 1
        assertEquals(null, matrix[0, 6])
    }

    @Test
    fun canStoreValueAtHugeIndex() {
        matrix[3_037_000_499, 3_037_000_499] = 1
        assertEquals(1, matrix[3_037_000_499, 3_037_000_499])
    }

    @Test
    fun readOptimizationWorks() {
        matrix[20, 20] = 3
        matrix[10, 10] = 2
        matrix[0, 0] = 1

        matrix.makeReadOptimized()

        assertEquals(3, matrix.count())
        assertEquals(1, matrix[0, 0])
        assertEquals(2, matrix[10, 10])
        assertEquals(3, matrix[20, 20])
    }

    @Test
    fun getRowWorks() {
        matrix[0, 0] = 1
        matrix[0, 18] = 2
        matrix[0, 24] = 3

        matrix[1, 3] = 4

        matrix.makeReadOptimized()

        val expected = listOf<Pair<Long, Int>>(
            Pair(0, 1),
            Pair(18, 2),
            Pair(24, 3)
        )
        assertEquals(expected, matrix.getRow(0))
    }
}