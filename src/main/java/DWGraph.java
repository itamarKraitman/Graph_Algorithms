package main.java;

import com.google.gson.*;
import main.java.api.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DWGraph implements DirectedWeightedGraph {
    
    private HashMap<Integer,Node> Nodes = new HashMap<>();
    private HashMap<Integer,HashMap<Integer, Edge>> Edges = new HashMap<>();
    private int modeCount = 0;

    public DWGraph(String filename){
        File input = new File(filename);
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
            JsonObject fileObject = fileElement.getAsJsonObject();
            JsonArray arrayOfEdges = fileObject.get("Edges").getAsJsonArray();
            for (JsonElement graphElement : arrayOfEdges){
                JsonObject graphEdge = graphElement.getAsJsonObject();
                int source = graphEdge.get("src").getAsInt();
                double weight = graphEdge.get("w").getAsDouble();
                int destination = graphEdge.get("dest").getAsInt();
                Edge edge = new Edge(source,destination,weight);
                HashMap<Integer, Edge> tempEdge = new HashMap<>();
                tempEdge.put(edge.getDest(), edge);
                Edges.put(edge.getSrc(), tempEdge);
            }
            JsonArray arrayOfNodes = fileObject.get("Nodes").getAsJsonArray();
            for(JsonElement graphElement : arrayOfNodes){
                JsonObject graphNode = graphElement.getAsJsonObject();
                String position = graphNode.get("pos").getAsString();
                String[] positions = position.split("[,]");
                double x = Double.parseDouble(positions[0]);
                double y = Double.parseDouble(positions[1]);
                double z = Double.parseDouble(positions[2]);
                int id = graphNode.get("id").getAsInt();
                Geo_Location pos = new Geo_Location(x,y,z);
                Node node = new Node(id, pos);
                Nodes.put(node.getKey(), node);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public NodeData getNode(int key) {
        return null;
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return null;
    }

    @Override
    public void addNode(NodeData n) {

    }

    @Override
    public void connect(int src, int dest, double w) {

    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return this.modeCount;
    }
}
