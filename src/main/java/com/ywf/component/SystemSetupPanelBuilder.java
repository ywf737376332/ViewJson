package com.ywf.component;

import com.ywf.component.setting.FrameFontsPanel;
import com.ywf.component.setting.SettingOptions;
import com.ywf.component.setting.ThemesPanel;
import com.ywf.framework.base.SvgIconFactory;

import java.awt.*;

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
        tabs.setSize(700, 520);
        tabs.setMinimumSize(new Dimension(700, 520));
        tabs.addTab("系统主题", SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.theme), ScrollPaneBuilder.createScrollPane(new ThemesPanel()));
        tabs.addTab("界面字体", SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.frameFont), ScrollPaneBuilder.createScrollPane(new FrameFontsPanel()));
        //tabs.addTab("内容字体", SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.editorFont), ScrollPaneBuilder.createScrollPane(new EditorFontsPanel()));
        tabs.addTab("系统设置", SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.systemSet), ScrollPaneBuilder.createScrollPane(new SettingOptions()));
        //tabs.addTab("配置预览", SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.systemLog), ScrollPaneBuilder.createScrollPane(new DefaultSettingPanel()));
        return tabs;
    }

}
