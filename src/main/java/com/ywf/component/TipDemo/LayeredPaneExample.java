package com.ywf.component.TipDemo;

import javax.swing.*;
import java.awt.*;

public class LayeredPaneExample {
    public static void main(String[] args) {
        // 创建 JFrame 对象
        JFrame frame = new JFrame("LayeredPane Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // 创建 JRootPane 对象
        JRootPane rootPane = new JRootPane();
        frame.setContentPane(rootPane);

        // 创建 LayeredPane 对象
        JLayeredPane layeredPane = new JLayeredPane();
        rootPane.setLayeredPane(layeredPane);

        // 创建两个标签组件
        JLabel label1 = new JLabel("Label 1");
        label1.setBounds(50, 50, 100, 30);
        JLabel label2 = new JLabel("Label 2");
        label2.setBounds(100, 100, 100, 30);

        // 将标签组件添加到 LayeredPane 中
        layeredPane.add(label1, Integer.valueOf(1)); // 设置层级为 1
        layeredPane.add(label2, Integer.valueOf(2)); // 设置层级为 2

        // 显示窗口
        frame.setVisible(true);
    }
}
