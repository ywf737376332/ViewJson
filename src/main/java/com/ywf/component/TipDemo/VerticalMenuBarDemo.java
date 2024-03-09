package com.ywf.component.TipDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

class VerticalMenuBar extends JMenuBar {

    private static final LayoutManager grid = new GridLayout(0, 1);

    public VerticalMenuBar() {

        setLayout(grid);

    }

}

class RotatedTextButton extends JMenu {
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

public class VerticalMenuBarDemo {

    public static void main(final String args[]) {

        JFrame frame = new JFrame("MenuSample Example");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new VerticalMenuBar();

// File Menu, F - Mnemonic

        RotatedTextButton fileMenu = new RotatedTextButton("File");
        fileMenu.setText("File");

        fileMenu.setMnemonic(KeyEvent.VK_F);

        menuBar.add(fileMenu);

        RotatedTextButton editMenu = new RotatedTextButton("Edit");
        fileMenu.setText("Edit");

        menuBar.add(editMenu);

        frame.setJMenuBar(menuBar);

        JLabel label = new JLabel("<html><body style='transform: rotate(90deg);'>旋转文本</body></html>");
        label.setVerticalAlignment(SwingConstants.TOP); // 设置垂直对齐方式为顶部对齐
        frame.add(label);
        frame.setSize(350, 250);

        frame.setVisible(true);

    }

}