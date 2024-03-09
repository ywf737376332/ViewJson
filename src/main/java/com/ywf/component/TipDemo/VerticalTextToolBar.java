package com.ywf.component.TipDemo;

import javax.swing.*;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.*;

public class VerticalTextToolBar {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("竖向文字的JToolBar");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 600);

            JPanel toolBar = new JPanel();
            toolBar.setBackground(Color.GREEN);
            toolBar.setPreferredSize(new Dimension(200,500));
            RotatedTextButton btn1 = new RotatedTextButton("按钮1");
            btn1.setPreferredSize(new Dimension(50,80));
            toolBar.add(btn1);

            frame.add(toolBar, BorderLayout.EAST);
            frame.setVisible(true);
        });
    }

    static class RotatedTextButton extends JButton {
        public RotatedTextButton(String text) {
            super(text);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.rotate(Math.toRadians(90), getWidth() / 2, getHeight() / 2);
            super.paintComponent(g);
        }
    }
}
