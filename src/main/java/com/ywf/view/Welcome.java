package com.ywf.view;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.sun.awt.AWTUtilities;
import com.ywf.framework.base.ThemeColor;
import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import java.awt.*;

/**
 * 欢迎界面
 *
 * @Author YWF
 * @Date 2024/4/1 10:18
 */
public class Welcome extends JWindow {

    private FlatLabel label;

    public Welcome() {
        SwingUtilities.invokeLater(() -> {
            this.initUI();
        });
    }

    private void initUI() {
        setLayout(new BorderLayout());
        //设置背景图
        ImageIcon backgroundImage = IconUtils.getIcon("/images/welcome.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        getContentPane().add(backgroundLabel);
        backgroundLabel.setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        setSize(425,236);
        setSize(backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
        setLocationRelativeTo(null); // 居中显示窗口
        label = new FlatLabel();
        setAppRunLog("欢迎使用程序员梦工场");
        add(label, BorderLayout.SOUTH);
    }

    public void start() {
        this.setVisible(true);
    }

    public void destory() {
        this.setVisible(false);
        this.dispose();
    }

    public void setAppRunLog(String description) {
        label.setText("<html><span color=\"#454544\" style=\"font-family:'Microsoft YaHei UI';font-size:8px;font-weight:plain\">" + "&nbsp;&nbsp;&nbsp;" + description + "</span></html>");
    }
}
