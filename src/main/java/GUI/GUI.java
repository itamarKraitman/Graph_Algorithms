package main.java.GUI;

import javax.swing.*;

public class GUI extends JFrame {

    private JPanel menu;

    GUI() {
        menu = new JPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(menu);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
