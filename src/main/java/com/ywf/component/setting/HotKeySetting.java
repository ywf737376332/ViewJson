package com.ywf.component.setting;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.action.ResourceBundleService;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.ThemeColor;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;

/**
 * 快捷键面板
 *
 * @Author YWF
 * @Date 2024/3/18 16:21
 */
public class HotKeySetting extends JPanel {

    private final static Logger logger = LoggerFactory.getLogger(HotKeySetting.class);

    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    private ResourceBundle resourceBundle;

    private JTextField formatHotkey, compressHotkey, lookUpHotkey, copyContent, copyPicture, shareQRcode, clearContent;

    volatile private static HotKeySetting instance = null;

    public HotKeySetting() {
        super();
        resourceBundle = ResourceBundleService.getInstance().getResourceBundle();
        init();
    }

    public static HotKeySetting getInstance() {
        if (instance == null) {
            synchronized (SettingOptions.class) {
                if (instance == null) {
                    instance = new HotKeySetting();
                }
            }
        }
        return instance;
    }

    private void init() {
        // 创建一个垂直箱容器
        Box vBox = Box.createVerticalBox();
        JPanel hotKeyPanel_A = createMainLayoutPanel_A(getMessage("System.Hotkey.ContentPanel"));
        JPanel hotKeyPanel_B = createMainLayoutPanel_B(getMessage("System.Hotkey.FilePanel"));
        JPanel hotKeyPanel_C = createMainLayoutPanel_C(getMessage("System.Hotkey.EditorPanel"));
        JPanel hotKeyPanel_D = createMainLayoutPanel_D(getMessage("System.Hotkey.ToolSetting"));
        // 创建一个 垂直方向胶状 的不可见组件，用于撑满垂直方向剩余的空间（如果有多个该组件，则平分剩余空间）
        Dimension viewSize = new Dimension(450, 20);
        vBox.add(hotKeyPanel_A);
        vBox.add(Box.createRigidArea(viewSize));
        vBox.add(hotKeyPanel_B);
        vBox.add(Box.createRigidArea(viewSize));
        vBox.add(hotKeyPanel_C);
        vBox.add(Box.createRigidArea(viewSize));
        vBox.add(hotKeyPanel_D);
        this.add(vBox);
    }

    /**
     * 编辑器设置面板
     *
     * @return
     */
    private JPanel createMainLayoutPanel_A(String title) {
        JPanel settingPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        settingPanel.setBorder(BorderFactory.createTitledBorder(title));
        formatHotkey = new HotKeyLabel("CTRL + B");
        settingPanel.add(createTitleAndDescPanelLayout(getMessage("System.Hotkey.Format"), getMessage("System.Hotkey.Format.Descript"), formatHotkey));
        compressHotkey = new HotKeyLabel("CTRL + L");
        settingPanel.add(createTitleAndDescPanelLayout(getMessage("System.Hotkey.Compress"), getMessage("System.Hotkey.Compress.Descript"), compressHotkey));
        clearContent = new HotKeyLabel("CTRL + SHIFT + D");
        settingPanel.add(createTitleAndDescPanelLayout(getMessage("System.Hotkey.ClearContent"), getMessage("System.Hotkey.ClearContent.Descript"), clearContent));
        return settingPanel;
    }

    private JPanel createMainLayoutPanel_B(String title) {
        JPanel settingPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        settingPanel.setBorder(BorderFactory.createTitledBorder(title));
        copyContent = new HotKeyLabel("CTRL + SHIFT + C");
        settingPanel.add(createTitleAndDescPanelLayout(getMessage("System.Hotkey.CopyContent"), getMessage("System.Hotkey.CopyContent.Descript"), copyContent));
        copyPicture = new HotKeyLabel("CTRL + SHIFT + P");
        settingPanel.add(createTitleAndDescPanelLayout(getMessage("System.Hotkey.CopyPicture"), getMessage("System.Hotkey.CopyPicture.Descript"), copyPicture));
        shareQRcode = new HotKeyLabel("CTRL + SHIFT + Q");
        settingPanel.add(createTitleAndDescPanelLayout(getMessage("System.Hotkey.ShareQRcode"), getMessage("System.Hotkey.ShareQRcode.Descript"), shareQRcode));
        return settingPanel;
    }

    private JPanel createMainLayoutPanel_C(String title) {
        JPanel settingPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        settingPanel.setBorder(BorderFactory.createTitledBorder(title));
        lookUpHotkey = new HotKeyLabel("CTRL + F");
        settingPanel.add(createTitleAndDescPanelLayout(getMessage("System.Hotkey.Find"), getMessage("System.Hotkey.Find.Descript"), lookUpHotkey));
        copyContent = new HotKeyLabel("CTRL + N");
        settingPanel.add(createTitleAndDescPanelLayout(getMessage("System.Hotkey.NewTab"), getMessage("System.Hotkey.NewTab.Descript"), copyContent));
        copyPicture = new HotKeyLabel("ALT + Q");
        settingPanel.add(createTitleAndDescPanelLayout(getMessage("System.Hotkey.Exit"), getMessage("System.Hotkey.Exit.Descript"), copyPicture));
        return settingPanel;
    }

    private JPanel createMainLayoutPanel_D(String title) {
        JPanel settingPanel = new JPanel(new GridLayout(1, 1, 10, 10));
        settingPanel.setBorder(BorderFactory.createTitledBorder(title));
        lookUpHotkey = new HotKeyLabel("CTRL + ALT+ S");
        settingPanel.add(createTitleAndDescPanelLayout(getMessage("System.Hotkey.Setting"), getMessage("System.Hotkey.Setting.Descript"), lookUpHotkey));
        return settingPanel;
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
        titleLabel.setText("<html><span style=\"font-weight:500\">" + title + "</span></html>");
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

    private String getMessage(String keyRoot) {
        return resourceBundle.getString(keyRoot + ".Name");
    }


}


class HotKeyLabel extends JTextField {
    private JTextField textField = this;

    public HotKeyLabel(String text) {
        super(text, 12);
        this.setEditable(false);
        this.setHorizontalAlignment(JTextField.CENTER);
        this.setOpaque(false); // 设置为不可见背景
        this.setBorder(BorderBuilder.emptyBorder(0));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                textField.setBorder(BorderBuilder.bottomBorder(1, ThemeColor.loadingColor)); // 鼠标移入时设置边框为黑色
            }

            @Override
            public void mouseExited(MouseEvent e) {
                textField.setBorder(BorderBuilder.emptyBorder(0)); // 鼠标移出时移除边框
            }
        });
    }
}