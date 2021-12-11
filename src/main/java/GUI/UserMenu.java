package main.java.GUI;

import main.java.GraphClass.Edge;
import main.java.GraphClass.Geo_Location;
import main.java.GraphClass.Node;
import main.java.api.DirectedWeightedGraphAlgorithms;
import main.java.api.EdgeData;
import main.java.api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserMenu extends JMenuBar implements ActionListener {

    FrameWindow frame;
    DirectedWeightedGraphAlgorithms graphAlgo;
    GraphPainter painter;
    JFileChooser fc;
    JMenu fileMnu, drawMnu, algoMnu;


    public UserMenu(DirectedWeightedGraphAlgorithms g, GraphPainter painter, FrameWindow frame) {
        this.frame = frame;
        this.painter = painter;
        this.graphAlgo = g;
        this.fc = new JFileChooser(new File(System.getProperty("user.dir")));

        this.fileMnu = new JMenu("File");
        this.drawMnu = new JMenu("Graph");
        this.algoMnu = new JMenu("Algorithms");

        JMenuItem loadGraph = new JMenuItem("Open/Load Graph");
        JMenuItem saveGraph = new JMenuItem("Save Graph");
        loadGraph.addActionListener(this);
        saveGraph.addActionListener(this);

        JMenuItem addNode = new JMenuItem("Add Vertex");
        JMenuItem removeNode = new JMenuItem("Remove Vertex");
        JMenuItem addEdge = new JMenuItem("Add Edge");
        JMenuItem removeEdge = new JMenuItem("Remove Edge");
        JMenuItem numVer = new JMenuItem("Number Of Vertices");
        JMenuItem numEdg = new JMenuItem("Number Of Edges");
        JMenuItem getVer = new JMenuItem("Get Vertex Details");
        JMenuItem getEdg = new JMenuItem("Get Edge Details");

        addNode.addActionListener(this);
        removeNode.addActionListener(this);
        addEdge.addActionListener(this);
        removeEdge.addActionListener(this);
        numVer.addActionListener(this);
        numEdg.addActionListener(this);
        getVer.addActionListener(this);
        getEdg.addActionListener(this);

        JMenuItem connected = new JMenuItem("Is Connected?");
        JMenuItem center = new JMenuItem("Center Of Graph");
        JMenuItem sp = new JMenuItem("Shortest Path (Weight)");
        JMenuItem spd = new JMenuItem("Shortest Path (Vertex List)");
        JMenuItem tsp = new JMenuItem("TSP Algorithm");
        connected.addActionListener(this);
        center.addActionListener(this);
        sp.addActionListener(this);
        spd.addActionListener(this);
        tsp.addActionListener(this);

        this.fileMnu.add(loadGraph);
        this.fileMnu.add(saveGraph);

        this.drawMnu.add(addNode);
        this.drawMnu.add(removeNode);
        this.drawMnu.addSeparator();
        this.drawMnu.add(addEdge);
        this.drawMnu.add(removeEdge);
        this.drawMnu.addSeparator();
        this.drawMnu.add(numVer);
        this.drawMnu.add(numEdg);
        this.drawMnu.add(getVer);
        this.drawMnu.add(getEdg);


        this.algoMnu.add(connected);
        this.algoMnu.add(center);
        this.algoMnu.add(sp);
        this.algoMnu.add(spd);
        this.algoMnu.add(tsp);

        this.add(this.fileMnu);
        this.add(this.drawMnu);
        this.add(this.algoMnu);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        switch (str) {
            case "Add Vertex" -> {
                String id = JOptionPane.showInputDialog(this, "Enter Vertex Key");
                String x_pos = JOptionPane.showInputDialog(this, "Enter X Position");
                String y_pos = JOptionPane.showInputDialog(this, "Enter Y Position");

                double finalX = Double.parseDouble(x_pos);
                double finalY = Double.parseDouble(y_pos);

//                Geo_Location loc = new Geo_Location(Double.parseDouble(x_pos), Double.parseDouble(y_pos), 0);
                Geo_Location loc = new Geo_Location(finalX, finalY, 0);
                NodeData node = new Node(Integer.parseInt(id), loc);

//                double[] XY;
//                XY = painter.linearTransform(node.getPosition());
                this.graphAlgo.getGraph().addNode(node);
//                this.painter.refreshPainter(graphAlgo);
                this.painter = new GraphPainter(graphAlgo);
                getTopLevelAncestor().repaint();
//                repaint();
            }
            case "Add Edge" -> {
                String src = JOptionPane.showInputDialog(this, "Enter Source Vertex");
                String dest = JOptionPane.showInputDialog(this, "Enter destination vertex");
                String weight = JOptionPane.showInputDialog(this, "Enter Weight");
                EdgeData edge = new Edge(Integer.parseInt(src), Integer.parseInt(dest), Double.parseDouble(weight));
                this.graphAlgo.getGraph().connect(Integer.parseInt(src), Integer.parseInt(dest), Double.parseDouble(weight));
                getTopLevelAncestor().repaint();
            }
            case "Remove Vertex" -> {
                String key = JOptionPane.showInputDialog(this, "Enter Vertex Key");
                this.graphAlgo.getGraph().removeNode(Integer.parseInt(key));
                getTopLevelAncestor().repaint();
            }
            case "Remove Edge" -> {
                String src = JOptionPane.showInputDialog(this, "Enter source Vertex");
                String dest = JOptionPane.showInputDialog(this, "Enter Destination Vertex");
                this.graphAlgo.getGraph().removeEdge(Integer.parseInt(src), Integer.parseInt(dest));
                getTopLevelAncestor().repaint();
            }
            case "Number Of Vertices" -> JOptionPane.showMessageDialog(this, "The Number Of Vertices In Graph is:\n" + this.graphAlgo.getGraph().nodeSize());
            case "Number Of Edges" -> JOptionPane.showMessageDialog(this, "The Number Of Edges In Graph Is:\n" + this.graphAlgo.getGraph().edgeSize());
            case "Is Connected?" -> {
                boolean answer = this.graphAlgo.isConnected();
                String message = "";
                if (answer) message = "The Graph Is Connected";
                else message = "The Graph Is Not Connected";
                JOptionPane.showMessageDialog(this, message);
            }
            case "Shortest Path (Weight)" -> {
                String src = JOptionPane.showInputDialog(this, "Enter Source Vertex");
                String dest = JOptionPane.showInputDialog(this, "Enter Destination Vertex");
                JOptionPane.showMessageDialog(this, "Shortest Path Distance Is " + this.graphAlgo.shortestPathDist(Integer.parseInt(src), Integer.parseInt(dest)));
            }
            case "Shortest Path (Vertex List)" -> {
                String src = JOptionPane.showInputDialog(this, "Enter Source Vertex");
                String dest = JOptionPane.showInputDialog(this, "Enter Destination Vertex");
                List<NodeData> path = this.graphAlgo.shortestPath(Integer.parseInt(src), Integer.parseInt(dest));
                JOptionPane.showMessageDialog(this, "Shortest Path Is:\n" + ToString(path));
            }
            case "Center Of Graph" -> {
                Node vertex = (Node) this.graphAlgo.center();
                if (vertex != null) {
                    JOptionPane.showMessageDialog(this, "Center Vertex Of Graph is:\n" + vertex.getKey());
                } else {
                    JOptionPane.showMessageDialog(this, "Graph Has No Center Since It's Not Connected");
                }
            }
            case "TSP Algorithm" -> {
                List<NodeData> cities = new ArrayList<>();
                String vertex = JOptionPane.showInputDialog("Enter vertex ID, To Finish Enter -1");
                while (!vertex.equals("-1")) {
                    cities.add(this.graphAlgo.getGraph().getNode(Integer.parseInt(vertex)));
                    vertex = JOptionPane.showInputDialog("Enter vertex ID, To Finish Enter -1");
                }
                List<NodeData> vertexInPath = this.graphAlgo.tsp(cities);
                JOptionPane.showMessageDialog(this, "TSP Result For Those Vertexes Is:\n" + ToString(vertexInPath));
            }
            case "Open/Load Graph" -> {
                this.fc.showOpenDialog(null);
                if (this.fc.getSelectedFile() == null) {
                    return;
                } else {
                    this.graphAlgo.load(this.fc.getSelectedFile().getPath());
                    this.painter.refreshPainter(this.graphAlgo);
                    //this.painter.setColors(this.painter.defaultNodeColor, this.painter.defaultEdgeColor);
                    JOptionPane.showMessageDialog(this, "Load Success");
                    getTopLevelAncestor().repaint();
                }
            }
            case "Save Graph" -> {
                this.fc.showSaveDialog(null);
                if (this.fc.getSelectedFile() == null) {
                    return;
                } else {
                    this.graphAlgo.save(this.fc.getSelectedFile().getPath());
                    JOptionPane.showMessageDialog(this, "File Was Saved");
                }
            }
            case "Get Vertex Details" -> {
                String vertex = JOptionPane.showInputDialog(this, "Enter Vertex ID\n");
                JOptionPane.showMessageDialog(this, this.graphAlgo.getGraph().getNode(Integer.parseInt(vertex)).getInfo());
            }
            case "Get Edge Details" -> {
                String src = JOptionPane.showInputDialog(this, "Enter Edge Source");
                String dest = JOptionPane.showInputDialog(this, "Enter Edge Destination");
                JOptionPane.showMessageDialog(this, this.graphAlgo.getGraph().getEdge(Integer.parseInt(src), Integer.parseInt(dest)));
            }
        }
    }

    private String ToString(List<NodeData> path) {
        StringBuilder ans = new StringBuilder();
        for (NodeData nodeData : path) {
            ans.append(nodeData.getKey());
            ans.append("->");
        }
        ans.delete(ans.length() - 2, ans.length());
        return ans.substring(0);
    }
}

