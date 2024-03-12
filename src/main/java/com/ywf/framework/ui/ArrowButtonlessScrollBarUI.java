package com.ywf.framework.ui;

import com.ywf.framework.base.ThemeColor;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 * 自定义滚动条UI
 */
public class ArrowButtonlessScrollBarUI extends BasicScrollBarUI {
    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new ZeroSizeButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new ZeroSizeButton();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        //g2.setPaint(trackColor);
        g2.setPaint(ThemeColor.noColor);
        g2.fill(r);
        g2.dispose();
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        JScrollBar sb = (JScrollBar) c;
        if (!sb.isEnabled()) {
            return;
        }
        BoundedRangeModel m = sb.getModel();
        if (m.getMaximum() - m.getMinimum() - m.getExtent() > 0) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color color;
            if (isDragging) {
                color = thumbDarkShadowColor;
            } else if (isThumbRollover()) {
                color = thumbLightShadowColor;
            } else {
                color = thumbColor;
            }
            g2.setPaint(color);
            g2.fillRect(r.x + 1, r.y + 1, r.width - 2, r.height - 2);
            g2.dispose();
        }
    }
}


/**
 * 滚动条下方按钮
 */
class ZeroSizeButton extends JButton {
    private static final Dimension ZERO_SIZE = new Dimension();

    @Override
    public Dimension getPreferredSize() {
        return ZERO_SIZE;
    }
}