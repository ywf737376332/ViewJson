package com.ywf.framework.utils;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.ywf.component.MenuBarBuilder;
import com.ywf.component.PopupMenuBuilder;
import com.ywf.component.TextAreaBuilder;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.SystemThemesEnum;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 全局主题应用
 *
 * @Author YWF
 * @Date 2023/11/30 22:17
 */
public class ChangeUIUtils {

    /**
     * 主题改变工具类类
     *
     * @param frame
     * @param themesStyles
     * @date 2023/12/2 15:35
     */
    public static void changeUIStyle(JFrame frame, SystemThemesEnum themesStyles) {
        //全局字体设置 小米兰亭 幼圆 华文中宋 黑体 等线
        initGlobalFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
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


    private static void initGlobalTheme(SystemThemesEnum themesStyles) {
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
     * @param frame
     * @param style
     * @date 2023/12/2 15:32
     */
    public static void changeTextAreaThemes(JFrame frame, String style) {
        RSyntaxTextArea rSyntaxTextArea = TextAreaBuilder.getSyntaxTextArea();
        try {
            Theme theme = Theme.load(ChangeUIUtils.class.getResourceAsStream(style));
            theme.apply(rSyntaxTextArea);
        } catch (IOException e) {
            System.err.println("textAreaThemes apply error");
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
            System.out.println("fontFamilyName:" + fontFamilyName);
        }
    }
}
