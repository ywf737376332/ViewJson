package com.ywf.component;

import com.ywf.framework.handle.ApplicationContext;
import com.ywf.framework.utils.IconUtils;
import com.ywf.framework.utils.PropertiesUtil;

import javax.swing.*;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/14 23:04
 */
public class SystemTrayBuilder {

    public static void createSystemTray(JFrame frame, PropertiesUtil systemProperties) {
        if (SystemTray.isSupported()) {
            try {
                SystemTray tray = SystemTray.getSystemTray();
                PopupMenu popup = new PopupMenu();
                MenuItem exitItem = new MenuItem("Exit");
                Image image = IconUtils.getIcon("/icons/logo10.png");
                TrayIcon trayIcon = new TrayIcon(image, "JSON格式化工具", popup);
                exitItem.addActionListener(e -> closeWindowsActionPerformed(frame, systemProperties));
                popup.add(exitItem);
                trayIcon.setImageAutoSize(true);
                trayIcon.addActionListener(e2 -> {
                    if (trayIcon != null) {
                        SystemTray.getSystemTray().remove(trayIcon);
                    }
                    frame.setVisible(true);
                    frame.toFront();//将此窗口置于前端
                });
                tray.add(trayIcon);
            } catch (AWTException ex) {
                throw new RuntimeException(ex);
            }
            frame.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(frame, "系统不支持系统托盘", "错误", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void closeWindowsActionPerformed(JFrame frame, PropertiesUtil systemProperties) {
        int confirmed = JOptionPane.showConfirmDialog(frame,
                "您是否想退出当前应用？", "确认关闭",
                JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            // 屏幕尺寸大小保存
            systemProperties.setValue(ApplicationContext.SCREEN_SIZE_WIDTH_KEY, String.valueOf(frame.getWidth()));
            systemProperties.setValue(ApplicationContext.SCREEN_SIZE_HEIGHT_KEY, String.valueOf(frame.getHeight()));
            frame.dispose();
        }
    }


}
