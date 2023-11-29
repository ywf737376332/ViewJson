package com.ywf.framework.component.button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class ToolBarTest {
    //public static void main(String[] args) {
    //    JFrame frame = new JFrame("JToolBar Example");
    //    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //    frame.setSize(300, 200);
    //
    //    JToolBar toolBar = new JToolBar();
    //    JButton button1 = new JButton("Button 1");
    //    JButton button2 = new JButton("Button 2");
    //    toolBar.add(button1);
    //    toolBar.add(button2);
    //
    //    frame.getContentPane().add(toolBar, BorderLayout.NORTH);
    //    frame.setVisible(true);
    //
    //    List<JButton> buttons = getButtonsFromToolBar(toolBar);
    //    System.out.println("找到的按钮数量： " + buttons.size());
    //}

    public static List<JButton> getButtonsFromToolBar(JToolBar toolBar) {
        List<JButton> buttons = new ArrayList<>();
        for (Component component : toolBar.getComponents()) {
            if (component instanceof JButton) {
                // 为 JToolBar 添加鼠标事件监听器
                component.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        Component source = e.getComponent();
                        System.out.println("鼠标移入："+source.getName());
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("JToolBar 边框示例");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JToolBar toolBar = new JToolBar();
            toolBar.setFloatable(false);
            toolBar.setRollover(true);
            toolBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // 设置边框颜色和宽度

            // 添加按钮到工具栏
            for (int i = 0; i < 5; i++) {
                toolBar.add(new JButton("按钮 " + (i + 1)));
            }

            frame.getContentPane().add(toolBar, BorderLayout.NORTH);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
