package graphs.roadApplication

class Graph<T> {
    @JvmInline
    value class NodeRef(val index: Int)

    private val nodes = ArrayList<T>()
    private val edges = Matrix<Int>()

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
        edges.add(lhs.index, rhs.index, distance)
    }

    fun getEdge(lhs: NodeRef, rhs: NodeRef): Int {
        return edges[lhs.index, rhs.index]
    }

    fun nodeCount(): Int {
        return nodes.size
    }

    fun edgeCount(): Int {
        return edges.usedSize()
    }
}