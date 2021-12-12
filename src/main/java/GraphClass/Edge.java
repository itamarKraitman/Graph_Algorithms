package main.java.GraphClass;

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
        this.info = this.getInfo();
        this.tag = 0;
    }

    // Deep copy constructor
    public Edge(Edge copy) {
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
        return "\"src\": " + this.getSrc() + ",\n\"w\": " + this.getWeight() + ",\n\"dest\": " + this.getDest();
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
}