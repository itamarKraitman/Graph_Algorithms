package main.java.GUI;

import main.java.Algorithms.DWGraphAlgo;
import main.java.GraphClass.DWGraph;
import main.java.GraphClass.Edge;
import main.java.GraphClass.Geo_Location;
import main.java.GraphClass.Node;
import main.java.api.DirectedWeightedGraphAlgorithms;
import main.java.api.EdgeData;
import main.java.api.NodeData;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;

public class UserWindow extends JFrame implements ActionListener, MouseListener {

    DWGraphAlgo graphAlgo = new DWGraphAlgo();
    public JMenuBar menuBar;
    int vertexCount = 0, edgesCount = 0;
    private Graphics graphics;
    private Image image;
    Panel panel = new Panel();

    public UserWindow() {
        JFrame frame = new JFrame();
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        InitWindow(frame);
        UserWindow window = new UserWindow();
        frame.setJMenuBar(window.menuBar);
        frame.setVisible(true);
    }

    public void InitWindow(JFrame frame) {
        this.menuBar = new JMenuBar();
        JMenu drawingMenu = new JMenu("Drawing Graph");
        drawingMenu.getAccessibleContext().setAccessibleDescription("Drawing Graph");
        JMenu algoMenu = new JMenu("Algorithms");
        algoMenu.getAccessibleContext().setAccessibleDescription("Running algorithms on the graph");
        JMenu loadGraph = new JMenu("Load Graph");
        loadGraph.getAccessibleContext().setAccessibleDescription("Load Graph From json");
        JMenu saveGraph = new JMenu("Save Graph");
        saveGraph.getAccessibleContext().setAccessibleDescription("Save Graph To json");
        menuBar.add(drawingMenu);
        menuBar.add(algoMenu);
        menuBar.add(loadGraph);
        menuBar.add(saveGraph);
        JMenuItem drawNode = new JMenuItem("Add Vertex");
        drawNode.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                String x = JOptionPane.showInputDialog(frame, "Enter X Value");
                String y = JOptionPane.showInputDialog(frame, "Enter Y Value");
                Node vertex = new Node(vertexCount, new Geo_Location(Double.parseDouble(x), Double.parseDouble(y), 0));
                vertexCount++;
                graphAlgo.graph.addNode(vertex);
                paintVertex(graphics);
            }
        });
        JMenuItem drawEdge = new JMenuItem("Add Edge");
        drawEdge.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                String src = JOptionPane.showInputDialog(frame, "Enter Source Vertex");
                String dest = JOptionPane.showInputDialog(frame, "Enter destination vertex");
                String weight = JOptionPane.showInputDialog(frame, "Enter Weight");
                Edge edge = new Edge(Integer.parseInt(src), Integer.parseInt(dest), Double.parseDouble(weight));
                edgesCount++;
                graphAlgo.graph.connect(Integer.parseInt(src), Integer.parseInt(dest), Double.parseDouble(weight));
                paintEdge(graphics);
            }
        });
//        JMenuItem connect = new JMenuItem("Connect vertex");
        JMenuItem removeNode = new JMenuItem("Remove Vertex");
        removeNode.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                String key = JOptionPane.showInputDialog(frame, "Enter Vertex Key");
                graphAlgo.graph.removeNode(Integer.parseInt(key));
                vertexCount--;
                paintVertex(graphics);
                paintEdge(graphics);
            }
        });
        JMenuItem removeEdge = new JMenuItem("Remove Edge");
        removeEdge.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                String src = JOptionPane.showInputDialog(frame, "Enter source Vertex");
                String dest = JOptionPane.showInputDialog(frame, "Enter Destination Vertex");
                graphAlgo.graph.removeEdge(Integer.parseInt(src), Integer.parseInt(dest));
                edgesCount--;
                paintEdge(graphics);
            }
        });
        JMenuItem nodeSize = new JMenuItem("How Many Vertex?");
        nodeSize.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                JOptionPane.showInputDialog("The Number Of Vertex In Graph is:\n" + graphAlgo.graph.nodeSize());
            }
        });
        JMenuItem edgeSize = new JMenuItem("Hoe Many Edges?");
        edgeSize.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                JOptionPane.showInputDialog("The Number Of Edges In Graph Is:\n" + graphAlgo.graph.edgeSize());
            }
        });
        drawingMenu.add(drawNode);
        drawingMenu.add(drawEdge);
//        drawingMenu.add(connect);
        drawingMenu.add(removeNode);
        drawingMenu.add(removeEdge);
        drawingMenu.add(nodeSize);
        drawingMenu.add(edgeSize);
        JMenuItem isConnected = new JMenuItem("Is Connected");
        isConnected.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                JOptionPane.showInputDialog(graphAlgo.isConnected());
            }
        });
        JMenuItem shortestDistance = new JMenuItem("Shortest Distance");
        shortestDistance.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                String src = JOptionPane.showInputDialog(frame, "Enter Source Vertex");
                String dest = JOptionPane.showInputDialog(frame, "Enter Destination Vertex");
                JOptionPane.showInputDialog(frame, graphAlgo.shortestPathDist(Integer.parseInt(src), Integer.parseInt(dest)));
            }
        });
        JMenuItem shortestPath = new JMenuItem("Shortest Path");
        shortestPath.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                //TODO complete this method
            }
        });
        JMenuItem Center = new JMenuItem("Center");
        Center.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Node vertex = (Node) graphAlgo.center();
                JOptionPane.showInputDialog("Center Vertex Of Graph is:\n" + vertex.getKey());
                paintVertex(graphics);
            }
        });
        JMenuItem tsp = new JMenuItem("TSP algorithm");
        tsp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                List<NodeData> cities = new ArrayList<>();
                String vertex = JOptionPane.showInputDialog("Enter vertex ID, To Finish Enter -1");
                while (!vertex.equals("-1")) {
                    cities.add(graphAlgo.graph.getNode(Integer.parseInt(vertex)));
                    vertex = JOptionPane.showInputDialog("Enter vertex ID, To Finish Enter -1");
                }
                List<NodeData> vertexInPath = graphAlgo.tsp(cities);
                //TODO- paint the path- continue
            }
        });
        algoMenu.add(isConnected);
        algoMenu.add(shortestDistance);
        algoMenu.add(shortestPath);
        algoMenu.add(Center);
        algoMenu.add(tsp);
    }

//    public void paint(Graphics g) {
//        image = createImage(500, 500);
//        graphics = image.getGraphics();
//        paintComponents(graphics);
//        g.drawImage(image, 0, 0, this);
//    }

    public void paintVertex(Graphics g) {
        Iterator<NodeData> iterator = graphAlgo.graph.nodeIter();
        while (iterator.hasNext()) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.red);
            NodeData vertex = iterator.next();
            g2d.fillOval((int) vertex.getPosition().x(), (int) vertex.getPosition().y(), 30, 30);
        }
    }

    public void paintEdge(Graphics g) {
        Iterator<EdgeData> iterator = graphAlgo.graph.edgeIter();
        while (iterator.hasNext()) {
            Edge edge = (Edge) iterator.next();
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.BLUE);
            paintArrowLine(g, (Node) graphAlgo.graph.Nodes.get(edge.getSrc()), (Node) graphAlgo.graph.Nodes.get(edge.getDest()), 10, 10);
        }
    }

    private void paintArrowLine(Graphics g, Node src, Node dest, int width, int height) {
        double v1x = src.getPosition().x();
        double v1y = src.getPosition().y();
        double v2x = dest.getPosition().x();
        double v2y = dest.getPosition().y();
        double dx = v2x - v1x, dy = v2y - v1y;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - width, xn = xm, ym = height, yn = -height, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + v1x;
        ym = xm*sin + ym*cos + v1y;
        xm = x;

        x = xn*cos - yn*sin + v1x;
        yn = xn*sin + yn*cos + v1y;
        xn = x;

        int[] xpoints = {(int) v2x, (int) xm, (int) xn};
        int[] ypoints = {(int) v2y, (int) ym, (int) yn};

        g.drawLine((int) v1x, (int) v1y, (int) v2x, (int) v2y);
        g.fillPolygon(xpoints, ypoints, 3);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

