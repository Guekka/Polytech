package graphs.roadApplication

/// Sparse matrix implementation
/// Optimized for large matrices with few values
/// Optimized for inserting large amount of values at once then reading them
class SparseMatrix<T> {
    companion object {
        private const val maxWidth = 3_037_000_499 // sqrt(Long.MAX_VALUE)
    }

    private val values = ArrayList<T>()
    private val indices = ArrayList<Long>()

    private var readOptimized = false

    /// Calculate index in array by (x, y) position
    /// O(1) complexity
    private fun index(x: Long, y: Long): Long {
        return x * maxWidth + y
    }

    /// Calculate (x, y) position by index in array
    /// O(1) complexity
    private fun position(index: Long): Pair<Long, Long> {
        return Pair(index / maxWidth, index % maxWidth)
    }

    /// Add value at (x, y) position
    /// O(1) complexity
    fun add(x: Long, y: Long, value: T) {
        values.add(value)
        indices.add(index(x, y))
    }

    /// Set value at (x, y) position
    /// O(n) complexity
    operator fun set(x: Long, y: Long, value: T) {
        readOptimized = false

        val idx = index(x, y)

        val actualIndex = indices.indexOf(idx)
        if (actualIndex != -1)
            values[actualIndex] = value
        else
            add(x, y, value)
    }

    /// Get value at (x, y) position
    /// O(n) complexity
    operator fun get(x: Long, y: Long): T? {
        if (readOptimized) {
            val actualIndex = indices.binarySearch(index(x, y))
            if (actualIndex >= 0)
                return values[actualIndex]
            return null
        }

        val actualIndex = indices.indexOf(index(x, y))
        if (actualIndex != -1)
            return values[actualIndex]
        return null
    }


    /// Get row at y position
    /// Faster than getting values one by one
    /// Indexes are row indexes, not matrix indexes
    /// Requires read optimized matrix
    fun getRow(row: Long): List<Pair<Long, T>> {
        if (!readOptimized)
            throw IllegalStateException("SparseMatrix must be read optimized to get row")

        val rowStart = index(row, 0)
        val rowEnd = index(row, maxWidth - 1)

        val start = indices.binarySearch(rowStart)
        val end = indices.binarySearch(rowEnd)

        val actualStart = if (start < 0) -start - 1 else start
        val actualEnd = if (end < 0) -end - 2 else end

        if (actualStart > actualEnd) return emptyList()

        return indices.subList(actualStart, actualEnd + 1)
            .zip(values.subList(actualStart, actualEnd + 1))
            .map { (index, value) ->
                Pair(position(index).second, value)
            }
    }

    /// Get number of used values
    /// O(1) complexity
    fun count(): Int {
        return indices.size
    }

    fun makeReadOptimized() {
        if (readOptimized) return

        indices.zip(values).sortedBy { it.first }.unzip().let {
            indices.clear()
            values.clear()

            indices.addAll(it.first)
            values.addAll(it.second)
        }

        readOptimized = true
    }

    fun toPrettyString(): String {
        makeReadOptimized() // sort indices

        val sb = StringBuilder()
        for ((i, value) in indices.zip(values)) {
            sb.append("($i, $value) ")
        }
        return sb.toString()
    }

}
