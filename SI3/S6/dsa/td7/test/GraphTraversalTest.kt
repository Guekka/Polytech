package graphs.tools

import graphs.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

internal class GraphTraversalTest {
    @Test
    fun testDFSU1() {
        val graph = GraphReader.unDiGraph("A B C D C E D E E F G H G K H I H J H K I J")
        var path = dfs(graph, graph.getVertex("A"))
        println(path)
        Assertions.assertEquals(2, path.size)
        path = dfs(graph, graph.getVertex("C"))
        println(path)
        Assertions.assertEquals(4, path.size)
        path = dfs(graph, graph.getVertex("G"))
        println(path)
        Assertions.assertEquals(5, path.size)
        Assertions.assertEquals(graph.getVertex("G"), path[0])
        if (path[1] == graph.getVertex("H")) Assertions.assertEquals(
            graph.getVertex("K"),
            path[4]
        ) else Assertions.assertEquals(graph.getVertex("K"), path[1])
    }

    @Test
    fun testBFSU1() {
        val graph = GraphReader.unDiGraph("A B C D C E D E E F G H G K H I H J H K I J")
        var path: List<Vertex> = bfs(graph, graph.getVertex("A"))
        println(path)
        Assertions.assertEquals(2, path.size)
        path = bfs(graph, graph.getVertex("C"))
        println(path)
        Assertions.assertEquals(4, path.size)
        path = bfs(graph, graph.getVertex("G"))
        println(path)
        Assertions.assertEquals(5, path.size)
        Assertions.assertEquals(graph.getVertex("G"), path[0])
        if (path[1] == graph.getVertex("H"))
            Assertions.assertEquals(graph.getVertex("K"), path[2])
        else
            Assertions.assertEquals(graph.getVertex("K"), path[1])
    }

    @Test
    fun testDFSU2() {
        //GraphReader.unDiGraph("A D A E A J B C B F B G B I C F C G C H D E D F G H");
        val graph = GraphReaderTest.U2
        val path: List<Vertex> = dfs(graph, graph.getVertex("F"))
        println(path)
        Assertions.assertEquals(10, path.size)
        Assertions.assertEquals(graph.getVertex("F"), path[0])
    }

    @Test
    fun testBFSU2() {
        //GraphReader.unDiGraph("A D A E A J B C B F B G B I C F C G C H D E D F G H");
        val graph = GraphReaderTest.U2
        val path: List<Vertex> = bfs(graph, graph.getVertex("F"))
        println(path)
        Assertions.assertEquals(10, path.size)
        Assertions.assertEquals(graph.getVertex("F"), path[0])
    }

    @Test
    fun testBFSTree() {
        val graph = GraphReaderTest.tree
        val path: List<Vertex> = bfs(graph, graph.getVertex("A"))
        println(path)
        Assertions.assertEquals(26, path.size)
        Assertions.assertEquals(graph.getVertex("A"), path[0])
        Assertions.assertEquals(graph.getVertex("Z"), path[25])
    }

    @Test
    fun testDFSTree() {
        val graph = GraphReaderTest.tree
        val path: List<Vertex> = dfs(graph, graph.getVertex("A"))
        println(path)
        Assertions.assertEquals(26, path.size)
        Assertions.assertEquals(graph.getVertex("A"), path[0])
        Assertions.assertEquals(graph.getVertex("I"), path[25])
        if (path[1] == graph.getVertex("B"))
            Assertions.assertEquals(graph.getVertex("D"), path[2])
    }

    @Test
    @Throws(DuplicateTagException::class)
    fun testDijkstra43Nodes() {
        val graph = DiGraph()
        val a = graph.addVertex("A")
        val b = graph.addVertex("B")
        val c = graph.addVertex("C")
        val d = graph.addVertex("D")
        graph.addEdge(a, b)
        graph.addEdge(a, c)
        graph.addEdge(b, d)
        graph.addEdge(c, b)
        graph.addEdge(b, a)
        var path: List<Vertex?> = dijkstra(graph, graph.getVertex("A"), graph.getVertex("D"))
        println(path)
        Assertions.assertEquals(3, path.size)
        path = dijkstra(graph, graph.getVertex("C"), graph.getVertex("A"))
        println(path)
        Assertions.assertEquals(3, path.size)
        path = dijkstra(graph, graph.getVertex("C"), graph.getVertex("B"))
        println(path)
        Assertions.assertEquals(2, path.size)
    }

    @Test
    fun testDijkstra4Tree() {
        val graph = GraphReaderTest.tree
        val path: List<Vertex> = dijkstra(graph, graph.getVertex("A"), graph.getVertex("Z"))
        println(path)
        Assertions.assertEquals(4, path.size)
        checkPath(graph, path)
    }

    @Test
    fun testDijkstra4WeightedGraph() {
        val graph = GraphReader.diGraph("A B 2.5 A C 5.2 B C 1.0 B D 3.0 C D 2.0 C E 4.0 D E 1.0")
        var path: List<Vertex> = dijkstra(graph, graph.getVertex("A"), graph.getVertex("E"))
        println(path)
        Assertions.assertEquals(4, path.size)
        checkPath(graph, path)
        val gh = GraphHelper()
        Assertions.assertEquals(6.5, gh.computeDistanceOfPath(path, graph), 0.0001)
        println("Distance for : " + path + " = " + gh.computeDistanceOfPath(path, graph))
        path = Arrays.asList(
            graph.getVertex("A"),
            graph.getVertex("B"),
            graph.getVertex("C"),
            graph.getVertex("D"),
            graph.getVertex("E")
        )
        println("Distance for : " + path + " = " + gh.computeDistanceOfPath(path, graph))
        path = Arrays.asList(graph.getVertex("A"), graph.getVertex("C"), graph.getVertex("E"))
        println("Distance for : " + path + " = " + gh.computeDistanceOfPath(path, graph))
        Assertions.assertEquals(gh.computeDistanceOfPath(path, graph), 9.2, 0.0001)
        path = dijkstra(graph, graph.getVertex("C"), graph.getVertex("E"))
        println(path)
        Assertions.assertEquals(3, path.size)
        checkPath(graph, path)
        Assertions.assertEquals(gh.computeDistanceOfPath(path, graph), 3.0, 0.0001)
        path = Arrays.asList(graph.getVertex("C"), graph.getVertex("E"))
        Assertions.assertEquals(gh.computeDistanceOfPath(path, graph), 4.0, 0.0001)
    }

    companion object {
        private fun checkPath(graph: AbstractGraph, path: List<Vertex>) {
            for (i in 0 until path.size - 1) {
                println(
                    path[i].toString() + " -> " + path[i + 1] + " : " + graph.findEdge(path[i], path[i + 1]).weight()
                )
                Assertions.assertNotNull(graph.findEdge(path[i], path[i + 1]))
            }
        }
    }
}
