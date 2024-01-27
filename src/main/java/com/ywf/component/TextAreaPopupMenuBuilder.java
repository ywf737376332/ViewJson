package com.ywf.component;

import com.ywf.action.QRCodeEventService;
import com.ywf.component.demo2.DemoTabble002;
import com.ywf.framework.utils.PropertiesUtil;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.awt.*;
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
    private JPopupMenu contextMenu;
    private MouseListener popupListener;

    private RSyntaxTextArea rSyntaxTextArea;
    /**
     * 新建、关闭 | 撤销、重做 | 剪切、复制、粘贴、删除 | 全选、切换折叠 | 菜单栏、工具栏
     */
    private JMenuItem menuBarNewEdit, menuBarTabClose,menuBarUndo, menuBarRedo, menuBarCut, menuBarCopy, menuBarPaste, menuBarDelete, menuBarSelectAll, menuBarCollapse;

    volatile private static TextAreaPopupMenuBuilder instance = null;

    static {
    }

    private TextAreaPopupMenuBuilder() {
        contextMenu = new JPopupMenu();
        contextMenu.setPopupSize(150,310);
        menuBarNewEdit = new JMenuItem("新建");
        menuBarNewEdit.addActionListener(e -> DemoTabble002.createNewTabActionPerformed());
        menuBarTabClose = new JMenuItem("关闭");
        menuBarTabClose.addActionListener(e -> {
            DemoTabble002.closeAbleTabbedSplitPane(rSyntaxTextArea);
            System.out.println("执行："+e);
        });
        menuBarUndo = new JMenuItem("撤销");
        menuBarRedo = new JMenuItem("重做");
        menuBarCut = new JMenuItem("剪切");
        menuBarCopy = new JMenuItem("复制");
        menuBarPaste = new JMenuItem("粘贴");
        menuBarDelete = new JMenuItem("删除");
        menuBarSelectAll = new JMenuItem("全选");
        menuBarCollapse = new JMenuItem("切换折叠");

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

        @Override
        public void mousePressed(MouseEvent e) {
            showPopupMenu(e);
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            showPopupMenu(e);
        }

        private void showPopupMenu(MouseEvent e) {
            System.out.println("====================");
            if (SwingUtilities.isRightMouseButton(e)) {
                //如果当前事件与鼠标事件相关，则弹出菜单
                Component comonent = e.getComponent();
                if (comonent != null){
                    if (comonent instanceof RSyntaxTextArea) {
                        rSyntaxTextArea = (RSyntaxTextArea) comonent;
                        popupMenu.show(comonent, e.getX(), e.getY());
                    }
                }
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

    public JMenuItem getMenuBarTabClose() {
        return menuBarTabClose;
    }

    public void setMenuBarTabClose(JMenuItem menuBarTabClose) {
        this.menuBarTabClose = menuBarTabClose;
    }
}
