package graphs.tools

import graphs.AbstractGraph
import graphs.DiGraph
import graphs.Graph
import graphs.Vertex
import java.util.*

/**
 * A class to find cycles in undirected and directed graphs
 */
class GraphHelper  /////////////// Cycle detection for undirected graphs
{
    fun hasCycle(graph: Graph): Boolean {
        val visited = ArrayList<Vertex>()
        for (vertex in graph.vertices()) {
            if (!visited.contains(vertex) && hasCycleUtil(graph, vertex, visited, null)) {
                return true
            }
        }
        return false
    }

    private fun hasCycleUtil(graph: Graph, current: Vertex, visited: ArrayList<Vertex>, parent: Vertex?): Boolean {
        visited.add(current)
        for (adj in graph.adjacents(current)) {
            if (!visited.contains(adj)) {
                if (hasCycleUtil(graph, adj, visited, current)) {
                    return true
                }
            } else if (adj != parent) {
                return true
            }
        }
        return false
    }

    /**
     * Returns a path as from vertex 'u' to vertex 'v' in the graph 'G'
     * as a list of vertices if such a path exists, the empty list otherwise
     */
    fun findPath(graph: Graph, u: Vertex, v: Vertex): List<Vertex> {
        val path = dfs(graph, u)
        if (path.contains(v)) {
            val index = path.indexOf(v)
            return path.subList(0, index + 1)
        }
        return ArrayList()
    }
    /* ------------------- Path finding ------------------- */
    /**
     * Returns a root of the graph 'G' if
     * such root exists, null otherwise
     */
    fun findRoot(diGraph: DiGraph): Optional<Vertex> {
        return Optional.empty()
    }

    /* ------------------- Root finding ------------------- */
    fun computeDistanceOfPath(path: List<Vertex>, graph: AbstractGraph): Double {
        var distance = 0.0
        for (i in 0 until path.size - 1) {
            val u = path[i]
            val v = path[i + 1]
            if (!graph.adjacents(u).contains(v)) {
                return -1.0
            }
            distance += graph.findEdge(u, v)!!.weight()
        }
        return distance
    }

    private enum class Status {
        UN_DISCOVERED,
        IN_PROGRESS,
        COMPLETED
    } // status of vertex
    ///////////////
}
