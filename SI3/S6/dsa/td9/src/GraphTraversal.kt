package graphs.roadApplication

import graphs.roadApplication.Graph.NodeRef
import java.util.*

/**
 * Returns the list of vertices of 'graph' in DFS order
 *
 * @param graph
 * @param start
 * @return
 */
fun <T> dfs(graph: Graph<T>, start: NodeRef): List<NodeRef> {
    val visited = ArrayList<NodeRef>()
    val stack = ArrayList<NodeRef>()
    stack.add(start)
    while (stack.isNotEmpty()) {
        val current = stack.removeLast()
        if (visited.contains(current)) {
            continue
        }
        visited.add(current)
        stack.addAll(graph.adjacents(current).map { it.first }) // will be filtered later
    }

    return ArrayList(visited)
}

/**
 * Returns the list of vertices of 'graph' in BFS order
 *
 * @param graph
 * @param start
 * @return
 */
fun <T> bfs(graph: Graph<T>, start: NodeRef): List<NodeRef> {
    val progress = ProgressBar(graph.nodeCount(), "BFS")
    val visited = ArrayList<NodeRef>()
    val queue = ArrayList<NodeRef>()
    queue.add(start)
    visited.add(start)
    while (queue.isNotEmpty()) {
        val current = queue.removeAt(0)
        progress.step()
        for ((adj, _) in graph.adjacents(current)) {
            if (!visited.contains(adj)) {
                visited.add(adj)
                queue.add(adj)
            }
        }
    }

    progress.complete()

    return ArrayList(visited)
}

/* -------------------- Dijkstra ---------------------- */
fun <T> dijkstra(graph: Graph<T>, start: NodeRef, end: NodeRef): List<NodeRef> {
    val frontier = ArrayDeque<NodeRef>() // TODO: use a priority queue
    frontier.add(start)
    val cameFrom = HashMap<NodeRef, NodeRef>()
    val costSoFar = HashMap<NodeRef, Double>()
    cameFrom[start] = start
    costSoFar[start] = 0.0

    while (frontier.isNotEmpty()) {
        val current = frontier.poll()

        if (current == end)
            break

        for ((next, edge) in graph.adjacents(current)) {
            val newCost = costSoFar[current]!! + edge
            if (!costSoFar.containsKey(next) || newCost < costSoFar[next]!!) {
                costSoFar[next] = newCost
                frontier.add(next)
                cameFrom[next] = current
            }
        }

    }
    val path = ArrayList<NodeRef>()
    var current = end
    while (current != start) {
        path.add(current)
        current = cameFrom[current]!!
    }
    path.add(start)
    return path.reversed()
}
