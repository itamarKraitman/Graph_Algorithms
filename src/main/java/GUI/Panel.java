package main.java.GUI;

import main.java.api.DirectedWeightedGraph;
import main.java.api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class Panel extends JPanel {

    private DirectedWeightedGraph graph;
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    public Panel(DirectedWeightedGraph graph) {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setFocusable(true);
        this.graph = graph;
    }
}
