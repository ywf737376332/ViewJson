package com.ywf.component.setting;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.action.MenuEventService;
import com.ywf.action.ResourceBundleService;
import com.ywf.component.JSONButton;
import com.ywf.component.SwiftButton;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.DropcapLabel;
import com.ywf.framework.enums.FontEnum;
import com.ywf.framework.enums.LanguageEnum;
import com.ywf.framework.enums.PictureQualityEnum;
import com.ywf.framework.enums.TextConvertEnum;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import com.ywf.framework.ui.ColorRadioButton;
import com.ywf.framework.utils.WindowUtils;
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

    @Autowired
    public static ConfigurableApplicationContext applicationContext;

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
        SwiftButton showToolBarTextBtn = new SwiftButton();
        showToolBarTextBtn.setSelected(applicationContext.getShowToolBarText());
        showToolBarTextBtn.setStateListener(source -> MenuEventService.getInstance().showToolBarTextActionPerformed(WindowUtils.getFrame()));
        JPanel toolBarTextPanel = createBorderTitleAndDescPanelLayout("","工具栏名称", "是否显示工具栏名称",showToolBarTextBtn);
        JSONButton logButton = new JSONButton("查看日志");
        logButton.addActionListener(e -> MenuEventService.getInstance().opneLogFileActionPerformed());
        JPanel logViewPanel = createBorderTitleAndDescPanelLayout("","日志查看", "打开当前系统运行的日志文件",logButton);
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
        vBox.add(toolBarTextPanel);
        vBox.add(Box.createRigidArea(viewSize));
        vBox.add(logViewPanel);
        this.add(vBox);
    }

    /**
     * 语言设置面板
     *
     * @return
     */
    private JPanel createLanguageSettingPanel() {
        JPanel settingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        settingPanel.setBorder(BorderFactory.createTitledBorder("语言"));
        ButtonGroup languageButtonGroup = new ButtonGroup();
        for (LanguageEnum value : LanguageEnum.values()) {
            ColorRadioButton languageRadioButton = new ColorRadioButton(getMessage(value.getMessageKey()), value.getLanguage() + "_" + value.getCountry());
            languageButtonGroup.add(languageRadioButton);
            settingPanel.add(languageRadioButton);
        }
        MenuEventService.getInstance().setupLanguageActionPerformed(WindowUtils.getFrame(), languageButtonGroup);
        return settingPanel;
    }

    /**
     * 编辑器设置面板
     *
     * @return
     */
    private JPanel createEditorSettingPanel() {
        JPanel settingPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        settingPanel.setBorder(BorderFactory.createTitledBorder("编辑器"));
        SwiftButton isEditableBtn = new SwiftButton();
        isEditableBtn.setSelected(!applicationContext.getTextAreaEditState());
        isEditableBtn.setStateListener(source -> MenuEventService.getInstance().editSwitchActionPerformed());
        settingPanel.add(createTitleAndDescPanelLayout("锁定编辑：", "编辑器是否开启编辑功能", isEditableBtn));
        SwiftButton lineWrapBtn = new SwiftButton();
        lineWrapBtn.setSelected(applicationContext.getTextAreaBreakLineState());
        lineWrapBtn.setStateListener(source -> MenuEventService.getInstance().lineSetupActionPerformed());
        settingPanel.add(createTitleAndDescPanelLayout("自动换行：", "编辑器是否可自动换行", lineWrapBtn));
        SwiftButton showLineBtn = new SwiftButton();
        showLineBtn.setSelected(applicationContext.getTextAreaShowlineNumState());
        showLineBtn.setStateListener(source -> MenuEventService.getInstance().showLineNumActionPerformed());
        settingPanel.add(createTitleAndDescPanelLayout("显示行号：", "编辑器是否显示行号", showLineBtn));
        SwiftButton whitespaceBtn = new SwiftButton();
        whitespaceBtn.setSelected(applicationContext.getShowWhitespace());
        whitespaceBtn.setStateListener(e -> MenuEventService.getInstance().showWhitespaceActionPerformed());
        settingPanel.add(createTitleAndDescPanelLayout("显示空格符：", "编辑器中显示空格符，以帮助您识别尾随空格", whitespaceBtn));
        settingPanel.add(createTitleAndDescPanelLayout("显示分割线：", "调节编辑器中分割线位置", new ViewSlider(JSlider.HORIZONTAL, 0, 80, applicationContext.getMarginLine().getMarginWidth())));
        return settingPanel;
    }

    private JPanel createPictureQualitySettingPanel() {
        JPanel settingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        settingPanel.setBorder(BorderFactory.createTitledBorder("图片质量"));
        ButtonGroup pictureQualityButtonGroup = new ButtonGroup();
        for (PictureQualityEnum value : PictureQualityEnum.values()) {
            ColorRadioButton pictureQualityRadioButton = new ColorRadioButton(getMessage(value.getMessageKey()), value.getPictureQualityState());
            pictureQualityButtonGroup.add(pictureQualityRadioButton);
            settingPanel.add(pictureQualityRadioButton);
        }
        MenuEventService.getInstance().pictureQualityActionPerformed(pictureQualityButtonGroup);
        return settingPanel;
    }

    private JPanel createChineseConverSettingPanel() {
        JPanel rootPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        rootPanel.setBorder(BorderFactory.createTitledBorder("中文转码"));
        JPanel settingPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        settingPanel.setBorder(BorderBuilder.emptyBorder(10, 20, 10, 20));
        ButtonGroup buttonGroup = new ButtonGroup();
        for (TextConvertEnum value : TextConvertEnum.values()) {
            ColorRadioButton radioButton = new ColorRadioButton(getMessage(value.getMessageKey()), value.getConverType());
            buttonGroup.add(radioButton);
            settingPanel.add(radioButton);
        }
        MenuEventService.getInstance().chineseConverActionPerformed(buttonGroup);
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
    private JPanel createTitleAndDescPanelLayout(String title, String description, Component component) {
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderBuilder.emptyBorder(10, 20, 10, 20));
        main.setPreferredSize(new Dimension(400, 80));
        // 左侧两行两个元素的面板
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        FlatLabel titleLabel = new FlatLabel();
        titleLabel.setText("<html><span style=\"font-weight:400\">" + title + "</span></html>");
        left.add(titleLabel);
        left.add(Box.createVerticalStrut(10)); // 添加间距
        FlatLabel descLabel = new FlatLabel();
        descLabel.setText("<html><span style=\"font-size:10px;\">" + description + "</span></html>");
        left.add(descLabel);
        main.add(left, BorderLayout.WEST);
        // 右侧一行一个元素的面板
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.add(component);
        right.add(Box.createVerticalStrut(10));
        main.add(right, BorderLayout.EAST);
        return main;
    }

    private JPanel createBorderTitleAndDescPanelLayout(String borderTitle,String title, String description, Component component) {
        JPanel root = new JPanel();
        root.setBorder(BorderFactory.createTitledBorder(borderTitle));
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderBuilder.emptyBorder(10, 10, 10, 10));
        main.setPreferredSize(new Dimension(400, 70));
        // 左侧两行两个元素的面板
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        FlatLabel titleLabel = new FlatLabel();
        titleLabel.setText("<html><span style=\"float:right\">" + title + "</span></html>");
        left.add(titleLabel);
        left.add(Box.createVerticalStrut(10)); // 添加间距
        FlatLabel descLabel = new FlatLabel();
        descLabel.setText("<html><span style=\"font-size:10px\">" + description + "</span></html>");
        left.add(descLabel);
        main.add(left, BorderLayout.WEST);
        // 右侧一行一个元素的面板
        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.add(component);
        right.add(Box.createVerticalStrut(10));
        main.add(right, BorderLayout.EAST);
        root.add(main, BorderLayout.CENTER);
        return root;
    }

    private String getMessage(String keyRoot) {
        return resourceBundle.getString(keyRoot + ".Name");
    }


}


class ViewSlider extends JSlider {
    public ViewSlider(int orientation, int min, int max, int value) {
        super(orientation, min, max, value);
        setMajorTickSpacing(20); // 设置主刻度间隔
        setMinorTickSpacing(10); // 设置次刻度间隔
        setPaintTicks(true); // 显示刻度
        setPaintLabels(true); // 显示刻度标签
        setFont(new Font(FontEnum.Name.MicYaHei.getName(), Font.PLAIN, FontEnum.Size.mini.getSize()));
        setPreferredSize(new Dimension(200, 60));
        addChangeListener(e -> MenuEventService.getInstance().updateEditorMarginLineWidth(e));
    }
}