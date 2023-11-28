package com.ywf.framework.component;

import javax.swing.*;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/28 23:23
 */
public class BasePanel extends JPanel{
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(4.0f)); // 设置线条粗细和颜色
        g2d.drawRect(50, 50, getWidth() - 100, getHeight() - 100); // 绘制矩形，留出外边距
    }
}
