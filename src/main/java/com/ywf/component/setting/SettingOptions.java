package com.ywf.component.setting;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.action.MenuEventService;
import com.ywf.action.ResourceBundleService;
import com.ywf.component.JSONButton;
import com.ywf.component.SwiftButton;
import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.DropcapLabel;
import com.ywf.framework.enums.LanguageEnum;
import com.ywf.framework.enums.PictureQualityEnum;
import com.ywf.framework.enums.TextConvertEnum;
import com.ywf.framework.ui.ColorRadioButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final static Logger logger = LoggerFactory.getLogger(SettingOptions.class);

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
        JPanel logViewPanel = createLogViewPanel("日志查看", "打开当前系统运行的日志文件");
        // 创建一个 垂直方向胶状 的不可见组件，用于撑满垂直方向剩余的空间（如果有多个该组件，则平分剩余空间）
        Dimension viewSize = new Dimension(450, 20);
        vBox.add(languageSetting);
        vBox.add(Box.createRigidArea(viewSize));
        vBox.add(editorSetting);
        vBox.add(Box.createRigidArea(viewSize));
        vBox.add(pictureQualitySetting);
        vBox.add(Box.createRigidArea(viewSize));
        vBox.add(chineseConverSetting);
        vBox.add(Box.createRigidArea(viewSize));
        vBox.add(logViewPanel);
        this.add(vBox);
    }

    private JPanel createLanguageSettingPanel() {
        JPanel settingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        settingPanel.setBorder(BorderFactory.createTitledBorder("语言"));
        ButtonGroup languageButtonGroup = new ButtonGroup();
        for (LanguageEnum value : LanguageEnum.values()) {
            ColorRadioButton languageRadioButton = new ColorRadioButton(getMessage(value.getMessageKey()), value.getLanguage() + "_" + value.getCountry());
            languageButtonGroup.add(languageRadioButton);
            settingPanel.add(languageRadioButton);
        }
        return settingPanel;
    }

    private JPanel createEditorSettingPanel() {
        JPanel settingPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        settingPanel.setBorder(BorderFactory.createTitledBorder("编辑器"));
        settingPanel.add(createTitleAndDescPanelLayout("是否可编辑：", "当前窗口打开的所有编辑器是否可进行编辑"));
        settingPanel.add(createTitleAndDescPanelLayout("自动换行：", "当前窗口打开的所有编辑器是否可自动换行"));
        settingPanel.add(createTitleAndDescPanelLayout("显示行号：", "当前窗口打开的所有编辑器是否显示行号"));
        return settingPanel;
    }

    private JPanel createPictureQualitySettingPanel() {
        JPanel settingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        settingPanel.setBorder(BorderFactory.createTitledBorder("图片质量"));
        ButtonGroup pictureQualityButtonGroup = new ButtonGroup();
        for (PictureQualityEnum value : PictureQualityEnum.values()) {
            ColorRadioButton pictureQualityRadioButton = new ColorRadioButton(getMessage(value.getMessageKey()), value.getMessageKey() + "_" + value.getPictureQualityState());
            pictureQualityButtonGroup.add(pictureQualityRadioButton);
            settingPanel.add(pictureQualityRadioButton);
        }
        settingPanel.add(new JRadioButton("hhhh"));
        return settingPanel;
    }

    private JPanel createChineseConverSettingPanel() {
        JPanel rootPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        rootPanel.setBorder(BorderFactory.createTitledBorder("中文转码"));
        JPanel settingPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        settingPanel.setBorder(BorderBuilder.emptyBorder(10, 20, 10, 20));
        ButtonGroup buttonGroup = new ButtonGroup();
        for (TextConvertEnum value : TextConvertEnum.values()) {
            ColorRadioButton radioButton = new ColorRadioButton(getMessage(value.getMessageKey()), value.getMessageKey() + "_" + value.getConverType());
            buttonGroup.add(radioButton);
            settingPanel.add(radioButton);
        }
        JPanel descriptPanel = new JPanel(new GridLayout(1, 1, 5, 5));
        descriptPanel.setBorder(BorderBuilder.emptyBorder(10, 20, 10, 20));
        String text = "当前的文本编辑器，在点击格式化按钮的时候，是否开启中文和Unicode互转的功能。";
        DropcapLabel descriptLabel = new DropcapLabel(text);
        descriptLabel.setPreferredSize(new Dimension(80, 40));
        descriptPanel.add(descriptLabel);
        rootPanel.add(descriptPanel);
        rootPanel.add(settingPanel);
        return rootPanel;
    }


    /**
     * 系统设置组件模板
     * 左边两行文字
     * 右边上下居中一个swift按钮
     *
     * @param title
     * @param description
     * @return
     */
    private JPanel createTitleAndDescPanelLayout(String title, String description) {
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderBuilder.emptyBorder(10, 20, 10, 20));
        main.setPreferredSize(new Dimension(400, 80));
        // 左侧两行两个元素的面板
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        FlatLabel titleLabel = new FlatLabel();
        titleLabel.setText("<html><span style=\"float:right\"><b>" + title + "</b></span></html>");
        left.add(titleLabel);
        left.add(Box.createVerticalStrut(10)); // 添加间距
        FlatLabel descLabel = new FlatLabel();
        descLabel.setText("<html><span style=\"font-size:10px\">" + description + "</span></html>");
        left.add(descLabel);
        main.add(left, BorderLayout.WEST);
        // 右侧一行一个元素的面板
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        SwiftButton swiftButton = new SwiftButton();
        right.add(swiftButton);
        right.add(Box.createVerticalStrut(10));
        main.add(right, BorderLayout.EAST);
        return main;
    }

    private JPanel createLogViewPanel(String title, String description) {
        JPanel root = new JPanel();
        root.setBorder(BorderFactory.createTitledBorder("日志"));
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderBuilder.emptyBorder(10, 20, 10, 20));
        main.setPreferredSize(new Dimension(400, 70));
        // 左侧两行两个元素的面板
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        FlatLabel titleLabel = new FlatLabel();
        titleLabel.setText("<html><span style=\"float:right\"><b>" + title + "</b></span></html>");
        left.add(titleLabel);
        left.add(Box.createVerticalStrut(10)); // 添加间距
        FlatLabel descLabel = new FlatLabel();
        descLabel.setText("<html><span style=\"font-size:10px\">" + description + "</span></html>");
        left.add(descLabel);
        main.add(left, BorderLayout.WEST);
        // 右侧一行一个元素的面板
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        JSONButton logButton = new JSONButton("查看日志");
        logButton.addActionListener(e -> MenuEventService.getInstance().opneLogFileActionPerformed());
        right.add(logButton);
        right.add(Box.createVerticalStrut(10));
        main.add(right, BorderLayout.EAST);
        root.add(main, BorderLayout.CENTER);
        return root;
    }


    private String getMessage(String keyRoot) {
        return resourceBundle.getString(keyRoot + ".Name");
    }


}
