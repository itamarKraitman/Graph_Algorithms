package main.java.GUI;

import main.java.api.DirectedWeightedGraphAlgorithms;

import javax.swing.*;
import java.awt.*;

public class FrameWindow extends JFrame {

    DirectedWeightedGraphAlgorithms graphAlgo;
    UserMenu menu;

    public FrameWindow(DirectedWeightedGraphAlgorithms g){

        this.graphAlgo = g;
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
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
