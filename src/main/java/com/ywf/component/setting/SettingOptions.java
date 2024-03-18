package com.ywf.component.setting;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.action.ResourceBundleService;
import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.ThemeColor;
import com.ywf.framework.enums.LanguageEnum;
import com.ywf.framework.ui.ColorRadioButton;

import javax.swing.*;
import java.awt.*;
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
        //setBackground(Color.GRAY);
        //setPreferredSize(new Dimension(480, 400));
        setLayout(new BorderLayout(10, 10));
        init();
    }

    private void init() {
        //设置语言
        JPanel languageSetting = createLanguageSettingPanel();
        JPanel editorSetting = createEditorSettingPanel();


        this.add(languageSetting, BorderLayout.NORTH);
        this.add(editorSetting, BorderLayout.CENTER);
    }

    private JPanel createLanguageSettingPanel() {
        JPanel settingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
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
        // 语言设置
        JPanel settingPanel = new JPanel(new GridLayout(10, 1, 10, 20));
        settingPanel.setBorder(BorderFactory.createTitledBorder("编辑器设置"));

        JPanel editEnablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        editEnablePanel.setBorder(BorderBuilder.bottomBorder(1, ThemeColor.watermarkColor));
        FlatLabel editEnableLabel = new FlatLabel();
        editEnableLabel.setText("可编辑状态：");
        ColorRadioButton openRadioButton = new ColorRadioButton("打开");
        ColorRadioButton closeRadioButton = new ColorRadioButton("关闭");
        ButtonGroup editEnableButtonGroup = new ButtonGroup();
        editEnableButtonGroup.add(openRadioButton);
        editEnableButtonGroup.add(closeRadioButton);
        editEnablePanel.add(editEnableLabel);
        editEnablePanel.add(openRadioButton);
        editEnablePanel.add(closeRadioButton);

        JPanel lineSetupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        lineSetupPanel.setBorder(BorderBuilder.bottomBorder(1, ThemeColor.watermarkColor));
        FlatLabel lineSetupLabel = new FlatLabel();
        lineSetupLabel.setText("是否自动换行：");
        ColorRadioButton yesRadioButton = new ColorRadioButton("是");
        ColorRadioButton noRadioButton = new ColorRadioButton("否");
        ButtonGroup lineSetupButtonGroup = new ButtonGroup();
        lineSetupButtonGroup.add(yesRadioButton);
        lineSetupButtonGroup.add(noRadioButton);
        lineSetupPanel.add(lineSetupLabel);
        lineSetupPanel.add(yesRadioButton);
        lineSetupPanel.add(noRadioButton);


        settingPanel.add(editEnablePanel);
        settingPanel.add(lineSetupPanel);
        return settingPanel;
    }

    private String getMessage(String keyRoot) {
        return resourceBundle.getString(keyRoot + ".Name");
    }
}
