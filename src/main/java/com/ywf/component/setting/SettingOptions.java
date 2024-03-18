package com.ywf.component.setting;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.action.ResourceBundleService;
import com.ywf.component.toolToast.JSONButton;
import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.ThemeColor;
import com.ywf.framework.enums.LanguageEnum;
import com.ywf.framework.enums.PictureQualityEnum;
import com.ywf.framework.enums.TextConvertEnum;
import com.ywf.framework.init.SysConfigInit;
import com.ywf.framework.ioc.ApplicationContext;
import com.ywf.framework.ui.ColorRadioButton;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * 系统设置面板
 *
 * @Author YWF
 * @Date 2024/3/18 16:21
 */
public class SettingOptions extends JPanel {

    private ResourceBundle resourceBundle;

    public SettingOptions() {
        super();
        resourceBundle = ResourceBundleService.getInstance().getResourceBundle();
        init();
    }

    private void init() {
        // 创建一个垂直箱容器
        Box vBox = Box.createVerticalBox();
        //设置语言
        JPanel languageSetting = createLanguageSettingPanel();
        JPanel editorSetting = createEditorSettingPanel();
        JPanel pictureQualitySetting = createPictureQualitySettingPanel();
        JPanel chineseConverSetting = createChineseConverSettingPanel();
        JPanel logViewPanel = createLogViewPanel();
        // 创建一个 垂直方向胶状 的不可见组件，用于撑满垂直方向剩余的空间（如果有多个该组件，则平分剩余空间）
        vBox.add(languageSetting);
        vBox.add(Box.createRigidArea(new Dimension(470,20)));
        vBox.add(editorSetting);
        vBox.add(Box.createRigidArea(new Dimension(470,20)));
        vBox.add(pictureQualitySetting);
        vBox.add(Box.createRigidArea(new Dimension(470,20)));
        vBox.add(chineseConverSetting);
        vBox.add(Box.createRigidArea(new Dimension(470,20)));
        vBox.add(logViewPanel);
        this.add(vBox);
    }

    private JPanel createLanguageSettingPanel() {
        JPanel settingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        settingPanel.setBorder(BorderFactory.createTitledBorder("语言设置"));
        ButtonGroup languageButtonGroup = new ButtonGroup();
        for (LanguageEnum value : LanguageEnum.values()) {
            ColorRadioButton languageRadioButton = new ColorRadioButton(getMessage(value.getMessageKey()), value.getLanguage() + "_" + value.getCountry());
            languageButtonGroup.add(languageRadioButton);
            settingPanel.add(languageRadioButton);
        }
        this.add(settingPanel);
        return settingPanel;
    }

    private JPanel createEditorSettingPanel() {
        JPanel settingPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        settingPanel.setBorder(BorderFactory.createTitledBorder("编辑器设置"));

        JPanel editEnablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        editEnablePanel.setBorder(BorderBuilder.bottomBorder(1, ThemeColor.watermarkColor));
        FlatLabel editEnableLabel = new FlatLabel();
        editEnableLabel.setText("是否可编辑：");
        ColorRadioButton openRadioButton = new ColorRadioButton("是");
        ColorRadioButton closeRadioButton = new ColorRadioButton("否");
        ButtonGroup editEnableButtonGroup = new ButtonGroup();
        editEnableButtonGroup.add(openRadioButton);
        editEnableButtonGroup.add(closeRadioButton);
        editEnablePanel.add(editEnableLabel);
        editEnablePanel.add(openRadioButton);
        editEnablePanel.add(closeRadioButton);

        JPanel lineFeedSetupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        lineFeedSetupPanel.setBorder(BorderBuilder.bottomBorder(1, ThemeColor.watermarkColor));
        FlatLabel lineFeedSetupLabel = new FlatLabel();
        lineFeedSetupLabel.setText("自动换行：");
        ColorRadioButton yesLineFeedRadioButton = new ColorRadioButton("是");
        ColorRadioButton noLineFeedRadioButton = new ColorRadioButton("否");
        ButtonGroup lineFeedSetupButtonGroup = new ButtonGroup();
        lineFeedSetupButtonGroup.add(yesLineFeedRadioButton);
        lineFeedSetupButtonGroup.add(noLineFeedRadioButton);
        lineFeedSetupPanel.add(lineFeedSetupLabel);
        lineFeedSetupPanel.add(yesLineFeedRadioButton);
        lineFeedSetupPanel.add(noLineFeedRadioButton);

        JPanel lineNumSetupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        FlatLabel lineNumSetupLabel = new FlatLabel();
        lineNumSetupLabel.setText("显示行号：");
        ColorRadioButton yesLineNumRadioButton = new ColorRadioButton("是");
        ColorRadioButton noLineNumRadioButton = new ColorRadioButton("否");
        ButtonGroup lineNumSetupButtonGroup = new ButtonGroup();
        lineNumSetupButtonGroup.add(yesLineNumRadioButton);
        lineNumSetupButtonGroup.add(noLineNumRadioButton);
        lineNumSetupPanel.add(lineNumSetupLabel);
        lineNumSetupPanel.add(yesLineNumRadioButton);
        lineNumSetupPanel.add(noLineNumRadioButton);

        settingPanel.add(editEnablePanel);
        settingPanel.add(lineFeedSetupPanel);
        settingPanel.add(lineNumSetupPanel);
        return settingPanel;
    }

    private JPanel createPictureQualitySettingPanel() {
        JPanel settingPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        settingPanel.setBorder(BorderFactory.createTitledBorder("图片质量"));
        ButtonGroup pictureQualityButtonGroup = new ButtonGroup();
        for (PictureQualityEnum value : PictureQualityEnum.values()) {
            ColorRadioButton pictureQualityRadioButton = new ColorRadioButton(getMessage(value.getMessageKey()), value.getMessageKey() + "_" + value.getPictureQualityState());
            pictureQualityButtonGroup.add(pictureQualityRadioButton);
            settingPanel.add(pictureQualityRadioButton);
        }
        return settingPanel;
    }

    private JPanel createChineseConverSettingPanel() {
        JPanel settingPanel = new JPanel(new GridLayout(3, 1, 10, 5));
        settingPanel.setBorder(BorderFactory.createTitledBorder("中文转码"));
        ButtonGroup buttonGroup = new ButtonGroup();
        for (TextConvertEnum value : TextConvertEnum.values()) {
            ColorRadioButton radioButton = new ColorRadioButton(getMessage(value.getMessageKey()), value.getMessageKey() + "_" + value.getConverType());
            buttonGroup.add(radioButton);
            settingPanel.add(radioButton);
        }
        return settingPanel;
    }

    private JPanel createLogViewPanel() {
        JPanel settingPanel = new JPanel(new GridLayout(1, 1, 10, 5));
        settingPanel.setBorder(BorderFactory.createTitledBorder("查看日志"));
        JSONButton logViewButton = new JSONButton("查看日志");
        logViewButton.addActionListener(e -> {
            File file = new File(ApplicationContext.SYSTEM_LOG_FILE_PATH);
            if (file.exists()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException ex) {
                    throw new RuntimeException("日志文件打开失败" + ex.getMessage());
                }
            } else {
                throw new RuntimeException("日志文件未找到，请检查");
            }
        });
        settingPanel.add(logViewButton);
        return settingPanel;
    }

    private String getMessage(String keyRoot) {
        return resourceBundle.getString(keyRoot + ".Name");
    }
}
