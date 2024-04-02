package com.ywf.component;

import com.ywf.framework.ui.ArrowButtonlessScrollBarUI;
import com.ywf.framework.ui.EditScrollPane;

import javax.swing.*;
import java.awt.*;

/**
 * 系统设置选项卡
 *
 * @Author YWF
 * @Date 2024/3/16 9:53
 */
public class SettingTabbedPane extends JTabbedPane {


    public SettingTabbedPane() {
        super(SwingConstants.LEFT);
        this.addChangeListener(e -> {
            Component[] components = getComponents();
            for (Component component : components) {
                if (component instanceof JScrollPane) {
                    EditScrollPane scrollPane = (EditScrollPane) component;
                    scrollPane.getVerticalScrollBar().setUI(new ArrowButtonlessScrollBarUI());
                    scrollPane.getHorizontalScrollBar().setUI(new ArrowButtonlessScrollBarUI());
                }
            }
        });
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }


}
