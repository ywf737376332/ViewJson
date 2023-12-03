package com.ywf.framework.constant;

import java.io.File;

/**
 * 系统常量
 *
 * @Author YWF
 * @Date 2023/12/2 18:23
 */
public class SystemConstant {

    /**
     * 保存文件扩展名
     */
    public static final String SAVE_JSON_EXTENSION = ".json";
    public static final String SAVE_IMAGE_EXTENSION = ".png";

    /**
     * 系统配置文件路径
     */
    public static final String SYSTEM_CONFIG_HOME = System.getProperty("user.home");
    public static final String SYSTEM_CONFIG_FOLDER_PATH = File.separator + "jsonView";
    public static final String SYSTEM_CONFIG_FILE_PATH = File.separator + "jsonView.properties";

    /**
     * 主题类型常量
     */
    public static final int THEMES_TYPE_SYSTEM = 1;   // 系统主题
    public static final int THEMES_TYPE_OTHER = 2;    // 其他第三方自定义主题

    /**
     * 系统设置配置参数
     */
    public static final String TEXTAREA_EDIT_STATE_KEY = "textAreaEditState"; // 文本编辑器是否可编辑状态KEY
    public static final String TEXTAREA_BREAK_LINE_KEY = "textAreaBreakLineState"; // 文本编辑器是否可换行KEY

}
