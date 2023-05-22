package graphs.roadApplication

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MatrixTest {
    private var matrix = Matrix<Int>()

    @BeforeEach
    fun setUp() {
        matrix = Matrix()
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

        assertEquals(3, matrix.usedSize())
    }
}