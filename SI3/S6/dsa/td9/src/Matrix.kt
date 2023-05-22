package graphs.roadApplication

class Matrix<T> {
    private val values = ArrayList<T>()
    private val indices = ArrayList<Int>()

    /// Calculate index in array by (x, y) position
    /// O(1) complexity
    private fun index(x: Int, y: Int): Int {
        return x * y + x
    }

    /// Add value at (x, y) position
    /// O(1) complexity
    fun add(x: Int, y: Int, value: T) {
        values.add(value)
        indices.add(index(x, y))
    }

    /// Set value at (x, y) position
    /// O(n) complexity
    operator fun set(x: Int, y: Int, value: T) {
        val idx = index(x, y)

        val actualIndex = indices.indexOf(idx)
        if (actualIndex != -1)
            values[actualIndex] = value
        else
            add(x, y, value)
    }

    /// Get value at (x, y) position
    /// O(n) complexity
    operator fun get(x: Int, y: Int): T {
        return values[indices.indexOf(index(x, y))]
    }

    /// Get number of used values
    /// O(1) complexity
    fun usedSize(): Int {
        return indices.size
    }
}
