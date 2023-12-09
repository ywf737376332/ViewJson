package com.ywf.framework.component;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;

public class TextAreaToClipboard {
    public static void main(String[] args) {
        JTextArea textArea = new JTextArea("这是一段文本");
        JButton button = new JButton("复制为图片");
        button.addActionListener(e -> copyTextAreaToClipboard(textArea));

        JFrame frame = new JFrame("TextArea to Clipboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void copyTextAreaToClipboard(JTextArea textArea) {
        String content = textArea.getText();
        int width = textArea.getWidth();
        int height = textArea.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setFont(textArea.getFont());
        g2d.setColor(textArea.getForeground());
        g2d.drawString(content, 0, height - g2d.getFontMetrics().getAscent());
        g2d.dispose();

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new ImageSelection(image);
        clipboard.setContents(transferable, null);
    }

    static class ImageSelection implements Transferable {
        private final Image image;

        public ImageSelection(Image image) {
            this.image = image;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.imageFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.imageFlavor.equals(flavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (isDataFlavorSupported(flavor)) {
                return image;
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }
    }
}
