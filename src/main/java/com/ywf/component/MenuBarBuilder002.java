package com.ywf.component;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.ywf.action.MenuEventService;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.enums.FontEnum;
import com.ywf.framework.enums.PictureQualityEnum;
import com.ywf.framework.enums.TextConvertEnum;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 菜单工具条
 *
 * @Author YWF
 * @Date 2023/11/30 16:07
 */
public class MenuBarBuilder002 {

    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    private static JMenuBar menuBar;
    private static JCheckBoxMenuItem showToolBarMenuItem;
    private static JCheckBoxMenuItem showMenuBarMenuItem;
    private static JMenuItem unescapeMenuItem;
    private static JMenuItem escapeTabMenuItem;

    public static JMenuBar createMenuBar(JFrame frame) {

        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("文件");
        JMenuItem newTabMenuItem = new JMenuItem("新建");
        newTabMenuItem.setIcon(IconUtils.getSVGIcon("icons/newEditer.svg", 12, 12));
        newTabMenuItem.addActionListener(e -> MenuEventService.getInstance().addTabbedSplitEditorActionPerformed());
        JMenuItem savePictMenuItem = new JMenuItem("导出图片");
        savePictMenuItem.setIcon(IconUtils.getSVGIcon("icons/exportPict.svg", 12, 12));
        savePictMenuItem.addActionListener(e -> MenuEventService.getInstance().saveJsonToImageActionPerformed(frame));
        JMenuItem saveFileMenuItem = new JMenuItem("导出文件");
        saveFileMenuItem.setIcon(IconUtils.getSVGIcon("icons/saveCode.svg", 12, 12));
        saveFileMenuItem.addActionListener(e -> MenuEventService.getInstance().saveJsonToFileActionPerformed(frame));
        JMenuItem favoritesMenuItem = new JMenuItem("收藏夹");
        favoritesMenuItem.setIcon(IconUtils.getSVGIcon("icons/favorites.svg", 12, 12));
        JMenuItem exitMenuItem = new JMenuItem("退出");
        exitMenuItem.setIcon(IconUtils.getSVGIcon("icons/exit.svg", 12, 12));
        exitMenuItem.addActionListener(e -> MenuEventService.getInstance().closeWindowsFrameActionPerformed(frame));

        fileMenu.add(newTabMenuItem);
        fileMenu.add(savePictMenuItem);
        fileMenu.add(saveFileMenuItem);
        fileMenu.add(favoritesMenuItem);
        fileMenu.add(exitMenuItem);

        JMenu editMenu = new JMenu("编辑");
        JMenuItem compMenuItem = new JMenuItem("压缩");
        compMenuItem.setIcon(IconUtils.getSVGIcon("icons/comp.svg", 12, 12));
        compMenuItem.addActionListener(e -> MenuEventService.getInstance().compressionJsonActionPerformed());
        escapeTabMenuItem = new JMenuItem("转义");
        escapeTabMenuItem.setIcon(IconUtils.getSVGIcon("icons/escapeCode.svg", 12, 12));
        escapeTabMenuItem.addActionListener(e -> MenuEventService.getInstance().escapeJsonActionPerformed());
        unescapeMenuItem = new JMenuItem("去除转义");
        unescapeMenuItem.setIcon(IconUtils.getSVGIcon("icons/unEscapeCode.svg", 12, 12));
        unescapeMenuItem.addActionListener(e -> MenuEventService.getInstance().unEscapeJsonActionPerformed());
        JMenuItem formatMenuItem = new JMenuItem("格式化");
        formatMenuItem.setIcon(IconUtils.getSVGIcon("icons/formatCode.svg", 12, 12));
        formatMenuItem.addActionListener(e -> MenuEventService.getInstance().formatJsonActionPerformed(frame));
        JMenuItem cleanMenuItem = new JMenuItem("清空");
        cleanMenuItem.setIcon(IconUtils.getSVGIcon("icons/delete.svg", 12, 12));
        cleanMenuItem.addActionListener(e -> MenuEventService.getInstance().cleanJsonActionPerformed());
        JMenuItem findRepMenuItem = new JMenuItem("查找");
        findRepMenuItem.setIcon(IconUtils.getSVGIcon("icons/find.svg", 12, 12));
        findRepMenuItem.addActionListener(e -> MenuEventService.getInstance().showFindDialogActionPerformed());
        editMenu.add(compMenuItem);
        editMenu.add(escapeTabMenuItem);
        editMenu.add(unescapeMenuItem);
        editMenu.add(formatMenuItem);
        editMenu.add(cleanMenuItem);
        editMenu.add(findRepMenuItem);


        JMenu setupMenu = new JMenu("设置");
        // 界面字体
        JMenu frameFontMenu = createFrameFontMenu();

        JMenu facadeMenu = new JMenu("外观菜单");
        showToolBarMenuItem = new JCheckBoxMenuItem("显示工具栏");
        showToolBarMenuItem.setSelected(applicationContext.getShowToolBarState());
        showToolBarMenuItem.addActionListener(e -> MenuEventService.getInstance().showToolBarActionPerformed());
        showMenuBarMenuItem = new JCheckBoxMenuItem("显示菜单栏");
        showMenuBarMenuItem.setSelected(applicationContext.getShowMenuBarState());
        //showMenuBarMenuItem.addActionListener(e -> MenuEventService.getInstance().showMenuBarActionPerformed());
        facadeMenu.add(showToolBarMenuItem);
        facadeMenu.add(showMenuBarMenuItem);

        JCheckBoxMenuItem editSetupMenuItem = new JCheckBoxMenuItem("禁止编辑");
        editSetupMenuItem.setSelected(!applicationContext.getTextAreaEditState());
        editSetupMenuItem.addActionListener(e -> MenuEventService.getInstance().editSwitchActionPerformed());

        JCheckBoxMenuItem lineSetupMenuItem = new JCheckBoxMenuItem("自动换行");
        lineSetupMenuItem.setSelected(applicationContext.getTextAreaBreakLineState());
        lineSetupMenuItem.addActionListener(e -> MenuEventService.getInstance().lineSetupActionPerformed());

        JCheckBoxMenuItem showlineNumMenuItem = new JCheckBoxMenuItem("显示行号");
        showlineNumMenuItem.setSelected(applicationContext.getTextAreaShowlineNumState());
        showlineNumMenuItem.addActionListener(e -> MenuEventService.getInstance().showLineNumActionPerformed());

        JMenu pictureQualityMenu = new JMenu("图片质量");
        PictureQualityRadioButtonMenuItem lowPictureQualityMenuItem = new PictureQualityRadioButtonMenuItem("低", PictureQualityEnum.LOW_PICTURE_QUALITY.getPictureQualityState());
        PictureQualityRadioButtonMenuItem middlePictureQualityMenuItem = new PictureQualityRadioButtonMenuItem("中", PictureQualityEnum.MIDDLE_PICTURE_QUALITY.getPictureQualityState());
        PictureQualityRadioButtonMenuItem hightPictureQualityMenuItem = new PictureQualityRadioButtonMenuItem("高", PictureQualityEnum.HEIGHT_PICTURE_QUALITY.getPictureQualityState());
        ButtonGroup pictureQualityBtn = new ButtonGroup();
        pictureQualityBtn.add(lowPictureQualityMenuItem);
        pictureQualityBtn.add(middlePictureQualityMenuItem);
        pictureQualityBtn.add(hightPictureQualityMenuItem);
        pictureQualityMenu.add(lowPictureQualityMenuItem);
        pictureQualityMenu.add(middlePictureQualityMenuItem);
        pictureQualityMenu.add(hightPictureQualityMenuItem);
        MenuEventService.getInstance().pictureQualityActionPerformed(pictureQualityMenu);

        JMenu chineseConverMenu = new JMenu("中文转码");
        CHToCNRadioButtonMenuItem chineseConverUnicodeMenuItem = new CHToCNRadioButtonMenuItem("中文转Unicode", TextConvertEnum.CH_TO_UN.getConverType());
        CHToCNRadioButtonMenuItem unicodeConverChineseMenuItem = new CHToCNRadioButtonMenuItem("Unicode转中文", TextConvertEnum.UN_TO_CH.getConverType());
        CHToCNRadioButtonMenuItem unConverMenuItem = new CHToCNRadioButtonMenuItem("转码功能关闭", TextConvertEnum.CONVERT_CLOSED.getConverType());

        ButtonGroup chineseConverButtonGroup = new ButtonGroup();
        chineseConverButtonGroup.add(unConverMenuItem);
        chineseConverButtonGroup.add(chineseConverUnicodeMenuItem);
        chineseConverButtonGroup.add(unicodeConverChineseMenuItem);
        chineseConverMenu.add(unConverMenuItem);
        chineseConverMenu.add(chineseConverUnicodeMenuItem);
        chineseConverMenu.add(unicodeConverChineseMenuItem);
        MenuEventService.getInstance().chineseConverActionPerformed(chineseConverMenu);

        setupMenu.add(frameFontMenu);
        setupMenu.add(facadeMenu);
        setupMenu.add(editSetupMenuItem);
        setupMenu.add(lineSetupMenuItem);
        setupMenu.add(showlineNumMenuItem);
        setupMenu.add(pictureQualityMenu);
        setupMenu.add(chineseConverMenu);

        JMenu themesMenu = new JMenu("主题");
        JRadioButtonMenuItem lightThemesMenuItem = new JRadioButtonMenuItem("FlatLaf Light");
        JRadioButtonMenuItem arcLightOrangeMenuItem = new JRadioButtonMenuItem("Arc Light Orange");
        JRadioButtonMenuItem solarizedLightMenuItem = new JRadioButtonMenuItem("Solarized Light");
        JRadioButtonMenuItem arcDarkOrangeMenuItem = new JRadioButtonMenuItem("Arc Dark Orange");
        JRadioButtonMenuItem gruvboxDarkMediumMenuItem = new JRadioButtonMenuItem("Gruvbox Dark Medium");
        JRadioButtonMenuItem materialDarkerMenuItem = new JRadioButtonMenuItem("Material Darker");
        JRadioButtonMenuItem materialDeepOceanMenuItem = new JRadioButtonMenuItem("Material Deep Ocean");
        JRadioButtonMenuItem nightOwlMenuItem = new JRadioButtonMenuItem("Night Owl");

        // 组装为单选
        ButtonGroup buttonGroupThemes = new ButtonGroup();
        buttonGroupThemes.add(lightThemesMenuItem);
        buttonGroupThemes.add(arcLightOrangeMenuItem);
        buttonGroupThemes.add(solarizedLightMenuItem);
        buttonGroupThemes.add(arcDarkOrangeMenuItem);
        buttonGroupThemes.add(gruvboxDarkMediumMenuItem);
        buttonGroupThemes.add(materialDarkerMenuItem);
        buttonGroupThemes.add(materialDeepOceanMenuItem);
        buttonGroupThemes.add(nightOwlMenuItem);

        themesMenu.add(lightThemesMenuItem);
        themesMenu.add(arcLightOrangeMenuItem);
        themesMenu.add(solarizedLightMenuItem);
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
        menuBar.setVisible(applicationContext.getShowMenuBarState());
        return menuBar;
    }

    private static JMenu createFrameFontMenu() {
        JMenu frameFontMenu = new JMenu("界面字体");
        JMenu fontStyleMenu = new JMenu("字体样式");
        JMenu fontSizeMenu = new JMenu("字体大小");
        initFontMenu(fontStyleMenu, fontSizeMenu);
        frameFontMenu.add(fontStyleMenu);
        frameFontMenu.add(fontSizeMenu);
        MenuEventService.getInstance().applyFrameFontActionPerformed(fontStyleMenu, fontSizeMenu);
        return frameFontMenu;
    }

    private static void initFontMenu(JMenu fontStyleMenu, JMenu fontSizeMenu) {
        JRadioButtonMenuItem micYaHeiFontMenuItem = new JRadioButtonMenuItem(FontEnum.Name.micYaHei.getName());
        JRadioButtonMenuItem christmasWorshipFontMenuItem = new JRadioButtonMenuItem(FontEnum.Name.christmasWorship.getName());
        JRadioButtonMenuItem arialFontMenuItem = new JRadioButtonMenuItem(FontEnum.Name.arial.getName());
        JRadioButtonMenuItem blackLetterFontMenuItem = new JRadioButtonMenuItem(FontEnum.Name.blackLetter.getName());
        ButtonGroup fontStyleButtonGroup = new ButtonGroup();
        fontStyleButtonGroup.add(micYaHeiFontMenuItem);
        fontStyleButtonGroup.add(christmasWorshipFontMenuItem);
        fontStyleButtonGroup.add(arialFontMenuItem);
        fontStyleButtonGroup.add(blackLetterFontMenuItem);
        fontStyleMenu.add(micYaHeiFontMenuItem);
        fontStyleMenu.add(christmasWorshipFontMenuItem);
        fontStyleMenu.add(arialFontMenuItem);
        fontStyleMenu.add(blackLetterFontMenuItem);

        FontSizeRadioButtonMenuItem smallFontMenuItem = new FontSizeRadioButtonMenuItem(FontEnum.Size.small.getDesc(), FontEnum.Size.small.getSize());
        FontSizeRadioButtonMenuItem mediumFontMenuItem = new FontSizeRadioButtonMenuItem(FontEnum.Size.medium.getDesc(), FontEnum.Size.medium.getSize());
        FontSizeRadioButtonMenuItem regularFontMenuItem = new FontSizeRadioButtonMenuItem(FontEnum.Size.regular.getDesc(), FontEnum.Size.regular.getSize());
        FontSizeRadioButtonMenuItem largeFontMenuItem = new FontSizeRadioButtonMenuItem(FontEnum.Size.large.getDesc(), FontEnum.Size.large.getSize());
        FontSizeRadioButtonMenuItem tooLargeFontMenuItem = new FontSizeRadioButtonMenuItem(FontEnum.Size.tooLarge.getDesc(), FontEnum.Size.tooLarge.getSize());
        ButtonGroup fontSizeButtonGroup = new ButtonGroup();
        fontSizeButtonGroup.add(smallFontMenuItem);
        fontSizeButtonGroup.add(mediumFontMenuItem);
        fontSizeButtonGroup.add(regularFontMenuItem);
        fontSizeButtonGroup.add(largeFontMenuItem);
        fontSizeButtonGroup.add(tooLargeFontMenuItem);
        fontSizeMenu.add(smallFontMenuItem);
        fontSizeMenu.add(mediumFontMenuItem);
        fontSizeMenu.add(regularFontMenuItem);
        fontSizeMenu.add(largeFontMenuItem);
        fontSizeMenu.add(tooLargeFontMenuItem);
    }

    public static JMenuBar getMenuBar() {
        return menuBar;
    }

    public static JCheckBoxMenuItem getShowToolBarMenuItem() {
        return showToolBarMenuItem;
    }

    public static JCheckBoxMenuItem getShowMenuBarMenuItem() {
        return showMenuBarMenuItem;
    }

    public static JMenuItem getUnescapeMenuItem() {
        return unescapeMenuItem;
    }

    public static JMenuItem getEscapeTabMenuItem() {
        return escapeTabMenuItem;
    }

}
