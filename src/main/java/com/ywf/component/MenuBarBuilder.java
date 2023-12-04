package com.ywf.component;

import cn.hutool.core.util.StrUtil;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.utils.ChangeUIUtils;
import com.ywf.framework.utils.IconUtils;
import com.ywf.framework.utils.PropertiesUtil;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/30 16:07
 */
public class MenuBarBuilder {

    private static PropertiesUtil systemProperties = PropertiesUtil.instance();

    public static JMenuBar createMenuBar(JFrame frame) {

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("文件");
        JMenuItem newTabMenuItem = new JMenuItem("新建");
        JMenuItem newFrameMenuItem = new JMenuItem("新建窗口");
        JMenuItem copyMenuItem = new JMenuItem("复制内容");
        copyMenuItem.setIcon(IconUtils.getSVGIcon("icons/copyCode.svg"));
        JMenuItem savePictMenuItem = new JMenuItem("导出图片");
        savePictMenuItem.setIcon(IconUtils.getSVGIcon("icons/cutPict.svg"));
        JMenuItem saveFileMenuItem = new JMenuItem("导出文件");
        saveFileMenuItem.setIcon(IconUtils.getSVGIcon("icons/saveCode.svg"));
        fileMenu.add(newTabMenuItem);
        fileMenu.add(newFrameMenuItem);
        fileMenu.add(copyMenuItem);
        fileMenu.add(savePictMenuItem);
        fileMenu.add(saveFileMenuItem);

        JMenu editMenu = new JMenu("编辑");
        JMenuItem compMenuItem = new JMenuItem("压缩");
        compMenuItem.setIcon(IconUtils.getSVGIcon("icons/comp.svg"));
        JMenuItem escapeTabMenuItem = new JMenuItem("转义");
        escapeTabMenuItem.setIcon(IconUtils.getSVGIcon("icons/escapeCode.svg"));
        JMenuItem unescapeMenuItem = new JMenuItem("去除转义");
        unescapeMenuItem.setIcon(IconUtils.getSVGIcon("icons/unEscapeCode.svg"));
        JMenuItem formatMenuItem = new JMenuItem("格式化");
        formatMenuItem.setIcon(IconUtils.getSVGIcon("icons/formatCode.svg"));
        JMenuItem cleanMenuItem = new JMenuItem("清空");
        cleanMenuItem.setIcon(IconUtils.getSVGIcon("icons/Basket.svg"));
        JMenuItem findRepMenuItem = new JMenuItem("查找替换");
        findRepMenuItem.setIcon(IconUtils.getSVGIcon("icons/findCode.svg"));
        editMenu.add(compMenuItem);
        editMenu.add(escapeTabMenuItem);
        editMenu.add(unescapeMenuItem);
        editMenu.add(formatMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(cleanMenuItem);
        editMenu.add(findRepMenuItem);


        JMenu setupMenu = new JMenu("设置");
        JCheckBoxMenuItem editSetupMenuItem = new JCheckBoxMenuItem("禁止编辑");
        editSetupMenuItem.setSelected(!Boolean.valueOf(systemProperties.getValueFromProperties(SystemConstant.TEXTAREA_EDIT_STATE_KEY)));
        editSetupMenuItem.addActionListener(e -> editSwitchActionPerformed(frame));

        JCheckBoxMenuItem lineSetupMenuItem = new JCheckBoxMenuItem("自动换行");
        lineSetupMenuItem.setSelected(Boolean.valueOf(systemProperties.getValueFromProperties(SystemConstant.TEXTAREA_BREAK_LINE_KEY)));
        lineSetupMenuItem.addActionListener(e -> lineSetupActionPerformed(frame));

        JCheckBoxMenuItem replaceSpaceMenuItem = new JCheckBoxMenuItem("去除空格");
        setupMenu.add(editSetupMenuItem);
        setupMenu.add(lineSetupMenuItem);
        //setupMenu.add(replaceSpaceMenuItem);

        JMenu themesMenu = new JMenu("主题");
        JRadioButtonMenuItem lightThemesMenuItem = new JRadioButtonMenuItem("FlatLaf Light");
        lightThemesMenuItem.setSelected(true);
        JRadioButtonMenuItem gitHubLightMenuItem = new JRadioButtonMenuItem("GitHub Light");
        JRadioButtonMenuItem darkThemesMenuItem = new JRadioButtonMenuItem("FlatLaf Dark");
        JRadioButtonMenuItem arcDarkOrangeMenuItem = new JRadioButtonMenuItem("Arc Dark Orange");
        JRadioButtonMenuItem gruvboxDarkMediumMenuItem = new JRadioButtonMenuItem("Gruvbox Dark Medium");
        JRadioButtonMenuItem solarizedLightMenuItem = new JRadioButtonMenuItem("Solarized Light");

        // 组装为单选
        ButtonGroup buttonGroupThemes = new ButtonGroup();
        buttonGroupThemes.add(lightThemesMenuItem);
        buttonGroupThemes.add(gitHubLightMenuItem);
        buttonGroupThemes.add(darkThemesMenuItem);
        buttonGroupThemes.add(arcDarkOrangeMenuItem);
        buttonGroupThemes.add(gruvboxDarkMediumMenuItem);
        buttonGroupThemes.add(solarizedLightMenuItem);
        themesMenu.add(lightThemesMenuItem);
        themesMenu.add(gitHubLightMenuItem);
        themesMenu.add(darkThemesMenuItem);
        themesMenu.add(arcDarkOrangeMenuItem);
        themesMenu.add(gruvboxDarkMediumMenuItem);
        themesMenu.add(solarizedLightMenuItem);

        //添加事件
        themesActionPerformed(frame, themesMenu);

        JMenu helpMenu = new JMenu("帮助");
        JMenuItem updateVersionLogMenuItem = new JMenuItem("更新日志");
        JMenuItem privacyPolicyMenuItem = new JMenuItem("隐私条款");
        JMenuItem officialWebsiteMenuItem = new JMenuItem("官方网站");
        JMenuItem expressThanksMenuItem = new JMenuItem("鸣谢反馈");
        JMenuItem aboutMenuItem = new JMenuItem("关于");
        aboutMenuItem.addActionListener(e -> aboutActionPerformed());
        helpMenu.add(updateVersionLogMenuItem);
        helpMenu.add(privacyPolicyMenuItem);
        helpMenu.add(officialWebsiteMenuItem);
        helpMenu.add(expressThanksMenuItem);
        helpMenu.add(aboutMenuItem);

        JMenu viewMenu = new JMenu("Beat");
        JRadioButtonMenuItem radioButtonMenuItem1 = new JRadioButtonMenuItem("单选一");
        radioButtonMenuItem1.setSelected(true);
        radioButtonMenuItem1.setMnemonic('D');
        JRadioButtonMenuItem radioButtonMenuItem2 = new JRadioButtonMenuItem("单选二");
        JRadioButtonMenuItem radioButtonMenuItem3 = new JRadioButtonMenuItem("单选三");
        // 组装为单选
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(radioButtonMenuItem1);
        buttonGroup1.add(radioButtonMenuItem2);
        buttonGroup1.add(radioButtonMenuItem3);

        JMenuItem htmlMenuItem = new JMenuItem("<html>some <b color=\"red\">HTML</b> <i color=\"blue\">text</i></html>");
        htmlMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        htmlMenuItem.setMnemonic('O');
        htmlMenuItem.setIcon(new FlatSVGIcon("icons/menu-cut.svg"));
        JCheckBoxMenuItem checkBoxMenuItem1 = new JCheckBoxMenuItem("复选A");
        JCheckBoxMenuItem checkBoxMenuItem2 = new JCheckBoxMenuItem("复选B");

        //添加组件
        viewMenu.add(radioButtonMenuItem1);
        viewMenu.add(radioButtonMenuItem2);
        viewMenu.add(radioButtonMenuItem3);
        viewMenu.add(htmlMenuItem);
        viewMenu.add(checkBoxMenuItem1);
        viewMenu.add(checkBoxMenuItem2);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(setupMenu);
        menuBar.add(themesMenu);
        menuBar.add(helpMenu);
        menuBar.add(viewMenu);

        return menuBar;
    }

    private static void aboutActionPerformed() {
        JLabel titleLabel = new JLabel("JSON格式化工具");
        titleLabel.setIcon(IconUtils.getSVGIcon("icons/FlatLaf.svg"));
        titleLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "H2");
        String link = "737376332@qq.com";
        JLabel linkLabel = new JLabel("<html><span>联系方式：</span><a href=737376332@qq.com>" + link + "</a></html>");
        linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(link));
                } catch (IOException | URISyntaxException ex) {
                    JOptionPane.showMessageDialog(linkLabel,
                            "发送邮件到 '" + link + "' 邮箱，反馈问题、建议、或加入我们!",
                            "提示", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        JOptionPane.showMessageDialog(null,
                new Object[]{
                        titleLabel,
                        " ",
                        "作者：莫斐鱼",
                        "座右铭：读万卷书，行万里路，阅无数人",
                        linkLabel,
                        "Copyright 2023-" + Year.now() + ""
                },
                "关于", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * 修改主题事件
     *
     * @param frame
     * @param themesMenu
     * @date 2023/12/2 21:30
     */
    private static void themesActionPerformed(JFrame frame, JMenu themesMenu) {
        for (Component menuComponent : themesMenu.getMenuComponents()) {
            if (menuComponent instanceof JRadioButtonMenuItem) {
                JRadioButtonMenuItem radioButtonMenuItem = (JRadioButtonMenuItem) menuComponent;

                // 主题按钮选中
                SystemThemesEnum themesCss = SystemThemesEnum.findThemesBykey(systemProperties.getValueFromProperties(SystemConstant.SYSTEM_THEMES_KEY));
                if (themesCss.getThemesKey().equals(radioButtonMenuItem.getText())){
                    radioButtonMenuItem.setSelected(true);
                }

                radioButtonMenuItem.addActionListener(e -> {
                    String name = radioButtonMenuItem.getText();
                    SystemThemesEnum themesStyles = SystemThemesEnum.findThemesBykey(name);
                    ChangeUIUtils.changeUIStyle(frame, themesStyles);
                    // 改变多文本内容的主题
                    ChangeUIUtils.changeTextAreaThemes(frame, themesStyles.getTextAreaStyles());
                    // 保存上一次选定的主题
                    systemProperties.setValueToProperties(SystemConstant.SYSTEM_THEMES_KEY, themesStyles.getThemesKey());
                });
            }
        }
    }

    /**
     * 对多文本框进行是否可编辑设置
     *
     * @param frame
     * @date 2023/12/2 21:44
     */
    private static void editSwitchActionPerformed(JFrame frame) {
        RSyntaxTextArea rSyntaxTextArea = TextAreaBuilder.getSyntaxTextArea();
        boolean isEditable = rSyntaxTextArea.isEditable();
        rSyntaxTextArea.setEditable(!isEditable);
        systemProperties.setValueToProperties(SystemConstant.TEXTAREA_EDIT_STATE_KEY, String.valueOf(!isEditable));
    }

    /**
     * 对多文本框进行换行设置
     *
     * @param frame
     * @date 2023/12/2 21:44
     */
    private static void lineSetupActionPerformed(JFrame frame) {
        RSyntaxTextArea rSyntaxTextArea = TextAreaBuilder.getSyntaxTextArea();
        boolean breakLine = rSyntaxTextArea.getLineWrap();
        rSyntaxTextArea.setLineWrap(!breakLine);
        systemProperties.setValueToProperties(SystemConstant.TEXTAREA_BREAK_LINE_KEY, String.valueOf(!breakLine));
    }
}
