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
        JFrame frame = new JFrame("JTextArea Undo Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        UndoManager undoManager = new UndoManager();
        textArea.getDocument().addUndoableEditListener(e -> undoManager.addEdit(e.getEdit()));
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> {
            if (undoManager.canUndo()) {
                undoManager.undo();
            }
        });

        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.getContentPane().add(undoButton, BorderLayout.SOUTH);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
