package graphs.roadApplication


object Main {
    const val distanceLimit = 12000 // 12km

    private fun createGraph(cities: List<City>): Graph<City> {
        val graph = Graph<City>()
        for (city in cities) {
            graph.addNode(city)
        }

        val total = cities.size / 2 * (cities.size - 1)
        val progress = ProgressBar(total, "Creating graph")

        for (i in cities.indices) {
            val lhsIndex = graph.makeRef(i)
            for (j in i + 1 until cities.size) {
                progress.step()

                val rhsIndex = graph.makeRef(j)

                val distance = graph.getNode(lhsIndex).distance(graph.getNode(rhsIndex))
                if (distance > distanceLimit) continue

                graph.addEdge(lhsIndex, rhsIndex, distance.toInt())
            }
        }
        return graph
    }

    private fun findShortestPath(
        graph: Graph<City>,
        lhs: Graph.NodeRef,
        rhs: Graph.NodeRef
    ): List<Graph.NodeRef> {
        return dijkstra(graph, lhs, rhs)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val cityParser = CityParser("resources/fr.txt")
        val rawCities = ArrayList<City>()
        cityParser.readAll(rawCities)

        // Remove duplicates
        var cities: List<City>? = CityParser.removeDuplicates(rawCities)
        rawCities.clear() // free memory

        // Create graph
        val graph = createGraph(cities!!)
        cities = null // free memory

        println("Number of cities: ${graph.nodeCount()}")
        println("Number of edges: ${graph.edgeCount()}")

        graph.makeReadOptimized()

        // Count number of connected components
        val components = findConnectedComponents(graph)
        val componentCount = components.size

        println("Number of connected components: $componentCount")
        for (component in components.sortedBy { it.size }) {
            println("Component size: ${component.size}")
        }

        // Find the shortest path between two cities
        val nice = graph.find { it.nom == "Nice" }
        val paris = graph.find { it.nom == "Paris" }

        val path = findShortestPath(graph, nice!!, paris!!)
        println("Shortest path between Nice and Paris:")
        for (city in path) {
            println(graph.getNode(city))
        }
    }
}
