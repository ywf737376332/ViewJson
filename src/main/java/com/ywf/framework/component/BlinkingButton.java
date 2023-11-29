package com.ywf.framework.component;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlinkingButton {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Blinking Button");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        RoundButton button = new RoundButton("点击我");
        button.setPreferredSize(new Dimension(20,20));
        button.setSize(new Dimension(15,15));
        button.setUI(new BasicButtonUI());
        button.setBackground(Color.RED);
        button.setOpaque(true);
        button.setBorderPainted(false);

        Timer timer = new Timer(500, new ActionListener() {
            private boolean isRed = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRed) {
                    button.setBackground(Color.GREEN);
                } else {
                    button.setBackground(Color.RED);
                }
                isRed = !isRed;
            }
        });
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(button);
        frame.setVisible(true);

        timer.start();
    }
}
