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

    private static JMenuBar menuBar;
    private static JCheckBoxMenuItem showToolBarMenuItem;
    private static JCheckBoxMenuItem showMenuBarMenuItem;
    public static JMenuBar createMenuBar(JFrame frame) {

        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("文件");
        JMenuItem newTabMenuItem = new JMenuItem("新建");
        newTabMenuItem.setIcon(IconUtils.getSVGIcon("icons/open.svg"));
        newTabMenuItem.setEnabled(false);
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
        compMenuItem.addActionListener(e -> MenuEventService.getInstance().compressionJsonActionPerformed());
        JMenuItem escapeTabMenuItem = new JMenuItem("转义");
        escapeTabMenuItem.setIcon(IconUtils.getSVGIcon("icons/escapeCode.svg"));
        escapeTabMenuItem.addActionListener(e -> MenuEventService.getInstance().escapeJsonActionPerformed());
        JMenuItem unescapeMenuItem = new JMenuItem("去除转义");
        unescapeMenuItem.setIcon(IconUtils.getSVGIcon("icons/unEscapeCode.svg"));
        unescapeMenuItem.addActionListener(e -> MenuEventService.getInstance().unEscapeJsonActionPerformed());
        JMenuItem formatMenuItem = new JMenuItem("格式化");
        formatMenuItem.setIcon(IconUtils.getSVGIcon("icons/formatCode.svg"));
        formatMenuItem.addActionListener(e -> MenuEventService.getInstance().formatJsonActionPerformed(frame));
        JMenuItem cleanMenuItem = new JMenuItem("清空");
        cleanMenuItem.setIcon(IconUtils.getSVGIcon("icons/Basket.svg"));
        cleanMenuItem.addActionListener(e -> MenuEventService.getInstance().cleanJsonActionPerformed());
        JMenuItem findRepMenuItem = new JMenuItem("查找替换");
        findRepMenuItem.setIcon(IconUtils.getSVGIcon("icons/findCode.svg"));
        findRepMenuItem.setEnabled(false);
        editMenu.add(compMenuItem);
        editMenu.add(escapeTabMenuItem);
        editMenu.add(unescapeMenuItem);
        editMenu.add(formatMenuItem);
        editMenu.add(cleanMenuItem);
        editMenu.add(findRepMenuItem);


        JMenu setupMenu = new JMenu("设置");
        JCheckBoxMenuItem editSetupMenuItem = new JCheckBoxMenuItem("禁止编辑");
        editSetupMenuItem.setSelected(!Boolean.valueOf(systemProperties.getValueFromProperties(SystemConstant.TEXTAREA_EDIT_STATE_KEY)));
        editSetupMenuItem.addActionListener(e -> MenuEventService.getInstance().editSwitchActionPerformed());

        JCheckBoxMenuItem lineSetupMenuItem = new JCheckBoxMenuItem("自动换行");
        lineSetupMenuItem.setSelected(Boolean.valueOf(systemProperties.getValueFromProperties(SystemConstant.TEXTAREA_BREAK_LINE_KEY)));
        lineSetupMenuItem.addActionListener(e -> MenuEventService.getInstance().lineSetupActionPerformed());

        //JCheckBoxMenuItem replaceSpaceMenuItem = new JCheckBoxMenuItem("去除空格");
        //replaceSpaceMenuItem.setSelected(Boolean.valueOf(systemProperties.getValueFromProperties(SystemConstant.TEXTAREA_REPLACE_BLANKSPACE_KEY)));
        //replaceSpaceMenuItem.addActionListener(e -> MenuEventService.getInstance().replaceBlankSpaceActionPerformed(replaceSpaceMenuItem));

        showToolBarMenuItem = new JCheckBoxMenuItem("显示工具栏");
        showToolBarMenuItem.setSelected(Boolean.valueOf(systemProperties.getValueFromProperties(SystemConstant.SHOW_TOOL_BAR_KEY)));
        showToolBarMenuItem.addActionListener(e -> MenuEventService.getInstance().showToolBarActionPerformed());

        showMenuBarMenuItem = new JCheckBoxMenuItem("显示菜单栏");
        showMenuBarMenuItem.setSelected(Boolean.valueOf(systemProperties.getValueFromProperties(SystemConstant.SHOW_MENU_BAR_KEY)));
        showMenuBarMenuItem.addActionListener(e -> MenuEventService.getInstance().showMenuBarActionPerformed());

        JMenu toolBarMenuItem = new JMenu("外观菜单");
        toolBarMenuItem.add(showToolBarMenuItem);
        toolBarMenuItem.add(showMenuBarMenuItem);

        setupMenu.add(editSetupMenuItem);
        setupMenu.add(lineSetupMenuItem);
        //setupMenu.add(replaceSpaceMenuItem);
        setupMenu.add(toolBarMenuItem);

        JMenu themesMenu = new JMenu("主题");
        JRadioButtonMenuItem lightThemesMenuItem = new JRadioButtonMenuItem("FlatLaf Light");
        lightThemesMenuItem.setSelected(true);
        JRadioButtonMenuItem gitHubLightMenuItem = new JRadioButtonMenuItem("GitHub Light");
        JRadioButtonMenuItem arcLightOrangeMenuItem = new JRadioButtonMenuItem("Arc Light Orange");
        JRadioButtonMenuItem solarizedLightMenuItem = new JRadioButtonMenuItem("Solarized Light");
        JRadioButtonMenuItem darkThemesMenuItem = new JRadioButtonMenuItem("FlatLaf Dark");
        JRadioButtonMenuItem arcDarkOrangeMenuItem = new JRadioButtonMenuItem("Arc Dark Orange");
        JRadioButtonMenuItem gruvboxDarkMediumMenuItem = new JRadioButtonMenuItem("Gruvbox Dark Medium");
        JRadioButtonMenuItem materialDarkerMenuItem = new JRadioButtonMenuItem("Material Darker");
        JRadioButtonMenuItem materialDeepOceanMenuItem = new JRadioButtonMenuItem("Material Deep Ocean");
        JRadioButtonMenuItem nightOwlMenuItem = new JRadioButtonMenuItem("Night Owl");

        // 组装为单选
        ButtonGroup buttonGroupThemes = new ButtonGroup();
        buttonGroupThemes.add(lightThemesMenuItem);
        buttonGroupThemes.add(gitHubLightMenuItem);
        buttonGroupThemes.add(arcLightOrangeMenuItem);
        buttonGroupThemes.add(solarizedLightMenuItem);
        buttonGroupThemes.add(darkThemesMenuItem);
        buttonGroupThemes.add(arcDarkOrangeMenuItem);
        buttonGroupThemes.add(gruvboxDarkMediumMenuItem);
        buttonGroupThemes.add(materialDarkerMenuItem);
        buttonGroupThemes.add(materialDeepOceanMenuItem);
        buttonGroupThemes.add(nightOwlMenuItem);

        themesMenu.add(lightThemesMenuItem);
        themesMenu.add(gitHubLightMenuItem);
        themesMenu.add(arcLightOrangeMenuItem);
        themesMenu.add(solarizedLightMenuItem);
        themesMenu.add(darkThemesMenuItem);
        themesMenu.add(arcDarkOrangeMenuItem);
        themesMenu.add(gruvboxDarkMediumMenuItem);
        themesMenu.add(materialDarkerMenuItem);
        themesMenu.add(materialDeepOceanMenuItem);
        themesMenu.add(nightOwlMenuItem);
        //添加事件
        MenuEventService.getInstance().setupThemesActionPerformed(frame, themesMenu);

        JMenu helpMenu = new JMenu("帮助");
        JMenuItem updateVersionLogMenuItem = new JMenuItem("更新日志");
        updateVersionLogMenuItem.addActionListener(e -> MenuEventService.getInstance().updateLogActionPerformed());
        JMenuItem privacyPolicyMenuItem = new JMenuItem("隐私条款");
        privacyPolicyMenuItem.setEnabled(false);
        JMenuItem officialWebsiteMenuItem = new JMenuItem("官方网站");
        officialWebsiteMenuItem.setEnabled(false);
        JMenuItem expressThanksMenuItem = new JMenuItem("鸣谢反馈");
        expressThanksMenuItem.setEnabled(false);
        JMenuItem aboutMenuItem = new JMenuItem("关于");
        aboutMenuItem.addActionListener(e -> MenuEventService.getInstance().aboutActionPerformed());
        helpMenu.add(updateVersionLogMenuItem);
        helpMenu.add(privacyPolicyMenuItem);
        helpMenu.add(officialWebsiteMenuItem);
        helpMenu.add(expressThanksMenuItem);
        helpMenu.add(aboutMenuItem);

        JMenu viewMenu = new JMenu("Beat");
        JMenuItem htmlMenuItem = new JMenuItem("<html>some <b color=\"red\">HTML</b> <i color=\"blue\">text</i></html>");
        htmlMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        htmlMenuItem.setMnemonic('O');
        htmlMenuItem.setIcon(new FlatSVGIcon("icons/menu-cut.svg"));
        //添加组件
        viewMenu.add(htmlMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(setupMenu);
        menuBar.add(themesMenu);
        menuBar.add(helpMenu);
        menuBar.setVisible(Boolean.valueOf(systemProperties.getValueFromProperties(SystemConstant.SHOW_MENU_BAR_KEY)));
        return menuBar;
    }

    public static JMenuBar getMenuBar() {
        return menuBar;
    }

    public static void setMenuBar(JMenuBar menuBar) {
        MenuBarBuilder.menuBar = menuBar;
    }

    public static JCheckBoxMenuItem getShowToolBarMenuItem() {
        return showToolBarMenuItem;
    }

    public static void setShowToolBarMenuItem(JCheckBoxMenuItem showToolBarMenuItem) {
        MenuBarBuilder.showToolBarMenuItem = showToolBarMenuItem;
    }

    public static JCheckBoxMenuItem getShowMenuBarMenuItem() {
        return showMenuBarMenuItem;
    }

    public static void setShowMenuBarMenuItem(JCheckBoxMenuItem showMenuBarMenuItem) {
        MenuBarBuilder.showMenuBarMenuItem = showMenuBarMenuItem;
    }
}
