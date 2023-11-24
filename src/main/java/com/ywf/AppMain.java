package com.ywf;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.ywf.view.TestDemo;

import javax.swing.*;
import java.awt.*;

/**
 * Hello world!
 *
 */
public class AppMain {
    public static void main(String[] args) {
        try {
            /**
             * FlatLightLaf.install();
             *
             * //Flat Dark
             * FlatDarkLaf.install();
             *
             * //Flat IntelliJ
             * FlatIntelliJLaf.install();
             *
             * //Flat Darcula
             * FlatDarculaLaf.install();
             */
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            //滚动条的默认宽度为 。要使它们更宽（或更小），请使用：10
            UIManager.put("ScrollBar.width", 10);
            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
            //默认情况下，滚动条的上一个/下一个箭头按钮处于隐藏状态。要使 它们对应用程序使用中的所有滚动条可见：
            UIManager.put("ScrollBar.showButtons", true);
            //按钮组件的弧度
            UIManager.put("ProgressBar.arc", 999);
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 999);
            UIManager.put("TextComponent.arc", 10);
            //聚焦组件的特殊边框表示聚焦状态
            UIManager.put("Component.focusWidth", 2);

            //控制右侧滑块
            UIManager.put("ScrollBar.trackArc", 999);

            UIManager.put("ScrollBar.thumbArc", 999);

            UIManager.put("ScrollBar.trackInsets", new Insets(2, 4, 2, 4));

            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));

            UIManager.put("ScrollBar.track", new Color(0xe0e0e0));
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        new TestDemo().createAndShowGUI();
    }
}
