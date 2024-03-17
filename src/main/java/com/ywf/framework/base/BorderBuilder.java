package com.ywf.framework.base;

import org.jdesktop.swingx.border.DropShadowBorder;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * @description: 扩展BorderFactory外常用的border
 * @author: jee
 */
public class BorderBuilder {


    public static Border leftBorder(int size, Color color) {
        return BorderFactory.createMatteBorder(0, size, 0, 0, color);
    }

    public static Border notLeftBorder(int size, Color color) {
        return BorderFactory.createMatteBorder(size, 0, size, size, color);
    }

    public static Border topBorder(int size, Color color) {
        return BorderFactory.createMatteBorder(size, 0, 0, 0, color);
    }

    public static Border topAndRightBorder(int size, Color color) {
        return BorderFactory.createMatteBorder(size, 0, 0, size, color);
    }

    public static Border notTopBorder(int size, Color color) {
        return BorderFactory.createMatteBorder(0, size, size, size, color);
    }

    public static Border rightBorder(int size, Color color) {
        return BorderFactory.createMatteBorder(0, 0, 0, size, color);
    }

    public static Border notRightBorder(int size, Color color) {
        return BorderFactory.createMatteBorder(size, size, size, 0, color);
    }

    public static Border bottomBorder(int size, Color color) {
        return BorderFactory.createMatteBorder(0, 0, size, 0, color);
    }

    public static Border notBottomBorder(int size, Color color) {
        return BorderFactory.createMatteBorder(size, size, 0, size, color);
    }

    public static Border topAndBottomBorder(int size, Color color) {
        return BorderFactory.createMatteBorder(size, 0, size, 0, color);
    }

    public static Border border(int size, Color color) {
        return BorderFactory.createMatteBorder(size, size, size, size, color);
    }

    public static Border leftAndRightBorder(int size, Color color) {
        return BorderFactory.createMatteBorder(0, size, 0, size, color);
    }

    public static Border topEmptyBorder(int size) {
        return BorderFactory.createEmptyBorder(size, 0, 0, 0);
    }

    public static Border leftEmptyBorder(int size) {
        return BorderFactory.createEmptyBorder(0, size, 0, 0);
    }

    public static Border rightEmptyBorder(int size) {
        return BorderFactory.createEmptyBorder(0, 0, 0, size);
    }

    public static Border leftAndRightEmptyBorder(int size) {
        return BorderFactory.createEmptyBorder(0, size, 0, size);
    }

    public static Border leftAndRightEmptyBorder(int leftSize,int rightSize) {
        return BorderFactory.createEmptyBorder(0, leftSize, 0, rightSize);
    }

    public static Border emptyBorder(int size) {
        return BorderFactory.createEmptyBorder(size, size, size, size);
    }

    public static Border emptyBorder(int top, int left, int bottom, int right) {
        return BorderFactory.createEmptyBorder(top, left, bottom, right);
    }

    /**
     * 替换与原组件一致的的border
     *
     * @param component 组件
     * @param border    边框
     * @return 包裹与原组件insets一致的的border
     */
    public static Border createWrapBorder(JComponent component, Border border) {
        return BorderFactory.createCompoundBorder(
                border,
                BorderFactory.createEmptyBorder(
                        component.getBorder().getBorderInsets(component).top,
                        component.getBorder().getBorderInsets(component).left,
                        component.getBorder().getBorderInsets(component).bottom,
                        component.getBorder().getBorderInsets(component).right));

    }


    /**
     * 8像素,0.6不透明度的边框
     */
    public static DropShadowBorder createShadowBorder() {
        return createShadowBorder(8, 0.6f, ThemeColor.themeColor);
    }

    /**
     * @param shadowSize    阴影大小
     * @param shadowOpacity 不透明度
     * @param shadowColor   颜色
     * @return 阴影边框
     */
    public static DropShadowBorder createShadowBorder(int shadowSize, float shadowOpacity, Color shadowColor) {
        return new DropShadowBorder(shadowColor, shadowSize, shadowOpacity, 10, false, false, true, true);
    }
}
