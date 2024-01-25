package com.ywf.component;

import com.ywf.action.MenuEventService;
import com.ywf.action.QRCodeEventService;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.utils.PropertiesUtil;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/10 14:13
 */
public class TextAreaPopupMenuBuilder {
    private static PropertiesUtil systemProperties = PropertiesUtil.instance();
    private JPopupMenu contextMenu;
    private MouseListener popupListener;
    /**
     * 新建、关闭 | 撤销、重做 | 剪切、复制、粘贴、删除 | 全选、切换折叠 | 菜单栏、工具栏
     */
    private JMenuItem menuBarNewEdit, menuBarTabClose,menuBarUndo, menuBarRedo, menuBarCut, menuBarCopy, menuBarPaste, menuBarDelete, menuBarSelectAll, menuBarCollapse;
    private JCheckBoxMenuItem menuBarShowMenuBar, menuBarShowToolBar;

    volatile private static TextAreaPopupMenuBuilder instance = null;

    static {
    }

    private TextAreaPopupMenuBuilder() {
        contextMenu = new JPopupMenu();
        contextMenu.setPopupSize(150,310);
        menuBarNewEdit = new JMenuItem("新建");
        menuBarTabClose = new JMenuItem("关闭");
        menuBarUndo = new JMenuItem("撤销");
        menuBarRedo = new JMenuItem("重做");
        menuBarCut = new JMenuItem("剪切");
        menuBarCopy = new JMenuItem("复制");
        menuBarPaste = new JMenuItem("粘贴");
        menuBarDelete = new JMenuItem("删除");
        menuBarSelectAll = new JMenuItem("全选");
        menuBarCollapse = new JMenuItem("切换折叠");
        menuBarShowMenuBar = new JCheckBoxMenuItem("菜单栏");
        menuBarShowMenuBar.addActionListener(e -> menuActionPerformed());
        menuBarShowToolBar = new JCheckBoxMenuItem("工具栏");
        menuBarShowToolBar.addActionListener(e -> menuActionPerformed());

        contextMenu.add(menuBarNewEdit);
        contextMenu.add(menuBarTabClose);
        contextMenu.addSeparator();
        contextMenu.add(menuBarUndo);
        contextMenu.add(menuBarRedo);
        contextMenu.addSeparator();
        contextMenu.add(menuBarCut);
        contextMenu.add(menuBarCopy);
        contextMenu.add(menuBarPaste);
        contextMenu.add(menuBarDelete);
        contextMenu.addSeparator();
        contextMenu.add(menuBarSelectAll);
        contextMenu.add(menuBarCollapse);
        contextMenu.addSeparator();
        contextMenu.add(menuBarShowMenuBar);
        contextMenu.add(menuBarShowToolBar);
        popupListener = new PopupListener(contextMenu);
    }

    private void menuActionPerformed() {
        System.out.println("点击了右键菜单");
    }

    public static TextAreaPopupMenuBuilder getInstance() {
        if (instance == null) {
            synchronized (QRCodeEventService.class) {
                if (instance == null) {
                    instance = new TextAreaPopupMenuBuilder();
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

    public void setContextMenu(JPopupMenu contextMenu) {
        this.contextMenu = contextMenu;
    }

    public MouseListener getPopupListener() {
        return popupListener;
    }

    public void setPopupListener(MouseListener popupListener) {
        this.popupListener = popupListener;
    }

}
