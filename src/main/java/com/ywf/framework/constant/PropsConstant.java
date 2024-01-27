package com.ywf.framework.constant;

import java.io.File;
import java.io.Serializable;

/**
 * 系统常量
 *
 * @Author YWF
 * @Date 2023/12/2 18:23
 */
public class PropsConstant implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统配置文件路径
     */
    public static final String SYSTEM_CONFIG_HOME = System.getProperty("user.home");
    public static final String SYSTEM_CONFIG_FOLDER_PATH = File.separator + "jsonView";
    public static final String SYSTEM_CONFIG_FILE_PATH = File.separator + "jsonView.properties";

    /**
     * 系统设置配置参数
     */
    public static final String TEXTAREA_EDIT_STATE_KEY = "textAreaEditState"; // 文本编辑器是否可编辑状态KEY
    public static final String TEXTAREA_BREAK_LINE_KEY = "textAreaBreakLineState"; // 文本编辑器是否可换行KEY
    public static final String TEXTAREA_SHOW_LINE_NUM_KEY = "textAreaShowlineNumState"; // 文本编辑器是否可换行KEY
    public static final String SHOW_TOOL_BAR_KEY = "showToolBarState"; // 是否显示工具栏KEY
    public static final String SHOW_MENU_BAR_KEY = "showMenuBarState"; // 是否显示菜单栏KEY
    public static final String SCREEN_SIZE_WIDTH_KEY = "screenSizeKey.width"; // 屏幕大小记录KEY
    public static final String SCREEN_SIZE_HEIGHT_KEY = "screenSizeKey.height"; // 屏幕大小记录KEY
    public static final String TEXTAREA_CHINESE_CONVERT_STATE_KEY = "chineseConverState"; // 中文转Unicode
    public static final String SHARE_PICTURE_QUALITY_STATE_KEY = "pictureQualityState"; // 图片质量

}
