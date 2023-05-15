package graphs;

import java.util.*;

/**
 * A class for abstract graph. An abstract graph is the common 
 * implementation between directed graph (DiGraph) and undirected
 * graph (UnDiGraph)
 */
public abstract class AbstractGraph implements Graph {

	// the adjacency list
	protected Map<Vertex,List<Vertex>> adjacencyList;
	// the number of edges
	protected int nbEdges;
	// the set of all tags
	private final Map<String,Vertex> tags;
	// the set of edges
	protected Map<Vertex,Map<Vertex,Edge>> edges;

	/**
	 * Builds an abstract graph with no vertices
	 */
	protected AbstractGraph() {
		adjacencyList = new HashMap<>();
		tags = new HashMap<>();
		edges = new HashMap<>();
		nbEdges = 0;
	}

	/////////////// public methods ///////////////

	/**
	 * Returns the number of vertices
	 */
	public int nbVertices() {
		return adjacencyList.size();
	}

	/**
	 * Returns the number of edges
	 */
	public int nbEdges() {
		return nbEdges;
	}

	/**
	 * Add a new vertex of tag 'tag' to the graph
	 * and returns that vertex
	 * If 'tag' is already used in that graph, the
	 * method raises a DuplicateTagException exception
	 */
	public Vertex addVertex(String tag) throws DuplicateTagException {
		if ( tags.containsKey(tag) )
			throw new DuplicateTagException(tag);
		InnerVertex v = new InnerVertex(this,tag);
		tags.put(tag, v);
		adjacencyList.put(v,new LinkedList<>());
		return v;
	}

	/**
	 * Return the vertex of tag 'tag' from the graph
	 * If no vertex has tag 'tag', the method
	 * returns null
	 */
	public Vertex getVertex(String tag) {
		if ( ! tags.containsKey(tag) )
			return null;
		return tags.get(tag);
	}


	/**
	 * Returns a random vertex from the graph
	 */
	public Vertex getRandomVertex() {
		return adjacencyList.keySet().iterator().next();
	}

	/**
	 * Adds the new edge ('u','v') to the graph unless
	 * this edge is already present in the graph
	 */
	public void addEdge(Vertex u, Vertex v) {
		checkVertex(u);
		checkVertex(v);
		addEdge(u,v,1.0);
	}

	/**
	 * Removes edge 'e' from the graph unless
	 * this edge is not present in the graph
	 */
	public void removeEdge(Edge e) {
		removeEdge(e.origin(), e.destination());
	}

	/**
	 * Returns an iterable object over the vertices
	 * of the graph. The vertices come in random
	 * order
	 */
	public Iterable<Vertex> vertices() {
		return adjacencyList.keySet();
	}

	/**
	 * Returns an iterable object over the edges
	 * of the graph. The edges come in random
	 * order
	 */
	public Iterable<Edge> edges() {
		return new EdgeIterator();
	}

	/**
	 * Returns an iterable object over the adjacent
	 * vertices of vertex 'u' in the graph. The adjacents
	 * come in random order
	 */
	public Iterable<Vertex> adjacents(Vertex u) {
		checkVertex(u);
		return adjacencyList.get(u);
	}

	/**
	 * Returns true if 'v' is adjacent to 'u' in
	 * the graph, false otherwise
	 */
	public boolean adjacents(Vertex u, Vertex v) {
		checkVertex(u);
		checkVertex(v);
		for ( Vertex ve : adjacencyList.get(u) )
			if ( ve == v )
				return true;
		return false;
	}

	/**
	 * Returns an iterable object over the incident
	 * edges of vertex 'u' in the graph. The incident
	 * edges come in random order. For all incident
	 * edge e, e.origin() = u
	 */
	public Iterable<Edge> incidents(Vertex u) {
		checkVertex(u);
		return new IncidentEdgeIterator(u);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("number of verticies: ").append(nbVertices());
		s.append("\nnumber of edges: ").append(nbEdges);
		s.append("\nvertices:");
		for ( Vertex u : vertices() )
			s.append(" ").append(u);
		s.append("\nedges :");
		for ( Edge e : edges() )
			s.append(" ").append(e);
		return s.toString();
	}

	/////////////// public abstract methods ///////////////




	/////////////// protected abstract methods ///////////////

	public abstract Edge findEdge(Vertex u, Vertex v);

	/////////////// protected methods ///////////////

	protected boolean add(Vertex u, Vertex v, double weight) {
		if ( adjacencyList.get(u).contains(v) )
			return false;
		adjacencyList.get(u).add(v);
		return true;
	}

	protected boolean remove(Vertex u, Vertex v) {
		if ( adjacencyList.get(u).contains(v) ) {
			adjacencyList.get(u).remove(v);
			return true;
		}
		return false;
	}

	protected void checkVertex(Vertex v) {
		if ( ((InnerVertex) v).fromGraph != this )
			throw new BadVertexException(v.getTag());
	}

	protected void storeEdge(Vertex u , Vertex v, double weight) {
		//edges.computeIfAbsent(u, k -> new HashMap<>());
		if ( edges.get(u) == null )
			edges.put(u, new HashMap<Vertex,Edge>());
		edges.get(u).put(v,new InnerEdge(u,v,weight));
	}

	/////////////// private class and methods ///////////////

	// A class for vertices
	private class InnerVertex implements Vertex {
		AbstractGraph fromGraph; // back link to the host graph
		String tag; // the tag of the vertex
		double weight = 1; // the weight of the vertex (mainly for the Dijkstra algorithm) by default 1 to compute the number of edges in a path when the weight of the edges is not specified

		// Builds a vertex of tag 'tag' and linked to the
		// graph 'fromGraph'
		InnerVertex(AbstractGraph fromGraph, String tag) {
			this.tag = tag;
			this.fromGraph = fromGraph;
		}

		/**
		 * Returns the tag of the vertex
		 */
		public String getTag() {
			return tag;
		}

		/**
		 * Returns the weight of the vertex
		 */
		public double getWeight() {
			return weight;
		}

		/**
		 * Sets the weight of the vertex to 'weight'
		 */
		public void setWeight(double weight) {
			this.weight = weight;
		}

		/**
		 * Vertices can have a weight and be comparable
		 * on that weight (mainly for the Dijkstra algorithm)
		 */
		public int compareTo(Vertex v) {
			InnerVertex vv = (InnerVertex) v;
			return Double.compare(weight, vv.weight);
		}

		@Override
		public String toString() {
			return tag;
		}
	}

	// a class for edges
	private class InnerEdge implements Edge {

		Vertex x; // the origin of the edge
		Vertex y; // the destination of the edge
		double weight; // the weight of the edge

		/**
		 * builds the edge (x,y,weight)
		 */
		InnerEdge(Vertex x, Vertex y, double weight) {
			this.x = x;
			this.y = y;
			this.weight = weight;
		}

		/**
		 * Returns the origin of the edge
		 */
		public Vertex origin() {
			return x;
		}

		/**
		 * Returns the destination of the edge
		 */
		public Vertex destination() {
			return y;
		}

		/**
		 * Returns the weight of the edge
		 */
		public double weight() {
			return weight;
		}

		/**
		 * edges can have a weight and be compared on that weight
		 * (mainly for Prim and Kruskal algorithms)
		 */
		public int compareTo(Edge e) {
			InnerEdge ee = (InnerEdge) e;
			return Double.compare(weight, ee.weight);
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			InnerEdge innerEdge = (InnerEdge) o;
			return Double.compare(innerEdge.weight, weight) == 0 && Objects.equals(x, innerEdge.x) && Objects.equals(y, innerEdge.y);
		}


		@Override
		public int hashCode() {
			return Objects.hash(x, y, weight);
		}
	}

	// an inner class to build iterators over all edges
	private class EdgeIterator implements Iterable<Edge>, Iterator<Edge> {

		Iterator<Map.Entry<Vertex,Map<Vertex,Edge>>> firstMapIterator;
		Iterator<Map.Entry<Vertex,Edge>> secondMapIterator;

		EdgeIterator() {
			firstMapIterator = edges.entrySet().iterator();
			if ( firstMapIterator.hasNext() ) {
				secondMapIterator = firstMapIterator.next().getValue().entrySet().iterator();
			}

		}

		public Iterator<Edge> iterator() {
			return this;
		}


		public boolean hasNext() {
			return secondMapIterator.hasNext() || firstMapIterator.hasNext();
		}

		public Edge next() {
			if ( ! secondMapIterator.hasNext() )
				secondMapIterator = firstMapIterator.next().getValue().entrySet().iterator();
			return secondMapIterator.next().getValue();
		}
	}

	// an inner class to iterate over the incident edges
	private class IncidentEdgeIterator implements Iterable<Edge>, Iterator<Edge> {

		Vertex origin;
		Iterator<Vertex> adjacents;

		IncidentEdgeIterator(Vertex u) {
			origin = u;
			adjacents = adjacencyList.get(u).iterator();
		}

		public Iterator<Edge> iterator() {
			return this;
		}

		public boolean hasNext() {
			return adjacents.hasNext();
		}

		public Edge next() {
			return findEdge(origin,adjacents.next());
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
