package graphs.roadApplication

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

object Main {
    const val distanceLimit = 12000

    private fun createGraph(cities: List<City>): Graph<City> {
        val graph = Graph<City>()
        for (city in cities) {
            graph.addNode(city)
        }

        val total = cities.size / 2 * (cities.size - 1)
        val progress = ProgressBar(total)

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

    private fun createGraphPar(cities: List<City>): Graph<City> {
        val graph = Graph<City>()

        for (city in cities) {
            graph.addNode(city)
        }

        val total = cities.size / 2 * (cities.size - 1)
        val progress = ProgressBar(total)

        val executor: ExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

        val tasks = ArrayList<Future<Unit>>()

        for (i in cities.indices) {
            val task = executor.submit<Unit> {
                val lhsIndex = graph.makeRef(i)
                for (j in i + 1 until cities.size) {
                    val rhsIndex = graph.makeRef(j)

                    val distance = graph.getNode(lhsIndex).distance(graph.getNode(rhsIndex))
                    if (distance > distanceLimit) continue

                    graph.addEdge(lhsIndex, rhsIndex, distance.toInt())

                    progress.step()
                }
            }

            tasks.add(task)
        }

        tasks.forEach { it.get() }

        executor.shutdown()

        return graph
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val cityParser = CityParser("resources/fr.txt")
        val rawCities = ArrayList<City>()
        cityParser.readAll(rawCities)

        // Remove duplicates
        val cities = CityParser.removeDuplicates(rawCities)

        // Create graph
        val graph = createGraphPar(cities)

        println("Number of cities: ${graph.nodeCount()}")
        println("Number of edges: ${graph.edgeCount()}")
    }
}
