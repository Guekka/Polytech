package graphs.tools;

import graphs.*;
import graphs.GraphReaderTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GraphTraversalTest {

    @Test
    public void testDFSU1() {
        UnDiGraph graph = GraphReader.unDiGraph("A B C D C E D E E F G H G K H I H J H K I J");
        List<Vertex> path = GraphTraversal.dfs(graph, graph.getVertex("A"));
        System.out.println(path);
        assertEquals(2, path.size());
        path = GraphTraversal.dfs(graph, graph.getVertex("C"));
        System.out.println(path);
        assertEquals(4, path.size());
        path = GraphTraversal.dfs(graph, graph.getVertex("G"));
        System.out.println(path);
        assertEquals(5, path.size());
        assertEquals(graph.getVertex("G"), path.get(0));
        if (path.get(1).equals(graph.getVertex("H")))
            assertEquals(graph.getVertex("K"), path.get(4));
        else
            assertEquals(graph.getVertex("K"), path.get(1));
    }

    @Test
    public void testBFSU1() {
        UnDiGraph graph = GraphReader.unDiGraph("A B C D C E D E E F G H G K H I H J H K I J");
        List<Vertex> path = GraphTraversal.bfs(graph, graph.getVertex("A"));
        System.out.println(path);
        assertEquals(2, path.size());
        path = GraphTraversal.bfs(graph, graph.getVertex("C"));
        System.out.println(path);
        assertEquals(4, path.size());
        path = GraphTraversal.bfs(graph, graph.getVertex("G"));
        System.out.println(path);
        assertEquals(5, path.size());
        assertEquals(graph.getVertex("G"), path.get(0));
        if (path.get(1).equals(graph.getVertex("H")))
            assertEquals(graph.getVertex("K"), path.get(2));
        else
            assertEquals(graph.getVertex("K"), path.get(1));
    }


    @Test
    public void testDFSU2() {
        //GraphReader.unDiGraph("A D A E A J B C B F B G B I C F C G C H D E D F G H");
        UnDiGraph graph = GraphReaderTest.U2;
        List<Vertex> path = GraphTraversal.dfs(graph, graph.getVertex("F"));
        System.out.println(path);
        assertEquals(10, path.size());
        assertEquals(graph.getVertex("F"), path.get(0));
    }
    @Test
    public void testBFSU2() {
        //GraphReader.unDiGraph("A D A E A J B C B F B G B I C F C G C H D E D F G H");
        UnDiGraph graph = GraphReaderTest.U2;
        List<Vertex> path = GraphTraversal.bfs(graph, graph.getVertex("F"));
        System.out.println(path);
        assertEquals(10, path.size());
        assertEquals(graph.getVertex("F"), path.get(0));
    }

    @Test
    public void testBFSTree(){
        UnDiGraph graph = GraphReaderTest.tree;
        List<Vertex> path = GraphTraversal.bfs(graph, graph.getVertex("A"));
        System.out.println(path);
        assertEquals(26, path.size());
        assertEquals(graph.getVertex("A"), path.get(0));
        assertEquals(graph.getVertex("Z"), path.get(25));
    }

    @Test
    public void testDFSTree(){
        UnDiGraph graph = GraphReaderTest.tree;
        List<Vertex> path = GraphTraversal.dfs(graph, graph.getVertex("A"));
        System.out.println(path);
        assertEquals(26, path.size());
        assertEquals(graph.getVertex("A"), path.get(0));
        assertEquals(graph.getVertex("Z"), path.get(25));
        if (path.get(1).equals(graph.getVertex("B")))
            assertEquals(graph.getVertex("D"), path.get(2));
    }

    @Test
    public void testDijkstra43Nodes() throws DuplicateTagException {
        DiGraph graph = new DiGraph();
        Vertex a = graph.addVertex("A");
        Vertex b = graph.addVertex("B");
        Vertex c = graph.addVertex("C");
        Vertex d = graph.addVertex("D");
        graph.addEdge(a,b);
        graph.addEdge(a,c);
        graph.addEdge(b,d);
        graph.addEdge(c,b);
        graph.addEdge(b,a);

        List<Vertex> path = GraphTraversal.dijkstra(graph, graph.getVertex("A"), graph.getVertex("D"));
        System.out.println(path);
        assertEquals(3, path.size());
        path = GraphTraversal.dijkstra(graph, graph.getVertex("C"), graph.getVertex("A"));
        System.out.println(path);
        assertEquals(3, path.size());
        path = GraphTraversal.dijkstra(graph, graph.getVertex("C"), graph.getVertex("B"));
        System.out.println(path);
        assertEquals(2, path.size());
    }
    @Test
    public void testDijkstra4Tree(){
        UnDiGraph graph = GraphReaderTest.tree;
        List<Vertex> path = GraphTraversal.dijkstra(graph, graph.getVertex("A"), graph.getVertex("Z"));
        System.out.println(path);
        assertEquals(4, path.size());
        checkPath(graph, path);
    }

    @Test
    public void testDijkstra4WeightedGraph(){
        DiGraph graph = GraphReader.diGraph("A B 2.5 A C 5.2 B C 1.0 B D 3.0 C D 2.0 C E 4.0 D E 1.0");
        List<Vertex> path = GraphTraversal.dijkstra(graph, graph.getVertex("A"), graph.getVertex("E"));
        System.out.println(path);
        assertEquals(4, path.size());
        checkPath(graph, path);
        GraphHelper gh = new GraphHelper();
        assertEquals(gh.computeDistanceOfPath(path,graph), 6.5, 0.0001);
        System.out.println("Distance for : " + path + " = " + gh.computeDistanceOfPath(path,graph));
        path= Arrays.asList(graph.getVertex("A"),graph.getVertex("B"),graph.getVertex("C"),graph.getVertex("D"),graph.getVertex("E"));
        System.out.println("Distance for : " + path + " = " + gh.computeDistanceOfPath(path,graph));
        path= Arrays.asList(graph.getVertex("A"),graph.getVertex("C"),graph.getVertex("E"));
        System.out.println("Distance for : " + path + " = " + gh.computeDistanceOfPath(path,graph));
        assertEquals(gh.computeDistanceOfPath(path,graph), 9.2, 0.0001);

        path = GraphTraversal.dijkstra(graph, graph.getVertex("C"), graph.getVertex("E"));
        System.out.println(path);
        assertEquals(3, path.size());
        checkPath(graph, path);
        assertEquals(gh.computeDistanceOfPath(path,graph), 3.0, 0.0001);
        path= Arrays.asList(graph.getVertex("C"),graph.getVertex("E"));
        assertEquals(gh.computeDistanceOfPath(path,graph), 4.0, 0.0001);

    }

    private static void checkPath(AbstractGraph graph, List<Vertex> path) {
        for (int i = 0; i < path.size()-1; i++) {
            System.out.println(path.get(i) + " -> " + path.get(i+1) + " : " + graph.findEdge(path.get(i), path.get(i+1)).weight());
            assertNotNull(graph.findEdge(path.get(i), path.get(i+1)));
        }
    }
}
