package com.ywf.framework.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("全局组件示例");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        JButton button1 = new JButton("按钮1");
        button1.setName("button1");
        button1.setBounds(20,20, 100, 50);
        GlobalComponents.getInstance().addComponent("button1",button1);
        add(button1);

        JButton button2 = new JButton("按钮2");
        button2.setName("button2");
        button2.setBounds(20,90, 100, 50);
        GlobalComponents.getInstance().addComponent("button2",button2);
        add(button2);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();

        JButton button11 = (JButton)GlobalComponents.getInstance().getComponent("button2");
        button11.addActionListener(e -> {
            button11.setBackground(Color.CYAN);
        });
    }
}
