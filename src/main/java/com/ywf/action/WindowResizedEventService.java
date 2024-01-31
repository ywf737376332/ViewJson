package com.ywf.action;

import com.ywf.component.TextAreaBuilder;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
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
    private RTextScrollPane rTextScrollPane;

    public WindowResizedEventService(JFrame frame) {
        this.frame = frame;
        rTextScrollPane = TextAreaBuilder.getrTextScrollPane();
    }

    @Override
    public void componentResized(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }
}
