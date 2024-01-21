package com.ywf.framework.enums;

import com.ywf.framework.utils.JsonUtil;

/**
 * 内容类型
 *
 * @Author YWF
 * @Date 2024/1/21 19:28
 */
public enum TextTypeEnum {
    JSON("JSON","JSON类型"),
    URL("URL","网址类型"),
    XML("XML","XML类型"),
    TEXT("TEXT","文本类型")
    ;

    private String type;
    private String discription;

    TextTypeEnum(String type, String discription) {
        this.type = type;
        this.discription = discription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
