package com.ywf.action;

import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.utils.PropertiesUtil;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 主窗口事件
 *
 * @Author YWF
 * @Date 2023/12/10 13:17
 */
public class FrameEventService extends WindowAdapter {

    private PropertiesUtil systemProperties;
    private JFrame frame;

    public FrameEventService(JFrame frame) {
        this.frame = frame;
        this.systemProperties = PropertiesUtil.instance();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        int confirmed = JOptionPane.showConfirmDialog(frame,
                "您是否想退出当前应用？", "确认关闭",
                JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            // 屏幕尺寸大小保存
            systemProperties.setValueToProperties(SystemConstant.SCREEN_SIZE_WIDTH_KEY, String.valueOf(frame.getWidth()));
            systemProperties.setValueToProperties(SystemConstant.SCREEN_SIZE_HEIGHT_KEY, String.valueOf(frame.getHeight()));
            frame.dispose();
        }
    }
}
