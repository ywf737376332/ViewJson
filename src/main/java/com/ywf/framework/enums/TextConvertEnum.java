package com.ywf.framework.enums;

/**
 * 中文转码数据
 *
 * @Author YWF
 * @Date 2023/12/15 10:02
 */
public enum TextConvertEnum {

    CONVERT_CLOSED(0, "转码功能关闭"),
    CH_TO_UN(1, "中文转Unicode"),
    UN_TO_CH(2, "Unicode转中文");

    private int converType;
    private String converDesc;

    TextConvertEnum(int converType, String converDesc) {
        this.converType = converType;
        this.converDesc = converDesc;
    }

    public static TextConvertEnum findConverEnumByState(int state){
        for (TextConvertEnum value : TextConvertEnum.values()) {
            if (state==value.converType){
                return value;
            }
        }
        return null;
    }

    public int getConverType() {
        return converType;
    }

    public void setConverType(int converType) {
        this.converType = converType;
    }

    public String getConverDesc() {
        return converDesc;
    }

    public void setConverDesc(String converDesc) {
        this.converDesc = converDesc;
    }
}
