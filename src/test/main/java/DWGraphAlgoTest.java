package main.java;

import main.java.api.DirectedWeightedGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DWGraphAlgoTest {

    String path1 = "C:\\Users\\yuval\\IdeaProjects\\OOP_Ex2\\src\\main\\java\\data\\G1.json";
    String path2 = "C:\\Users\\yuval\\IdeaProjects\\OOP_Ex2\\src\\main\\java\\data\\G2.json";
    String path3 = "C:\\Users\\yuval\\IdeaProjects\\OOP_Ex2\\src\\main\\java\\data\\G3.json";
    DWGraph t1Graph = new DWGraph(path1);
    DWGraph t2Graph = new DWGraph(path2);
    DWGraph t3Graph = new DWGraph(path3);
    DWGraphAlgo test1Graph = new DWGraphAlgo(t1Graph);
    DWGraphAlgo test2Graph = new DWGraphAlgo(t2Graph);
    DWGraphAlgo test3Graph = new DWGraphAlgo(t3Graph);

    @Test
    void init() {
    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
    }

    @Test
    void isConnected() {
        assertTrue(test1Graph.isConnected());
        assertTrue(test2Graph.isConnected());
        assertTrue(test3Graph.isConnected());
    }

    @Test
    void shortestPathDist() {
    }

    @Test
    void shortestPath() {
    }

    @Test
    void center() {
    }

    @Test
    void tsp() {
    }

    @Test
    void save() {
    }

    @Test
    void load(String path) {
    }
}