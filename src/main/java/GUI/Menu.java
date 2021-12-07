package main.java.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends MenuBar {

    JMenuBar menuBar;
    private JMenu DrawingMenu, algoMenu, saveGraph, loadGraph;

    public Menu() {
        this.menuBar = new JMenuBar();
        this.DrawingMenu = new JMenu("Drawing Graph");
        DrawingMenu.getAccessibleContext().setAccessibleDescription("Drawing Graph");
        this.algoMenu = new JMenu("Algorithms");
        algoMenu.getAccessibleContext().setAccessibleDescription("Running algorithms on the graph");
        this.loadGraph = new JMenu("Load Graph");
        loadGraph.getAccessibleContext().setAccessibleDescription("Load Graph From json");
        this.saveGraph = new JMenu("Save Graph");
        saveGraph.getAccessibleContext().setAccessibleDescription("Save Graph To json");
        menuBar.add(DrawingMenu);
        menuBar.add(algoMenu);
        menuBar.add(loadGraph);
        menuBar.add(saveGraph);
        JMenuItem drawNode = new JMenuItem("Add Vertex");
        JMenuItem drawEdge = new JMenuItem("Add Vertex");
        JMenuItem connect = new JMenuItem("Connect vertex");
        JMenuItem removeNode = new JMenuItem("Remove Vertex");
        JMenuItem removeEdge = new JMenuItem("Remove Edge");
        JMenuItem nodeSize = new JMenuItem("How Many Vertex?");
        JMenuItem edgeSize = new JMenuItem("Hoe Many Edges?");
        DrawingMenu.add(drawNode);
        DrawingMenu.add(drawEdge);
        DrawingMenu.add(connect);
        DrawingMenu.add(removeNode);
        DrawingMenu.add(removeEdge);
        DrawingMenu.add(nodeSize);
        DrawingMenu.add(edgeSize);
        JMenuItem isConnected = new JMenuItem("Is Connected");
        JMenuItem shortestDistance = new JMenuItem("Shortest Diastance");
        JMenuItem shortestPath = new JMenuItem("Shortest Path");
        JMenuItem Center = new JMenuItem("Center");
        JMenuItem tsp = new JMenuItem("TSP algorithm");
        algoMenu.add(isConnected);
        algoMenu.add(shortestDistance);
        algoMenu.add(shortestPath);
        algoMenu.add(Center);
        algoMenu.add(tsp);
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(800,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Menu menu = new Menu();
        frame.setJMenuBar(menu.menuBar);
        frame.setVisible(true);

    }
}
