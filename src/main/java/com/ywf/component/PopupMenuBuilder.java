package com.ywf.component;

import com.ywf.action.QRCodeEventService;
import com.ywf.action.ResourceBundleService;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.config.MenuAction;
import com.ywf.framework.config.MenuBarKit;
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
    private MenuAction showToolBarAction, showMenuBarAction;
    private JCheckBoxMenuItem showToolBarMenuItem, showMenuBarMenuItem;

    volatile private static PopupMenuBuilder instance = null;

    private PopupMenuBuilder() {
        init();
    }

    private void init() {
        resourceBundle = ResourceBundleService.getInstance().getResourceBundle();
        createMenuActions();
        createMenus();
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

    private void createMenus() {
        contextMenu = new JPopupMenu();
        contextMenu.add(showToolBarMenuItem = createCheckBoxMenu(showToolBarAction));
        showToolBarMenuItem.setSelected(applicationContext.getShowToolBarState());
        contextMenu.addSeparator();
        contextMenu.add(showMenuBarMenuItem = createCheckBoxMenu(showMenuBarAction));
        showMenuBarMenuItem.setSelected(applicationContext.getShowMenuBarState());
        popupListener = new PopupListener(contextMenu);
    }

    /**
     * 菜单事件初始化
     */
    private void createMenuActions() {
        ResourceBundle msg = resourceBundle;
        showMenuBarAction = new MenuBarKit.ShowMenuBarAction();
        showMenuBarAction.setProperties(msg, "MenuItem.ShowMenuBar");
        showToolBarAction = new MenuBarKit.ShowToolBarAction();
        showToolBarAction.setProperties(msg, "MenuItem.ShowToolBar");
    }

    protected JCheckBoxMenuItem createCheckBoxMenu(Action a) {
        return new JCheckBoxMenuItem(a);
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

    public JCheckBoxMenuItem getShowToolBarMenuItem() {
        return showToolBarMenuItem;
    }

    public JCheckBoxMenuItem getShowMenuBarMenuItem() {
        return showMenuBarMenuItem;
    }
}
