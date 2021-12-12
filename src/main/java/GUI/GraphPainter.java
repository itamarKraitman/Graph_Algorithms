package main.java.GUI;

import main.java.GraphClass.Geo_Location;
import main.java.GraphClass.Node;
import main.java.api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;

public class GraphPainter extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    final double width = 800;
    final double height = 800;
    private double widthArrow;
    private double heightArrow;

    DirectedWeightedGraphAlgorithms graphAlgo;
    DirectedWeightedGraph graph;

    double min_x;
    double min_y;
    double max_x;
    double max_y;
    double zoomInOut;

    final Stroke nodeStroke = new BasicStroke((float) 1.0);
    final Stroke edgeStroke = new BasicStroke((float) 4.0);
    final Color defaultEdgeColor;
    final Color defaultNodeColor;

    public GraphPainter(DirectedWeightedGraphAlgorithms g) {
        this.graphAlgo = g;
        this.graph = g.getGraph();
        this.widthArrow = 8.0;
        this.heightArrow = 4.0;
        this.setFocusable(true);
        getCoordinates();
        this.defaultEdgeColor = Color.BLACK;
        this.defaultNodeColor = Color.RED;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }

    // Determining our boundaries for drawing
    public void getCoordinates() {
        Iterator<NodeData> it = this.graph.nodeIter();
        NodeData temp;
        this.min_x = 0;
        this.min_y = 0;
        this.max_x = 0;
        this.max_y = 0;

        if (it.hasNext()) {
            temp = it.next();
            min_x = temp.getPosition().x();
            min_y = temp.getPosition().y();
            max_x = temp.getPosition().x();
            max_y = temp.getPosition().y();
        }

        while (it.hasNext()) {
            temp = it.next();
            if (temp.getPosition().x() < this.min_x) {
                this.min_x = temp.getPosition().x();
            } else if (temp.getPosition().x() > max_x) {
                this.max_x = temp.getPosition().x();
            }
            if (temp.getPosition().y() < this.min_y) {
                this.min_y = temp.getPosition().y();
            } else if (temp.getPosition().y() > this.max_y) {
                this.max_y = temp.getPosition().y();
            }
        }
    }

    public void refreshPainter(DirectedWeightedGraphAlgorithms g) {
        this.graphAlgo = g;
        this.graph = g.getGraph();
        this.widthArrow = 10.0;
        this.heightArrow = 10.0;
        getCoordinates();
    }

    public void paint(Graphics g) {
        Image bufferImage = createImage(800, 800);
        Graphics bufferGraphics = bufferImage.getGraphics();
        paintComponents(bufferGraphics);
        g.drawImage(bufferImage, 0, 0, this);
    }

    // This function draws the Vertices & Edges of the graph, we use a linear transformation to adapt the x,y values  in order
    // to correctly represent the graph on the panel
    @Override
    public void paintComponents(Graphics g) {
        Graphics2D graphic = (Graphics2D) g;
        graphic.setStroke(this.edgeStroke);
        double[] X, Y;
        Iterator<EdgeData> edgeIt = this.graph.edgeIter();
        EdgeData temp;

        while (edgeIt != null && edgeIt.hasNext()) {
            temp = edgeIt.next();
            if (temp == null) {
                break;
            }

            X = linearTransform(this.graph.getNode(temp.getSrc()).getPosition());
            Y = linearTransform(this.graph.getNode(temp.getDest()).getPosition());
            graphic.setColor(this.defaultEdgeColor);
            drawEdgeArrow(graphic, temp, X[0], X[1], Y[0], Y[1]);
        }

        graphic.setStroke(this.nodeStroke);
        Iterator<NodeData> nodeIt = this.graph.nodeIter();
        NodeData tempNode;
        double[] XY;

        while (nodeIt.hasNext()) {
            tempNode = nodeIt.next();
            XY = linearTransform(tempNode.getPosition());
            graphic.setColor(this.defaultNodeColor);
            graphic.fillOval((int) XY[0] - 5, (int) XY[1] - 5, 10, 10);
            graphic.drawString("" + tempNode.getKey(), (int) XY[0] + 10, (int) XY[1] - 5);
        }
    }

    // Credit to StackOverflow & Linear Algebra for this formula:
    public double[] linearTransform(GeoLocation point) {
        double xValue = this.max_x - point.x();
        double calculatedX = this.max_x - this.min_x;
        double yValue = this.max_y - point.y();
        double calculatedY = this.max_y - this.min_y;
        double x = (xValue / calculatedX * (this.width) * 0.8 + (this.height) * 0.1);
        double y = (yValue / calculatedY * (this.width) * 0.8 + (this.height) * 0.1);
        return new double[]{x, y};
    }

    // Credit: https://coderanch.com/t/339505/java/drawing-arrows
    private void drawEdgeArrow(Graphics g, EdgeData temp, double xSrc, double ySrc, double xDest, double yDest) {
        // init vars via formulas
        double xHead1, xHead2, yHead1, yHead2; // which combine to 2 extra points that create triangular for the arrow head
        double xValue = (xDest - xSrc);
        double yValue = (yDest - ySrc);
        double distBetNodes = Math.sqrt(xValue * xValue + yValue * yValue);
        double sinVal = yValue / distBetNodes;
        double cosVal = xValue / distBetNodes;

        yHead1 = (distBetNodes - this.widthArrow) * sinVal + this.heightArrow * cosVal + ySrc; // formula
        xHead1 = (distBetNodes - this.widthArrow) * cosVal - this.heightArrow * sinVal + xSrc; // formula

        yHead2 = (distBetNodes - this.widthArrow) * sinVal + -1 * (this.heightArrow) * cosVal + ySrc; // formula
        xHead2 = (distBetNodes - this.widthArrow) * cosVal - -1 * (this.heightArrow) * sinVal + xSrc; // formula

        // Arrays are needed for x,y for the painter to draw the polygon
        int[] xpoints = {(int) (xDest), (int) (xHead1), (int) (xHead2)};
        int[] ypoints = {(int) (yDest), (int) (yHead1), (int) (yHead2)};

        g.drawLine((int) (xSrc), (int) (ySrc), (int) (xDest), (int) (yDest));
        g.drawPolygon(xpoints, ypoints, 3);
        g.setColor(Color.BLUE);

        // Draw the edge's weight
        String weight = temp.getWeight() + "";
        try {
            weight = weight.substring(0, weight.indexOf(".") + 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        g.drawString(weight, (int) (xSrc * 0.7 + xDest * 0.3), (int) (ySrc * 0.7 + yDest * 0.3));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int id = this.graphAlgo.getGraph().nodeSize() - 1;
        Geo_Location loc = new Geo_Location(e.getX(), e.getY(), 0);
        NodeData node = new Node(id, loc);
        this.graphAlgo.getGraph().addNode(node);
        getTopLevelAncestor().repaint();
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

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    }
}

