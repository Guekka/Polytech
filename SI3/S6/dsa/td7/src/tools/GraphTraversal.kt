package graphs.tools

import graphs.AbstractGraph
import graphs.Graph
import graphs.Vertex
import java.util.*


/* -------------------- DFS ---------------------- */
/**
 * Returns the list of vertices of 'graph' in DFS order
 *
 * @param graph
 * @param start
 * @return
 */
fun dfs(graph: Graph, start: Vertex): List<Vertex> {
    val visited = ArrayList<Vertex>()
    val stack = ArrayList<Vertex>()
    stack.add(start)
    while (stack.isNotEmpty()) {
        val current = stack.removeLast()
        if (visited.contains(current)) {
            continue
        }
        visited.add(current)
        stack.addAll(graph.adjacents(current)) // will be filtered later
    }

    return ArrayList(visited)
}

/* -------------------- BFS ---------------------- */
/**
 * Returns the list of vertices of 'graph' in BFS order
 *
 * @param graph
 * @param start
 * @return
 */
fun bfs(graph: Graph, start: Vertex): List<Vertex> {
    val visited = ArrayList<Vertex>()
    val queue = ArrayList<Vertex>()
    queue.add(start)
    visited.add(start)
    while (queue.isNotEmpty()) {
        val current = queue.removeAt(0)
        for (adj in graph.adjacents(current)) {
            if (!visited.contains(adj)) {
                visited.add(adj)
                queue.add(adj)
            }
        }
    }

    return ArrayList(visited)
}

/* -------------------- Dijkstra ---------------------- */
fun dijkstra(graph: AbstractGraph, start: Vertex, end: Vertex): List<Vertex> {
    val frontier = PriorityQueue<Vertex>()
    frontier.add(start)
    val cameFrom = HashMap<Vertex, Vertex>()
    val costSoFar = HashMap<Vertex, Double>()
    cameFrom[start] = start
    costSoFar[start] = 0.0

    while (frontier.isNotEmpty()) {
        val current = frontier.poll()

        if (current == end)
            break

        for (next in graph.adjacents(current)) {
            val edge = graph.findEdge(current, next)
            val newCost = costSoFar[current]!! + edge.weight()
            if (!costSoFar.containsKey(next) || newCost < costSoFar[next]!!) {
                costSoFar[next] = newCost
                frontier.add(next)
                cameFrom[next] = current
            }
        }

    }
    val path = ArrayList<Vertex>()
    var current = end
    while (current != start) {
        path.add(current)
        current = cameFrom[current]!!
    }
    path.add(start)
    return path.reversed()
}
