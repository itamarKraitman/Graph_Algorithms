package main.java;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.java.api.DirectedWeightedGraph;
import main.java.api.EdgeData;
import main.java.api.NodeData;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DWGraph implements DirectedWeightedGraph {

    public Map<Integer, Node> Nodes = new HashMap<>();
    public Map<Integer, HashMap<Integer, Edge>> Edges = new HashMap<>();
    private int modCount = 0;

    public DWGraph(String filename) {
        File input = new File(filename);
        try {
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
            JsonObject fileObject = fileElement.getAsJsonObject();
            JsonArray arrayOfEdges = fileObject.get("Edges").getAsJsonArray();
            for (JsonElement graphElement : arrayOfEdges) {
                JsonObject graphEdge = graphElement.getAsJsonObject();
                int source = graphEdge.get("src").getAsInt();
                double weight = graphEdge.get("w").getAsDouble();
                int destination = graphEdge.get("dest").getAsInt();
                Edge edge = new Edge(source, destination, weight);
                HashMap<Integer, Edge> tempEdge = new HashMap<>();
                if (Edges.containsKey(source)) {
                    Edges.get(source).put(destination, edge);
                } else {
                    tempEdge.put(edge.getDest(), edge);
                    Edges.put(edge.getSrc(), tempEdge);
                }
            }
            JsonArray arrayOfNodes = fileObject.get("Nodes").getAsJsonArray();
            for (JsonElement graphElement : arrayOfNodes) {
                JsonObject graphNode = graphElement.getAsJsonObject();
                String position = graphNode.get("pos").getAsString();
                String[] positions = position.split("[,]");
                double x = Double.parseDouble(positions[0]);
                double y = Double.parseDouble(positions[1]);
                double z = Double.parseDouble(positions[2]);
                int id = graphNode.get("id").getAsInt();
                Geo_Location pos = new Geo_Location(x, y, z);
                Node node = new Node(id, pos);
                Nodes.put(node.getKey(), node);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public NodeData getNode(int key) {
        return this.Nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        if (this.Nodes.containsKey(src) && this.Nodes.containsKey(dest)) {
            return this.Edges.get(src).get(dest);
        } else throw new IllegalArgumentException("This Graph Doesn't Hold This Edge! Please Enter A Valid Value!");
    }

    @Override
    public void addNode(NodeData n)  {
        int key = n.getKey();
        if(this.Nodes.containsKey(n.getKey())) {
            key = Nodes.size();
        }
        Node toAdd = new Node(key, (Geo_Location) n.getPosition());
        Nodes.put(key,toAdd);
        this.modCount++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        Edge toAdd = new Edge(src, dest, w);
        HashMap<Integer, Edge> tempEdge = new HashMap<>();
        if (Edges.containsKey(src)) {
            Edges.get(src).put(dest, toAdd);
        } else {
            tempEdge.put(toAdd.getDest(), toAdd);
            Edges.put(toAdd.getSrc(), tempEdge);
        }
        this.modCount++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        Iterator<Map.Entry<Integer,Node>> it = Nodes.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Integer,Node> node = it.next();
        }
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
        this.modCount++;
        return Nodes.remove(key);
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        if (this.Nodes.containsKey(src) && this.Nodes.containsKey(dest) && this.Edges.get(src).containsKey(dest)) {
            this.modCount++;
            return Edges.get(src).remove(dest);
        } else throw new IllegalArgumentException("This Graph Doesn't Hold This Edge! Please Enter A Valid Value!");
    }

    @Override
    public int nodeSize() {
        return Nodes.size();
    }

    @Override
    public int edgeSize() {
        int total = 0;
        for(int i = 0;i<Edges.size();i++){
            total = total + Edges.get(i).size();
        }
        return total;
    }

    @Override
    public int getMC() {
        return this.modCount;
    }

    public static ArrayList<HashMap<Integer, Edge>> getAllEdges(DWGraph graph) {
        ArrayList<HashMap<Integer, Edge>> allEdges = new ArrayList<>();
        for (int i = 0; i < graph.Edges.size(); i++) {
            allEdges.add(graph.Edges.get(i));
        }
        return allEdges;
    }

    public ArrayList<HashMap<Integer, Edge>> getEdgesFrom(int nodeId) {
        ArrayList<HashMap<Integer, Edge>> ans = new ArrayList<>();
        for (int i = 0; i < this.Edges.size(); i++) {
            HashMap<Integer, Edge> current = this.Edges.get(i);
            if (current.containsKey(nodeId))
                ans.add(current);
        }
        return ans;
    }
}
