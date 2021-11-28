package main.java;
import main.java.api.EdgeData;

public class Edge implements EdgeData {

    private final int src;
    private final double weight;
    private final int dest;
    private transient String info;
    private transient int tag;

    // Constructor
    public Edge(int source, int destination, double weight) {
        this.src = source;
        this.dest = destination;
        this.weight = weight;
        // TODO: add this.string = something - create the string info on object creation
        // TODO: add this.tag - when we figure out what this is for
    }

    // Deep copy constructor
    public Edge(Edge copy){
        this.src = copy.src;
        this.dest = copy.dest;
        this.weight = copy.weight;
        this.info = copy.info;
        this.tag = copy.tag;
    }


    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return this.toString();
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("src: ").append(this.src).append(".\n");
        sb.append("dest: ").append(this.dest).append(".\n");
        sb.append("weight: ").append(this.weight).append(".\n");
        sb.append("tag: ").append(this.tag).append(".\n");
        return sb.substring(0);
    }
}
