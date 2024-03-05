package com.ywf.framework.config;

import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

/**
 * 菜单事件基类
 *
 * @Author YWF
 * @Date 2024/3/1 22:19
 */
public abstract class MenuAction extends AbstractAction {
    @Override
    public final void actionPerformed(ActionEvent e) {
        actionPerformedImpl(e);
    }

    public abstract void actionPerformedImpl(ActionEvent e);

    public void setProperties(ResourceBundle msg, String keyRoot, IconSize iconSize, boolean showText) {
        System.out.println("显示菜单名称设置状态：" + showText);
        if (showText) {
            setName(msg.getString(keyRoot + ".Name"));
        }
        if (msg.containsKey(keyRoot + ".Icon")) {
            setSmallIcon(IconUtils.getSVGIcon(msg.getString(keyRoot + ".Icon"), iconSize.getSize(), iconSize.getSize()));
        }
    }

    public void setProperties(ResourceBundle msg, String keyRoot) {
        setName(msg.getString(keyRoot + ".Name"));
        if (msg.containsKey(keyRoot + ".Icon")) {
            setSmallIcon(IconUtils.getSVGIcon(msg.getString(keyRoot + ".Icon"), IconSize.small.getSize(), IconSize.small.getSize()));
        }
    }

    public void setName(String name) {
        putValue(NAME, name);
    }

    public void setSmallIcon(Icon smallIcon) {
        putValue(SMALL_ICON, smallIcon);
    }


    public enum IconSize {
        small(12),
        medium(14),
        large(16);

        private int size;

        IconSize(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

}
