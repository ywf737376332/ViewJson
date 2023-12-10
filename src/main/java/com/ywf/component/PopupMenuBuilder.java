package com.ywf.component;

import javax.swing.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/10 14:13
 */
public class PopupMenuBuilder {

    private static JPopupMenu contextMenu;
    public static JPopupMenu createPopupMenu(){
        contextMenu = new JPopupMenu();
        JCheckBoxMenuItem menuBar = new JCheckBoxMenuItem("菜单栏");
        JCheckBoxMenuItem toolBar = new JCheckBoxMenuItem("工具栏");
        contextMenu.add(menuBar);
        contextMenu.add(toolBar);
        return contextMenu;
    }

    public static JPopupMenu getContextMenu() {
        return contextMenu;
    }

    public static void setContextMenu(JPopupMenu contextMenu) {
        PopupMenuBuilder.contextMenu = contextMenu;
    }
}
