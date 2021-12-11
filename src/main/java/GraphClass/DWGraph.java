package main.java.GraphClass;

import com.google.gson.*;
import main.java.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class DWGraph implements DirectedWeightedGraph {

    public Map<Integer, NodeData> Nodes = new ConcurrentHashMap<>();
    public Map<Integer, ConcurrentHashMap<Integer, EdgeData>> Edges = new ConcurrentHashMap<>();
    public Map<Integer, ConcurrentHashMap<Integer, EdgeData>> reversedEdges = new ConcurrentHashMap<>();
    int edgeCounter = 0;
    private int modCount = 0;

    // Empty Constructor
    public DWGraph() {

    }

    // JSON Constructor
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
                ConcurrentHashMap<Integer, EdgeData> tempEdge = new ConcurrentHashMap<>();


                if (Edges.containsKey(source)) {
                    Edges.get(source).put(destination, edge);
                    this.edgeCounter++;
                } else {
                    tempEdge.put(edge.getDest(), edge);
                    Edges.put(edge.getSrc(), tempEdge);
                    this.edgeCounter++;
                }
            }


            for (JsonElement graphElement : arrayOfEdges) {
                JsonObject graphEdge = graphElement.getAsJsonObject();
                int source = graphEdge.get("dest").getAsInt();
                double weight = graphEdge.get("w").getAsDouble();
                int destination = graphEdge.get("src").getAsInt();


                Edge edge = new Edge(source, destination, weight);
                ConcurrentHashMap<Integer, EdgeData> tempEdge = new ConcurrentHashMap<>();


                if (reversedEdges.containsKey(source)) {
                    reversedEdges.get(source).put(destination, edge);
                } else {
                    tempEdge.put(edge.getDest(), edge);
                    reversedEdges.put(edge.getSrc(), tempEdge);
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

    // copy constructor
    public DWGraph(DWGraph g) {
        this.Nodes = g.Nodes;
        this.Edges = g.Edges;
        this.reversedEdges = g.reversedEdges;
        this.modCount = g.modCount;
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
    public void addNode(NodeData n) {
        int key = n.getKey();
        if (this.Nodes.containsKey(n.getKey())) {
            key = Nodes.size();
        }
        Node toAdd = new Node(key, (Geo_Location) n.getPosition());
        Nodes.put(key, toAdd);
        this.modCount++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        Edge toAdd = new Edge(src, dest, w);
        if (Edges.containsKey(src)) {
            Edges.get(src).put(dest, toAdd);
        } else {
            ConcurrentHashMap<Integer, EdgeData> tempEdge = new ConcurrentHashMap<>();
            tempEdge.put(toAdd.getDest(), toAdd);
            Edges.put(toAdd.getSrc(), tempEdge);
        }
        this.edgeCounter++;
        this.modCount++;
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return new Iterator<>() {

            private final Iterator<NodeData> it = Nodes.values().iterator();
            private int counter = getMC();
            private NodeData value = null;

            @Override
            public boolean hasNext() {
                if (getMC() != counter) {
                    throw new RuntimeException("Graph Was Changed While Iterator Was Running!!!");
                }
                return it.hasNext();
            }

            @Override
            public NodeData next() {
                if (getMC() != counter) {
                    throw new RuntimeException("Graph Was Changed While Iterator Was Running!!!");
                }
                if (hasNext()) {
                    value = it.next();
                    return value;
                } else throw new NullPointerException("Iterator Has No Next Value");
            }

            @Override
            public void remove() {
                if (getMC() != counter) {
                    throw new RuntimeException("Graph Was Changed While Iterator Was Running!!!");
                }
                if (value != null) {
                    removeNode(value.getKey());
                    this.counter = getMC();
                }
            }

            @Override
            public void forEachRemaining(Consumer<? super NodeData> action) {
                if (getMC() != counter) {
                    throw new RuntimeException("Graph Was Changed While Iterator Was Running!!!");
                }
                while (it.hasNext()) {
                    action.accept(next());
                }
            }
        };
    }

    // Iterating over all edges in the graph
    @Override
    public Iterator<EdgeData> edgeIter() {
        return new Iterator<>() {

            private final Iterator<ConcurrentHashMap<Integer, EdgeData>> it = Edges.values().iterator();
            private Iterator<EdgeData> edgeIt = null;
            private EdgeData value = null;
            public int counter = getMC();

            @Override
            public boolean hasNext() {
                if (getMC() != counter) {
                    throw new RuntimeException("Graph Was Changed While Iterator Was Running!!!");
                }
                return it.hasNext() || (edgeIt == null || edgeIt.hasNext());
            }

            @Override
            public EdgeData next() {
                    if (getMC() != counter) {
                        throw new RuntimeException("Graph Was Changed While Iterator Was Running!!!");
                    }
                if (this.hasNext()) {
                    if (edgeIt == null || !edgeIt.hasNext()) {
                        edgeIt = it.next().values().iterator();
                    }
                    value = edgeIt.next();
                    return value;
                }
               else throw new RuntimeException("Iterator Has Mo Next Value!");
            }

            @Override
            public void remove() {
                if (getMC() != counter) {
                    throw new RuntimeException("Graph Was Changed While Iterator Was Running!!!");
                }
                if (value != null) {
                    removeEdge(value.getSrc(), value.getDest());
                    this.counter = getMC();
                }
            }

            @Override
            public void forEachRemaining(Consumer<? super EdgeData> action) {
                if (getMC() != counter) {
                    throw new NullPointerException("Graph Was Changed While Iterator Was Running!!!");
                }
                while (it.hasNext()) {
                    action.accept(next());
                }
            }
        };
    }

    // Iterator over Edges going OUT of a node
    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        if (!Nodes.containsKey(node_id)) {
            throw new IllegalArgumentException("Node Doesn't Exist In The Graph!!!");
        }
        return new Iterator<>() {

            private final Iterator<EdgeData> it = Edges.get(node_id).values().iterator();
            private int counter = getMC();
            private EdgeData value = null;

            @Override
            public boolean hasNext() {
                if (getMC() != counter) {
                    throw new RuntimeException("Graph Was Changed While Iterator Was Running!!!");
                }
                return it.hasNext();
            }

            @Override
            public EdgeData next() {
                if (getMC() != counter) {
                    throw new RuntimeException("Graph Was Changed While Iterator Was Running!!!");
                }
                if (hasNext()) {
                    value = it.next();
                    return value;
                } else throw new NullPointerException("Iterator Has No Next Value");
            }

            @Override
            public void remove() {
                if (getMC() != counter) {
                    throw new RuntimeException("Graph Was Changed While Iterator Was Running!!!");
                }
                if (value != null) {
                    removeEdge(value.getSrc(), value.getDest());
                    this.counter = getMC();
                }
            }

            @Override
            public void forEachRemaining(Consumer<? super EdgeData> action) {
                if (getMC() != counter) {
                    throw new RuntimeException("Graph Was Changed While Iterator Was Running!!!");
                }
                while (it.hasNext()) {
                    action.accept(next());
                }
            }
        };
    }


    public Iterator<EdgeData> reversedEdgeIter(int node_id) {
        if (!Nodes.containsKey(node_id)) {
            throw new IllegalArgumentException("Node Doesn't Exist In The Graph!!!");
        }
        return new Iterator<>() {

            private final Iterator<EdgeData> it = reversedEdges.get(node_id).values().iterator();
            private int counter = getMC();
            private EdgeData value = null;

            @Override
            public boolean hasNext() {
                if (getMC() != counter) {
                    throw new RuntimeException("Graph Was Changed While Iterator Was Running!!!");
                }
                return it.hasNext();
            }

            @Override
            public EdgeData next() {
                if (getMC() != counter) {
                    throw new RuntimeException("Graph Was Changed While Iterator Was Running!!!");
                }
                if (hasNext()) {
                    value = it.next();
                    return value;
                } else throw new NullPointerException("Iterator Has No Next Value");
            }

            @Override
            public void remove() {
                if (getMC() != counter) {
                    throw new RuntimeException("Graph Was Changed While Iterator Was Running!!!");
                }
                if (value != null) {
                    removeEdge(value.getSrc(), value.getDest());
                    this.counter = getMC();
                }
            }

            @Override
            public void forEachRemaining(Consumer<? super EdgeData> action) {
                if (getMC() != counter) {
                    throw new RuntimeException("Graph Was Changed While Iterator Was Running!!!");
                }
                while (it.hasNext()) {
                    action.accept(next());
                }
            }
        };
    }


    @Override
    public NodeData removeNode(int key) {
        removeIngoingEdges(key); // removing all ingoing edges
        if (this.Edges.get(key) != null) {
            Set<Integer> toRemove = this.Edges.get(key).keySet();
            for (int dest : toRemove) {
                this.removeEdge(key, dest);
            }
            this.Edges.remove(key);
        }
        this.modCount++;
        return Nodes.remove(key);
    }


    private void removeIngoingEdges(int key) { // key is destination node id
        for (Integer srcKey : this.Edges.keySet()) {
            if (this.Edges.get(srcKey).containsKey(key))
                this.removeEdge(srcKey, key);
        }
//        Set<Integer> toRemove = this.reversedEdges.get(key).keySet();
//        for(int dest : toRemove){
//            this.removeEdge(dest, key);
//        }
        this.reversedEdges.remove(key);
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        if (this.Nodes.containsKey(src) && this.Nodes.containsKey(dest) && this.Edges.get(src).containsKey(dest)) {
            this.modCount++;
            this.edgeCounter--;
            this.reversedEdges.get(dest).remove(src);
            return this.Edges.get(src).remove(dest);
        } else throw new IllegalArgumentException("This Graph Doesn't Hold This Edge! Please Enter A Valid Value!");
    }

    @Override
    public int nodeSize() {
        return Nodes.size();
    }

    @Override
    public int edgeSize() {
        return this.edgeCounter;
    }

    @Override
    public int getMC() {
        return this.modCount;
    }

    // TODO: add this test
    public void resetTag() {
        Iterator<NodeData> it = this.nodeIter();
        NodeData pointer;
        while (it.hasNext()) {
            pointer = it.next();
            pointer.setTag(0);
        }
    }
}
