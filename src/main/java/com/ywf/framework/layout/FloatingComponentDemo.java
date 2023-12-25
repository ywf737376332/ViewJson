package com.ywf.framework.layout;

import javax.swing.*;
import java.awt.*;

public class FloatingComponentDemo {
    public static void main(String[] args) {
        // 创建 JFrame
        JFrame frame = new JFrame("Floating Component Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 340);
        frame.setLayout(new BorderLayout());

        // 创建 JTextArea
        JTextArea textArea = new JTextArea();
        textArea.setBounds(50, 50, 300, 200);
        JButton button = new JButton("搜索框");
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setBounds(50, 50, 300, 200);
        jScrollPane.setViewportView(textArea);

        // 创建要浮动的组件（例如 JLabel）
        JTextField floatingTextField = new JTextField();
        Point location = textArea.getLocation();
        System.out.println("Location: " + location);
        floatingTextField.setBounds(0,  0, textArea.getWidth(), 30);

        // 创建 JLayeredPane 并将 JTextArea 和要浮动的组件添加到其中
        JLayeredPane layeredPane = new JLayeredPane();
        //layeredPane.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        layeredPane.add(jScrollPane, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(floatingTextField, JLayeredPane.PALETTE_LAYER);

        frame.add(layeredPane,BorderLayout.CENTER);
        frame.add(button,BorderLayout.SOUTH);

        // 设置组件的初始位置
        floatingTextField.setLocation(textArea.getX(), textArea.getY());
        button.addActionListener(e -> {
            floatingTextField.setVisible(!floatingTextField.isVisible());
        });
        // 显示窗口
        frame.setVisible(true);
    }
}
