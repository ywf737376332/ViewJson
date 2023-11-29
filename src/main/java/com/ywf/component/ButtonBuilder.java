package com.ywf.component;

import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/24 22:36
 */
public class ButtonBuilder {


    public static JButton formatButton() {
        JButton button = new JButton("格式化");
        button.setBackground(new Color(64, 158, 255));
        button.setForeground(new Color(255, 255, 255));
        button.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        button.setBounds(130, 15, 80, 35);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(2, 101, 210));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(64, 158, 255));
            }
        });
        return button;
    }

    public static JButton clearButton() {
        JButton button = new JButton("清空");
        button.setBounds(20, 15, 80, 35);
        button.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 51, 51));
                button.setForeground(new Color(255, 255, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 255, 255));
                button.setForeground(new Color(0, 0, 0));
            }
        });
        return button;
    }

    public static JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar("工具栏");
        // 可移动工具栏
        toolBar.setFloatable(true);
        toolBar.setMargin(new Insets(2, 10, 2, 10));
        // 需要绘制边框
        toolBar.setBorderPainted(true);
        toolBar.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        toolBar.putClientProperty("JToolBar.toolBarStyle", "Flat"); // 设置扁平化样式
        // 将默认大小的分隔符添加到工具栏的末尾
        toolBar.addSeparator(new Dimension(5,5));
        JButton btnClean = new JButton("清空");
        JButton btnFormat = new JButton("格式化");
        JButton btnComp = new JButton("压缩");
        JButton btnFind = new JButton("查找");
        JButton btnRepl = new JButton("替换");
        btnClean.setIcon(IconUtils.getSVGIcon("ico/btn.svg"));

        toolBar.add(btnClean);
        toolBar.add(btnFormat);
        toolBar.add(btnComp);
        toolBar.add(btnFind);
        toolBar.add(btnRepl);

        return toolBar;
    }

}
