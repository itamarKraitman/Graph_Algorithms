package main.java;

import main.java.api.DirectedWeightedGraph;
import main.java.api.DirectedWeightedGraphAlgorithms;
import main.java.api.EdgeData;
import main.java.api.NodeData;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DWGraphAlgo implements DirectedWeightedGraphAlgorithms {

    public DWGraph graph;

    public DWGraphAlgo(DWGraph g){
        this.graph = new DWGraph(g);

    }
    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph =  new DWGraphAlgo(g);
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return null;
    }

    @Override
    public boolean isConnected() {
        boolean[] visited = new boolean[getGraph().nodeSize()];
        int v = 0;
        DFS(getGraph(),v,visited,true);
        for(boolean i:visited){
            if(!i){
                return false;
            }
        }
        Arrays.fill(visited,false);
        DFS(getGraph(),v,visited,false);
        for(boolean i:visited){
            if(!i){
                return false;
            }
        }
        return true;
    }

    private void DFS(DirectedWeightedGraph g, int v, boolean[] visited, boolean b){
        visited[v] = true;
        Iterator<EdgeData> it;
        if(b) {
            it = this.getGraph().edgeIter(v);
        } else{
            it = this.getGraph().reversedEdgeIter(v);
        }
        while(it.hasNext()){
            int u = it.next().getDest();
            if(!visited[u]){
                DFS(g,u,visited,b);
            }
        }

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
