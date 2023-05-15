package graphs.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A class to demonstrate the use of the graph methods
 * @author Marc Gaetano
 * @author Mireille Blay
 * @// TODO: 11/05/2023  add more tests
 *
 */
public class TestGraph {


	/**
	 * Displays all information about the graph 'G'
	 * (directed or undirected) named 'name'
	 */
	private static void testGraphMethods(String name, Graph G) {
		System.out.println(name);
		System.out.println(G);

		System.out.println("\nVertices enumeration:");
		for ( Vertex vertex :  G.vertices() )
			System.out.print(vertex.getTag() + " ");
		System.out.println();

		System.out.println("\nEdges enumeration:");
		for ( Edge edge :  G.edges() )
			System.out.print("(" + edge.origin() + "," + edge.destination() + ") ");
		System.out.println();

		System.out.println("\nAdjacents enumeration:");
		for ( Vertex vertex :  G.vertices() ) {
			System.out.print("Adjacents of vertex " + vertex + ": ");
			for ( Vertex adjacent : G.adjacents(vertex) )
				System.out.print(adjacent + " ");
			System.out.println();
		}

		System.out.println("\nIncidents enumeration:");
		for ( Vertex vertex :  G.vertices() ) {
			System.out.println("Incident edges of vertex " + vertex + ":");
			for ( Edge edge : G.incidents(vertex) )
				System.out.println("   " + edge + ", origin = " + edge.origin() + ", destination = " + edge.destination());
			System.out.println();
		}

		System.out.println("\nIn-degree of vertices:");
		for ( Vertex vertex :  G.vertices() )
			System.out.println("in-degree(" + vertex + ") = " + G.inDegree(vertex));
		System.out.println();

		System.out.println("\nOut-degree of vertices:");
		for ( Vertex vertex :  G.vertices() )
			System.out.println("out-degree(" + vertex + ") = " + G.outDegree(vertex));
		System.out.println();

		System.out.println("\n(total) degree of vertices:");
		for ( Vertex vertex :  G.vertices() )
			System.out.println("degree(" + vertex + ") = " + G.degree(vertex));
		System.out.println();

	}

	@Test
	void testReadWeightedGraph() {
		GraphReader.diGraph("A B 2.5");
		GraphReader.diGraph("A B 2.5 A C 5.2");
		System.out.println("-------------------------------\n");
		DiGraph wg = GraphReader.diGraph("A B 2.5 A C 5.2 B C 1.0 B D 3.0 C D 2.0 C E 4.0 D E 1.0");
		testGraphMethods("Weighted graph:", wg );
		System.out.println("-------------------------------\n");
		Edge e = wg.findEdge(wg.getVertex("A"),wg.getVertex("B"));
		assertEquals(2.5, e.weight());
		e = wg.findEdge(wg.getVertex("B"),wg.getVertex("D"));
		assertEquals(3.0, e.weight());
	}

	@Test
	void testWeighted(){
		assertTrue(GraphReader.weighted("A B 2.5"));
		System.out.println("-------------------------------\n");
		assertTrue(GraphReader.weighted("A B 2.5 A C 5.2 B C 1.0 B D 3.0 C D 2.0 C E 4.0 D E 1.0"));
	}
	/**
	 * You can display information about graph here
	 */
/*	public static void main(String[] args) {
		testGraphMethods("U1:",U1);
		System.out.println("-------------------------------\n");
		testGraphMethods("D1:",D1);
		System.out.println("-------------------------------\n");
		testGraphMethods("Weighted graph:", GraphReaderTest.weightedGraph );
		System.out.println("-------------------------------\n");
		testGraphMethods("Weighted graph:", GraphReaderTest.weightedDiGraph );
		//// you can test more graphs here
	}

 */
}
