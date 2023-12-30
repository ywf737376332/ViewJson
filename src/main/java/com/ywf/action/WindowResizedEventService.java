package com.ywf.action;

import com.ywf.component.DialogBuilder;
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
        this.movedWindwos();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        this.movedWindwos();
    }

    /**
     * 窗口发生变化，重新设置查找对话框的位置
     *
     * @date 2023/12/24 18:45
     */
    private void movedWindwos() {
        Point tslPoint = rTextScrollPane.getLocationOnScreen();
        JDialog findDialog = DialogBuilder.getFindDialog();
        if (findDialog != null) {
            findDialog.setLocation(tslPoint.x + rTextScrollPane.getWidth() - findDialog.getWidth() - 11, tslPoint.y + 1);
        }
    }
}
