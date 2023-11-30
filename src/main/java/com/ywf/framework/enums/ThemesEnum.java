package com.ywf.framework.enums;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/30 22:36
 */
public enum ThemesEnum {

    FlatLightLafThemesStyle("FlatLaf Light","com.formdev.flatlaf.FlatLightLaf"),
    FlatDarkLafThemesStyle("FlatLaf Dark","com.formdev.flatlaf.FlatDarkLaf"),
    FlatIntelliJLafThemesStyle("FlatLaf IntelliJ","com.formdev.flatlaf.FlatIntelliJLaf"),
    FlatDarculaLafThemesStyle("FlatLaf Darcula","com.formdev.flatlaf.FlatDarculaLaf")
    ;
    private String themesKey;
    private String themesStyles;

    ThemesEnum(String themesKey, String themesStyles) {
        this.themesKey = themesKey;
        this.themesStyles = themesStyles;
    }

    public String getThemesKey() {
        return themesKey;
    }

    public void setThemesKey(String themesKey) {
        this.themesKey = themesKey;
    }

    public String getThemesStyles() {
        return themesStyles;
    }

    public void setThemesStyles(String themesStyles) {
        this.themesStyles = themesStyles;
    }
}
