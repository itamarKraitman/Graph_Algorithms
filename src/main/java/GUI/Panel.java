package main.java.GUI;

import main.java.GraphClass.Edge;
import main.java.GraphClass.Node;
import main.java.api.DirectedWeightedGraph;
import main.java.api.EdgeData;
import main.java.api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class Panel extends JPanel {

    private DirectedWeightedGraph graph;
    private double maxX = Double.MAX_VALUE;
    private double minX = Double.MAX_VALUE;
    private double maxY = Double.MAX_VALUE;
    private double minY = Double.MAX_VALUE;
    private int X = Integer.MAX_VALUE;
    private int Y = Integer.MAX_VALUE;
    Graphics graphics;
    Image image;

    public Panel(DirectedWeightedGraph graph) {
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        this.graph = graph;
        Iterator<NodeData> n = graph.nodeIter();
        NodeData vertex = n.next();
        minX = vertex.getPosition().x();
        minY = vertex.getPosition().y();
        maxX = vertex.getPosition().x();
        maxY = vertex.getPosition().y();
        while (n.hasNext()) {
            vertex = n.next();
            minX = Math.min(minX, vertex.getPosition().x());
            minY = Math.min(minY, vertex.getPosition().y());

            maxX = Math.max(maxX, vertex.getPosition().x());
            maxY = Math.max(maxY, vertex.getPosition().y());
        }
    }

    private void findEdge() {
        Iterator<NodeData> n = graph.nodeIter();
        NodeData node = n.next();
        minX = node.getPosition().x();
        minY = node.getPosition().y();
        maxX = node.getPosition().x();
        maxY = node.getPosition().y();
        while (n.hasNext()) {
            node = n.next();
            minX = Math.min(minX, node.getPosition().x());
            minY = Math.min(minY, node.getPosition().y());

            maxX = Math.max(maxX, node.getPosition().x());
            maxY = Math.max(maxY, node.getPosition().y());
        }
        createCorrectSize();
    }

    public void paint(Graphics g) {
        // Create a new "canvas"
        this.image = createImage(1600,1600);
        graphics = image.getGraphics();

        // Draw on the new "canvas"
        paintComponent(graphics);

        // "Switch" the old "canvas" for the new one
        g.drawImage(image, 0, 0, this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (this.graph == null) return;
        super.paintComponent(g);
        createCorrectSize();
        // painting vertexes
        Graphics2D g2d = (Graphics2D) g;
        Iterator<NodeData> vertexIter = graph.nodeIter();
        while (vertexIter.hasNext()) {
            NodeData vertex = vertexIter.next();
            double currentX = vertex.getPosition().x();
            double currentY = vertex.getPosition().y();
            double finalX = (int) ((currentX - minX) * X);
            double finalY = (int) ((currentY - minY) * Y);
            g.setColor(Color.red);
            g.fillOval((int) finalX, (int) finalY, 20, 20);
            g.setFont(new Font("David", Font.ITALIC, 12));
            g.drawString("" + vertex.getKey(), (int) (finalX + 8), (int) (finalY + 15));
        }
        Iterator<EdgeData> edgesIter = graph.edgeIter();
        while (edgesIter.hasNext()) {
            Edge currentEdge = (Edge) edgesIter.next();
            double SX = (graph.getNode(currentEdge.getSrc()).getPosition().x() - minX) * X + 10;
            double SY = (graph.getNode(currentEdge.getSrc()).getPosition().y() - minY) * Y + 10;
            double DX = (graph.getNode(currentEdge.getDest()).getPosition().x() - minX) * X + 10;
            double DY = (graph.getNode(currentEdge.getDest()).getPosition().y() - minY) * Y + 10;
            g.setColor(Color.BLUE);
            paintArrowLine(g, (int) SX, (int) SY, (int) DX, (int) DY, 30, 7);
            // weight
            String weight;
            try {
                weight = String.valueOf(currentEdge.getWeight()).substring(0, String.valueOf(currentEdge.getWeight()).indexOf(".") + 5);
                g.setColor(Color.BLACK);
                g.drawString(weight, (int) (SX * 0.25 + DX * 0.75), (int) (SY * 0.25 + DY * 0.75));
                g.setFont(new Font("David", Font.ITALIC, 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void paintArrowLine(Graphics g, int SX, int SY, int DX, int DY, int width, int height) {
        int dx = DX - SX, dy = DY - SY;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - width, xn = xm, ym = height, yn = -height, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + SX;
        ym = xm * sin + ym * cos + SY;
        xm = x;

        x = xn * cos - yn * sin + SX;
        yn = xn * sin + yn * cos + SY;
        xn = x;

        int[] xpoints = {DX, (int) xm, (int) xn};
        int[] ypoints = {DY, (int) ym, (int) yn};

        g.drawLine( SX, SY,  DX,  DY);
        g.fillPolygon(xpoints, ypoints, 3);
    }

    public void createCorrectSize() {
        X = (int) (getWidth() / Math.abs(maxX - minX) * 0.975);
        Y = (int) (getHeight() / Math.abs(maxY - minY) * 0.9);
    }

}
