package com.ywf.framework.utils;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.ywf.component.JSONRSyntaxTextArea;
import com.ywf.component.JTabbedSplitEditor;
import com.ywf.component.MenuBarBuilder;
import com.ywf.component.PopupMenuBuilder;
import com.ywf.framework.config.GlobalMenuKEY;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.SystemThemesEnum;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;

/**
 * 全局主题应用
 *
 * @Author YWF
 * @Date 2023/11/30 22:17
 */
public class ChangeUIUtils {

    private final static Logger logger = LoggerFactory.getLogger(ChangeUIUtils.class);

    /**
     * 主题改变工具类类
     *
     * @param frame
     * @param themesStyles
     * @date 2023/12/2 15:35
     */
    public static void changeUIStyle(JFrame frame, SystemThemesEnum themesStyles) {
        initUIStyle();
        // 全局主题应用
        initGlobalTheme(themesStyles);
        // 其他个别组件主题应用
        initGlobalOtherTheme(frame);
        // 更新UI
        updateViewUI();
    }

    /**
     * 更新当前UI的外观
     * 隐藏动画快照，以确保在更新过程中不会出现闪烁或不连贯的视觉效果,可以提供更平滑和一致的用户体验
     */
    public static void updateViewUI() {
        FlatLaf.updateUI();
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }


    public static void initGlobalTheme(SystemThemesEnum themesStyles) {
        try {
            if (SystemConstant.THEMES_TYPE_SYSTEM == themesStyles.getThemeType()) {
                // 系统主题整体外观
                UIManager.setLookAndFeel(themesStyles.getThemesStyles());
            } else {
                //第三方主题
                IntelliJTheme.setup(MenuBarBuilder.class.getResourceAsStream(themesStyles.getThemesStyles()));
            }
        } catch (Exception ex) {
            System.err.println("皮肤应用失败，请检查：" + ex.getMessage());
        }
    }

    private static void initGlobalOtherTheme(JFrame frame) {
        if (PopupMenuBuilder.getInstance().getContextMenu() != null) {
            // 菜单主题应用
            SwingUtilities.updateComponentTreeUI(PopupMenuBuilder.getInstance().getContextMenu());
        }
        //树组件主题应用
        SwingUtilities.updateComponentTreeUI(frame);
    }

    /**
     * 富文本组件主题改变
     *
     * @param style
     * @date 2023/12/2 15:32
     */
    public static void changeTextAreaThemes(String style) {
        JTabbedSplitEditor tabbedSplitEditor = ObjectUtils.getBean(GlobalMenuKEY.TABBED_SPLIT_EDITOR);
        LinkedList<JScrollPane> sp = tabbedSplitEditor.getPages();
        for (JScrollPane scrollPane : sp) {
            JSONRSyntaxTextArea rSyntaxTextArea = ComponentUtils.convertEditor(scrollPane);
            try {
                Theme theme = Theme.load(ChangeUIUtils.class.getResourceAsStream(style));
                theme.apply(rSyntaxTextArea);
            } catch (IOException e) {
                System.err.println("textAreaThemes apply error");
            }
        }
    }

    /**
     * 统一设置字体，父界面设置之后，所有由父界面进入的子界面都不需要再次设置字体
     */
    public static void initGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }

        }
    }

    /**
     * 获取系统字体列表
     */
    public static void getSystemFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] availableFontFamilyNames = ge.getAvailableFontFamilyNames();
        for (String fontFamilyName : availableFontFamilyNames) {
            logger.info("系统字体：{}", fontFamilyName);
        }
    }

    /**
     * 系统组件整体样式定义
     *
     * @date 2023/12/2 15:35
     */
    public static void initUIStyle() {
        //滚动条的默认宽度为 。要使它们更宽（或更小），请使用：10
        UIManager.put("ScrollBar.width", 1);
        //UIManager.put("ScrollBar.thumbInsets", new Insets(20, 20, 20, 20));
        //默认情况下，滚动条的上一个/下一个箭头按钮处于隐藏状态。要使 它们对应用程序使用中的所有滚动条可见：
        //UIManager.put("ScrollBar.showButtons", true);
        //按钮组件的弧度
        //UIManager.put("ProgressBar.arc", 0);
        //UIManager.put("Button.arc", 0);
        //UIManager.put("Component.arc", 0);
        //UIManager.put("TextComponent.arc", 0);
        //聚焦组件的特殊边框表示聚焦状态
        //UIManager.put("Component.focusWidth", 0);
        //控制右侧滑块
        //UIManager.put("ScrollBar.trackArc", 20);
        //UIManager.put("ScrollBar.thumbArc", 20);
        //UIManager.put("ScrollBar.trackInsets", new Insets(20, 40, 20, 40));
        //UIManager.put("ScrollBar.thumbInsets", new Insets(20, 20, 20, 20));
        //UIManager.put("ScrollBar.track", new Color(0xe0e0e0));
    }
}
