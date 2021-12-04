package main.java;

import com.google.gson.Gson;
import main.java.api.DirectedWeightedGraph;
import main.java.api.DirectedWeightedGraphAlgorithms;
import main.java.api.EdgeData;
import main.java.api.NodeData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DWGraphAlgo implements DirectedWeightedGraphAlgorithms {

    public DWGraph graph;
    private static final double EPS = 1e-100;

    public DWGraphAlgo() {
        this.graph = new DWGraph();
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = (DWGraph) g;
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
        boolean[] visited = new boolean[getGraph().nodeSize()];
        int v = 0;
        DFS(this.getGraph(), v, visited, true);
        for (boolean i : visited) {
            if (!i) {
                return false;
            }
        }
        Arrays.fill(visited, false);
        DFS(getGraph(), v, visited, false);
        for (boolean i : visited) {
            if (!i) {
                return false;
            }
        }
        return true;
    }

    private void DFS(DirectedWeightedGraph g, int v, boolean[] visited, boolean b) {
        visited[v] = true;
        Iterator<EdgeData> it;
        if (b) {
            it = g.edgeIter(v);
        } else {
            it = g.reversedEdgeIter(v);
        }
        while (it.hasNext()) {
            int u = it.next().getDest();
            if (!visited[u]) {
                DFS(g, u, visited, b);
            }
        }

    }

    // Comparator for Dijkstr'a shortest path algorithm
    @Override
    public double shortestPathDist(int src, int dest) {
        int n = this.graph.nodeSize();
        double[] distance = new double[n];
        Arrays.fill(distance, Double.POSITIVE_INFINITY);
        distance[src] = 0;
        Comparator<Node> comparator = (o1, o2) -> {
            if (Math.abs((o1.getWeight() - o2.getWeight())) < EPS) {
                return 0;
            } else {
                return (o1.getWeight() - o2.getWeight() > 0 ? +1 : -1);
            }
        };
        PriorityQueue<Node> pq = new PriorityQueue<Node>(2*n,comparator);
        pq.offer((Node) this.graph.Nodes.get(src));
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            // iterating over all adjacent of current
            for (int i = 0; i < this.graph.Edges.get(current.getKey()).size(); i++) {
                Edge edgeToAdj = (Edge) this.graph.Edges.get(current.getKey()).get(i);
                if (distance[edgeToAdj.getDest()] > distance[current.getKey()] + edgeToAdj.getWeight()) {
                    distance[edgeToAdj.getDest()] = distance[current.getKey()] + edgeToAdj.getWeight();
                    pq.add((Node) this.graph.Nodes.get(edgeToAdj.getDest()));
                }
            }
        }
        return distance[dest];
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
        try {
            FileWriter f = new FileWriter(file);
            Gson gson = new Gson();
            String toWrite = gson.toJson(this.graph.Nodes);
            String toWrite2 = gson.toJson(this.graph.Edges);
            f.write(toWrite);
            f.write(toWrite2);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An Error Occured!");
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        try {
            this.graph = new DWGraph(file);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("An Error Occured!");
            return false;
        }
    }
}
