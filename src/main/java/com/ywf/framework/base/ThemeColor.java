package com.ywf.framework.base;

import cn.hutool.core.util.StrUtil;

import java.awt.*;

/**
 * @author jee
 * @version 1.0
 */
public final class ThemeColor {

    public static final Color noColor = new Color(255, 255, 255, 0);
    public static final Color themeColor = new Color(130, 128, 128, 130);
    public static final Color authorColor = new Color(156, 170, 207);
    public static final Color findSelectColor = new Color(248, 201, 171);
    public static final Color watermarkColor = new Color(130, 128, 128, 130);
    public static final Color highLightColor = new Color(255, 150, 50);
    public static final Color stateBarLabelColor = new Color(167, 179, 211);

    /**
     * @param color 颜色
     * @return toHex颜色:#263238
     */
    public static String toHEXColor(Color color) {
        String redHex = Integer.toHexString(color.getRed());
        String greenHex = Integer.toHexString(color.getGreen());
        String blueHex = Integer.toHexString(color.getBlue());
        return StrUtil.format("#{}{}{}", redHex, greenHex, blueHex).toUpperCase();
    }
}
