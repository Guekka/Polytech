package graphs.tools.ORIGIN;

import graphs.graph.*;

import java.util.*;

/**
 * A class to find cycles in undirected and directed graphs
 */
public class GraphHelper {
	
	private  Graph aGraph;


	/////////////// Cycle detection for undirected graphs

	public GraphHelper() {
		//TO IMPLEMENT
	}

	/**
	 * Returns true if the graph 'G' is cyclic
	 * false otherwise
	 */
	public boolean hasCycle(UnDiGraph unDiGraph) {
		return false;
	}
	
	/**
	 * Returns true if a cycle was found by traversing
	 * the graph, coming from vertex from and going by vertex u
	 * Precondition: vertex 'from' is visited and vertex 'u' is
	 * not visited
	 */
	private boolean hasCycle(Vertex u, Vertex from) {
		return false;
	}
	
	/////////////// Cycle detection for directed graphs
	
	private enum Status {UN_DISCOVERED, IN_PROGRESS, COMPLETED} // status of vertex

	/**
	 * Returns true if the graph 'G' is cyclic
	 * false otherwise
	 */
	public boolean hasCycle(DiGraph diGraph) {
		return false;
	}
	
	/**
	 * Returns true if a cycle was found by traversing
	 * the graph from vertex u
	 * Precondition: vertex 'u' is 'UnDiscovered'
	 */
	private boolean hasCycle(Vertex u) {
		return false;
	}
	
	

	/* ------------------- Path finding ------------------- */

	/**
	 * Returns a path as from vertex 'u' to vertex 'v' in the graph 'G'
	 * as a list of vertices if such a path exists, the empty list otherwise
	 */
	public List<Vertex> findPath(Graph graph, Vertex u, Vertex v) {
		//TO IMPLEMENT
		List<Vertex> path = new LinkedList<>();
		return path;
	}

	/* ------------------- Root finding ------------------- */

	/**
	 * Returns a root of the graph 'G' if
	 * such root exists, null otherwise
	 */
	public Optional<Vertex> findRoot(DiGraph diGraph) {
		return Optional.empty();
	}


	public  double computeDistanceOfPath(List<Vertex> path, AbstractGraph graph) {
		return 0.0;
	}

	/////////////// 

}
