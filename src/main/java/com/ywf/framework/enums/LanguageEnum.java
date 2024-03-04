package com.ywf.framework.enums;

/**
 * 语言
 *
 * @Author YWF
 * @Date 2024/1/31 11:40
 */
public enum LanguageEnum {

    CHINESE("MenuItem.Chinese","zh","CN"),
    ENGLISH("MenuItem.English","en","US");

    private String messageKey;
    private String language;
    private String country;

    LanguageEnum(String messageKey, String language, String country) {
        this.messageKey = messageKey;
        this.language = language;
        this.country = country;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
