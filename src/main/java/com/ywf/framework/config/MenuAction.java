package com.ywf.framework.config;

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
        if (showText) {
            setName(msg.getString(keyRoot + ".Name"));
        }
        if (msg.containsKey(keyRoot + ".Icon")) {
            setSmallIcon(SvgIconFactory.icon(msg.getString(keyRoot + ".Icon"), iconSize.getSize(), iconSize.getSize()));
        }
        setToolTipText(msg.getString(keyRoot + ".Name"));
        // 鼠标悬停提示 MenuItem.Format.Tip=\u683c\u5f0f\u5316
        //if (msg.containsKey(keyRoot + ".Tip")) {
        //    setToolTipText(msg.getString(keyRoot + ".Tip"));
        //}
    }

    public void setProperties(ResourceBundle msg, String keyRoot) {
        setName(msg.getString(keyRoot + ".Name"));
        if (msg.containsKey(keyRoot + ".Icon")) {
            setSmallIcon(SvgIconFactory.smallIcon(msg.getString(keyRoot + ".Icon")));
        }
    }

    private void setName(String name) {
        putValue(NAME, name);
    }

    private void setSmallIcon(Icon smallIcon) {
        putValue(SMALL_ICON, smallIcon);
    }

    private void setToolTipText(String msgTip) {
        putValue(SHORT_DESCRIPTION, msgTip);
    }

    public enum IconSize {
        mini(10),
        small(12),
        medium(14),
        large(16),
        tooLarge(16);

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
