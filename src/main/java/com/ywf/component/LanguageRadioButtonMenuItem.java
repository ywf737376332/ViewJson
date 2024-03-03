package com.ywf.component;

import javax.swing.*;

/**
 * 语言单选按钮
 *
 * @Author YWF
 * @Date 2023/12/16 22:03
 */
public class LanguageRadioButtonMenuItem extends JRadioButtonMenuItem {

    private String languageKey;

    public LanguageRadioButtonMenuItem() {
        super();
    }

    public LanguageRadioButtonMenuItem(String text, boolean selected) {
        super(text, selected);
    }

    public LanguageRadioButtonMenuItem(String text, String languageKey) {
        super(text);
        this.languageKey = languageKey;
    }

    public String getLanguageKey() {
        return languageKey;
    }

    public void setLanguageKey(String languageKey) {
        this.languageKey = languageKey;
    }
}
