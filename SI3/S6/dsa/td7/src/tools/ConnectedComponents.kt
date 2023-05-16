package graphs.tools

import graphs.GraphReader
import graphs.UnDiGraph
import graphs.Vertex

/**
 * A class to find connected components from an undirected graphs
 */
class ConnectedComponents(u1: UnDiGraph?) {
    private val unDiGraph = u1

    /**
     * Returns the map of the connected components of 'G'
     * If cc is the returned map, then, for all vertices u and v,
     * cc.get(u) = cc.get(v) if and only if u and v are in the same
     * connected component
     */
    fun find(): Map<Vertex, Int> {
        if (unDiGraph == null)
            throw NullPointerException("The graph is null")

        val connectedComponents = HashMap<Vertex, Int>()

        var number = 0
        for (u in unDiGraph.vertices()) {
            if (!connectedComponents.containsKey(u)) {
                number++
            }
            for (sameGroup in bfs(unDiGraph, u))
                connectedComponents[sameGroup] = number
        }
        return connectedComponents
    }

    companion object {
        @JvmStatic
        fun main(s: Array<String>) {
            val u1 = GraphReader.unDiGraph("A B C D C E D E E F G H G K H I H J H K I J")
            val u2 = GraphReader.unDiGraph("A D A E A J B C B F B G B I C F C G C H D E D F G H")
            var ccFinder = ConnectedComponents(u1)
            var cc = ccFinder.find()
            println(cc)
            ccFinder = ConnectedComponents(u2)
            cc = ccFinder.find()
            println(cc)
        }
    }
}
