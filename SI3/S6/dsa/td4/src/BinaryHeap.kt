import java.util.*
import kotlin.math.pow

/**
 * array class for binary heap implementation
 */
class BinaryHeap<T : Comparable<T>> {
    private val data: ArrayList<T>

    // comparator to choose
    private var comparator = Comparator.naturalOrder<T?>()

    /**
     * Return the array of the heap.
     * This method is only for testing purposes.
     * @return the array of the heap
     */
    fun getArray(): List<T> {
        return data
    }
    ///////////// Constructors
    /**
     * Build a heap of capacity n.
     * The elements are ordered according to the
     * natural order on T.
     * The heap is empty.
     * Complexity: THETA(1)
     */
    constructor(n: Int) {
        data = ArrayList()
    }

    private fun gt(lhs: T, rhs: T): Boolean {
        return comparator.compare(lhs, rhs) > 0
    }

    /**
     * Build a heap of capacity n.
     * The elements are ordered according to comparator.
     * The heap is empty.
     * Complexity: THETA(1)
     */
    constructor(n: Int, comparator: Comparator<T>) : this(n) {
        this.comparator = comparator
    }

    /**
     * Build a heap based on array.
     * The elements are ordered according to the
     * natural order on T.
     * The heap is full
     */
    constructor(a: Array<T>) {
        data = ArrayList(listOf(*a))
        buildHeap()
    }

    /**
     * Build a heap based on array.
     * The elements are ordered according to comparator.
     * The heap is full
     */
    constructor(array: Array<T>, comparator: Comparator<T>) : this(array) {
        this.comparator = comparator
        buildHeap()
    }
    ///////////// Private methods
    /**
     * Swap values in the array
     * at indexes i and j.
     * Complexity: THETA(1)
     */
    private fun swap(i: Int, j: Int) {
        data[i] = data[j].apply { data[j] = data[i] }
    }

    /**
     * Return the number of the left
     * node of node number n.
     * Complexity: THETA(1)
     */

    private fun left(n: Int): Int {
        return 2 * n + 1
    }

    /**
     * Return the number of the right
     * node of node number n.
     * Complexity: THETA(1)
     */
    private fun right(n: Int): Int {
        return 2 * (n + 1)
    }

    /**
     * Return the number of the parent
     * node of node number n.
     * Complexity: THETA(1)
     */
    private fun parent(n: Int): Int {
        return (n - 1) / 2
    }

    /**
     * Arrange the elements in array such
     * that it has the heap property.
     * Complexity: O(size)
     */

    // percolateUp method
    private fun percolateUp(i: Int) {
        var index = i
        while (index > 0) {
            val parentIndex = (index - 1) / 2
            if (gt(data[index], data[parentIndex])) {
                swap(index, parentIndex)
                index = parentIndex
            } else {
                break
            }
        }
    }

    // percolateDown method
    private fun percolateDown(index: Int) {
        var currentIndex = index
        while (left(currentIndex) < data.size) {
            val leftChildIndex = left(currentIndex)
            val rightChildIndex = right(currentIndex)
            val maxChildIndex = if (rightChildIndex < data.size && gt(data[rightChildIndex], data[leftChildIndex])) {
                rightChildIndex
            } else {
                leftChildIndex
            }
            if (gt(data[maxChildIndex], data[currentIndex])) {
                swap(maxChildIndex, currentIndex)
                currentIndex = maxChildIndex
            } else {
                break
            }
        }
    }

    // buildHeap method
    private fun buildHeap() {
        for (i in (data.size / 2 - 1) downTo 0) {
            percolateDown(i)
        }
    }


    ///////////// Public methods
    /**
     * Return the size of the heap
     * (the number of elements in the heap).
     * Complexity: THETA(1)
     */
    fun size(): Int {
        return data.size
    }

    /**
     * Check if the heap is empty.
     * Complexity: THETA(1)
     */
    fun isEmpty(): Boolean {
        return data.isEmpty()
    }

    /**
     * Return the extreme element.
     * Complexity: THETA(1)
     */
    @Throws(EmptyHeapException::class)
    fun extreme(): T {
        if (isEmpty()) throw EmptyHeapException()
        return data[0]
    }

    /**
     * Return and delete the extreme element.
     * Ensure that the free space is at the end of the array and does not refer to any element.
     * Complexity: O(log(size))
     */
    @Throws(EmptyHeapException::class)
    fun deleteExtreme(): T {
        if (isEmpty()) throw EmptyHeapException()
        val extreme = data[0]
        data[0] = data[data.size - 1]
        data.removeLast()
        percolateDown(0)
        return extreme
    }

    /**
     * Add a new element in the heap
     * Complexity: O(log(size))
     */
    @Throws(FullHeapException::class)
    fun add(e: T) {
        data.add(e)
        percolateUp(data.size - 1)
    }
    ///////////// Part 3: deleting in the heap
    /**
     * Delete the element e from the heap.
     * Complexity: O(size)
     */
    fun delete(e: T): Boolean {
        // deletes an element in a binary heap
        val index = data.indexOf(e)
        if (index == -1) {
            return false
        }
        data[index] = data[data.size - 1]
        data.removeLast()
        percolateDown(index)
        return true
    }

    /**
     * Delete all the elements e from the heap.
     * Complexity: O(size)
     */
    fun deleteAll(e: T) {
        while (delete(e)) {
            // do nothing
        }
    }

    fun toStringByLevels(): String {
        val bld = StringBuilder()
        var level = 0
        var nbNodes = 1
        for (i in data.indices) {
            if (i == nbNodes) {
                bld.append("\n")
                level++
                nbNodes = (nbNodes + 2.0.pow(level.toDouble())).toInt()
            }
            bld.append("(").append(i).append(")").append(data[i]).append(" ")
        }
        return bld.toString()
    }

    fun isLeaf(i: T): Boolean {
        val index = data.indexOf(i)
        if (index == -1) {
            throw IllegalArgumentException("Item not found in heap")
        }
        return index >= size() / 2 && index < size()
    }

    fun inverseExtreme(): T {
        if (data.isEmpty()) {
            throw NoSuchElementException("Heap is empty")
        }
        val start = data.size / 2
        return data.subList(start, data.size)
            .minWithOrNull(comparator)!!
    }
}
