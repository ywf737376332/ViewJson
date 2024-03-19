package com.ywf.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 锁屏界面
 *
 * @Author YWF
 * @Date 2024/3/15 19:14
 */
public class LockScreenPanelBuilder {

    public static JPanel initLockScreenPane(JRootPane rootPane) {
        JPanel panel = new JPanel();
        //panel.setBorder(BorderBuilder.topBorder(1, ThemeColor.themeColor));
        panel.setBackground(new Color(102, 0, 51, 0));
        JSONButton unLockButton = new JSONButton("解锁");
        unLockButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rootPane.getGlassPane().setVisible(false);
            }
        });
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));
        panel.add(unLockButton);
        return panel;
    }

}
