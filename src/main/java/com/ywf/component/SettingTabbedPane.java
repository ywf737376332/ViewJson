package com.ywf.component;

import javax.swing.*;

/**
 * 系统设置选项卡
 *
 * @Author YWF
 * @Date 2024/3/16 9:53
 */
public class SettingTabbedPane extends JTabbedPane {


    public SettingTabbedPane() {
        super();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setTabPlacement(SwingConstants.LEFT);
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

}
