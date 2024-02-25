package com.ywf.pojo;

import com.ywf.framework.enums.TextTypeEnum;

import java.io.Serializable;

/**
 * 状态栏实体类
 *
 * @Author YWF
 * @Date 2024/1/23 9:30
 */
public class StateBarEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private TextTypeEnum contentType;

    private long textLength;

    public StateBarEntity(TextTypeEnum contentType, long textLength) {
        this.contentType = contentType;
        this.textLength = textLength;
    }

    public TextTypeEnum getContentType() {
        return contentType;
    }

    public void setContentType(TextTypeEnum contentType) {
        this.contentType = contentType;
    }

    public long getTextLength() {
        return textLength;
    }

    public void setTextLength(long textLength) {
        this.textLength = textLength;
    }

    @Override
    public String toString() {
        return "StateBarEntity{" +
                "contentType=" + contentType +
                ", textLength=" + textLength +
                '}';
    }
}
