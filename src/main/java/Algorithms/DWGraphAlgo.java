package main.java.Algorithms;

import com.google.gson.Gson;
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
        while(it.hasNext()) {
            pointer = it.next();
            if (pointer.getTag()==0) {
                return false;
            }
        }
        this.graph.resetTag();
        Iterator<NodeData> itReversed = this.getGraph().nodeIter();
        NodeData pointerReversed;
        DFS(this.getGraph(), v, false);
        while(itReversed.hasNext()) {
            pointerReversed = itReversed.next();
            if (pointerReversed.getTag()==0) {
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
        while (stack.size()!=0) {
            v = stack.peek().getKey();
            stack.pop();
            if (this.graph.getNode(v).getTag()==1) {
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
        while (itN.hasNext()){
            current = itN.next();
            Dijkstra d = new Dijkstra(this.graph, current);
            d.DijkstraAlgo(current.getKey());
            ld.add(d);
        }
        double shortest = Double.MAX_VALUE, temp;
        for (Dijkstra dd : ld){
            temp = dd.maxLongestDist();
            if (shortest > temp){
                shortest = temp;
                current = dd.src;
            }
        }
        return current;
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
