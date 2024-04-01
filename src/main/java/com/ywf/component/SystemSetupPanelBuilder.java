package com.ywf.component;

import com.ywf.component.setting.FrameFontsPanel;
import com.ywf.component.setting.SettingOptions;
import com.ywf.component.setting.ThemesPanel;
import com.ywf.component.setting.ToolBarPanel;
import com.ywf.framework.base.SvgIconFactory;
import com.ywf.framework.constant.MessageConstant;

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
        if (tabs != null) {
            return tabs;
        }
        tabs = new SettingTabbedPane();
        tabs.setSize(680, 520);
        tabs.setMinimumSize(new Dimension(680, 520));
        tabs.addTab(MessageConstant.TAB_TITLE_SYSTEM_THEMES, SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.theme), ScrollPaneBuilder.createScrollPane(new ThemesPanel()));
        tabs.addTab(MessageConstant.TAB_TITLE_SETTINGS_FONT, SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.frameFont), ScrollPaneBuilder.createScrollPane(new FrameFontsPanel()));
        //tabs.addTab("内容字体", SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.editorFont), ScrollPaneBuilder.createScrollPane(new EditorFontsPanel()));
        tabs.addTab(MessageConstant.TAB_TITLE_SYSTEM_SETTINGS, SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.systemSet), ScrollPaneBuilder.createScrollPane(SettingOptions.getInstance()));
        //tabs.addTab("配置预览", SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.systemLog), ScrollPaneBuilder.createScrollPane(new DefaultSettingPanel()));
        //tabs.addTab("工具设置", SvgIconFactory.mediumIcon(SvgIconFactory.SystemIcon.systemSet), ScrollPaneBuilder.createScrollPane(new ToolBarPanel()));
        return tabs;
    }

}
