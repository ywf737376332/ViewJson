package com.ywf.component;

import com.ywf.component.setting.FontsPanel;
import com.ywf.component.setting.ThemesPanel;
import com.ywf.framework.base.SvgIconFactory;

import javax.swing.*;

/**
 * 系统设置面板
 *
 * @Author YWF
 * @Date 2024/3/17 12:10
 */
public final class SystemSetupPanelBuilder {

    private static SettingTabbedPane tabs;

    private SystemSetupPanelBuilder() {
    }

    public static SettingTabbedPane createSystemSetupPanel() {
        return initSystemSetupPanel();
    }

    private static SettingTabbedPane initSystemSetupPanel() {
        if (tabs == null) {
            tabs = new SettingTabbedPane();
            tabs.setSize(600, 500);
            tabs.addTab("系统主题", SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.theme), new JScrollPane(new ThemesPanel()));

            JScrollPane scrollPane = new JScrollPane(new FontsPanel());
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            tabs.addTab("字体设置", SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.fontSet), scrollPane);
            tabs.addTab("系统设置", SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.systemSet), new JScrollPane(new JTree()));
            tabs.addTab("日志查看", SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.systemLog), new JScrollPane(new JTextArea()));
        }
        tabs.updateUI();
        return tabs;
    }

}
