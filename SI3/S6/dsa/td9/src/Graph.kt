package graphs.roadApplication

class Graph<T> {
    @JvmInline
    value class NodeRef(val index: Int)

    private val nodes = ArrayList<T>()
    private val edges = SparseMatrix<Int>()

    fun addNode(value: T): NodeRef {
        nodes.add(value)
        return NodeRef(nodes.size - 1)
    }

    fun getNode(nodeRef: NodeRef): T {
        return nodes[nodeRef.index]
    }

    fun makeRef(index: Int): NodeRef {
        if (index >= nodes.size) throw IllegalArgumentException("index out of bounds")
        return NodeRef(index)
    }

    /// Add edge between two nodes
    /// Edge must be added only once
    /// O(1) complexity
    fun addEdge(lhs: NodeRef, rhs: NodeRef, distance: Int) {
        edges.add(lhs.index.toLong(), rhs.index.toLong(), distance)
        edges.add(rhs.index.toLong(), lhs.index.toLong(), distance)
    }

    fun getEdge(lhs: NodeRef, rhs: NodeRef): Int? {
        return edges[lhs.index.toLong(), rhs.index.toLong()]
    }

    fun nodes(): List<NodeRef> {
        return nodes.indices.map { NodeRef(it) }
    }

    fun adjacents(nodeRef: NodeRef): List<Pair<NodeRef, Int>> {
        return edges.getRow(nodeRef.index.toLong()).map { (index, value) ->
            Pair(NodeRef(index.toInt()), value)
        }
    }

    fun nodeCount(): Int {
        return nodes.size
    }

    fun edgeCount(): Int {
        return edges.count()
    }

    fun makeReadOptimized() {
        edges.makeReadOptimized()
    }

    fun find(pred: (T) -> Boolean): NodeRef? {
        for (i in nodes.indices) {
            if (pred(nodes[i])) return NodeRef(i)
        }
        return null
    }
}