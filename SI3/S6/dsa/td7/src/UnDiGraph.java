package graphs;

import java.util.*;

/**
 * A class for undirected graph
 */
public class UnDiGraph extends AbstractGraph {
	
	/**
	 * builds an undirected graph with no vertex
	 */	
	public UnDiGraph() {
		super();
	}

	@Override
	public void addEdge(Vertex u, Vertex v, double weight) {
		checkVertex(u);
		checkVertex(v);
		if ( add(u,v,weight) ) {
			storeEdge(u,v,weight);
			add(v,u,weight);
			nbEdges++;
		}
	}
	
	@Override
	public void removeEdge(Vertex u, Vertex v) {
		checkVertex(u);
		checkVertex(v);
		if ( remove(u,v) ) {
			remove(v,u);
			nbEdges--;
		}
	}

	@Override
	public int degree(Vertex u) {
		checkVertex(u);
		return adjacencyList.get(u).size();
	}

	@Override
	public int inDegree(Vertex u) {
		return degree(u);
	}

	@Override
	public int outDegree(Vertex u) {
		return degree(u);
	}
	
	@Override
	public String toString() {
		return "Undirected Graph\n" + super.toString();
	}
	
	@Override
    public Edge findEdge(Vertex u, Vertex v) {
		Map<Vertex,Edge> map = edges.get(u);
		if ( map == null )
			return edges.get(v).get(u);
		Edge e = map.get(v);
		if ( e == null)
			return edges.get(v).get(u);
		return e;
	}
}
