package main.java;

import main.java.api.GeoLocation;
import main.java.api.NodeData;

public class Node implements NodeData {

    int key;
    Geo_Location position;
    String info;
    int tag;

    // Constructor
    public Node(int key, Geo_Location loc) {
        this.key = key;
        this.position = new Geo_Location(loc);
        // TODO: add info generation
        // TODO: add tag
    }

    // Deep copy constructor
    public Node(Node n) {
        this.key = n.key;
        this.position = new Geo_Location(n.position);
        this.info = n.info;
        this.tag = n.tag;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(GeoLocation p) {
        this.position = new Geo_Location((Geo_Location) p);
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setWeight(double w) {
    }

    @Override
    public String getInfo() {
        return this.info;
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
