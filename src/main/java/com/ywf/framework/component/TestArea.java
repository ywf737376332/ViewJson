package com.ywf.framework.component;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;

/**
 * 撤销功能
 *
 * @Author YWF
 * @Date 2023/11/29 18:05
 */
public class TestArea {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Title");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(titleLabel, BorderLayout.CENTER);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenu("Menu"));
        frame.setJMenuBar(menuBar);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
