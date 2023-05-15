package graphs.graph;

/**
 * An interface for the vertices of a graph.
 */
public interface Vertex extends Comparable<Vertex> {
	
	/**
	 * Returns the tag of the vertex
	 */
	 String getTag();
	
	/**
	 * Returns the weight of the vertex
	 */	
	 double getWeight();
	
	/**
	 * Sets the weight of the vertex to 'weight'
	 */		
	 void setWeight(double weight);

}
