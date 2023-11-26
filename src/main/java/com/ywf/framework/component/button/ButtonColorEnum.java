package com.ywf.framework.component.button;

import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/25 11:53
 */
public enum ButtonColorEnum {

    // 默认按钮颜色
    DEFAULT_BUTTON_COLOR(
            new Color(0, 0, 0), new Color(0, 0, 0), new Color(255, 255, 255, 0),
            new Color(64,158,255), new Color(64,158,255), new Color(255, 255, 255, 0)
    ),

    PRIMARY_BUTTON_COLOR(
            new Color(64,158,255), new Color(64,158,255), new Color(236,245,255),
            new Color(255, 255, 255), new Color(64,158,255), new Color(64,158,255)
    ),

    DANGER_BUTTON_COLOR(
            new Color(245,108,108), new Color(245,108,108), new Color(254,240,240),
            new Color(255, 255, 255), new Color(245,108,108), new Color(245,108,108)
    ),
    SUCCESS_BUTTON_COLOR(
            new Color(103,194,58), new Color(103,194,58), new Color(240,249,235),
            new Color(255, 255, 255), new Color(103,194,58), new Color(103,194,58)
    );

    ButtonColorEnum(Color backgroundFontColor, Color backgroundBorderColor, Color backgroundColor, Color foregroundFontColor, Color foregroundBorderColor, Color foregroundColor) {
        this.backgroundFontColor = backgroundFontColor;
        this.backgroundBorderColor = backgroundBorderColor;
        this.backgroundColor = backgroundColor;
        this.foregroundFontColor = foregroundFontColor;
        this.foregroundBorderColor = foregroundBorderColor;
        this.foregroundColor = foregroundColor;
    }

    // 字体背景颜色
    private Color backgroundFontColor;

    //边框背景颜色
    private Color backgroundBorderColor;

    //背景颜色
    private Color backgroundColor;

    //前景字体颜色
    private Color foregroundFontColor;

    //前景边框颜色
    private Color foregroundBorderColor;

    //前景颜色
    private Color foregroundColor;

    public Color getBackgroundFontColor() {
        return backgroundFontColor;
    }

    public void setBackgroundFontColor(Color backgroundFontColor) {
        this.backgroundFontColor = backgroundFontColor;
    }

    public Color getBackgroundBorderColor() {
        return backgroundBorderColor;
    }

    public void setBackgroundBorderColor(Color backgroundBorderColor) {
        this.backgroundBorderColor = backgroundBorderColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getForegroundFontColor() {
        return foregroundFontColor;
    }

    public void setForegroundFontColor(Color foregroundFontColor) {
        this.foregroundFontColor = foregroundFontColor;
    }

    public Color getForegroundBorderColor() {
        return foregroundBorderColor;
    }

    public void setForegroundBorderColor(Color foregroundBorderColor) {
        this.foregroundBorderColor = foregroundBorderColor;
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }
}
