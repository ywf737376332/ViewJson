package com.ywf.component;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.ywf.action.MenuEventService;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.utils.IconUtils;
import com.ywf.framework.utils.PropertiesUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

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
        newTabMenuItem.setIcon(IconUtils.getSVGIcon("icons/open.svg"));
        JMenuItem savePictMenuItem = new JMenuItem("导出图片");
        savePictMenuItem.setIcon(IconUtils.getSVGIcon("icons/cutPict.svg"));
        savePictMenuItem.addActionListener(e -> MenuEventService.getInstance().saveJsonToImageActionPerformed(frame));

        JMenuItem saveFileMenuItem = new JMenuItem("导出文件");
        saveFileMenuItem.setIcon(IconUtils.getSVGIcon("icons/saveCode.svg"));
        saveFileMenuItem.addActionListener(e -> MenuEventService.getInstance().saveJsonToFileActionPerformed(frame));

        fileMenu.add(newTabMenuItem);
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
        editMenu.add(cleanMenuItem);
        editMenu.add(findRepMenuItem);


        JMenu setupMenu = new JMenu("设置");
        JCheckBoxMenuItem editSetupMenuItem = new JCheckBoxMenuItem("禁止编辑");
        editSetupMenuItem.setSelected(!Boolean.valueOf(systemProperties.getValueFromProperties(SystemConstant.TEXTAREA_EDIT_STATE_KEY)));
        editSetupMenuItem.addActionListener(e -> MenuEventService.getInstance().editSwitchActionPerformed(frame));

        JCheckBoxMenuItem lineSetupMenuItem = new JCheckBoxMenuItem("自动换行");
        lineSetupMenuItem.setSelected(Boolean.valueOf(systemProperties.getValueFromProperties(SystemConstant.TEXTAREA_BREAK_LINE_KEY)));
        lineSetupMenuItem.addActionListener(e -> MenuEventService.getInstance().lineSetupActionPerformed(frame));

        JCheckBoxMenuItem replaceSpaceMenuItem = new JCheckBoxMenuItem("去除空格");
        replaceSpaceMenuItem.setSelected(Boolean.valueOf(systemProperties.getValueFromProperties(SystemConstant.TEXTAREA_REPLACE_BLANKSPACE_KEY)));
        replaceSpaceMenuItem.addActionListener(e -> MenuEventService.getInstance().replaceBlankSpaceActionPerformed(frame, replaceSpaceMenuItem));

        setupMenu.add(editSetupMenuItem);
        setupMenu.add(lineSetupMenuItem);
        setupMenu.add(replaceSpaceMenuItem);

        JMenu themesMenu = new JMenu("主题");
        JRadioButtonMenuItem lightThemesMenuItem = new JRadioButtonMenuItem("FlatLaf Light");
        lightThemesMenuItem.setSelected(true);
        JRadioButtonMenuItem gitHubLightMenuItem = new JRadioButtonMenuItem("GitHub Light");
        JRadioButtonMenuItem arcLightOrangeMenuItem = new JRadioButtonMenuItem("Arc Light Orange");
        JRadioButtonMenuItem solarizedLightMenuItem = new JRadioButtonMenuItem("Solarized Light");
        JRadioButtonMenuItem darkThemesMenuItem = new JRadioButtonMenuItem("FlatLaf Dark");
        JRadioButtonMenuItem arcDarkOrangeMenuItem = new JRadioButtonMenuItem("Arc Dark Orange");
        JRadioButtonMenuItem gruvboxDarkMediumMenuItem = new JRadioButtonMenuItem("Gruvbox Dark Medium");

        // 组装为单选
        ButtonGroup buttonGroupThemes = new ButtonGroup();
        buttonGroupThemes.add(lightThemesMenuItem);
        buttonGroupThemes.add(gitHubLightMenuItem);
        buttonGroupThemes.add(arcLightOrangeMenuItem);
        buttonGroupThemes.add(solarizedLightMenuItem);
        buttonGroupThemes.add(darkThemesMenuItem);
        buttonGroupThemes.add(arcDarkOrangeMenuItem);
        buttonGroupThemes.add(gruvboxDarkMediumMenuItem);

        themesMenu.add(lightThemesMenuItem);
        themesMenu.add(gitHubLightMenuItem);
        themesMenu.add(arcLightOrangeMenuItem);
        themesMenu.add(solarizedLightMenuItem);
        themesMenu.add(darkThemesMenuItem);
        themesMenu.add(arcDarkOrangeMenuItem);
        themesMenu.add(gruvboxDarkMediumMenuItem);
        //添加事件
        MenuEventService.getInstance().setupThemesActionPerformed(frame, themesMenu);

        JMenu helpMenu = new JMenu("帮助");
        JMenuItem updateVersionLogMenuItem = new JMenuItem("更新日志");
        JMenuItem privacyPolicyMenuItem = new JMenuItem("隐私条款");
        JMenuItem officialWebsiteMenuItem = new JMenuItem("官方网站");
        JMenuItem expressThanksMenuItem = new JMenuItem("鸣谢反馈");
        JMenuItem aboutMenuItem = new JMenuItem("关于");
        aboutMenuItem.addActionListener(e -> MenuEventService.getInstance().aboutActionPerformed());
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

}
