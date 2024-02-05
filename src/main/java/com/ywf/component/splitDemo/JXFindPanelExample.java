package com.ywf.component.splitDemo;

import org.jdesktop.swingx.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JXFindPanelExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 创建主窗体
            JFrame frame = new JFrame("JXFindPanel Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            // 创建文本区域
            JTextArea textArea = new JTextArea();
            textArea.setText("This is a sample text for JXFindPanel demonstration.");
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            // 创建JXFindPanel
            JXFindPanel findPanel = new JXFindPanel();
            findPanel.add(textArea);
            findPanel.setLayout(new BorderLayout());
            findPanel.add(new JLabel("Find:"), BorderLayout.WEST);
            JTextField findField = new JTextField();
            findPanel.add(findField, BorderLayout.CENTER);
            findPanel.add(new JButton("Find Next"), BorderLayout.EAST);

            // 添加事件监听器

            // 将JXFindPanel添加到主窗体
            frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
            frame.add(findPanel, BorderLayout.SOUTH);

            // 显示窗体
            frame.setVisible(true);
        });
    }
}
