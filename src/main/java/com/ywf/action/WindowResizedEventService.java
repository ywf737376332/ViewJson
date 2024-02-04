package com.ywf.action;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * 主窗口事件
 *
 * @Author YWF
 * @Date 2023/12/10 13:17
 */
public class WindowResizedEventService extends ComponentAdapter {

    private JFrame frame;

    public WindowResizedEventService(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void componentResized(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }
}
