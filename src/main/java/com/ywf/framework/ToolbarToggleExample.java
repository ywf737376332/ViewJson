package com.ywf.framework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarToggleExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("工具栏切换示例");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JToolBar toolBar = new JToolBar();
        toolBar.add(new JButton("按钮1"));
        toolBar.add(new JButton("按钮2"));

        JButton toggleButton = new JButton("切换工具栏");
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolBar.setVisible(!toolBar.isVisible());
            }
        });

        frame.getContentPane().add(toolBar, BorderLayout.NORTH);
        frame.getContentPane().add(toggleButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
