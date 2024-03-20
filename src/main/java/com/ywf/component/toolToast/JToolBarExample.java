package com.ywf.component.toolToast;

import javafx.scene.control.ToolBar;

import javax.swing.*;
import java.awt.*;

public class JToolBarExample {
    public static void main(String[] args) {
        // 创建窗口
        JFrame frame = new JFrame("JToolBar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // 创建 JToolBar
        JToolBar toolBar = new JToolBar();

        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0)); // 设置布局管理器并移除边距和间距

        // 创建自定义的 JButton
        // 将按钮添加到 JToolBar
        toolBar.add(new DButton("Fixed Size Button"));
        toolBar.add(new DButton("哈哈"));
        toolBar.add(new DButton("东方时代"));
        toolBar.add(new DButton("似懂非懂舒服的风飞沙风飞沙"));
        toolBar.setOrientation(JToolBar.VERTICAL);
        // 将 JToolBar 添加到窗口中
        frame.add(toolBar, BorderLayout.EAST);

        // 显示窗口
        frame.setVisible(true);
    }
}

class DButton extends JButton {

    public DButton(String text) {
        super(text);
    }

    @Override
    public Dimension getPreferredSize() {
        // 设置按钮的固定宽度和高度
        int width = 150;
        int height = 50;
        return new Dimension(width, height);
    }
};