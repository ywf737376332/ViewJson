package com.ywf.action;

import com.ywf.component.DialogBuilder;
import com.ywf.component.TextAreaBuilder;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
    private RTextScrollPane rTextScrollPane;

    public WindowStateEventService(JFrame frame) {
        this.frame = frame;
        rTextScrollPane = TextAreaBuilder.getrTextScrollPane();
    }


    @Override
    public void windowStateChanged(WindowEvent e) {
        if ((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED) {
            JDialog findDialog = DialogBuilder.getFindDialog();
            if (findDialog != null) {
                findDialog.setVisible(false);
            }
        } else if (e.getNewState() == Frame.NORMAL) {
            JDialog findDialog = DialogBuilder.getFindDialog();
            if (findDialog != null) {
                findDialog.setVisible(true);
            }
        }
    }
}
