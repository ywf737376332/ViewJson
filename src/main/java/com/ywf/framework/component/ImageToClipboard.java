package com.ywf.framework.component;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.undo.UndoManager;

public class ImageToClipboard {
    public static void main(String[] args) {
        JFrame frame = new JFrame("图片复制到剪贴板");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setSize(400, 200);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        UndoManager manager = new UndoManager();
        textArea.getDocument().addUndoableEditListener(manager);
        panel.add(textArea, BorderLayout.NORTH);

        frame.add(panel);
        frame.setVisible(true);
    }
}
