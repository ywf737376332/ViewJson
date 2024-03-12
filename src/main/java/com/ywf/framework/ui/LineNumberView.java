package com.ywf.framework.ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Objects;

/**
 * 多文本框增加行号
 *
 * @Author YWF
 * @Date 2024/3/12 17:30
 */
public class LineNumberView extends JComponent {
    private static final int MARGIN = 5;
    private final JTextArea textArea;
    private final FontMetrics fontMetrics;
    private final int fontAscent;
    private final int fontHeight;
    private final int fontDescent;
    private final int fontLeading;

    public LineNumberView(JTextArea textArea) {
        super();
        this.textArea = textArea;
        Font font = textArea.getFont();
        fontMetrics = getFontMetrics(font);
        fontHeight = fontMetrics.getHeight();
        fontAscent = fontMetrics.getAscent();
        fontDescent = fontMetrics.getDescent();
        fontLeading = fontMetrics.getLeading();

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                repaint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                /* not needed */
            }
        });
        textArea.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidate();
                repaint();
            }
        });
        Insets i = textArea.getInsets();
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY),
                BorderFactory.createEmptyBorder(i.top, MARGIN, i.bottom, MARGIN - 1)));
        setOpaque(true);
        setBackground(Color.WHITE);
        setFont(font);
    }

    private int getComponentWidth() {
        int lineCount = textArea.getLineCount();
        int maxDigits = Math.max(3, Objects.toString(lineCount).length());
        Insets i = getInsets();
        return maxDigits * fontMetrics.stringWidth("0") + i.left + i.right;
    }

    private int getLineAtPoint(int y) {
        Element root = textArea.getDocument().getDefaultRootElement();
        int pos = textArea.viewToModel(new Point(0, y));
        // Java 9: int pos = textArea.viewToModel2D(new Point(0, y));
        return root.getElementIndex(pos);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getComponentWidth(), textArea.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        Rectangle clip = g.getClipBounds();
        g.fillRect(clip.x, clip.y, clip.width, clip.height);

        g.setColor(getForeground());
        int base = clip.y;
        int start = getLineAtPoint(base);
        int end = getLineAtPoint(base + clip.height);
        int y = start * fontHeight;
        int rmg = getInsets().right;
        for (int i = start; i <= end; i++) {
            String text = Objects.toString(i + 1);
            int x = getComponentWidth() - rmg - fontMetrics.stringWidth(text);
            y += fontAscent;
            g.drawString(text, x, y);
            y += fontDescent + fontLeading;
        }
    }
}