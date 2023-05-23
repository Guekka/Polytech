package graphs.roadApplication

import org.junit.jupiter.api.Assertions.assertEquals
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
        assertEquals(1, graph.getNode(nodeRef))
    }

    @Test
    fun edgeIsAdded() {
        val lhs = graph.addNode(1)
        val rhs = graph.addNode(2)
        graph.addEdge(lhs, rhs, 10)
        assertEquals(10, graph.getEdge(lhs, rhs))
    }

    @Test
    fun nodeRefIsCreated() {
        val nodeRef = graph.addNode(1)
        assertEquals(nodeRef, graph.makeRef(0))
    }

    @Test
    fun adjacentsAreFound() {
        val lhs = graph.addNode(1)
        val rhs = graph.addNode(2)
        graph.addEdge(lhs, rhs, 10)

        graph.addNode(3) // this node should not be found

        graph.makeReadOptimized()

        assertEquals(1, graph.adjacents(lhs).size)
        assertEquals(Pair(rhs, 10), graph.adjacents(lhs).first())
    }

}