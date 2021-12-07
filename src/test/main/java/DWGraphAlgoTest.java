package main.java;

import main.java.Algorithms.DWGraphAlgo;
import main.java.GraphClass.DWGraph;
import main.java.api.DirectedWeightedGraph;
import main.java.api.DirectedWeightedGraphAlgorithms;
import main.java.api.NodeData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DWGraphAlgoTest {

    String path1 = "C:\\Users\\itama\\IdeaProjects\\OOP_Ex2\\src\\main\\java\\data\\G1.json";
    String path2 = "C:\\Users\\itama\\IdeaProjects\\OOP_Ex2\\src\\main\\java\\data\\G2.json";
    String path3 = "C:\\Users\\itama\\IdeaProjects\\OOP_Ex2\\src\\main\\java\\data\\G3.json";
    String path4 = "C:\\Users\\itama\\IdeaProjects\\OOP_Ex2\\src\\main\\java\\data\\1K_Nodes.json";
    String path5 = "C:\\Users\\itama\\IdeaProjects\\OOP_Ex2\\src\\main\\java\\data\\10K_Nodes.json";

    DirectedWeightedGraph g1 = new DWGraph(path1);
    DirectedWeightedGraph g2 = new DWGraph(path2);
    DirectedWeightedGraph g3 = new DWGraph(path3);
    DirectedWeightedGraph g4 = new DWGraph(path4);
    DirectedWeightedGraph g5 = new DWGraph(path5);
    DirectedWeightedGraphAlgorithms test1Graph = new DWGraphAlgo();
    DirectedWeightedGraphAlgorithms test2Graph = new DWGraphAlgo();
    DirectedWeightedGraphAlgorithms test3Graph = new DWGraphAlgo();
    DirectedWeightedGraphAlgorithms test4Graph = new DWGraphAlgo();
    DirectedWeightedGraphAlgorithms test5Graph = new DWGraphAlgo();

    void testInit() {
        test1Graph.init(g1);
        test2Graph.init(g2);
        test3Graph.init(g3);
        test4Graph.init(g4);
        test5Graph.init(g5);
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
    // TODO: finish this test
    void getGraph() {
        testInit();

    }

    @Test
    // TODO: finish this test
    void copy() {
        testInit();

    }

    @Test
    // TODO: finish this test
    void isConnected() {
        testInit();
        assertTrue(test1Graph.isConnected());
        assertTrue(test2Graph.isConnected());
        assertTrue(test3Graph.isConnected());
        assertTrue(test4Graph.isConnected());
        assertTrue(test5Graph.isConnected());
    }

    @Test
    void shortestPathDist() {
        testInit();
        Assertions.assertEquals(2.87964096872708, test1Graph.shortestPathDist(0, 15));
        Assertions.assertEquals(8.72653175989357, test1Graph.shortestPathDist(0, 11));
        Assertions.assertEquals(1.5328553219807337, test1Graph.shortestPathDist(8, 9));
        Assertions.assertEquals(3.4901450707642723, test2Graph.shortestPathDist(2, 28));
        Assertions.assertEquals(5.970573559456582, test2Graph.shortestPathDist(11, 25));
        Assertions.assertEquals(1.3323304804848388, test3Graph.shortestPathDist(0, 9));
        Assertions.assertEquals(5.490432911352292, test3Graph.shortestPathDist(42, 47));
    }

    @Test
    void shortestPath() {
        testInit();
        List<NodeData> shouldBe = new ArrayList<>();
        shouldBe.add(g1.getNode(0));
        shouldBe.add(g1.getNode(16));
        shouldBe.add(g1.getNode(15));
        Assertions.assertEquals(shouldBe, test1Graph.shortestPath(0, 15));
        shouldBe.clear();
        shouldBe.add(g1.getNode(0));
        shouldBe.add(g1.getNode(16));
        shouldBe.add(g1.getNode(15));
        shouldBe.add(g1.getNode(14));
        shouldBe.add(g1.getNode(13));
        shouldBe.add(g1.getNode(12));
        shouldBe.add(g1.getNode(11));
        Assertions.assertEquals(shouldBe, test1Graph.shortestPath(0, 11));
        shouldBe.clear();
        shouldBe.add(g2.getNode(2));
        shouldBe.add(g2.getNode(3));
        shouldBe.add(g2.getNode(4));
        shouldBe.add(g2.getNode(28));
        Assertions.assertEquals(shouldBe, test2Graph.shortestPath(2, 28));
        shouldBe.clear();
        shouldBe.add(g3.getNode(0));
        shouldBe.add(g3.getNode(1));
        shouldBe.add(g3.getNode(9));
        Assertions.assertEquals(shouldBe, test3Graph.shortestPath(0, 9));
    }

    @Test
    void center() {
        testInit();
          Assertions.assertEquals(8, test1Graph.center().getKey());
       Assertions.assertEquals(0, test2Graph.center().getKey());
      Assertions.assertEquals(40, test3Graph.center().getKey());
       Assertions.assertEquals(362, test4Graph.center().getKey());
     // Assertions.assertEquals(362, test5Graph.center().getKey());
    }

    @Test
    // TODO: finish this test
    void tsp() {
        testInit();
        List<NodeData> cities = new ArrayList<>();
        cities.add(g1.getNode(0));
        cities.add(g1.getNode(2));
        cities.add(g1.getNode(4));
        cities.add(g1.getNode(6));
        List<NodeData> shouldBe = new ArrayList<>();
        int[] tspNodes = {0,1,2,6,5,4};
        for (int i = 0; i < tspNodes.length; i++)
            shouldBe.add(g1.getNode(tspNodes[i]));
        Assertions.assertEquals(shouldBe, test1Graph.tsp(cities));
        cities.clear();
        shouldBe.clear();
        int[] citiesKeys = {2,8,11};
        for (int city : citiesKeys) cities.add(g2.getNode(city));
        tspNodes = new int[]{2, 1,26,8,9,10,11};
        for (int pathNode : tspNodes) shouldBe.add(g2.getNode(pathNode));
        Assertions.assertEquals(shouldBe, test2Graph.tsp(cities));
        cities.clear();
        shouldBe.clear();
        citiesKeys = new int[]{4,9,12,28};
        tspNodes = new int[]{4,3,12,11,9,11,13,14,28};
        for (int city : citiesKeys) cities.add(g3.getNode(city));
        for (int pathNode : tspNodes) shouldBe.add(g3.getNode(pathNode));
        Assertions.assertEquals(shouldBe, test3Graph.tsp(cities));
    }

    @Test
    // TODO: finish this test
    void save() {
        testInit();
        String outputFile = "g1.json";
        assertTrue(test1Graph.save(outputFile));
//        assertFalse(test1Graph.save("g1.txt"));
        
    }

    @ParameterizedTest
    @ValueSource(strings = {"C:\\Users\\yuval\\IdeaProjects\\OOP_Ex2\\src\\main\\java\\data\\G1.json","C:\\Users\\yuval\\IdeaProjects\\OOP_Ex2\\src\\main\\java\\data\\G1.json"})
    void load(String path) {
        DirectedWeightedGraphAlgorithms g = new DWGraphAlgo();
        assertTrue(g.load(path));
    }
}