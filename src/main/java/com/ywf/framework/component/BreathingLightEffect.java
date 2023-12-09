package com.ywf.framework.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BreathingLightEffect {
    public static void main(String[] args) {
        JFrame frame = new JFrame("呼吸灯效果");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(255, 0, 0);
                Color color2 = new Color(0, 255, 0);
                float ratio = (System.currentTimeMillis() % 1000) / 1000f;
                if (ratio < 0.5f) {
                    g.setColor(color1);
                } else {
                    g.setColor(color2);
                }
                g.fillRect(0, 0, width, height);
            }
        };

        frame.add(panel);
        frame.setVisible(true);

        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.repaint();
            }
        });
        timer.start();
    }
}
