package com.ywf.component;

import com.ywf.action.MenuEventService;
import com.ywf.action.QRCodeEventService;
import com.ywf.action.ResourceBundleService;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.ioc.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ResourceBundle;

/**
 * 软件界面右键菜单
 *
 * @Author YWF
 * @Date 2023/12/10 14:13
 */
public class PopupMenuBuilder {
    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    private static ResourceBundle resourceBundle;
    private JPopupMenu contextMenu;
    private MouseListener popupListener;
    private JCheckBoxMenuItem menuBarShowState, toolBarShowState;

    volatile private static PopupMenuBuilder instance = null;

    private PopupMenuBuilder() {
        resourceBundle = ResourceBundleService.getInstance().getResourceBundle();
        initPopupMenu();
    }

    private void initPopupMenu() {
        contextMenu = new JPopupMenu();
        menuBarShowState = new JCheckBoxMenuItem(getMessage("MenuItem.ShowMenuBar"));
        menuBarShowState.setSelected(applicationContext.getShowMenuBarState());
        menuBarShowState.addActionListener(e -> MenuEventService.getInstance().showMenuBarActionPerformed());
        toolBarShowState = new JCheckBoxMenuItem(getMessage("MenuItem.ShowToolBar"));
        toolBarShowState.setSelected(applicationContext.getShowToolBarState());
        toolBarShowState.addActionListener(e -> MenuEventService.getInstance().showToolBarActionPerformed());
        contextMenu.add(menuBarShowState);
        contextMenu.addSeparator();
        contextMenu.add(toolBarShowState);
        popupListener = new PopupListener(contextMenu);
    }

    public static PopupMenuBuilder getInstance() {
        if (instance == null) {
            synchronized (QRCodeEventService.class) {
                if (instance == null) {
                    instance = new PopupMenuBuilder();
                }
            }
        }
        return instance;
    }

    //添加内部类，其扩展了MouseAdapter类，用来处理鼠标事件
    class PopupListener extends MouseAdapter {
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
            if (SwingUtilities.isRightMouseButton(e)) {
                //如果当前事件与鼠标事件相关，则弹出菜单
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    public JPopupMenu getContextMenu() {
        return contextMenu;
    }

    public MouseListener getPopupListener() {
        return popupListener;
    }

    public JCheckBoxMenuItem getMenuBarShowState() {
        return menuBarShowState;
    }

    public JCheckBoxMenuItem getToolBarShowState() {
        return toolBarShowState;
    }

    private static String getMessage(String keyRoot) {
        return resourceBundle.getString(keyRoot + ".Name");
    }

}
