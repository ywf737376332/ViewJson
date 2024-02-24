package com.ywf.component;

import javax.swing.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/16 22:03
 */
public class FontSizeRadioButtonMenuItem extends JRadioButtonMenuItem {

    // 图片质量状态：1-低 2-中 3-高
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
