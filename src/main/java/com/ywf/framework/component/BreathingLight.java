package com.ywf.framework.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BreathingLight {
    private static final int INTERVAL = 100; // 动画时间间隔，单位毫秒
    private static final int MAX_ALPHA = 255; // 最大透明度
    private static final int COLOR_CHANGE_OFFSET = 10; // 颜色改变的偏移量，以实现颜色渐变效果

    private JFrame frame;
    private JPanel panel;
    private int red; // 记录当前颜色中的红色分量
    private int green; // 记录当前颜色中的绿色分量
    private int blue; // 记录当前颜色中的蓝色分量
    private int alpha; // 记录当前颜色的透明度

    public BreathingLight() {
        frame = new JFrame("Breathing Light");
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(red, green, blue, alpha));
                g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
            }
        };
        panel.setPreferredSize(new Dimension(300, 300));
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        // 开始动画
        new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 更新颜色和透明度
                //red += COLOR_CHANGE_OFFSET;
                //green += COLOR_CHANGE_OFFSET;
                //blue += COLOR_CHANGE_OFFSET;
                //alpha = (alpha + MAX_ALPHA) % MAX_ALPHA; // alpha从0到255再到0，实现呼吸灯效果
                // 保证颜色值在0-255范围内
                red = (red % 256);
                green = (green % 256);
                blue = (blue % 256);
// 保证透明度在0-255范围内
                alpha = (alpha % 256);
                panel.repaint(); // 重绘面板，以显示新的颜色和透明度
            }
        }).start();
    }

    public static void main(String[] args) {
        new BreathingLight(); // 创建并显示呼吸灯效果
    }
}