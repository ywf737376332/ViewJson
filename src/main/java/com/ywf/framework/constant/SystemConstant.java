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
    public static final String TEXTAREA_REPLACE_BLANKSPACE_KEY = "textAreaReplaceBlankSpaceState"; // 文本编辑器是否可换行KEY
    public static final String SCREEN_SIZE_WIDTH_KEY = "screenSizeKey.width"; // 屏幕大小记录KEY
    public static final String SCREEN_SIZE_HEIGHT_KEY = "screenSizeKey.height"; // 屏幕大小记录KEY


    /**
     * 最后选定的主题
     */
    public static final String SYSTEM_THEMES_KEY = "lastSystemThemes"; // 最后一次选定的系统主题KEY
    public static final String TEXTAREA_THEMES_KEY = "lastTextAreaThemes"; // 最后一次选定的文本框主题的KEY

    /**
     * 国密SM2密钥
     */
    public final static String publicKey = "0489f68e0e9a4ff7cc5a664ff842813132d09931d80a4d7e40390b6a9754f5d5413cca075ac2d0a37613c151cd1ee82c81b20c6d9106f5f449a9333a02aa95b303";
    public final static String privateKey = "514762ec15c4f0d61970a213501c3cb31ad2efa13d00f4615fe55f9b2dc10d36";


}
