package com.ywf.framework.enums;

import com.ywf.framework.constant.SystemConstant;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/30 22:36
 */
public enum SystemThemesEnum {

    FlatLightLafThemesStyle("FlatLaf Light", SystemConstant.THEMES_TYPE_SYSTEM, "com.formdev.flatlaf.FlatLightLaf", "/themes/textAreaThemes/ideaLight.xml"),
    FlatDarkLafThemesStyle("FlatLaf Dark", SystemConstant.THEMES_TYPE_SYSTEM, "com.formdev.flatlaf.FlatDarkLaf", "/themes/textAreaThemes/ideaDark.xml"),
    OtherArcThemeOrangeThemesStyle("Arc Light Orange", SystemConstant.THEMES_TYPE_OTHER, "/themes/arcThemeOrange.theme.json", "/themes/textAreaThemes/arcLightOrange.xml"),
    OtherSolarizedLightThemesStyle("Solarized Light", SystemConstant.THEMES_TYPE_OTHER, "/themes/solarizedLight.theme.json", "/themes/textAreaThemes/solarizedLight.xml"),
    OtherArcThemeDarkOrangeThemesStyle("Arc Dark Orange", SystemConstant.THEMES_TYPE_OTHER, "/themes/arcThemeDarkOrange.theme.json", "/themes/textAreaThemes/arcDarkOrange.xml"),
    OtherGruvboxDarkMediumThemesStyle("Gruvbox Dark Medium", SystemConstant.THEMES_TYPE_OTHER, "/themes/gruvboxDarkMedium.theme.json", "/themes/textAreaThemes/gruvBoxDark.xml"),
    OtherGitHubThemesStyle("GitHub Light", SystemConstant.THEMES_TYPE_OTHER, "/themes/gitHub.theme.json", "/themes/textAreaThemes/gitHubLight.xml"),
    OtherMaterialDarkerThemesStyle("Material Darker", SystemConstant.THEMES_TYPE_OTHER, "/themes/materialDarker.theme.json", "/themes/textAreaThemes/materialDarker.xml"),
    OtherMaterialDeepOceanThemesStyle("Material Deep Ocean", SystemConstant.THEMES_TYPE_OTHER, "/themes/materialDeepOcean.theme.json", "/themes/textAreaThemes/materialDeepOcean.xml"),
    OtherNightOwlThemesStyle("Night Owl", SystemConstant.THEMES_TYPE_OTHER, "/themes/nightOwl.theme.json", "/themes/textAreaThemes/nightOwl.xml");
    //主題索引
    private String themesKey;
    //主题应用类型(两种主题生肖方式不同) 1-系统主题 2-第三方主题
    private int themeType;
    //主题样式(系统主题为类的全路径，第三方主题为xml文件位置)
    private String themesStyles;
    //主题对应的富文本编辑主题
    private String textAreaStyles;

    SystemThemesEnum(String themesKey, int themeType, String themesStyles, String textAreaStyles) {
        this.themesKey = themesKey;
        this.themeType = themeType;
        this.themesStyles = themesStyles;
        this.textAreaStyles = textAreaStyles;
    }

    public static SystemThemesEnum findThemesBykey(String themesKey) {
        for (SystemThemesEnum value : SystemThemesEnum.values()) {
            if (themesKey.equalsIgnoreCase(value.getThemesKey())) {
                return value;
            }
        }
        //默认返回此主题
        return SystemThemesEnum.FlatLightLafThemesStyle;
    }


    public String getThemesKey() {
        return themesKey;
    }

    public void setThemesKey(String themesKey) {
        this.themesKey = themesKey;
    }

    public int getThemeType() {
        return themeType;
    }

    public void setThemeType(int themeType) {
        this.themeType = themeType;
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
