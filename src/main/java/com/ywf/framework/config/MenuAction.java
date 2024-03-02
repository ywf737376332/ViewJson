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

    public void setProperties(ResourceBundle msg, String keyRoot) {
        setName(msg.getString(keyRoot + ".Name"));
        if (msg.containsKey(keyRoot + ".Icon")) {
            setSmallIcon(IconUtils.getSmallSVGIcon(msg.getString(keyRoot + ".Icon")));
        }
    }

    public void setName(String name) {
        putValue(NAME, name);
    }

    public void setSmallIcon(Icon smallIcon) {
        putValue(SMALL_ICON, smallIcon);
    }


}
