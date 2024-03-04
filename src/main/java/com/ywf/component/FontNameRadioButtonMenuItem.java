package com.ywf.component;

import javax.swing.*;

/**
 * 字体样式设置单选按钮
 *
 * @Author YWF
 * @Date 2023/12/16 22:03
 */
public class FontNameRadioButtonMenuItem extends JRadioButtonMenuItem {

    // 字体名称
    private String fontName;

    public FontNameRadioButtonMenuItem() {
        super();
    }

    public FontNameRadioButtonMenuItem(String text, boolean selected) {
        super(text, selected);
    }

    public FontNameRadioButtonMenuItem(String text, String fontName) {
        super(text);
        this.fontName = fontName;
    }

    public FontNameRadioButtonMenuItem(String text, String fontName, boolean selected) {
        super(text, selected);
        this.fontName = fontName;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }
}
