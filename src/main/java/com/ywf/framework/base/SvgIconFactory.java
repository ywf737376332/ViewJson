package com.ywf.framework.base;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.annotation.Nonnull;
import java.awt.*;

/**
 * ColorFunctions.darken/ColorFunctions.lighten  调明亮度 , 光线越强，看上去越亮；光线越弱，看上去越暗
 * ColorFunctions.saturate/ColorFunctions.desaturate   调饱和度
 * ColorFunctions.spin 旋转颜色
 * ColorFunctions.tint  与白色混合，增加颜色鲜艳
 * ColorFunctions.shade 与黑色混合，增加颜色灰度
 * ColorFunctions.luma 计算颜色比例值0-1
 * <p>
 * svg图标管理
 */
public final class SvgIconFactory {

    /**
     * @param path 图标路径
     * @return 20*20的图标
     */
    public static FlatSVGIcon icon(String path, int width, int height) {
        ClassLoader classLoader = SvgIconFactory.class.getClassLoader();
        return new FlatSVGIcon(path, width, height, classLoader);
    }

    /**
     * @param path 图标路径
     * @return 指定颜色的svgIcon
     */
    public static FlatSVGIcon miniIcon(String path) {
        return icon(path, 10, 10);
    }

    /**
     * @param path 图标路径
     * @return 指定颜色的svgIcon
     */
    public static FlatSVGIcon smallIcon(String path) {
        return icon(path, 12, 12);
    }

    /**
     * @param path 图标路径
     * @return 指定颜色的svgIcon
     */
    public static FlatSVGIcon mediumIcon(String path) {
        return icon(path, 14, 14);
    }


    /**
     * @param path 图标路径
     * @return 指定颜色的svgIcon
     */
    public static FlatSVGIcon largeIcon(String path) {
        return icon(path, 16, 16);
    }

    /**
     * @param path  图标路径
     * @param color 颜色
     * @return 指定颜色的svgIcon
     */
    public static FlatSVGIcon icon(String path, int width, int height, @Nonnull Color color) {
        FlatSVGIcon svgIcon = icon(path, width, height);
        svgIcon.setColorFilter(new FlatSVGIcon.ColorFilter(c -> color));
        return svgIcon;
    }

    public interface SystemIcon {
        String logo = "/icons/logo.svg";
        String about = "icons/logo.svg";
        String author = "icons/auth.svg";
        String theme = "icons/theme.svg";
        String frameFont = "icons/frameFont.svg";
        String editorFont = "icons/editorFont.svg";
        String systemSet = "icons/systemSet.svg";
        String systemLog = "icons/systemLog.svg";
        String systemHotkey = "icons/systemHotkey.svg";
    }

    public interface ThemesIcon {
        String flatLafLightTheme = "icons/themes/flatLafLightTheme.svg";
        String arcLightOrange = "icons/themes/arcLightOrange.svg";
        String solarizedLight = "icons/themes/solarizedLight.svg";
        String arcDarkOrange = "icons/themes/arcDarkOrange.svg";
        String gruvboxDarkMedium = "icons/themes/gruvboxDarkMedium.svg";
        String materialDarker = "icons/themes/materialDarker.svg";
        String materialDeepOcean = "icons/themes/materialDeepOcean.svg";
        String nightOwl = "icons/themes/nightOwl.svg";
    }


    public interface FindIcon {
        String find = "icons/find.svg";
        String matchCase = "icons/matchCase.svg";
        String matchCaseHovered = "icons/matchCaseHovered.svg";
        String matchCaseSelected = "icons/matchCaseSelected.svg";
        String regex = "icons/regex.svg";
        String regexHovered = "icons/regexHovered.svg";
        String regexSelected = "icons/regexSelected.svg";
        String close = "icons/close.svg";
        String closeHovered = "icons/closeRed.svg";
    }

    public interface TextAreaMenuIcon {
        String collapseCode = "icons/collapseCode.svg";
        String newEditor = "icons/newEditer.svg";
        String closeEditor = "icons/closeTab.svg";
    }
}
