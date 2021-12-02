package main.java;

import main.java.api.DirectedWeightedGraph;
import main.java.api.DirectedWeightedGraphAlgorithms;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DWGraphAlgoTest {

    String path1 = "C:\\Users\\yuval\\IdeaProjects\\OOP_Ex2\\src\\main\\java\\data\\G1.json";
    String path2 = "C:\\Users\\yuval\\IdeaProjects\\OOP_Ex2\\src\\main\\java\\data\\G2.json";
    String path3 = "C:\\Users\\yuval\\IdeaProjects\\OOP_Ex2\\src\\main\\java\\data\\G3.json";
    DirectedWeightedGraph g1 = new DWGraph(path1);
    DirectedWeightedGraph g2 = new DWGraph(path2);
    DirectedWeightedGraph g3 = new DWGraph(path3);
    DirectedWeightedGraphAlgorithms test1Graph = new DWGraphAlgo();
    DirectedWeightedGraphAlgorithms test2Graph = new DWGraphAlgo();
    DirectedWeightedGraphAlgorithms test3Graph = new DWGraphAlgo();

    void testInit() {
        test1Graph.init(g1);
        test2Graph.init(g2);
        test3Graph.init(g3);
    }

    @Test
    void init() {
        testInit();
        Assertions.assertEquals(17, test1Graph.getGraph().nodeSize());
        Assertions.assertEquals(36, test1Graph.getGraph().edgeSize());
        Assertions.assertEquals(31, test2Graph.getGraph().nodeSize());
        Assertions.assertEquals(80, test2Graph.getGraph().edgeSize());
        Assertions.assertEquals(48, test3Graph.getGraph().nodeSize());
        Assertions.assertEquals(166, test3Graph.getGraph().edgeSize());
    }

    @Test
    void getGraph() {
        testInit();

    }

    @Test
    void copy() {
        testInit();

    }

    @Test
    void isConnected() {
        testInit();
        assertTrue(test1Graph.isConnected());
        assertTrue(test2Graph.isConnected());
        assertTrue(test3Graph.isConnected());
    }

    @Test
    void shortestPathDist() {
        testInit();

    }

    @Test
    void shortestPath() {
        testInit();

    }

    @Test
    void center() {
        testInit();

    }

    @Test
    void tsp() {
        testInit();

    }

    @Test
    void save() {
        testInit();

    }

    @Test
    void load(String path) {
        testInit();

    }
}