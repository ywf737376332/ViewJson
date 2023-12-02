package com.ywf.framework.utils;

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
     * @date 2023/12/2 15:35
     *
     * @param frame
     * @param style
     */
    public static void changeUIStyle(JFrame frame, String style) {
        try {
            // 该表系统主题整体外观
            UIManager.setLookAndFeel(style);
            // 系统组件整体样式定义
            uiStyleInit();
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        SwingUtilities.updateComponentTreeUI(frame);
    }

    /**
     * 富文本组件主题改变
     * @date 2023/12/2 15:32
     *
     * @param frame
     * @param style
     */
    public static void changeTextAreaThemes(JFrame frame, String style) {
        RSyntaxTextArea rSyntaxTextArea = ComponentScanUtils.getComponentByType(frame, RSyntaxTextArea.class);
        try {
            Theme theme = Theme.load(ChangeUIUtils.class.getResourceAsStream(style));
            theme.apply(rSyntaxTextArea);
        } catch (IOException e) {
            System.err.println("textAreaThemes apply error");
        }
    }

    /**
     * 系统组件整体样式定义
     * @date 2023/12/2 15:35
     *
     */
    public static void uiStyleInit(){
        System.out.println("系统主题改变");
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
