package main.java.GUI;

import main.java.GraphClass.Geo_Location;
import main.java.GraphClass.Node;
import main.java.api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
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

    private void getCoordinates() {
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
            drawArrow(graphic, temp,X[0], X[1], Y[0], Y[1]);
            // drawing weight
            graphic.setColor(Color.BLUE);
            graphic.setFont(new Font("Ariel", Font.BOLD, 12));
//            String weight = temp.getWeight() + "";
//            weight = weight.substring(0, weight.indexOf(".") + 5);
//            graphic.drawString(weight,(int)X[0] + 2,(int)Y[1] + 2);
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
        double delCurrX = this.max_x - point.x();
        double delPicX = this.max_x - this.min_x;
        double delCurrY = this.max_y - point.y();
        double delPicY = this.max_y - this.min_y;
        double x = (delCurrX / delPicX * (this.width) * 0.8 + (this.height) * 0.1);
        double y = (delCurrY / delPicY * (this.width) * 0.8 + (this.height) * 0.1);
        return new double[]{x, y};
    }

    // Credit: https://coderanch.com/t/339505/java/drawing-arrows
    private void drawArrow(Graphics g, EdgeData temp,double xSrc, double ySrc, double xDest, double yDest) {
        // init vars via formulas
        double xHeadArrow1, xHeadArrow2, yHeadArrow1, yHeadArrow2; // which combine to 2 extra points that create triangular for the arrow head
        double delX = (xDest - xSrc), delY = (yDest - ySrc); // cal delta x,y
        double distBetNodes = Math.sqrt(delX * delX + delY * delY); // cal distance src->dest nodes
        double sinVal = delY / distBetNodes, cosVal = delX / distBetNodes; // const math via algebra, basic rules of sin/cos

        yHeadArrow1 = (distBetNodes - this.widthArrow) * sinVal + this.heightArrow * cosVal + ySrc; // via formula
        xHeadArrow1 = (distBetNodes - this.widthArrow) * cosVal - this.heightArrow * sinVal + xSrc; // via formula

        yHeadArrow2 = (distBetNodes - this.widthArrow) * sinVal + -1 * (this.heightArrow) * cosVal + ySrc; // via formula
        xHeadArrow2 = (distBetNodes - this.widthArrow) * cosVal - -1 * (this.heightArrow) * sinVal + xSrc; // via formula

        // arrays for x,y cordinates to draw the polygon
        int[] xpoints = {(int) (xDest), (int) (xHeadArrow1), (int) (xHeadArrow2)};
        int[] ypoints = {(int) (yDest), (int) (yHeadArrow1), (int) (yHeadArrow2)};
        // draw arrow line
        g.drawLine((int) (xSrc), (int) (ySrc), (int) (xDest), (int) (yDest));
        // draw arrow head
        g.drawPolygon(xpoints, ypoints, 3);
        g.setColor(Color.BLUE);
        String weight = temp.getWeight() + "";
        weight = weight.substring(0, weight.indexOf(".") + 5);
        g.drawString(weight,(int) (xSrc), (int) (yDest));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        int id = this.graphAlgo.getGraph().nodeSize() - 1;
//        Geo_Location loc = new Geo_Location(e.getX(), e.getY(), 0);
//        NodeData node = new Node(id, loc);
//        this.graphAlgo.getGraph().addNode(node);
//        getTopLevelAncestor().repaint();
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
//        double xMPrev = mousePosPrev.getX(), yMPrev = mousePosPrev.getY();
//        double xMNext = mousePosNext.getX(), yMNext = mousePosNext.getY();
//        double zoomedX = xMPrev + (e.getX() - xMNext) / this.zoomInOut, zoomedY = yMPrev + (e.getY() - yMNext) / this.zoomInOut;
//        mousePoint.setLocation(zoomedX, zoomedY);
//        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // https://www.javacodex.com/Swing/MouseWheelListener for more data
        // ensure that the picture wont minimize to size that will be too close to zero zoom
        // this way of implementation avoids from stuck picture cuz of too much zoom usage
//        double temp = ((double) -e.getWheelRotation()) / 7;
//        if (this.zoomInOut + temp > 0.05) {
//            this.zoomInOut = this.zoomInOut + temp;
//            repaint();
    }
}

