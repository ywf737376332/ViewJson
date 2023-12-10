package com.ywf.component;

import com.ywf.action.MenuEventService;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/10 14:13
 */
public class PopupMenuBuilder extends JFrame {

    private static JPopupMenu contextMenu;

    private JCheckBoxMenuItem menuBarShowState, toolBarShowState;

    public static JPopupMenu createPopupMenu() {
        contextMenu = new JPopupMenu();
        JCheckBoxMenuItem menuBarShowState = new JCheckBoxMenuItem("菜单栏");
        menuBarShowState.setSelected(true);
        menuBarShowState.addActionListener(e -> MenuEventService.getInstance().showMenuBarActionPerformed());
        PopupCheckBoxMenuItem toolBarShowState = new PopupCheckBoxMenuItem("工具栏");
        toolBarShowState.addActionListener(e -> MenuEventService.getInstance().showToolBarActionPerformed());
        contextMenu.add(menuBarShowState);
        contextMenu.add(toolBarShowState);
        return contextMenu;
    }


    public class PopupListener extends MouseAdapter {
        JPopupMenu popupMenu;

        PopupListener(JPopupMenu popupMenu) {
            this.popupMenu = popupMenu;
        }

        public void mousePressed(MouseEvent e) {
            showPopupMenu(e);
        }

        public void mouseReleased(MouseEvent e) {
            showPopupMenu(e);
        }

        private void showPopupMenu(MouseEvent e) {
            if (e.isPopupTrigger()) {
//如果当前事件与鼠标事件相关，则弹出菜单
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    public static JPopupMenu getContextMenu() {
        return contextMenu;
    }

    public static void setContextMenu(JPopupMenu contextMenu) {
        PopupMenuBuilder.contextMenu = contextMenu;
    }

    public JCheckBoxMenuItem getMenuBarShowState() {
        return menuBarShowState;
    }

    public void setMenuBarShowState(JCheckBoxMenuItem menuBarShowState) {
        this.menuBarShowState = menuBarShowState;
    }

    public JCheckBoxMenuItem getToolBarShowState() {
        return toolBarShowState;
    }

    public void setToolBarShowState(JCheckBoxMenuItem toolBarShowState) {
        this.toolBarShowState = toolBarShowState;
    }
}
