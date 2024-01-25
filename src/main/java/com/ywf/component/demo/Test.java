package com.ywf.component.demo;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Test {
    public static void main(String[] args) {
        // 初始化RSyntaxTextArea
        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
        // 创建自定义的右键菜单
        JPopupMenu customMenu = new JPopupMenu();
        JMenuItem item1 = new JMenuItem("Custom Action 1");
        JMenuItem item2 = new JMenuItem("Custom Action 2");

        // 添加动作监听器到菜单项
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Custom Action 1 triggered.");
                // 在这里执行你的自定义操作
            }
        });

        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Custom Action 2 triggered.");
                // 在这里执行你的自定义操作
            }
        });

        // 将菜单项添加到自定义菜单中
        customMenu.add(item1);
        customMenu.add(item2);

        // 监听文本区域的鼠标事件
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }

            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    customMenu.show(textArea, e.getX(), e.getY());
                }
            }
        });

        // 创建一个 JFrame 并将文本区域添加进去
        JFrame frame = new JFrame("Custom Context Menu Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(textArea));
        frame.pack();
        frame.setVisible(true);
    }
}

