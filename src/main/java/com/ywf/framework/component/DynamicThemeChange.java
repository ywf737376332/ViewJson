package com.ywf.framework.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DynamicThemeChange {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Dynamic Theme Change");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        frame.add(panel);

        JButton button1 = new JButton("Light Theme");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                SwingUtilities.updateComponentTreeUI(frame);
                frame.pack();
            }
        });
        panel.add(button1);

        JButton button2 = new JButton("Dark Theme");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                SwingUtilities.updateComponentTreeUI(frame);
                frame.pack();
            }
        });
        panel.add(button2);

        frame.setVisible(true);
    }
}
