package com.ywf.framework.base;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

/**
 * 第一个字符放大的文本段落
 *
 * @Author YWF
 * @Date 2024/3/19 15:03
 */
public class DropcapLabel extends JLabel {
    public DropcapLabel(String text) {
        super(text);
    }

    @Override protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(ThemeColor.noColor);
        g2.fillRect(0, 0, getWidth(), getHeight());

        Insets i = getInsets();
        float x0 = i.left;
        float y0 = i.top;

        Font font = getFont();
        String txt = getText();

        FontRenderContext frc = g2.getFontRenderContext();
        Shape shape = new TextLayout(txt.substring(0, 1), font, frc).getOutline(null);

        AffineTransform at1 = AffineTransform.getScaleInstance(2d, 2d);
        Shape s1 = at1.createTransformedShape(shape);
        Rectangle r = s1.getBounds();
        r.grow(6, 2);
        int rw = r.width;
        int rh = r.height;

        AffineTransform at2 = AffineTransform.getTranslateInstance(x0, y0 + rh);
        Shape s2 = at2.createTransformedShape(s1);
        g2.setPaint(getForeground());
        g2.fill(s2);

        float x = x0 + rw;
        float y = y0;
        int w0 = getWidth() - i.left - i.right;
        int w = w0 - rw;

        AttributedString as = new AttributedString(txt.substring(1));
        as.addAttribute(TextAttribute.FONT, font);
        AttributedCharacterIterator aci = as.getIterator();
        LineBreakMeasurer lbm = new LineBreakMeasurer(aci, frc);
        while (lbm.getPosition() < aci.getEndIndex()) {
            TextLayout tl = lbm.nextLayout(w);
            tl.draw(g2, x, y + tl.getAscent());
            y += tl.getDescent() + tl.getLeading() + tl.getAscent();
            if (y0 + rh < y) {
                x = x0;
                w = w0;
            }
        }
        g2.dispose();
    }
}
