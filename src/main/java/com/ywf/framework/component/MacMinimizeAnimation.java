package com.ywf.framework.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MacMinimizeAnimation {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mac Minimize Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        JButton minimizeButton = new JButton("最小化");
        minimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                minimizeWindow(frame);
            }
        });

        frame.getContentPane().add(minimizeButton, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void minimizeWindow(JFrame frame) {
        int width = frame.getWidth();
        int height = frame.getHeight();
        int x = frame.getX();
        int y = frame.getY();

        Timer timer = new Timer(5, new ActionListener() {
            int step = 100;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (step <= 10) {
                    frame.setBounds(x + step, y + step, width - 200 * step, height - 200 * step);
                } else {
                    ((Timer) e.getSource()).stop();
                }
                step++;
            }
        });
        timer.start();
    }
}
