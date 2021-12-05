package main.java;

import main.java.api.EdgeData;
import main.java.api.NodeData;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class DWGraphTest {

    String path = "C:\\Users\\yuval\\IdeaProjects\\OOP_Ex2\\src\\main\\java\\data\\G1.json";
    DWGraph testGraph = new DWGraph(path);

    @Test
    void graphConstruct() {
        assertNotNull(testGraph);
        assertNotNull(testGraph.getNode(1));
        assertEquals(35.20319591121872, testGraph.getNode(1).getPosition().x());
        assertNotNull(testGraph.getEdge(0, 1));
        assertEquals(0, testGraph.getEdge(0, 1).getSrc());
    }

    @Test
    void getNode() {
        assertNotNull(testGraph.getNode(2));
        // This is the same position as node 2 in the JSON file
        Geo_Location p1 = new Geo_Location(35.20752617756255, 32.1025646605042, 0.0);
        Node n1 = new Node((Node) testGraph.getNode(2));
        Node n2 = new Node(2, p1);
        assertEquals(n2.getPosition().x(), n1.getPosition().x());
        assertEquals(n2.getPosition().y(), n1.getPosition().y());
        assertEquals(n2.getPosition().z(), n1.getPosition().z());
        assertEquals(n2.getKey(), n1.getKey());
        assertEquals(n2.getInfo(), n1.getInfo());
        assertEquals(n2.getWeight(), n1.getWeight());
        assertEquals(n2.getTag(), n1.getTag());
    }

    @Test
    void getEdge() {
        // Two random edges from G1.json
        Edge e1 = new Edge(0, 16, 1.3118716362419698);
        Edge e2 = new Edge(7, 6, 1.5786081900467002);
        assertEquals(e1.getSrc(), testGraph.getEdge(0, 16).getSrc());
        assertEquals(e1.getDest(), testGraph.getEdge(0, 16).getDest());
        assertEquals(e1.getInfo(), testGraph.getEdge(0, 16).getInfo());
        assertEquals(e1.getWeight(), testGraph.getEdge(0, 16).getWeight());
        assertEquals(e1.getTag(), testGraph.getEdge(0, 16).getTag());
        assertEquals(e2.getSrc(), testGraph.getEdge(7, 6).getSrc());
        assertEquals(e2.getDest(), testGraph.getEdge(7, 6).getDest());
        assertEquals(e2.getInfo(), testGraph.getEdge(7, 6).getInfo());
        assertEquals(e2.getWeight(), testGraph.getEdge(7, 6).getWeight());
        assertEquals(e2.getTag(), testGraph.getEdge(7, 6).getTag());
    }

    @Test
    void addNode() {
        Geo_Location pos1 = new Geo_Location(10, 10, 0);
        Geo_Location pos2 = new Geo_Location(5, 5, 0);
        Geo_Location pos3 = new Geo_Location(2, 2, 0);
        Node n1 = new Node(0, pos1);
        Node n2 = new Node(20, pos2);
        Node n3 = new Node(16, pos3);
        testGraph.addNode(n1);
        testGraph.addNode(n2);
        testGraph.addNode(n3);
        assertTrue(testGraph.Nodes.containsKey(17));
        assertTrue(testGraph.Nodes.containsKey(19));
        assertTrue(testGraph.Nodes.containsKey(20));
    }

    @Test
    void connect() {
        testGraph.connect(1, 3, 1.5);
        testGraph.connect(6, 10, 3.333);
        assertTrue(testGraph.Edges.get(1).containsKey(3));
        assertTrue(testGraph.Edges.get(6).containsKey(10));
        assertEquals(1.5, testGraph.Edges.get(1).get(3).getWeight());
        assertEquals(3.333, testGraph.Edges.get(6).get(10).getWeight());
    }

    @Test
    void nodeIter() {
        Iterator<NodeData> testIt = testGraph.nodeIter();
        assertTrue(testIt.hasNext());
        NodeData n;
        // First we make sure the Iterator iterates over all the values in the map
        for (int i = 0; i < testGraph.nodeSize(); i++) {
            n = testIt.next();
            assertTrue(testGraph.Nodes.containsKey(n.getKey()));
            assertEquals(testGraph.Nodes.get(i).getKey(), n.getKey());
        }
        // Then we make sure it finished covering everything
        assertFalse(testIt.hasNext());
        // Resetting the Iterator for the next test
        testIt = testGraph.nodeIter();
        testGraph.removeNode(0);
        // We check to see if the Iterator throws the exception as expected
        // No need to worry about the warning below - we aren't intrested in the return value anyway
        assertThrows(RuntimeException.class, testIt::hasNext);
    }

    @Test
    void edgeIter() {
        Iterator<EdgeData> testIt = testGraph.edgeIter();
        assertTrue(testIt.hasNext());
        EdgeData n;
        for(int i=0;i<testGraph.edgeSize();i++){
            n = testIt.next();
            assertTrue(testGraph.Edges.containsKey(n.getSrc()));
        }
        assertFalse(testIt.hasNext());
        testIt = testGraph.edgeIter();
        testGraph.removeEdge(0,16);
        assertThrows(RuntimeException.class, testIt::hasNext);
    }

    @Test
    void testEdgeIter() {
        // Node ID:0 has 2 edges out of it : 1 & 16
        Iterator<EdgeData> testIt = testGraph.edgeIter(0);
        assertTrue(testIt.hasNext());
        assertEquals(16,testIt.next().getDest());
        assertEquals(1,testIt.next().getDest());
        assertFalse(testIt.hasNext());
        testIt = testGraph.edgeIter(1);
        testGraph.removeEdge(0,16);
        assertThrows(RuntimeException.class, testIt::hasNext);
    }

    @Test
    void removeNode() {
        testGraph.removeNode(0);
        testGraph.removeNode(1);
        assertFalse(testGraph.Edges.containsKey(0));
        assertFalse(testGraph.Nodes.containsKey(0));
        assertFalse(testGraph.Nodes.containsKey(1));
    }

    @Test
    void removeEdge() {
        testGraph.removeEdge(0, 16);
        testGraph.removeEdge(1, 2);
        assertFalse(testGraph.Edges.get(0).containsKey(16));
        assertFalse(testGraph.Edges.get(1).containsKey(2));
        // This is to make sure it only deletes the requested edge
        assertTrue(testGraph.Edges.get(1).containsKey(0));
    }

    @Test
    void nodeSize() {
        Geo_Location pos1 = new Geo_Location(10, 10, 0);
        Node n1 = new Node(0, pos1);
        assertEquals(17, testGraph.nodeSize());
        testGraph.removeNode(0);
        testGraph.removeNode(1);
        assertEquals(15, testGraph.nodeSize());
        testGraph.addNode(n1);
        assertEquals(16, testGraph.nodeSize());
    }

    @Test
    void edgeSize() {
        assertEquals(36, testGraph.edgeSize());
        testGraph.connect(1, 3, 1.5);
        assertEquals(37, testGraph.edgeSize());
        testGraph.removeEdge(1, 3);
        testGraph.removeEdge(0, 16);
        assertEquals(35, testGraph.edgeSize());
    }

    @Test
    void getMC() {
        assertEquals(0, testGraph.getMC());
        testGraph.connect(1, 3, 1.5);
        testGraph.removeEdge(1, 3);
        testGraph.removeEdge(0, 16);
        assertEquals(3, testGraph.getMC());
        Geo_Location pos1 = new Geo_Location(10, 10, 0);
        Node n1 = new Node(0, pos1);
        testGraph.addNode(n1);
        assertEquals(4, testGraph.getMC());
    }

}