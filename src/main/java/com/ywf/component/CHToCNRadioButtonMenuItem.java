package com.ywf.component;

import javax.swing.*;

/**
 * 中文转码状态单选按钮
 *
 * @Author YWF
 * @Date 2023/12/16 22:03
 */
public class CHToCNRadioButtonMenuItem extends JRadioButtonMenuItem {

    // 中文转码状态 1-中文转Unicode 2-Unicode转中文 3-不转码
    private int chineseConverState;

    public CHToCNRadioButtonMenuItem() {
        super();
    }

    public CHToCNRadioButtonMenuItem(String text, boolean selected) {
        super(text, selected);
    }

    public CHToCNRadioButtonMenuItem(String text, int chineseConverState) {
        super(text);
        this.chineseConverState = chineseConverState;
    }

    public int getChineseConverState() {
        return chineseConverState;
    }

    public void setChineseConverState(int chineseConverState) {
        this.chineseConverState = chineseConverState;
    }
}
