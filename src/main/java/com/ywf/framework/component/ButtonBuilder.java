package com.ywf.framework.component;

import com.ywf.utils.IconUtils;

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


}
