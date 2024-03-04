package com.ywf.framework.enums;

/**
 * 中文转码数据
 *
 * @Author YWF
 * @Date 2023/12/15 10:02
 */
public enum TextConvertEnum {

    CONVERT_CLOSED("MenuItem.Chinese.ConvertClosed",0, "转码功能关闭"),
    CH_TO_UN("MenuItem.Chinese.ChToUn",1, "中文转Unicode"),
    UN_TO_CH("MenuItem.Chinese.UnToCh",2, "Unicode转中文");

    private String messageKey;
    private int converType;
    private String converDesc;

    TextConvertEnum(String messageKey,int converType, String converDesc) {
        this.messageKey = messageKey;
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

    public String getMessageKey() {
        return messageKey;
    }

    public int getConverType() {
        return converType;
    }

    public String getConverDesc() {
        return converDesc;
    }
}
