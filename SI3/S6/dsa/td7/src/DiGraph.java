package graphs;

import java.util.HashMap;
import java.util.Map;

/**
 * A class for directed graph
 */
public class DiGraph extends AbstractGraph {

    // inDegree[u] is the in-degree of u
    private final Map<Vertex, Integer> inDegree;

    /**
     * builds a directed graph with no vertex
     */
    public DiGraph() {
        super();
        inDegree = new HashMap<>();
    }

    @Override
    public Vertex addVertex(String tag) throws DuplicateTagException {
        Vertex v = super.addVertex(tag);
        inDegree.put(v, 0);
        return v;
    }

    @Override
    public void addEdge(Vertex u, Vertex v, double weight) {
        checkVertex(u);
        checkVertex(v);
        if (add(u, v, weight)) {
            storeEdge(u, v, weight);
            nbEdges++;
            inDegree.put(v, inDegree.get(v) + 1);
        }
    }

    @Override
    public void removeEdge(Vertex u, Vertex v) {
        checkVertex(u);
        checkVertex(v);
        if (remove(u, v)) {
            nbEdges--;
            inDegree.put(v, inDegree.get(v) - 1);
        }
    }

    @Override
    public int degree(Vertex u) {
        checkVertex(u);
        return outDegree(u) + inDegree(u);
    }

    /**
     * returns the in-degree of u
     */
    public int inDegree(Vertex u) {
        checkVertex(u);
        return inDegree.get(u);
    }

    /**
     * returns the out-degree of u
     */
    public int outDegree(Vertex u) {
        checkVertex(u);
        return adjacencyList.get(u).size();
    }

    @Override
    public String toString() {
        return "Directed Graph\n" + super.toString();
    }

    @Override
    public Edge findEdge(Vertex u, Vertex v) {
        return edges.get(u).get(v);
    }
}
