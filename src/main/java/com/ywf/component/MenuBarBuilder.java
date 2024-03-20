package com.ywf.component;

import com.ywf.action.MenuEventService;
import com.ywf.action.ResourceBundleService;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.config.MenuAction;
import com.ywf.framework.config.MenuBarKit;
import com.ywf.framework.enums.*;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ResourceBundle;

/**
 * 菜单工具条
 *
 * @Author YWF
 * @Date 2023/11/30 16:07
 */
public class MenuBarBuilder {


    private final static Logger logger = LoggerFactory.getLogger(MenuBarBuilder.class);
    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    private static ResourceBundle resourceBundle;

    /**
     * 文件
     * 新建
     * 导出图片
     * 导出文件
     * 收藏夹
     * 退出
     */
    private JMenu fileMenu;
    private MenuAction newTabAction, savePictAction, saveFileAction, favoritesAction, exitAction;
    private JMenuItem newTabMenuItem, savePictMenuItem, saveFileMenuItem, favoritesMenuItem, exitMenuItem;

    /**
     * 编辑
     * 压缩
     * 转义
     * 去除转义
     * 格式化
     * 查找
     * 清空
     */
    private JMenu editMenu;
    private MenuAction compressAction, escapeAction, unescapeAction, formatAction, findAction, cleanAction;
    private JMenuItem compressMenuItem, escapeMenuItem, unescapeMenuItem, formatMenuItem, findMenuItem, cleanMenuItem;

    /**
     * 设置
     * ...界面字体
     * ......字体样式
     * .........微软雅黑
     * .........华文中宋
     * ......字体大小
     * .........小号
     * .........中等
     * .........常规
     * ...外观菜单
     * ......显示工具栏
     * ......显示菜单栏
     * ...系统语言
     * ......中文
     * ......英文
     * ...禁止编辑
     * ...自动换行
     * ...显示行号
     * ...图片质量
     * ......低
     * ......中
     * ......高
     * ...中文转码
     * ......转码功能关闭
     * ......中文转Unicode
     * ......Unicode转中文
     */
    private JMenu setupMenu;
    private JMenu frameFontMenu, fontStyleMenu, fontSizeMenu;
    private JMenu facadeMenu;
    private MenuAction systemSetupAction, showToolBarAction, showMenuBarAction, showToolBarTextAction;
    private JMenu languageMenu;
    private JCheckBoxMenuItem showToolBarMenuItem, showMenuBarMenuItem, showToolBarTextMenuItem;
    private MenuAction editSetupAction, lineSetupAction, showlineNumAction;
    private JMenuItem systemSetupMenuItem, editSetupMenuItem, lineSetupMenuItem, showlineNumMenuItem;
    private JMenu pictureQualityMenu;
    private JMenu chineseConverMenu;
    /**
     * 主题
     * ...FlatLaf Light
     * ...Arc Light Orange
     * ...Solarized Light
     * ...Arc Dark Orange
     * ...Gruvbox Dark Medium
     * ...Material Darker
     * ...Material Deep Ocean
     * ...Night Owl
     */
    private JMenu themesMenu;

    /**
     * 帮助
     * ...更新日志
     * ...隐私条款
     * ...官方网站
     * ...鸣谢反馈
     * ...关于
     */
    private JMenuBar menuBar;
    private JMenu helpMenu;
    private MenuAction updateVersionLogAction, privacyPolicyAction, officialWebsiteAction, expressThanksAction, aboutAction;
    private JMenuItem updateVersionLogMenuItem, privacyPolicyMenuItem, officialWebsiteMenuItem, expressThanksMenuItem, aboutMenuItem;

    volatile private static MenuBarBuilder instance = null;

    private MenuBarBuilder() {
        init();
    }

    public static MenuBarBuilder getInstance() {
        if (instance == null) {
            synchronized (MenuBarBuilder.class) {
                if (instance == null) {
                    instance = new MenuBarBuilder();
                }
            }
        }
        return instance;
    }

    private void init() {
        resourceBundle = ResourceBundleService.getInstance().getResourceBundle();
        createMenus();
        createMenuActions();
    }

    public JMenuBar createMenuBar(JFrame frame) {
        return initMenuBar(frame);
    }

    private JMenuBar initMenuBar(JFrame frame) {
        menuBar = new JMenuBar();

        /**
         * 文件
         */
        menuBar.add(fileMenu);
        fileMenu.add(newTabMenuItem = createMenuItem(newTabAction));
        fileMenu.add(savePictMenuItem = createMenuItem(savePictAction));
        fileMenu.add(saveFileMenuItem = createMenuItem(saveFileAction));
        fileMenu.add(favoritesMenuItem = createMenuItem(favoritesAction));
        favoritesMenuItem.setEnabled(false);
        fileMenu.add(exitMenuItem = createMenuItem(exitAction));

        /**
         * 编辑
         */
        menuBar.add(editMenu);
        editMenu.add(compressMenuItem = createMenuItem(compressAction));
        editMenu.add(escapeMenuItem = createMenuItem(escapeAction));
        editMenu.add(unescapeMenuItem = createMenuItem(unescapeAction));
        editMenu.add(formatMenuItem = createMenuItem(formatAction));
        editMenu.add(findMenuItem = createMenuItem(findAction));
        editMenu.add(cleanMenuItem = createMenuItem(cleanAction));

        /**
         * 设置
         */
        menuBar.add(setupMenu);
        setupMenu.add(systemSetupMenuItem = createMenuItem(systemSetupAction));
        setupMenu.add(frameFontMenu);
        frameFontMenu.add(fontStyleMenu);
        ButtonGroup fontNameButtonGroup = new ButtonGroup();
        for (FontEnum.Name value : FontEnum.Name.values()) {
            // 确保每一个字体都存在于系统中,此方法取消，系统在英文环境中得到的全部是英文字体
            //if (ChangeUIUtils.getSystemFonts(value.getName())){}
            FontNameRadioButtonMenuItem fontNameMenuItem = new FontNameRadioButtonMenuItem(getMessage(value.getMsgKey()), value.getName());
            fontNameButtonGroup.add(fontNameMenuItem);
            fontStyleMenu.add(fontNameMenuItem);
        }
        frameFontMenu.add(fontSizeMenu);
        ButtonGroup fontSizeButtonGroup = new ButtonGroup();
        for (FontEnum.Size value : FontEnum.Size.values()) {
            FontSizeRadioButtonMenuItem fontSizeMenuItem = new FontSizeRadioButtonMenuItem(getMessage(value.getMsgKey()), value.getSize());
            fontSizeButtonGroup.add(fontSizeMenuItem);
            fontSizeMenu.add(fontSizeMenuItem);
        }
        MenuEventService.getInstance().applyFrameFontActionPerformed(fontStyleMenu, fontSizeMenu);

        setupMenu.add(facadeMenu);
        facadeMenu.add(showToolBarMenuItem = createCheckBoxMenu(showToolBarAction));
        showToolBarMenuItem.setSelected(applicationContext.getShowToolBarState());
        facadeMenu.add(showMenuBarMenuItem = createCheckBoxMenu(showMenuBarAction));
        showMenuBarMenuItem.setSelected(applicationContext.getShowMenuBarState());
        //facadeMenu.add(showToolBarTextMenuItem = createCheckBoxMenu(showToolBarTextAction));
        //showToolBarTextMenuItem.setSelected(applicationContext.getShowToolBarText());

        /*setupMenu.add(languageMenu);
        ButtonGroup languageButtonGroup = new ButtonGroup();
        for (LanguageEnum value : LanguageEnum.values()) {
            LanguageRadioButtonMenuItem languageRadioMenuitem = new LanguageRadioButtonMenuItem(getMessage(value.getMessageKey()), value.getLanguage() + "_" + value.getCountry());
            languageButtonGroup.add(languageRadioMenuitem);
            languageMenu.add(languageRadioMenuitem);
        }
        MenuEventService.getInstance().setupLanguageActionPerformed(frame, languageMenu);*/

        //setupMenu.add(editSetupMenuItem = createCheckBoxMenu(editSetupAction));
        //editSetupMenuItem.setSelected(!applicationContext.getTextAreaEditState());
        //setupMenu.add(lineSetupMenuItem = createCheckBoxMenu(lineSetupAction));
        //lineSetupMenuItem.setSelected(applicationContext.getTextAreaBreakLineState());
        //setupMenu.add(showlineNumMenuItem = createCheckBoxMenu(showlineNumAction));
        //showlineNumMenuItem.setSelected(applicationContext.getTextAreaShowlineNumState());

        //setupMenu.add(pictureQualityMenu);
        //ButtonGroup pictureQualityButtonGroup = new ButtonGroup();
        //for (PictureQualityEnum value : PictureQualityEnum.values()) {
        //    PictureQualityRadioButtonMenuItem pictureQualityMenuItem = new PictureQualityRadioButtonMenuItem(getMessage(value.getMessageKey()), value.getPictureQualityState());
        //    pictureQualityButtonGroup.add(pictureQualityMenuItem);
        //    pictureQualityMenu.add(pictureQualityMenuItem);
        //}
        //MenuEventService.getInstance().pictureQualityActionPerformed(pictureQualityMenu);

        setupMenu.add(chineseConverMenu);
        ButtonGroup chineseConverButtonGroup = new ButtonGroup();
        for (TextConvertEnum value : TextConvertEnum.values()) {
            CHToCNRadioButtonMenuItem chineseConverMenuItem = new CHToCNRadioButtonMenuItem(getMessage(value.getMessageKey()), value.getConverType());
            chineseConverButtonGroup.add(chineseConverMenuItem);
            chineseConverMenu.add(chineseConverMenuItem);
        }
        MenuEventService.getInstance().chineseConverActionPerformed(chineseConverMenu);

        /**
         * 主题
         */
        //menuBar.add(themesMenu);
        /*ButtonGroup buttonGroupThemes = new ButtonGroup();
        for (SystemThemesEnum value : SystemThemesEnum.values()) {
            JRadioButtonMenuItem themeRadioButtonMenuItem = new JRadioButtonMenuItem(value.getThemesKey());
            buttonGroupThemes.add(themeRadioButtonMenuItem);
            themesMenu.add(themeRadioButtonMenuItem);
        }
        MenuEventService.getInstance().setupThemesActionPerformed(frame, themesMenu);*/

        menuBar.add(helpMenu);
        helpMenu.add(updateVersionLogMenuItem = createMenuItem(updateVersionLogAction));
        helpMenu.add(privacyPolicyMenuItem = createMenuItem(privacyPolicyAction));
        //privacyPolicyMenuItem.setEnabled(false);
        helpMenu.add(officialWebsiteMenuItem = createMenuItem(officialWebsiteAction));
        helpMenu.add(expressThanksMenuItem = createMenuItem(expressThanksAction));
        //expressThanksMenuItem.setEnabled(false);
        helpMenu.add(aboutMenuItem = createMenuItem(aboutAction));
        menuBar.setVisible(applicationContext.getShowMenuBarState());
        return menuBar;
    }

    protected JMenuItem createMenuItem(Action a) {
        return new JMenuItem(a);
    }

    protected JCheckBoxMenuItem createCheckBoxMenu(Action a) {
        return new JCheckBoxMenuItem(a);
    }

    /**
     * 菜单事件初始化
     */
    private void createMenuActions() {

        ResourceBundle msg = resourceBundle;
        newTabAction = new MenuBarKit.NewTabAction();
        newTabAction.setProperties(resourceBundle, "MenuItem.NewTab");
        savePictAction = new MenuBarKit.SavePictAction();
        savePictAction.setProperties(resourceBundle, "MenuItem.SavePict");
        saveFileAction = new MenuBarKit.SaveFileAction();
        saveFileAction.setProperties(resourceBundle, "MenuItem.SaveFile");
        favoritesAction = new MenuBarKit.FavoritesAction();
        favoritesAction.setProperties(resourceBundle, "MenuItem.Favorites");
        exitAction = new MenuBarKit.ExitAction();
        exitAction.setProperties(resourceBundle, "MenuItem.Exit");

        compressAction = new MenuBarKit.CompressAction();
        compressAction.setProperties(msg, "MenuItem.Compress");
        escapeAction = new MenuBarKit.EscapeAction();
        escapeAction.setProperties(msg, "MenuItem.Escape");
        unescapeAction = new MenuBarKit.UnescapeAction();
        unescapeAction.setProperties(msg, "MenuItem.Unescape");
        formatAction = new MenuBarKit.FormatAction();
        formatAction.setProperties(msg, "MenuItem.Format");
        findAction = new MenuBarKit.FindAction();
        findAction.setProperties(msg, "MenuItem.Find");
        cleanAction = new MenuBarKit.CleanAction();
        cleanAction.setProperties(msg, "MenuItem.Clean");

        showToolBarAction = new MenuBarKit.ShowToolBarAction();
        showToolBarAction.setProperties(msg, "MenuItem.ShowToolBar");
        showMenuBarAction = new MenuBarKit.ShowMenuBarAction();
        showMenuBarAction.setProperties(msg, "MenuItem.ShowMenuBar");
        showToolBarTextAction = new MenuBarKit.ShowToolBarTextAction();
        showToolBarTextAction.setProperties(msg, "MenuItem.ShowToolBarText");

        editSetupAction = new MenuBarKit.EditSetupAction();
        editSetupAction.setProperties(msg, "MenuItem.EditSetup");
        lineSetupAction = new MenuBarKit.LineSetupAction();
        lineSetupAction.setProperties(msg, "MenuItem.LineSetup");
        showlineNumAction = new MenuBarKit.ShowlineNumAction();
        showlineNumAction.setProperties(msg, "MenuItem.Showline");
        systemSetupAction = new MenuBarKit.SystemSetupAction();
        systemSetupAction.setProperties(msg, "MenuItem.SystemSetup");

        updateVersionLogAction = new MenuBarKit.UpdateVersionLogAction();
        updateVersionLogAction.setProperties(msg, "MenuItem.FeatureIntroduction");
        privacyPolicyAction = new MenuBarKit.PrivacyPolicyAction();
        privacyPolicyAction.setProperties(msg, "MenuItem.PrivacyPolicy");
        officialWebsiteAction = new MenuBarKit.OfficialWebsiteAction();
        officialWebsiteAction.setProperties(msg, "MenuItem.OfficialWebsite");
        expressThanksAction = new MenuBarKit.ExpressThanksAction();
        expressThanksAction.setProperties(msg, "MenuItem.ExpressThanks");
        aboutAction = new MenuBarKit.AboutAction();
        aboutAction.setProperties(msg, "MenuItem.About");
    }

    private void createMenus() {
        fileMenu = new JMenu(getMessage("MenuBar.File"));
        editMenu = new JMenu(getMessage("MenuBar.Edit"));

        setupMenu = new JMenu(getMessage("MenuBar.Setting"));
        frameFontMenu = new JMenu(getMessage("MenuItem.FrameFont"));
        fontStyleMenu = new JMenu(getMessage("MenuItem.FrameFont.FontStyle"));
        fontSizeMenu = new JMenu(getMessage("MenuItem.FrameFont.FontSize"));

        facadeMenu = new JMenu(getMessage("MenuItem.facadeMenu"));
        languageMenu = new JMenu(getMessage("MenuItem.languageMenu"));

        pictureQualityMenu = new JMenu(getMessage("MenuItem.PictureQuality"));
        chineseConverMenu = new JMenu(getMessage("MenuItem.ChineseConver"));

        themesMenu = new JMenu(getMessage("MenuBar.Theme"));
        helpMenu = new JMenu(getMessage("MenuBar.Help"));
    }

    private String getMessage(String keyRoot) {
        return resourceBundle.getString(keyRoot + ".Name");
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public JCheckBoxMenuItem getShowMenuBarMenuItem() {
        return showMenuBarMenuItem;
    }

    public JCheckBoxMenuItem getShowToolBarMenuItem() {
        return showToolBarMenuItem;
    }
}
