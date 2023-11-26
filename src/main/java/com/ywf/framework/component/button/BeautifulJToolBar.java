package com.ywf.framework.component.button;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;

public class BeautifulJToolBar extends JToolBar {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 在这里实现自定义绘制，例如设置背景颜色、边框样式等
        g.setColor(new Color(237,240,248));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}

class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatIntelliJLaf());

        JFrame frame = new JFrame("Beautiful JToolBar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        BeautifulJToolBar toolBar = new BeautifulJToolBar();
        toolBar.setFloatable(false); // 禁止拖动工具栏
        toolBar.setRollover(true); // 鼠标悬停时高亮显示按钮
        toolBar.setBorderPainted(false); // 隐藏边框
        toolBar.setBackground(Color.WHITE); // 设置背景颜色
        toolBar.addSeparator(); // 添加分隔线
        JButton btnClean = new JButton("清空");
        JButton btnFormat = new JButton("格式化");
        JButton btnComp = new JButton("压缩");
        toolBar.add(btnClean); // 添加按钮1
        toolBar.add(btnFormat); // 添加按钮2
        toolBar.add(btnComp); // 添加按钮3

        frame.getContentPane().add(toolBar, BorderLayout.NORTH); // 将工具栏添加到窗口顶部
        frame.setVisible(true);
    }
}
