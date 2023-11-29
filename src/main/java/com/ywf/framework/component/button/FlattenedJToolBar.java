package com.ywf.framework.component.button;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class FlattenedJToolBar {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("扁平化方格 JToolBar");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // 创建一个扁平化的 JToolBar
            JToolBar toolBar = new JToolBar();
            toolBar.setFloatable(false);
            toolBar.putClientProperty("JToolBar.toolBarStyle", "Flat"); // 设置扁平化样式

            // 为 JToolBar 添加按钮
            for (int i = 0; i < 5; i++) {
                toolBar.add(new JButton("按钮 " + (i + 1)));
            }

            // 为 JToolBar 添加鼠标事件监听器
            List<JButton> buttons = getButtonsFromToolBar(toolBar);
            System.out.println("找到的按钮数量： " + buttons.size());

            frame.getContentPane().add(toolBar, BorderLayout.NORTH);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public static java.util.List<JButton> getButtonsFromToolBar(JToolBar toolBar) {
        List<JButton> buttons = new ArrayList<>();
        for (Component component : toolBar.getComponents()) {
            if (component instanceof JButton) {
                // 为 JToolBar 添加鼠标事件监听器
                component.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        Component source = e.getComponent();
                        if (source instanceof JButton) {
                            System.out.println("鼠标移入："+source);
                            JButton button = (JButton) source;
                            button.setForeground(Color.WHITE);
                            button.setBackground(new Color(0,150,136)); // 改变鼠标移动上去的颜色
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        Component source = (Component) e.getSource();
                        if (source instanceof JButton) {
                            JButton button = (JButton) source;
                            button.setForeground(Color.BLACK);
                            button.setBackground(UIManager.getColor("control")); // 恢复默认颜色
                        }
                    }
                });
                buttons.add((JButton) component);
            }
        }
        return buttons;
    }

}
