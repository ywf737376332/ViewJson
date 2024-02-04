package com.ywf.framework.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;

public class ViewSplitPaneUI extends BasicSplitPaneUI {
    private final Color dividerColor = new Color(255, 255, 255, 255); // 设置分割条的颜色
    private final int dividerSize = 1; // 设置分割条的大小

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);

        // 获取分割条的位置和大小
        int x = getDividerLocation((JSplitPane) c);
        int y = 0;
        int width = c.getWidth();
        int height = c.getHeight();

        // 绘制分割条的背景
        g.setColor(dividerColor);
        g.fillRect(x, y, width, height);

        // 绘制分割条的边框
        g.setColor(dividerColor);
        g.drawRect(x, y, width, height);
    }

    @Override
    public BasicSplitPaneDivider createDefaultDivider() {
        return new BasicSplitPaneDivider(this) {
            @Override
            public void setBorder(Border border) {
                // 不设置边框
            }

            @Override
            public void paint(Graphics g) {
                // 不进行默认的绘制操作，而是调用CustomSplitPaneUI的paint方法进行绘制
            }
        };
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(dividerSize, c.getHeight());
    }
}
