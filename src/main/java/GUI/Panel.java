package main.java.GUI;

import main.java.api.DirectedWeightedGraph;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private DirectedWeightedGraph graph;

    public Panel(DirectedWeightedGraph graph) {
        this.setBackground(Color.WHITE);
        this.graph = graph;

    }



}
