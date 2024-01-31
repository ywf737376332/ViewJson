package com.ywf.framework.enums;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/1/31 11:40
 */
public enum ValidEnum {

    NOT_EMPTY("字段不能为空"),
    NOT_LIMIT("不做限制");

    String message;

    ValidEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
