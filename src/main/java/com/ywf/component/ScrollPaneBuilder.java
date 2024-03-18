package com.ywf.component;

import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.ThemeColor;
import com.ywf.framework.ui.EditScrollPane;

import javax.swing.*;
import java.awt.*;

/**
 * 滚动条创建
 *
 * @Author YWF
 * @Date 2024/3/18 15:04
 */
public final class ScrollPaneBuilder {

    /**
     * 创建可设置标题和大小的纵向滚动条的滚动面板
     *
     * @param selectList
     * @param title
     * @param size
     * @return
     */
    public static JScrollPane createScrollPane(JList selectList, String title, Dimension size) {
        JScrollPane scrollPane = createScrollPane(selectList);
        scrollPane.setPreferredSize(size);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderBuilder.border(1, ThemeColor.themeColor), title));
        return scrollPane;
    }

    /**
     * 创建带纵向滚动条的滚动面板
     *
     * @param component
     * @return
     */
    public static JScrollPane createScrollPane(Component component) {
        JScrollPane scrollPane = new EditScrollPane(component);
        scrollPane.setFocusable(false);
        scrollPane.setBorder(BorderBuilder.emptyBorder(5));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

}
