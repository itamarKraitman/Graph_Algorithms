package main.java;

import main.java.api.DirectedWeightedGraph;
import main.java.api.DirectedWeightedGraphAlgorithms;
import main.java.api.NodeData;

import java.util.List;

public class DWGraphAlgo implements DirectedWeightedGraphAlgorithms {

    public DWGraph graph;

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = (DWGraph)g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return new DWGraph(this.graph);
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        try {
            this.graph = new DWGraph(file);
            return true;
        } catch (RuntimeException e) {
            System.out.println("File Not Found!");
            return false;
        }
    }
}
