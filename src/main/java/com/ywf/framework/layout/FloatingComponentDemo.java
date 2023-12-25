package com.ywf.framework.layout;

import javax.swing.*;
import java.awt.*;

public class FloatingComponentDemo {
    public static void main(String[] args) {
        // 创建 JFrame
        JFrame frame = new JFrame("Floating Component Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 340);
        frame.setLayout(null);

        // 创建 JTextArea
        JTextArea textArea = new JTextArea();
        textArea.setBounds(50, 50, 300, 200);
        JButton button = new JButton("搜索框");
        button.setBounds(50, 260, 300, 30);
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(textArea);
        frame.add(jScrollPane);
        frame.add(button);

        // 创建要浮动的组件（例如 JLabel）
        //JLabel floatingLabel = new JLabel("Floating Label");
        JTextField floatingLabel = new JTextField();
        Point location = textArea.getLocation();
        System.out.println("Location: " + location);
        floatingLabel.setBounds(0,  0, textArea.getWidth(), 30);

        // 创建 JLayeredPane 并将 JTextArea 和要浮动的组件添加到其中
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setMinimumSize(new Dimension(235, 600));
        layeredPane.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        frame.add(layeredPane);
        layeredPane.add(textArea, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(floatingLabel, JLayeredPane.PALETTE_LAYER);

        // 设置组件的初始位置
        floatingLabel.setLocation(textArea.getX(), textArea.getY());
        button.addActionListener(e -> {
            floatingLabel.setVisible(!floatingLabel.isVisible());
        });
        // 显示窗口
        frame.setVisible(true);
    }
}
