package com.ywf.framework.component;

import javax.swing.*;
import java.awt.event.*;

public class MenuExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create a menu
        JMenu menu = new JMenu("File");

        // Add an action listener to the menu item
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Menu item clicked!");
            }
        });

        // Add the menu to the menu bar
        menuBar.add(menu);

        // Set the menu bar for the frame
        frame.setJMenuBar(menuBar);

        // Display the frame
        frame.pack();
        frame.setVisible(true);
    }
}
