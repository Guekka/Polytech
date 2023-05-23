package graphs.roadApplication

import graphs.roadApplication.Graph.NodeRef

/**
 * Returns the map of the connected components of 'G'
 * If cc is the returned map, then, for all vertices u and v,
 * cc.get(u) = cc.get(v) if and only if u and v are in the same
 * connected component
 */
fun <T> findConnectedComponents(graph: Graph<T>): Collection<List<NodeRef>> {
    val connectedComponents = ArrayList<Int?>()
    for (i in 0 until graph.nodeCount())
        connectedComponents.add(null)

    val progress = ProgressBar(graph.nodeCount(), "Finding connected components")
    progress.print() // print the progress bar
    var number = 0

    for (u in graph.nodes()) {
        progress.step()
        if (connectedComponents[u.index] != null)
            continue
        else
            number++

        for (sameGroup in bfs(graph, u))
            connectedComponents[sameGroup.index] = number
    }
    return connectedComponents
        .groupBy { it } // group by connected component
        .values // get the values of the map
        .map { group -> group.map { graph.makeRef(it!!) } } // convert to NodeRef
}
