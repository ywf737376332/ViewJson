package com.ywf.framework.enums;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/30 22:36
 */
public enum SystemThemesEnum {

    FlatLightLafThemesStyle("FlatLaf Light","com.formdev.flatlaf.FlatLightLaf","/themes/textAreaThemes/ideaLight.xml"),
    FlatDarkLafThemesStyle("FlatLaf Dark","com.formdev.flatlaf.FlatDarkLaf","/themes/textAreaThemes/ideaDark.xml"),
    FlatIntelliJLafThemesStyle("FlatLaf IntelliJ","com.formdev.flatlaf.FlatIntelliJLaf","/themes/textAreaThemes/ideaLight.xml"),
    FlatDarculaLafThemesStyle("FlatLaf Darcula","com.formdev.flatlaf.FlatDarculaLaf","/themes/textAreaThemes/ideaDark.xml")
    ;
    private String themesKey;
    private String themesStyles;
    private String textAreaStyles;

    SystemThemesEnum(String themesKey, String themesStyles, String textAreaStyles) {
        this.themesKey = themesKey;
        this.themesStyles = themesStyles;
        this.textAreaStyles = textAreaStyles;
    }

    public static SystemThemesEnum findThemesBykey(String themesKey){
        for (SystemThemesEnum value : SystemThemesEnum.values()) {
            if (themesKey.equalsIgnoreCase(value.getThemesKey())){
                return value;
            }
        }
        return null;
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

    public String getTextAreaStyles() {
        return textAreaStyles;
    }

    public void setTextAreaStyles(String textAreaStyles) {
        this.textAreaStyles = textAreaStyles;
    }
}
