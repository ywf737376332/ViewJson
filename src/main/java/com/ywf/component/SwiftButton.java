package com.ywf.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

public class SwiftButton extends JPanel {
    private boolean selected = true; // 开关, ON/OFF
    // 绘制参数设定
    protected Color bgColor;// = new Color(0xFFFFFF); // 主背景
    protected Color lineColor;// = new Color(0xDEDEDE); // 描边
    protected Color darkColor;// = new Color(0xE1E1E1); // 灰色填充
    protected Color lightColor;// = new Color(0x33B4FF); // 高亮填充
    protected int padding = 2; // 轮廓线与内部圆的距离

    public SwiftButton() {
        initThemeColor();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onMouseClicked();
            }
        });
    }

    /**
     * 初始化主题颜色
     */
    private void initThemeColor() {
        bgColor = new Color(0xFFFFFF); // 主背景
        lineColor = new Color(0xDEDEDE); // 描边
        darkColor = new Color(0xE1E1E1); // 灰色填充
        lightColor = UIManager.getColor("RadioButton.selectionEnabledColor"); // 高亮填充
    }

    private void onMouseClicked() {
        toggle(); // 点击时切换状态

        // 自定义接口
        if (stateListener != null) {
            stateListener.stateChanged(this);
        }
    }

    public interface StateListener // 内部类/接口
    {
        void stateChanged(Object source);
    }

    private StateListener stateListener; // 属性

    public void setStateListener(StateListener listener) {
        this.stateListener = listener;
    }

    // 获取数据
    public boolean isSelected() {
        return this.selected;
    }

    // 设置数据
    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

    // 切换开关
    public void toggle() {
        this.selected = !this.selected;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        Graphics2D g2d = (Graphics2D) g;

        // 平滑绘制 （ 反锯齿 )
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 算出一个2:1的最大矩形
        // Rectangle r = new Rectangle(0,0, 100, 50);
        int w = width;
        int h = width / 2;
        if (h > height) {
            h = height;
            w = height * 2;
        }
        Rectangle r = new Rectangle((width - w) / 2, (height - h) / 2, w, h);

        // 里面两个并排的圆，外层轮郭为拼接曲线(不规则形状的绘制：参考4.3讲)
        // 左半区
        Rectangle r1 = new Rectangle(r.x, r.y, r.width / 2, r.height);
        // 右半区
        Rectangle r2 = new Rectangle(r.x + r.width / 2, r.y, r.width / 2, r.height);

        // 绘制外部轮廓线
        Shape arc1 = new Arc2D.Double(r1, 90, 180, Arc2D.OPEN);
        Shape arc2 = new Arc2D.Double(r2, 270, 180, Arc2D.OPEN);

        Path2D outline = new Path2D.Double(); // 外轮廓，使用拼装路径
        outline.append(arc1.getPathIterator(null), false); // 左半圆弧
        outline.append(arc2.getPathIterator(null), true);  // 右半圆弧
        outline.closePath();
        g2d.setPaint(lineColor);
        g2d.draw(outline);
        g2d.setPaint(bgColor);
        g2d.fill(outline);

        // 内部画两个圆
        if (selected) {
            drawCircleInside(g2d, r2, padding, lineColor, lightColor);
        } else {
            drawCircleInside(g2d, r1, padding, lineColor, darkColor);
        }
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 12);
    }

    // 画内部的小圆
    private void drawCircleInside(Graphics2D g2d, Rectangle rect,
                                  int deflate, Paint lineColor, Paint fillColor) {
        Rectangle r = new Rectangle(rect); // 做一个备份，不会修改传入的rect
        r.x += deflate;
        r.y += deflate;
        r.width -= (deflate * 2);
        r.height -= (deflate * 2);

        Shape shape = new Ellipse2D.Double(r.x, r.y, r.width, r.height);

        // 描边
        g2d.setPaint(lineColor);
        g2d.draw(shape);
        // 填充
        g2d.setPaint(fillColor);
        g2d.fill(shape);
    }
}
