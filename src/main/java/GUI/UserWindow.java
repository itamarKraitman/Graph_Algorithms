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

public class UserWindow extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

    JFrame frame = new JFrame();
    DWGraphAlgo graphAlgo = new DWGraphAlgo();
    public JMenuBar menuBar;
    int vertexCount = 0, edgesCount = 0;
    Panel panel = new Panel();
    private Graphics graphics;
    private Image image;

    public UserWindow() {
//        JFrame frame = new JFrame();
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
//        frame.pack();
        InitWindow(frame);
        frame.setJMenuBar(this.menuBar);
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
        drawNode.addActionListener(this);
//        drawNode.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//                String x = JOptionPane.showInputDialog(frame, "Enter X Value");
//                String y = JOptionPane.showInputDialog(frame, "Enter Y Value");
//                Node vertex = new Node(vertexCount, new Geo_Location(Double.parseDouble(x), Double.parseDouble(y), 0));
//                vertexCount++;
//                graphAlgo.graph.addNode(vertex);
//                paint(graphics);
//            }
//        });
        JMenuItem drawEdge = new JMenuItem("Add Edge");
        drawEdge.addActionListener(this);
//        drawEdge.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//                String src = JOptionPane.showInputDialog(frame, "Enter Source Vertex");
//                String dest = JOptionPane.showInputDialog(frame, "Enter destination vertex");
//                String weight = JOptionPane.showInputDialog(frame, "Enter Weight");
//                Edge edge = new Edge(Integer.parseInt(src), Integer.parseInt(dest), Double.parseDouble(weight));
//                edgesCount++;
//                graphAlgo.graph.connect(Integer.parseInt(src), Integer.parseInt(dest), Double.parseDouble(weight));
//                paint(graphics);
//            }
//        });
//        JMenuItem connect = new JMenuItem("Connect vertex");
        JMenuItem removeNode = new JMenuItem("Remove Vertex");
        removeNode.addActionListener(this);
//        removeNode.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//                String key = JOptionPane.showInputDialog(frame, "Enter Vertex Key");
//                graphAlgo.graph.removeNode(Integer.parseInt(key));
//                vertexCount--;
//                paint(graphics);
//            }
//        });
        JMenuItem removeEdge = new JMenuItem("Remove Edge");
        removeEdge.addActionListener(this);
//        removeEdge.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//                String src = JOptionPane.showInputDialog(frame, "Enter source Vertex");
//                String dest = JOptionPane.showInputDialog(frame, "Enter Destination Vertex");
//                graphAlgo.graph.removeEdge(Integer.parseInt(src), Integer.parseInt(dest));
//                edgesCount--;
//                paint(graphics);
//            }
//        });
        JMenuItem nodeSize = new JMenuItem("How Many Vertex?");
        nodeSize.addActionListener(this);
//        nodeSize.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//                JOptionPane.showInputDialog("The Number Of Vertex In Graph is:\n" + graphAlgo.graph.nodeSize());
//            }
//        });
        JMenuItem edgeSize = new JMenuItem("How Many Edges?");
        edgeSize.addActionListener(this);
//        edgeSize.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//                JOptionPane.showInputDialog("The Number Of Edges In Graph Is:\n" + graphAlgo.graph.edgeSize());
//            }
//        });
        drawingMenu.add(drawNode);
        drawingMenu.add(drawEdge);
//        drawingMenu.add(connect);
        drawingMenu.add(removeNode);
        drawingMenu.add(removeEdge);
        drawingMenu.add(nodeSize);
        drawingMenu.add(edgeSize);
        JMenuItem isConnected = new JMenuItem("Is Connected");
        isConnected.addActionListener(this);

//        isConnected.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//                JOptionPane.showInputDialog(graphAlgo.isConnected());
//            }
//        });
        JMenuItem shortestDistance = new JMenuItem("Shortest Distance");
        shortestDistance.addActionListener(this);

//        shortestDistance.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//                String src = JOptionPane.showInputDialog(frame, "Enter Source Vertex");
//                String dest = JOptionPane.showInputDialog(frame, "Enter Destination Vertex");
//                JOptionPane.showInputDialog(frame, graphAlgo.shortestPathDist(Integer.parseInt(src), Integer.parseInt(dest)));
//            }
//        });
        JMenuItem shortestPath = new JMenuItem("Shortest Path");
        shortestPath.addActionListener(this);

//        shortestPath.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//                //TODO complete this method
//            }
//        });
        JMenuItem Center = new JMenuItem("Center");
        Center.addActionListener(this);

//        Center.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//                Node vertex = (Node) graphAlgo.center();
//                JOptionPane.showInputDialog("Center Vertex Of Graph is:\n" + vertex.getKey());
//                paint(graphics);
//            }
//        });
        JMenuItem tsp = new JMenuItem("TSP algorithm");
        tsp.addActionListener(this);
//        tsp.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//                List<NodeData> cities = new ArrayList<>();
//                String vertex = JOptionPane.showInputDialog("Enter vertex ID, To Finish Enter -1");
//                while (!vertex.equals("-1")) {
//                    cities.add(graphAlgo.graph.getNode(Integer.parseInt(vertex)));
//                    vertex = JOptionPane.showInputDialog("Enter vertex ID, To Finish Enter -1");
//                }
//                List<NodeData> vertexInPath = graphAlgo.tsp(cities);
//                //TODO- paint the path- continue
//            }
//        });
        algoMenu.add(isConnected);
        algoMenu.add(shortestDistance);
        algoMenu.add(shortestPath);
        algoMenu.add(Center);
        algoMenu.add(tsp);
    }

    @Override
    public void paintComponents(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//       if (this.graphics != null)
        if (this.graphAlgo == null) return;
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        paintVertex(g);
        paintEdge(g);
    }

    public void paint(Graphics g) {
        // Create a new "canvas"
        this.image = createImage(800, 800);
        graphics = image.getGraphics();

        // Draw on the new "canvas"
        paintComponents(graphics);

        // "Switch" the old "canvas" for the new one
        g.drawImage(image, 0, 0, this);
    }

    public void paintVertex(Graphics g) {
//        super.paintComponents(g);
//        g = getGraphics();
        Iterator<NodeData> iterator = graphAlgo.graph.nodeIter();
        while (iterator.hasNext()) {
//            Graphics2D g2d = (Graphics2D) g;
            g.setColor(Color.red);
            NodeData vertex = iterator.next();
            g.fillOval((int) vertex.getPosition().x(), (int) vertex.getPosition().y(), 100, 100);
        }
    }

    public void paintEdge(Graphics g) {
//        super.paintComponents(g);
//        g = getGraphics();
        Iterator<EdgeData> iterator = graphAlgo.graph.edgeIter();
        while (iterator.hasNext()) {
            Edge edge = (Edge) iterator.next();
//            Graphics2D g2d = (Graphics2D) g.create();
            g.setColor(Color.BLUE);
            paintArrowLine(g, (Node) graphAlgo.graph.Nodes.get(edge.getSrc()), (Node) graphAlgo.graph.Nodes.get(edge.getDest()), 10, 10);
        }
    }

    private void paintArrowLine(Graphics g, Node src, Node dest, int width, int height) {
//        super.paintComponents(g);
//        g = getGraphics();
        double v1x = src.getPosition().x();
        double v1y = src.getPosition().y();
        double v2x = dest.getPosition().x();
        double v2y = dest.getPosition().y();
        double dx = v2x - v1x, dy = v2y - v1y;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - width, xn = xm, ym = height, yn = -height, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + v1x;
        ym = xm * sin + ym * cos + v1y;
        xm = x;

        x = xn * cos - yn * sin + v1x;
        yn = xn * sin + yn * cos + v1y;
        xn = x;

        int[] xpoints = {(int) v2x, (int) xm, (int) xn};
        int[] ypoints = {(int) v2y, (int) ym, (int) yn};

        g.drawLine((int) v1x, (int) v1y, (int) v2x, (int) v2y);
        g.fillPolygon(xpoints, ypoints, 3);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        if (str.equals("Add Vertex")) {
            String x = JOptionPane.showInputDialog(this.frame, "Enter X Value");
            String y = JOptionPane.showInputDialog(this.frame, "Enter Y Value");
            Node vertex = new Node(vertexCount, new Geo_Location(Double.parseDouble(x), Double.parseDouble(y), 0));
            vertexCount++;
            graphAlgo.graph.addNode(vertex);
            paint(graphics);
        } else if (str.equals("Add Edge")) {
            String src = JOptionPane.showInputDialog(this, "Enter Source Vertex");
            String dest = JOptionPane.showInputDialog(this, "Enter destination vertex");
            String weight = JOptionPane.showInputDialog(this, "Enter Weight");
            Edge edge = new Edge(Integer.parseInt(src), Integer.parseInt(dest), Double.parseDouble(weight));
            edgesCount++;
            graphAlgo.graph.connect(Integer.parseInt(src), Integer.parseInt(dest), Double.parseDouble(weight));
            repaint();
        } else if (str.equals("Remove Vertex")) {
            String key = JOptionPane.showInputDialog(this, "Enter Vertex Key");
            graphAlgo.graph.removeNode(Integer.parseInt(key));
            vertexCount--;
            repaint();
        }
        else if (str.equals("Remove Edge")) {
            String src = JOptionPane.showInputDialog(this, "Enter source Vertex");
            String dest = JOptionPane.showInputDialog(this, "Enter Destination Vertex");
            graphAlgo.graph.removeEdge(Integer.parseInt(src), Integer.parseInt(dest));
            edgesCount--;
            repaint();
        }
        else if (str.equals("How Many Vertex?")) {
            JOptionPane.showMessageDialog(this, "The Number Of Vertex In Graph is:\n" + graphAlgo.graph.nodeSize());
        }
        else if (str.equals("How Many Edges?")) {
            JOptionPane.showMessageDialog(this,"The Number Of Edges In Graph Is:\n" + graphAlgo.graph.edgeSize());
        }
        else if (str.equals("Is Connected")) {
            JOptionPane.showInputDialog(graphAlgo.isConnected());
        }
        else if (str.equals("Shortest Distance")) {
            String src = JOptionPane.showInputDialog(this, "Enter Source Vertex");
            String dest = JOptionPane.showInputDialog(this, "Enter Destination Vertex");
            JOptionPane.showInputDialog(this, graphAlgo.shortestPathDist(Integer.parseInt(src), Integer.parseInt(dest)));
        }
        else if (str.equals("Shortest Path")) {
            //TODO
        }
        else if (str.equals("Center")) {
            Node vertex = (Node) graphAlgo.center();
            JOptionPane.showInputDialog("Center Vertex Of Graph is:\n" + vertex.getKey());
            //TODO- paint center node
        }
        else if (str.equals("TSP algorithm")) {
            List<NodeData> cities = new ArrayList<>();
            String vertex = JOptionPane.showInputDialog("Enter vertex ID, To Finish Enter -1");
            while (!vertex.equals("-1")) {
                cities.add(graphAlgo.graph.getNode(Integer.parseInt(vertex)));
                vertex = JOptionPane.showInputDialog("Enter vertex ID, To Finish Enter -1");
            }
            List<NodeData> vertexInPath = graphAlgo.tsp(cities);
            //TODO- paint the path- continue
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("clicked");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("exit");
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

