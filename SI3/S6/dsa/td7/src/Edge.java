package graphs.graph;

/**
 * An interface for the edges of a graph.
 */
public interface Edge extends Comparable<Edge> {
		
	/**
	 * Returns the origin of the edge
	 */
	 Vertex origin();
	
	/**
	 * Returns the destination of the edge
	 */
	 Vertex destination();
	
	/**
	 * Returns the weight of the edge
	 */
	 double weight();
}
