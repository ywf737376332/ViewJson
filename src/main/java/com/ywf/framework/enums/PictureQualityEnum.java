package com.ywf.framework.enums;

/**
 * 图片清晰度
 *
 * @Author YWF
 * @Date 2023/12/16 22:43
 */
public enum PictureQualityEnum {
    LOW_PICTURE_QUALITY(1,"低"),
    MIDDLE_PICTURE_QUALITY(2,"中"),
    HEIGHT_PICTURE_QUALITY(3,"高")
    ;

    PictureQualityEnum(int pictureQualityState, String pictureQualityDesc) {
        this.pictureQualityState = pictureQualityState;
        this.pictureQualityDesc = pictureQualityDesc;
    }

    private int pictureQualityState;
    private String pictureQualityDesc;

    public int getPictureQualityState() {
        return pictureQualityState;
    }

    public void setPictureQualityState(int pictureQualityState) {
        this.pictureQualityState = pictureQualityState;
    }

    public String getPictureQualityDesc() {
        return pictureQualityDesc;
    }

    public void setPictureQualityDesc(String pictureQualityDesc) {
        this.pictureQualityDesc = pictureQualityDesc;
    }
}
