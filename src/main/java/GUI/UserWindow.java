package main.java.GUI;

import main.java.Algorithms.DWGraphAlgo;
import main.java.GraphClass.DWGraph;
import main.java.GraphClass.Edge;
import main.java.GraphClass.Geo_Location;
import main.java.GraphClass.Node;
import main.java.api.DirectedWeightedGraph;
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

    private DirectedWeightedGraphAlgorithms graphAlgo;
    private DirectedWeightedGraph graph;
    private JMenuBar menuBar;
    private Panel panel;
    private Graphics graphics;
    private Image image;
    private double maxX = Double.MAX_VALUE;
    private double minX = Double.MAX_VALUE;
    private double maxY = Double.MAX_VALUE;
    private double minY = Double.MAX_VALUE;
    private double X = Double.MAX_VALUE;
    private  double Y = Double.MAX_VALUE;
    private int vertexCount = 0, edgesCount = 0;

    private boolean addNode = true;

    public UserWindow(DirectedWeightedGraphAlgorithms graph) {
        super();
        graphAlgo = graph;
        this.panel = new Panel(graphAlgo.getGraph());
        InitWindow();
        createCorrectSize();
        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addMouseListener(this);
        this.getContentPane().add(panel);
        this.pack();
//        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setVisible(true);
    }

    public void InitWindow() {
        this.menuBar = new JMenuBar();
        JMenu drawingMenu = new JMenu("Drawing Graph");
        JMenu algoMenu = new JMenu("Algorithms");
        JMenu loadGraph = new JMenu("Load Graph");
        JMenu saveGraph = new JMenu("Save Graph");
        menuBar.add(drawingMenu);
        menuBar.add(algoMenu);
        menuBar.add(loadGraph);
        menuBar.add(saveGraph);

        JMenuItem drawNode = new JMenuItem("Add Vertex");
        drawNode.addActionListener(this);
        JMenuItem drawEdge = new JMenuItem("Add Edge");
        drawEdge.addActionListener(this);
        JMenuItem removeNode = new JMenuItem("Remove Vertex");
        removeNode.addActionListener(this);
        JMenuItem removeEdge = new JMenuItem("Remove Edge");
        removeEdge.addActionListener(this);
        JMenuItem nodeSize = new JMenuItem("How Many Vertex?");
        nodeSize.addActionListener(this);
        JMenuItem edgeSize = new JMenuItem("How Many Edges?");
        edgeSize.addActionListener(this);
        drawingMenu.add(drawNode);
        drawingMenu.add(drawEdge);
        drawingMenu.add(removeNode);
        drawingMenu.add(removeEdge);
        drawingMenu.add(nodeSize);
        drawingMenu.add(edgeSize);

        JMenuItem isConnected = new JMenuItem("Is Connected");
        isConnected.addActionListener(this);
        JMenuItem shortestDistance = new JMenuItem("Shortest Distance");
        shortestDistance.addActionListener(this);
        JMenuItem shortestPath = new JMenuItem("Shortest Path");
        shortestPath.addActionListener(this);
        JMenuItem Center = new JMenuItem("Center");
        Center.addActionListener(this);
        JMenuItem tsp = new JMenuItem("TSP algorithm");
        tsp.addActionListener(this);
        algoMenu.add(isConnected);
        algoMenu.add(shortestDistance);
        algoMenu.add(shortestPath);
        algoMenu.add(Center);
        algoMenu.add(tsp);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.setJMenuBar(this.menuBar);
    }

    public void createCorrectSize() {
        Iterator<NodeData> iterator = this.graphAlgo.getGraph().nodeIter();
        while (iterator.hasNext()) {
            Node current = (Node) iterator.next();
            // x position
            if (current.getPosition().x() < minX)
                minX = current.getPosition().x();
            else if (current.getPosition().x() > maxX)
                maxX = current.getPosition().x();
            //y position
            if (current.getPosition().y() < minY)
                minY = current.getPosition().y();
            else if (current.getPosition().y() > maxY)
                maxY = current.getPosition().y();
        }
        X = getWidth() / Math.abs(maxX - minX) * 0.975;
        Y = getHeight() / Math.abs(maxY - minY) * 0.9;

    }

    @Override
    public void paintComponents(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//       if (this.graphics != null)
        if (this.graphAlgo == null) return;
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintVertex(g2d);
        paintEdge(g2d);
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

    public void paintVertex(Graphics2D g) {
        Iterator<NodeData> iterator = graphAlgo.getGraph().nodeIter();
        while (iterator.hasNext()) {
            NodeData vertex = iterator.next();
            double currentX = vertex.getPosition().x();
            double currentY = vertex.getPosition().y();
            double finalX = (currentX - minX) * X;
            double finalY = (currentY - minY) * Y;
            g.setColor(Color.red);
            g.fillOval((int) finalX, (int) finalY, 25, 25);
            g.setFont(new Font("David", Font.ITALIC,12));
            g.drawString("" + vertex.getKey(), (int) (finalX + 3), (int) (finalY + 7));
        }
    }

    public void paintEdge(Graphics2D g) {
        Iterator<EdgeData> iterator = graphAlgo.getGraph().edgeIter();
        while (iterator.hasNext()) {
            Edge edge = (Edge) iterator.next();
            double srcX = this.graphAlgo.getGraph().getNode(edge.getSrc()).getPosition().x();
            double srcY = this.graphAlgo.getGraph().getNode(edge.getSrc()).getPosition().y();
            double destX = this.graphAlgo.getGraph().getNode(edge.getDest()).getPosition().x();
            double destY = this.graphAlgo.getGraph().getNode(edge.getDest()).getPosition().y();
            double finalSX = (srcX - minX) * X;
            double finalSY = (srcY - minY) * Y;
            double finalDX = (destX - minX) * X;
            double finalDY = (destX - minX) * Y;

            String weight = edge.getWeight() + "";
            g.setColor(Color.BLUE);
            paintArrowLine(g, finalSX, finalSY, finalDX, finalDY, edge.getWeight(), 10);
            g.drawString(weight, (int) (finalSX * 0.3 + finalDX * 0.7), (int) (finalSY * 0.3 + finalDY * 0.7));
        }
    }

    private void paintArrowLine(Graphics g, double SX, double SY, double DX, double DY, double width, double height) {
        double dx = DX - SX, dy = DY - SX;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - width, xn = xm, ym = height, yn = -height, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + SX;
        ym = xm * sin + ym * cos + SY;
        xm = x;

        x = xn * cos - yn * sin + SX;
        yn = xn * sin + yn * cos + SY;
        xn = x;

        int[] xpoints = {(int) DX * 10, (int) xm * 10, (int) xn * 10};
        int[] ypoints = {(int) DY * 10, (int) ym * 10, (int) yn * 10};

        g.drawLine((int) SX * 10, (int) SY * 10, (int) DX * 10, (int) DY * 10);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        if (str.equals("Add Vertex")) {
            String x = JOptionPane.showInputDialog(this, "Enter X Value");
            String y = JOptionPane.showInputDialog(this, "Enter Y Value");
            Node vertex = new Node(vertexCount, new Geo_Location(Double.parseDouble(x), Double.parseDouble(y), 0));
            vertexCount++;
            graphAlgo.getGraph().addNode(vertex);
            repaint();
        } else if (str.equals("Add Edge")) {
            String src = JOptionPane.showInputDialog(this, "Enter Source Vertex");
            String dest = JOptionPane.showInputDialog(this, "Enter destination vertex");
            String weight = JOptionPane.showInputDialog(this, "Enter Weight");
            Edge edge = new Edge(Integer.parseInt(src), Integer.parseInt(dest), Double.parseDouble(weight));
            edgesCount++;
            graphAlgo.getGraph().connect(Integer.parseInt(src), Integer.parseInt(dest), Double.parseDouble(weight));
            repaint();
        } else if (str.equals("Remove Vertex")) {
            String key = JOptionPane.showInputDialog(this, "Enter Vertex Key");
            graphAlgo.getGraph().removeNode(Integer.parseInt(key));
            vertexCount--;
            repaint();
        }
        else if (str.equals("Remove Edge")) {
            String src = JOptionPane.showInputDialog(this, "Enter source Vertex");
            String dest = JOptionPane.showInputDialog(this, "Enter Destination Vertex");
            graphAlgo.getGraph().removeEdge(Integer.parseInt(src), Integer.parseInt(dest));
            edgesCount--;
            repaint();
        }
        else if (str.equals("How Many Vertex?")) {
            JOptionPane.showMessageDialog(this, "The Number Of Vertex In Graph is:\n" + graphAlgo.getGraph().nodeSize());
        }
        else if (str.equals("How Many Edges?")) {
            JOptionPane.showMessageDialog(this,"The Number Of Edges In Graph Is:\n" + graphAlgo.getGraph().edgeSize());
        }
        else if (str.equals("Is Connected")) {
            boolean answer = graphAlgo.isConnected();
            String message = "";
            if (answer) message = "The Graph Is Connected";
            else message = "The Graph Is Not Connected";
            JOptionPane.showMessageDialog(this, message);
        }
        else if (str.equals("Shortest Distance")) {
            String src = JOptionPane.showInputDialog(this, "Enter Source Vertex");
            String dest = JOptionPane.showInputDialog(this, "Enter Destination Vertex");
            JOptionPane.showMessageDialog(this, "Shortest Path Distance Is " + graphAlgo.shortestPathDist(Integer.parseInt(src), Integer.parseInt(dest)));
        }
        else if (str.equals("Shortest Path")) {
            String src = JOptionPane.showInputDialog(this, "Enter Source Vertex");
            String dest = JOptionPane.showInputDialog(this, "Enter Destination Vertex");
            List<NodeData> path = graphAlgo.shortestPath(Integer.parseInt(src), Integer.parseInt(dest));
            JOptionPane.showMessageDialog(this, "Shortest Path Is:\n" + ToString(path));
        }
        else if (str.equals("Center")) {
            Node vertex = (Node) graphAlgo.center();
            JOptionPane.showInputDialog("Center Vertex Of Graph is:\n" + vertex.getKey());
        }
        else if (str.equals("TSP algorithm")) {
            List<NodeData> cities = new ArrayList<>();
            String vertex = JOptionPane.showInputDialog("Enter vertex ID, To Finish Enter -1");
            while (!vertex.equals("-1")) {
                cities.add(graphAlgo.getGraph().getNode(Integer.parseInt(vertex)));
                vertex = JOptionPane.showInputDialog("Enter vertex ID, To Finish Enter -1");
            }
            List<NodeData> vertexInPath = graphAlgo.tsp(cities);
            JOptionPane.showMessageDialog(this, "TSP Result For Those Vertexes Is:\n" + ToString(vertexInPath));

        }
        else if (str.equals("Load Graph")) {
            String filePath = JOptionPane.showInputDialog("Enter New JSON File Path");
            DWGraph anotherGraph = new DWGraph(filePath);
            this.graphAlgo.init(anotherGraph);
            this.panel = new Panel(graphAlgo.getGraph());
            repaint();
        }
        else if (str.equals("Save Graph")) {
            graphAlgo.save("Graph.json");
            JOptionPane.showMessageDialog(this, "File Was Saved");
        }
    }

    private String ToString(List<NodeData> path) {
        StringBuffer ans = new StringBuffer();
        for (NodeData nodeData : path) {
            ans.append(nodeData.getKey());
            ans.append("->");
        }
        ans.delete(ans.length() - 2, ans.length());
        return ans.substring(0);
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

