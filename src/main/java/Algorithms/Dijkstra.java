package main.java.Algorithms;

import main.java.api.DirectedWeightedGraph;
import main.java.api.EdgeData;
import main.java.api.NodeData;

import java.util.*;

// No need to write tests for this class, its entire functionality is tested within DWGraphTest

/**
 * Dijkstra's algorithm and helper function encapsulated in a class for convenient code use
 * Once we create this class object for a specific node, we can immediatley tell it's distance from every other node
 * and also reconstruct that path.
 * Algorithm is implemented using a Priority Queue with an explicit Comparator to achieve minimal time-complexity
 */

public class Dijkstra {
    NodeData src;
    DirectedWeightedGraph currentGraph;
    HashMap<Integer, Integer> parents;
    HashMap<Integer, Double> distance;
    private final double EPS = 1e-100;


    public Dijkstra(DirectedWeightedGraph g, NodeData src) {
        this.currentGraph = g;
        this.src = src;
    }

    // Main algorithm function
    void DijkstraAlgo(int src) {

        NodeData current;
        Iterator<NodeData> iteratorNodes = this.currentGraph.nodeIter();
        this.parents = new HashMap<>();
        this.distance = new HashMap<>();


        while (iteratorNodes.hasNext()) {
            current = iteratorNodes.next();
            this.parents.put(current.getKey(), -1);
            this.distance.put(current.getKey(), Double.MAX_VALUE);
        }
        this.distance.replace(src, 0.0);


        Comparator<NodeData> comparator = (o1, o2) -> {
            if (Math.abs((this.distance.get(o1.getKey()) - this.distance.get(o2.getKey()))) < EPS) {
                return 0;
            } else {
                return (this.distance.get(o1.getKey()) - this.distance.get(o2.getKey()) > 0 ? +1 : -1);
            }
        };


        // initial capacity is 2 * the map size because at worst-case you are going through every node twice.
        PriorityQueue<NodeData> pq = new PriorityQueue<>(this.distance.size() * 2, comparator);
        pq.offer(this.currentGraph.getNode(src));


        while (!pq.isEmpty()) {
            current = pq.poll();
            Iterator<EdgeData> destOfOutgoingEdge = this.currentGraph.edgeIter(current.getKey());
            // iterating over all adjacent nodes
            while (destOfOutgoingEdge.hasNext()) {
                EdgeData edgeToAdj = destOfOutgoingEdge.next();
                if (this.distance.get(edgeToAdj.getDest()) > this.distance.get(current.getKey()) + edgeToAdj.getWeight()) {
                    this.distance.put(edgeToAdj.getDest(), this.distance.get(current.getKey()) + edgeToAdj.getWeight());
                    pq.offer(this.currentGraph.getNode(edgeToAdj.getDest()));
                    this.parents.put(edgeToAdj.getDest(), current.getKey());
                }
            }
        }
    }

    double getBestDistSrcToDest(int dest){
        return this.distance.get(dest);
    }

    double maxLongestDist(){
        double longest = Double.MIN_VALUE;
        for (double dist : this.distance.values()){
            if (dist > longest){
                longest = dist;
            }
        }
        return longest;
    }

    List<NodeData> shortestPathNodes(int dest){

        LinkedList<NodeData> list = new LinkedList<>();
        int pointer = dest;


        while (this.parents.get(pointer) != -1){
            list.addFirst(this.currentGraph.getNode(pointer));
            pointer = this.parents.get(pointer);
        }
        if (pointer == this.src.getKey()){
            list.addFirst(this.src);
        }
        if (list.getFirst().getKey() != this.src.getKey()){
            return null;
        }
        else {
            return list;
        }
    }
}
