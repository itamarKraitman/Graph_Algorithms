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
    // TODO: switch to BFS to avoid stack overflow on larger graphs
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

    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest) {
            return 0;
        }
        double[] distance = new double[this.graph.nodeSize()];
        int[] parents = new int[this.graph.nodeSize()];
        Arrays.fill(parents, -1);
        DijkstraAlgo(parents, distance, src);
        return distance[dest];
    }

    public double[] shortestPathArray(int src) {
        double[] distance = new double[this.graph.nodeSize()];
        int[] parents = new int[this.graph.nodeSize()];
        Arrays.fill(parents, -1);
        DijkstraAlgo(parents, distance, src);
        return distance;
    }

    // TODO: fix the comparator here
    private void DijkstraAlgo(int[] parents, double[] distance, int src) {
        Arrays.fill(distance, Double.POSITIVE_INFINITY);
        distance[src] = 0;
        Comparator<NodeData> comparator = (o1, o2) -> {
            if (Math.abs((o1.getWeight() - o2.getWeight())) < EPS) {
                return 0;
            } else {
                return (o1.getWeight() - o2.getWeight() > 0 ? +1 : -1);
            }
        };
        PriorityQueue<NodeData> pq = new PriorityQueue<>(distance.length, comparator);
        pq.offer(this.graph.Nodes.get(src));
        while (!pq.isEmpty()) {
            NodeData current = pq.poll();
            ArrayList<Integer> destOfOutgoingEdge = new ArrayList<>(this.graph.Edges.get(current.getKey()).keySet());
            // iterating over all adjacent of current
            for (Integer integer : destOfOutgoingEdge) { // for all edges which
                EdgeData edgeToAdj = this.graph.Edges.get(current.getKey()).get(integer);
                if (distance[edgeToAdj.getDest()] > distance[current.getKey()] + edgeToAdj.getWeight()) {
                    distance[edgeToAdj.getDest()] = distance[current.getKey()] + edgeToAdj.getWeight();
                    pq.add((Node) this.graph.Nodes.get(edgeToAdj.getDest()));
                    parents[edgeToAdj.getDest()] = current.getKey();
                }
            }
        }
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        if (src == dest) {
            List<NodeData> list = new ArrayList<>();
            list.add(this.graph.Nodes.get(src));
            return list;
        }
        double[] distance = new double[this.graph.nodeSize()];
        int[] parents = new int[this.graph.nodeSize()];
        Arrays.fill(parents, -1);
        DijkstraAlgo(parents, distance, src);
        return path(parents, dest, src);
    }

    private List<NodeData> path(int[] parents, int dest, int src) {
        List<NodeData> nodesPath = new ArrayList<>();
        nodesPath.add(this.graph.Nodes.get(dest));
        int i = parents[dest];
        while (i != src) {
            NodeData current = this.graph.Nodes.get(i);
            nodesPath.add(current);
            i = parents[current.getKey()];
        }
        // i equals to src
        nodesPath.add(this.graph.Nodes.get(src));
        Collections.reverse(nodesPath);
        return nodesPath;
    }


    @Override
    public NodeData center() {
        int n = this.graph.nodeSize();
        double[] arr;
        double[] allDist = new double[n];
        for (int i = 0; i < n; i++) {
            arr = shortestPathArray(i);
            for (int j = 0; j < n; j++) {
                double dist = arr[j];
                if (dist > allDist[i]) {
                    allDist[i] = dist;
                }
            }
        }
        double min = allDist[0];
        int index = 0;
        for (int i = 0; i < allDist.length; i++) {
            if (min > allDist[i]) {
                min = allDist[i];
                index = i;
            }
        }
        return this.graph.getNode(index);
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
