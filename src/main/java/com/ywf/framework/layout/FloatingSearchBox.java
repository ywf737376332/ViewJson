package com.ywf.framework.layout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FloatingSearchBox {
    public static void main(String[] args) {
        JFrame frame = new JFrame("悬浮搜索框");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JTextField searchField = new JTextField(20);
        searchField.setBounds(10, 10, 150, 25);
        panel.add(searchField);

        JButton searchButton = new JButton("搜索");
        searchButton.setBounds(170, 10, 70, 25);
        panel.add(searchButton);

        JPanel floatingPanel = new JPanel();
        floatingPanel.setLayout(null);
        floatingPanel.setBackground(Color.RED);
        floatingPanel.setBounds(10, 40, 380, 30);
        panel.add(floatingPanel);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(10, 40, 380, 230);
        panel.add(textArea);

        searchButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                floatingPanel.setVisible(!floatingPanel.isVisible());
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
