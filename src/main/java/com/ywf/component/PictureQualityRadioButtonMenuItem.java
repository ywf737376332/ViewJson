package com.ywf.component;

import javax.swing.*;

/**
 * 图片质量单选按钮
 *
 * @Author YWF
 * @Date 2023/12/16 22:03
 */
public class PictureQualityRadioButtonMenuItem extends JRadioButtonMenuItem {

    // 图片质量状态：1-低 2-中 3-高
    private int pictureQualityState;

    public PictureQualityRadioButtonMenuItem() {
        super();
    }

    public PictureQualityRadioButtonMenuItem(String text, boolean selected) {
        super(text, selected);
    }

    public PictureQualityRadioButtonMenuItem(String text, int pictureQualityState) {
        super(text);
        this.pictureQualityState = pictureQualityState;
    }

    public int getPictureQualityState() {
        return pictureQualityState;
    }

    public void setPictureQualityState(int pictureQualityState) {
        this.pictureQualityState = pictureQualityState;
    }
}
