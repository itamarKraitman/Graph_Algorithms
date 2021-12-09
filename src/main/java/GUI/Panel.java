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
    private double X = Double.MAX_VALUE;
    private  double Y = Double.MAX_VALUE;
    Graphics graphics;
    Image image;

    public Panel(DirectedWeightedGraph graph) {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setFocusable(true);
        this.graph = graph;
    }

    @Override
    public void paintComponents(Graphics g) {
        if (this.graph == null) return;
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
        Iterator<NodeData> iterator = graph.nodeIter();
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
        Iterator<EdgeData> iterator = graph.edgeIter();
        while (iterator.hasNext()) {
            Edge edge = (Edge) iterator.next();
            double srcX = this.graph.getNode(edge.getSrc()).getPosition().x();
            double srcY = this.graph.getNode(edge.getSrc()).getPosition().y();
            double destX = this.graph.getNode(edge.getDest()).getPosition().x();
            double destY = this.graph.getNode(edge.getDest()).getPosition().y();
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

    public void createCorrectSize() {
        Iterator<NodeData> iterator = this.graph.nodeIter();
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

}
