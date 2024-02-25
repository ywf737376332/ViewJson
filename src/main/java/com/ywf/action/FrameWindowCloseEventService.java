package com.ywf.action;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 主窗口事件
 *
 * @Author YWF
 * @Date 2023/12/10 13:17
 */
public class FrameWindowCloseEventService extends WindowAdapter {

    private JFrame frame;

    public FrameWindowCloseEventService(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        MenuEventService.getInstance().closeWindowsFrameActionPerformed(frame);
    }

}
