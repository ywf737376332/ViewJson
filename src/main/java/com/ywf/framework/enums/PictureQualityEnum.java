package com.ywf.framework.enums;

/**
 * 图片清晰度
 *
 * @Author YWF
 * @Date 2023/12/16 22:43
 */
public enum PictureQualityEnum {
    LOW_PICTURE_QUALITY("MenuItem.Quality.Low", 1, "低"),
    MIDDLE_PICTURE_QUALITY("MenuItem.Quality.Middle", 2, "中"),
    HEIGHT_PICTURE_QUALITY("MenuItem.Quality.Height", 3, "高");

    PictureQualityEnum(String messageKey, int pictureQualityState, String pictureQualityDesc) {
        this.messageKey = messageKey;
        this.pictureQualityState = pictureQualityState;
        this.pictureQualityDesc = pictureQualityDesc;
    }

    private String messageKey;
    private int pictureQualityState;
    private String pictureQualityDesc;

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

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
