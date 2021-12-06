package main.java.Algorithms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.java.GraphClass.DWGraph;
import main.java.api.DirectedWeightedGraph;
import main.java.api.DirectedWeightedGraphAlgorithms;
import main.java.api.EdgeData;
import main.java.api.NodeData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DWGraphAlgo implements DirectedWeightedGraphAlgorithms {

    public DWGraph graph;

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
        Iterator<NodeData> vertex = this.getGraph().nodeIter();
        int v = vertex.next().getKey();
        this.graph.resetTag();
        Iterator<NodeData> it = this.getGraph().nodeIter();
        NodeData pointer;
        DFS(this.getGraph(), v, true);
        while (it.hasNext()) {
            pointer = it.next();
            if (pointer.getTag() == 0) {
                return false;
            }
        }
        this.graph.resetTag();
        Iterator<NodeData> itReversed = this.getGraph().nodeIter();
        NodeData pointerReversed;
        DFS(this.getGraph(), v, false);
        while (itReversed.hasNext()) {
            pointerReversed = itReversed.next();
            if (pointerReversed.getTag() == 0) {
                return false;
            }
        }
        return true;
    }

    private void DFS(DirectedWeightedGraph g, int v, boolean b) {
        Stack<NodeData> stack = new Stack<>();
        EdgeData u = null;
        Iterator<EdgeData> it;
        DWGraph gg = (DWGraph) g; // This is only to use the reversed Edge iterator
        stack.push(g.getNode(v));
        while (stack.size() != 0) {
            v = stack.peek().getKey();
            stack.pop();
            if (this.graph.getNode(v).getTag() == 1) {
                continue;
            }
            this.graph.getNode(v).setTag(1);
            if (b) {
                it = g.edgeIter(v);
            } else {
                it = gg.reversedEdgeIter(v);
            }
            while (it.hasNext()) {
                u = it.next();
                if (this.graph.getNode(u.getDest()).getTag() == 0) {
                    stack.push(g.getNode(u.getDest()));
                }
            }
        }
    }


    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest) {
            return 0;
        }
        Dijkstra d = new Dijkstra(this.graph, this.graph.getNode(src));
        d.DijkstraAlgo(src);
        return d.getDistBetSrcToDest(dest);
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        if (src == dest) {
            List<NodeData> list = new ArrayList<>();
            list.add(this.graph.Nodes.get(src));
            return list;
        }
        Dijkstra d = new Dijkstra(this.graph, this.graph.getNode(src));
        d.DijkstraAlgo(src);
        return d.shortestPathNodes(dest);
    }

    @Override
    public NodeData center() {

        List<Dijkstra> ld = new ArrayList<>();
        Iterator<NodeData> itN = this.graph.nodeIter();
        NodeData current = null;
        while (itN.hasNext()) {
            current = itN.next();
            Dijkstra d = new Dijkstra(this.graph, current);
            d.DijkstraAlgo(current.getKey());
            ld.add(d);
        }
        double shortest = Double.MAX_VALUE, temp;
        for (Dijkstra dd : ld) {
            temp = dd.maxLongestDist();
            if (shortest > temp) {
                shortest = temp;
                current = dd.src;
            }
        }
        return current;
    }

    // Nearest Neighbour modified to use Dijkstra if no suitable neighbours are found
    // TODO: finish implementing this
    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        if (cities == null || cities.size() == 0) {
            return null;
        }
        List<NodeData> cityTraversal = new ArrayList<>();
        List<NodeData> needToVisit = new LinkedList<>();
        NodeData tempNode;
        HashMap<Integer, Boolean> visited = new HashMap<>();
        for (NodeData city : cities) {
            visited.put(city.getKey(), false);
            needToVisit.add(city);
        }
        int tempDest;
        double travelWeight = 0;
        cityTraversal.add(needToVisit.get(0));
        // In case the travelWeight is infinity that means no viable path is available and we exit the algorithm and return
        // Credit for this conditional statement goes to Amir Sabag.
        while (!needToVisit.isEmpty() && travelWeight < Double.MAX_VALUE) {
            tempNode = cityTraversal.get(cityTraversal.size() - 1);
            needToVisit.remove(tempNode);
            visited.replace(tempNode.getKey(), true);
            tempDest = minDirectedPath(tempNode.getKey(), visited, needToVisit);
            if (tempDest == -1) {
                Dijkstra d = new Dijkstra(this.graph, tempNode);
                d.DijkstraAlgo(tempNode.getKey()); // dijkstra algo
                List<NodeData> undirectedPath = minUndirectedPath(d, tempNode, visited, needToVisit);
                if (undirectedPath != null) {
                    travelWeight += d.getDistBetSrcToDest(undirectedPath.get(undirectedPath.size() - 1).getKey());
                    undirectedPath.remove(0);
                    cityTraversal.addAll(undirectedPath);
                    needToVisit.removeAll(undirectedPath);
                }
            } else {
                cityTraversal.add(this.graph.getNode(tempDest));
                needToVisit.remove(this.graph.getNode(tempDest));
                travelWeight += this.graph.getEdge(tempNode.getKey(), tempDest).getWeight();
            }
        }
        return cityTraversal;
    }

    private int minDirectedPath(int src, HashMap<Integer, Boolean> visited, List<NodeData> needToVisit) {
        double minPath = Double.MAX_VALUE;
        int minDest = -1;
        for (NodeData city : needToVisit) {
            if (!visited.get(city.getKey())) {
                if (this.graph.getEdge(src, city.getKey()) != null) {
                    if (this.graph.getEdge(src, city.getKey()).getWeight() < minPath) {
                        minPath = this.graph.getEdge(src, city.getKey()).getWeight();
                        minDest = city.getKey();
                    }
                }
            }
        }
        return minDest;
    }

    private List<NodeData> minUndirectedPath(Dijkstra d, NodeData src, HashMap<Integer, Boolean> visited, List<NodeData> needToVisit) {
        double tempDist = Double.MAX_VALUE;
        int tempKey = -1;
        for (NodeData node : needToVisit) {
            if (d.getDistBetSrcToDest(node.getKey()) < tempDist && !visited.get(node.getKey())) {
                tempDist = d.getDistBetSrcToDest(node.getKey());
                tempKey = node.getKey();
            }
        }
        if (tempKey == -1) {
            return null;
        } else {
            return d.shortestPathNodes(this.graph.getNode(tempKey).getKey());
        }
    }

    @Override
    // TODO: implement this fully & test it
    public boolean save(String file) {
        try {
            FileWriter f = new FileWriter("" + file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String toWrite = gson.toJson(this.graph.Nodes);
            String toWrite2 = gson.toJson(this.graph.Edges);
            f.write(toWrite);
            f.write(toWrite2);
            f.close();
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
