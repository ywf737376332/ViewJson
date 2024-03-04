package com.ywf.framework.enums;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * 工具栏位置
 *
 * @Author YWF
 * @Date 2024/3/4 13:23
 */
public enum LocationEnum {

    TOP(BorderLayout.NORTH, SwingConstants.HORIZONTAL, BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(130, 128, 128, 130))),
    LEFT(BorderLayout.WEST, SwingConstants.VERTICAL, BorderFactory.createMatteBorder(1, 0, 0, 1, new Color(130, 128, 128, 130)));

    private String layoutLocation;
    private int toolbarLocation;
    private Border border;

    LocationEnum(String layoutLocation, int toolbarLocation, Border border) {
        this.layoutLocation = layoutLocation;
        this.toolbarLocation = toolbarLocation;
        this.border = border;
    }

    public static Integer convertToolbarLocation(String layoutLocation) {
        for (LocationEnum value : LocationEnum.values()) {
            if (layoutLocation.equalsIgnoreCase(value.getLayoutLocation())) {
                return value.getToolbarLocation();
            }
        }
        return LocationEnum.TOP.getToolbarLocation();
    }

    public static String convertLayoutLocation(int toolbarLocation) {
        for (LocationEnum value : LocationEnum.values()) {
            if (toolbarLocation == value.getToolbarLocation()) {
                return value.getLayoutLocation();
            }
        }
        return LocationEnum.TOP.getLayoutLocation();
    }

    public static Border convertToolBarBorder(String layoutLocation) {
        for (LocationEnum value : LocationEnum.values()) {
            if (layoutLocation.equalsIgnoreCase(value.getLayoutLocation())) {
                return value.getBorder();
            }
        }
        return LocationEnum.TOP.getBorder();
    }

    public String getLayoutLocation() {
        return layoutLocation;
    }

    public void setLayoutLocation(String layoutLocation) {
        this.layoutLocation = layoutLocation;
    }

    public int getToolbarLocation() {
        return toolbarLocation;
    }

    public void setToolbarLocation(int toolbarLocation) {
        this.toolbarLocation = toolbarLocation;
    }

    public Border getBorder() {
        return border;
    }

    public void setBorder(Border border) {
        this.border = border;
    }
}
