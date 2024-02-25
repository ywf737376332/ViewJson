package com.ywf.component;

import javax.swing.*;

/**
 * 字体样式设置单选按钮
 *
 * @Author YWF
 * @Date 2023/12/16 22:03
 */
public class FontSizeRadioButtonMenuItem extends JRadioButtonMenuItem {

    // 字体大小
    private int fontSize;

    public FontSizeRadioButtonMenuItem() {
        super();
    }

    public FontSizeRadioButtonMenuItem(String text, boolean selected) {
        super(text, selected);
    }

    public FontSizeRadioButtonMenuItem(String text, int fontSize) {
        super(text);
        this.fontSize = fontSize;
    }

    public FontSizeRadioButtonMenuItem(String text, int fontSize, boolean selected) {
        super(text, selected);
        this.fontSize = fontSize;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
