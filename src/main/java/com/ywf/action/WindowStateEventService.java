package com.ywf.action;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

/**
 * 主窗口事件
 *
 * @Author YWF
 * @Date 2023/12/10 13:17
 */
public class WindowStateEventService implements WindowStateListener {

    private JFrame frame;

    public WindowStateEventService(JFrame frame) {
        this.frame = frame;
    }


    @Override
    public void windowStateChanged(WindowEvent e) {
        if ((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED) {

        } else if (e.getNewState() == Frame.NORMAL) {

        }
    }
}
