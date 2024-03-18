package com.ywf.component;

import com.ywf.component.setting.DefaultSettingPanel;
import com.ywf.component.setting.FontsPanel;
import com.ywf.component.setting.SettingOptions;
import com.ywf.component.setting.ThemesPanel;
import com.ywf.framework.base.SvgIconFactory;

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
        SettingTabbedPane tabs = new SettingTabbedPane();
        tabs.setSize(620, 500);
        tabs.addTab("系统主题", SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.theme), ScrollPaneBuilder.createScrollPane(new ThemesPanel()));
        tabs.addTab("字体设置", SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.fontSet), ScrollPaneBuilder.createScrollPane(new FontsPanel()));
        tabs.addTab("系统设置", SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.systemSet), ScrollPaneBuilder.createScrollPane(new SettingOptions()));
        tabs.addTab("配置预览", SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.systemLog), ScrollPaneBuilder.createScrollPane(new DefaultSettingPanel()));
        return tabs;
    }

}
