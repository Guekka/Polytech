package graphs.roadApplication

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GraphTest {
    private var graph = Graph<Int>()

    @BeforeEach
    fun setUp() {
        graph = Graph()
    }

    @Test
    fun nodeIsAdded() {
        val nodeRef = graph.addNode(1)
        assert(graph.getNode(nodeRef) == 1)
    }

    @Test
    fun edgeIsAdded() {
        val lhs = graph.addNode(1)
        val rhs = graph.addNode(2)
        graph.addEdge(lhs, rhs, 10)
        assert(graph.getEdge(lhs, rhs) == 10)
    }

    @Test
    fun nodeRefIsCreated() {
        val nodeRef = graph.addNode(1)
        assert(graph.makeRef(0) == nodeRef)
    }


}