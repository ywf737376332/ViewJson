package com.ywf.framework.layout;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // 创建一个JFrame窗口
        JFrame frame = new JFrame("JScrollPane Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // 创建一个JLayeredPane面板
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(400, 300));

        // 创建一个JScrollPane组件
        JTextArea textArea = new JTextArea(20, 30);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // 创建一个JPanel容器，并设置布局管理器为BorderLayout
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(400, 300));

        JTextField floatingLabel = new JTextField();
        Point location = textArea.getLocation();
        System.out.println("Location: " + location);
        floatingLabel.setBounds(0,  0, textArea.getWidth(), 30);
        layeredPane.add(floatingLabel, JLayeredPane.PALETTE_LAYER);

        // 将JLayeredPane添加到JPanel中，使用BorderLayout的CENTER位置
        panel.add(layeredPane, BorderLayout.CENTER);

        // 将JScrollPane添加到JPanel中，使用BorderLayout的CENTER位置
        panel.add(scrollPane, BorderLayout.CENTER);

        // 将JPanel添加到JFrame窗口中
        frame.add(panel);

        // 显示窗口
        frame.pack();
        frame.setVisible(true);
    }
}
