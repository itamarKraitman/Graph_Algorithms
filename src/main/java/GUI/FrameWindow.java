package main.java.GUI;

import main.java.api.DirectedWeightedGraphAlgorithms;

import javax.swing.*;
import java.awt.*;

public class FrameWindow extends JFrame {

    DirectedWeightedGraphAlgorithms graphAlgo;
    UserMenu menu;

    /**
     * Generate the frame window upon which a panel and a user menu will be placed
     * @param g, Directed Weighted Graph represented using HashMaps
     */
    public FrameWindow(DirectedWeightedGraphAlgorithms g){

        this.graphAlgo = g;
        this.setSize(1000, 1000);
        this.setBackground(Color.WHITE);
        this.setTitle("OOP 2021 Ex2 GUI - Yuval Bubnovsky & Itamar Kraitman");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GraphPainter painter = new GraphPainter(this.graphAlgo);
        menu = new UserMenu(this.graphAlgo,painter,this);

        this.setJMenuBar(menu);
        this.add(painter);
        this.setVisible(true);
    }


}
