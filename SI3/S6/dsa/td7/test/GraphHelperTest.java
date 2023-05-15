package graphs.tools;

import graphs.graph.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GraphHelperTest {

    @Test
    void hasCycleInGraphsOfOneNode() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        g.addVertex("A");
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(g));

        DiGraph dg = new DiGraph();
        dg.addVertex("A");
        assertFalse(gh.hasCycle(dg));
    }
    @Test
    void hasCycleInUndirectedGraphsOfTwoNodes() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a, b);
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(g));
    }
    @Test
    void hasCycleInDirectedGraphsOfTwoNodes() throws DuplicateTagException {
        DiGraph g = new DiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a, b);
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(g));
        g.addEdge(b, a);
        assertTrue(gh.hasCycle(g));
    }

    @Test
    void hasCycleInUndirectedGraphsOfTreeNodes() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a, b);
        Vertex c = g.addVertex("C");
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(g));
        g.addEdge(b, c);
        assertFalse(gh.hasCycle(g));
        g.addEdge(c, a);
        assertTrue(gh.hasCycle(g));
    }

    void hasCycleInUndirectedGraphsOfFourNodes() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        Vertex h = g.addVertex("H");
        Vertex b = g.addVertex("B");
        Vertex f = g.addVertex("F");
        Vertex d = g.addVertex("D");
        g.addEdge(h, b);
        g.addEdge(b, f);
        g.addEdge(b, d);
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(g));
    }
    @Test
    void hasCycleInUndirectedGraphsOfU3() {
        UnDiGraph U3 = GraphReader.unDiGraph("A E B D B F B H C G G I G J");
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(U3));
    }

    @Test
    void hasCycleInUndirectedGraphsOfU2() {
        UnDiGraph U2 = GraphReader.unDiGraph("A D A E A J B C B F B G B I C F C G C H D E D F G H");
        GraphHelper gh = new GraphHelper();
        assertTrue(gh.hasCycle(U2));
    }
    @Test
    void hasCycleInDirectedGraphsOfD2() {
        DiGraph D2 = GraphReader.diGraph("A C A E B D D F D G E C F B");
        GraphHelper gh = new GraphHelper();
        assertTrue(gh.hasCycle(D2));
    }

    @Test
    void hasCycleInDirectedGraphsOfThreeNodes() throws DuplicateTagException {
        DiGraph g = new DiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a, b);
        Vertex c = g.addVertex("C");
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(g));
        g.addEdge(b, c);
        assertFalse(gh.hasCycle(g));
        g.addEdge(a, c);
        assertFalse(gh.hasCycle(g));
        g.addEdge(c, a);
        assertTrue(gh.hasCycle(g));
    }

    @Test
    void hasCycleInDirectedGraphsOfFourNodes() throws DuplicateTagException {
        DiGraph g = new DiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a, b);
        Vertex c = g.addVertex("C");
        GraphHelper gh = new GraphHelper();
        assertFalse(gh.hasCycle(g));
        g.addEdge(b, c);
        assertFalse(gh.hasCycle(g));
        g.addEdge(a, c);
        //a->b->c   a-> c
        assertFalse(gh.hasCycle(g));
        Vertex d = g.addVertex("D");
        g.addEdge(c, d);
        assertFalse(gh.hasCycle(g));
        g.addEdge(d, a);
        //a->b->c->d->a  a-> c
        assertTrue(gh.hasCycle(g));
    }


    @Test
    void findPathInGraphOfOneNode() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        Vertex a = g.addVertex("A");
        GraphHelper gh = new GraphHelper();
        List<Vertex> path = gh.findPath(g,a,a);
        assertEquals(1,path.size());
        assertEquals(a,path.get(0));

        DiGraph dg = new DiGraph();
        a = dg.addVertex("A");
        gh = new GraphHelper();
        path = gh.findPath(dg,a,a);
        assertEquals(1,path.size());
        assertEquals(a,path.get(0));
    }


    @Test
    void findPathInUnDiGraphOfTwoNodes() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        GraphHelper gh = new GraphHelper();
        List<Vertex> path = gh.findPath(g,a,b);
        assertTrue(path.isEmpty());
        g.addEdge(a,b);
        path = gh.findPath(g,a,b);
        assertEquals(2,path.size());
        assertEquals(a,path.get(0));
        assertEquals(b,path.get(1));
        path = gh.findPath(g,b,a);
        assertEquals(2,path.size());
        assertEquals(b,path.get(0));
        assertEquals(a,path.get(1));

    }

    @Test
    void findPathInDiGraphOfTwoNodes() throws DuplicateTagException {
        DiGraph g = new DiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        GraphHelper gh = new GraphHelper();
        List<Vertex> path = gh.findPath(g,a,b);
        assertTrue(path.isEmpty());
        g.addEdge(a,b);
        path = gh.findPath(g,a,b);
        assertEquals(2,path.size());
        assertEquals(a,path.get(0));
        assertEquals(b,path.get(1));
        path = gh.findPath(g,b,a);
        assertTrue(path.isEmpty());
    }

    @Test
    void findPathInUnDiGraphOfU4(){
        UnDiGraph g = GraphReader.unDiGraph("A C A D B E B K C E C J D F D H E G E I");
        GraphHelper gh = new GraphHelper();
        List<Vertex> path = gh.findPath(g,g.getVertex("H"),g.getVertex("K"));
        assertEquals(7,path.size());
        assertEquals(g.getVertex("H"),path.get(0));
        assertEquals(g.getVertex("D"),path.get(1));
        assertEquals(g.getVertex("A"),path.get(2));
        assertEquals(g.getVertex("C"),path.get(3));
        assertEquals(g.getVertex("E"),path.get(4));
        assertEquals(g.getVertex("B"),path.get(5));
        assertEquals(g.getVertex("K"),path.get(6));
    }

    @Test
    void findPathInDiGraphOfD3(){
        DiGraph g = GraphReader.diGraph("A C B D C E C G D A D F E A F B");
        GraphHelper gh = new GraphHelper();
        List<Vertex> path = gh.findPath(g,g.getVertex("F"),g.getVertex("E"));
        assertEquals(6,path.size());
        assertEquals(g.getVertex("F"),path.get(0));
        assertEquals(g.getVertex("B"),path.get(1));
        assertEquals(g.getVertex("D"),path.get(2));
        assertEquals(g.getVertex("A"),path.get(3));
        assertEquals(g.getVertex("C"),path.get(4));
        assertEquals(g.getVertex("E"),path.get(5));
    }

    /* ------Find Root----------- */

    @Test
    void findRootIndirectedGraph() throws DuplicateTagException {
        DiGraph g = new DiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a, b);
        GraphHelper gh = new GraphHelper();
        Optional<Vertex> root = gh.findRoot(g);
        assertEquals(a, root.get());
        Vertex c = g.addVertex("C");
        g.addEdge(a, c);
        root = gh.findRoot(g);
        assertEquals(a, root.get());
        Vertex d = g.addVertex("D");
        g.addEdge(c, d);
        root = gh.findRoot(g);
        assertEquals(a, root.get());
        Vertex e = g.addVertex("E");
        root = gh.findRoot(g);
        assertTrue(root.isEmpty());
        Vertex f = g.addVertex("F");
        g.addEdge(e, f);
        root = gh.findRoot(g);
        assertTrue(root.isEmpty());
    }


    @Test
    void findRootFromLesson() {
        DiGraph g = GraphReader.diGraph("B D B E A B A C C F F G F H");
        GraphHelper gh = new GraphHelper();
        Optional<Vertex> root = gh.findRoot(g);
        Vertex a = g.getVertex("A");
        assertEquals(a, root.get());
    }

    @Test
    void testDistanceForAPathOfTwoNodes() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a,b);
        GraphHelper gh = new GraphHelper();
        List<Vertex> path = gh.findPath(g,a,b);
        assertEquals(1,gh.computeDistanceOfPath(path,g));
        path = gh.findPath(g,b,a);
        assertEquals(1,gh.computeDistanceOfPath(path,g));
        DiGraph dg = new DiGraph();
        a = dg.addVertex("A");
        b = dg.addVertex("B");
        dg.addEdge(a,b);
        gh = new GraphHelper();
        path = gh.findPath(dg,a,b);
        assertEquals(1,gh.computeDistanceOfPath(path,dg));
        UnDiGraph wg = new UnDiGraph();
        a = wg.addVertex("A");
        b = wg.addVertex("B");
        wg.addEdge(a,b,2);
        gh = new GraphHelper();
        path = gh.findPath(wg,a,b);
        assertEquals(2,gh.computeDistanceOfPath(path,wg));
    }



    @Test
    void testDistanceForAPathOfTreeNodes() throws DuplicateTagException {
        UnDiGraph g = new UnDiGraph();
        Vertex a = g.addVertex("A");
        Vertex b = g.addVertex("B");
        g.addEdge(a,b);
        Vertex c = g.addVertex("C");
        g.addEdge(b,c);
        GraphHelper gh = new GraphHelper();
        List<Vertex> path = gh.findPath(g,a,c);
        assertEquals(2,gh.computeDistanceOfPath(path,g));
        UnDiGraph wg = new UnDiGraph();
        a = wg.addVertex("A");
        b = wg.addVertex("B");
        wg.addEdge(a,b,2);
        c = wg.addVertex("C");
        wg.addEdge(b,c,3);
        gh = new GraphHelper();
        path = gh.findPath(wg,a,c);
        assertEquals(5,gh.computeDistanceOfPath(path,wg));
    }

    @Test
    void testDistanceForAPath() throws DuplicateTagException {
        UnDiGraph g = GraphReader.unDiGraph("A C A D B E B K C E C J D F D H E G E I");
        GraphHelper gh = new GraphHelper();
        List<Vertex> path = gh.findPath(g,g.getVertex("H"),g.getVertex("K"));
        assertEquals(7,path.size());
        assertEquals(6,gh.computeDistanceOfPath(path,g));

        UnDiGraph gw = GraphReader.unDiGraph("A C 5.2 B C 1.0 C D 2.0 D E 1.0");
        path = gh.findPath(gw,gw.getVertex("A"),gw.getVertex("E"));
        assertEquals(4,path.size());
        assertEquals(8.2,gh.computeDistanceOfPath(path,gw));

    }


}