package com.ywf.action;

import com.ywf.component.PopupMenuBuilder;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/10 14:18
 */
public class FrameMouseEventService extends MouseAdapter {

    private JFrame frame;

    public FrameMouseEventService(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        JPopupMenu contextMenu = PopupMenuBuilder.createPopupMenu();
        if (SwingUtilities.isRightMouseButton(e)) {
            contextMenu.show(frame, e.getX(), e.getY());
        }
    }
}
