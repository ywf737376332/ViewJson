package com.ywf.framework.utils;

import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.ywf.component.DialogBuilder;
import com.ywf.component.MenuBarBuilder;
import com.ywf.component.PopupMenuBuilder;
import com.ywf.component.TextAreaBuilder;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.SystemThemesEnum;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Theme;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * TODO
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
        //主题应用
        SwingUtilities.updateComponentTreeUI(frame);
        if (PopupMenuBuilder.getInstance().getContextMenu() != null) {
            // 菜单主题应用
            SwingUtilities.updateComponentTreeUI(PopupMenuBuilder.getInstance().getContextMenu());
        }
        if (DialogBuilder.getFindDialog() != null){
            // 查找对话框主题应用
            SwingUtilities.updateComponentTreeUI(DialogBuilder.getFindDialog());
        }
        // 过渡动画
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
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
     * 系统组件整体样式定义
     *
     * @date 2023/12/2 15:35
     */
    public static void uiStyleInit() {
        //滚动条的默认宽度为 。要使它们更宽（或更小），请使用：10
        UIManager.put("ScrollBar.width", 0);
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        //默认情况下，滚动条的上一个/下一个箭头按钮处于隐藏状态。要使 它们对应用程序使用中的所有滚动条可见：
        UIManager.put("ScrollBar.showButtons", true);
        //按钮组件的弧度
        UIManager.put("ProgressBar.arc", 0);
        UIManager.put("Button.arc", 0);
        UIManager.put("Component.arc", 0);
        UIManager.put("TextComponent.arc", 0);
        //聚焦组件的特殊边框表示聚焦状态
        UIManager.put("Component.focusWidth", 0);
        //控制右侧滑块
        UIManager.put("ScrollBar.trackArc", 0);
        UIManager.put("ScrollBar.thumbArc", 0);
        UIManager.put("ScrollBar.trackInsets", new Insets(2, 4, 2, 4));
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("ScrollBar.track", new Color(0xe0e0e0));
    }
}
