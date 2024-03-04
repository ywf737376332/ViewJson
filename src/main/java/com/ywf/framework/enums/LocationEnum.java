package com.ywf.framework.enums;

import javax.swing.*;
import java.awt.*;

/**
 * 工具栏位置
 *
 * @Author YWF
 * @Date 2024/3/4 13:23
 */
public enum LocationEnum {

    TOP(BorderLayout.NORTH, SwingConstants.HORIZONTAL),
    LEFT(BorderLayout.WEST, SwingConstants.VERTICAL),
    RIGHT(BorderLayout.EAST, SwingConstants.VERTICAL);

    private String layoutLocation;
    private int toolbarLocation;

    LocationEnum(String layoutLocation, int toolbarLocation) {
        this.layoutLocation = layoutLocation;
        this.toolbarLocation = toolbarLocation;
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
}
