package com.ywf.framework.init;

import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.utils.PropertiesUtil;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/2 22:23
 */
public class SysConfigInit {

    private static PropertiesUtil systemProperties = PropertiesUtil.instance();

    public static void initSysConfig() {
        // 系统配置文件初始化
        configFileInit(getSysConfigFilePath());
        // 系统配置属性初始化
        configInitInit();
    }

    private static void configFileInit(String configFilePath) {
        File file = new File(configFilePath);
        //判断父路径是否存在，不存在，则创建
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        // 创建配置文件
        File configFile = new File(configFilePath);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("系统配置文件文件初始化失败");
            }
        }
    }

    /**
     * 获取系统配置文件
     *
     * @date 2023/12/3 20:04
     */
    public static String getSysConfigFilePath() {
        // 获取用户目录
        String userHome = SystemConstant.SYSTEM_CONFIG_HOME;
        // 创建文件夹路径
        String folderPath = userHome + SystemConstant.SYSTEM_CONFIG_FOLDER_PATH;
        // 创建配置文件路径
        return folderPath + SystemConstant.SYSTEM_CONFIG_FILE_PATH;
    }

    private static void configInitInit() {
        // 首次启动时加载配置文件并设置组件的属性值
        if (StringUtils.isEmpty(systemProperties.getValueFromProperties(SystemConstant.TEXTAREA_EDIT_STATE_KEY))) {
            // 是否可编辑
            systemProperties.setValueToProperties(SystemConstant.TEXTAREA_EDIT_STATE_KEY, "true");
        }
        if (StringUtils.isEmpty(systemProperties.getValueFromProperties(SystemConstant.TEXTAREA_BREAK_LINE_KEY))) {
            // 是否换行
            systemProperties.setValueToProperties(SystemConstant.TEXTAREA_BREAK_LINE_KEY, "false");
        }
        //if (StringUtils.isEmpty(systemProperties.getValueFromProperties(SystemConstant.TEXTAREA_REPLACE_BLANKSPACE_KEY))) {
        //    // 是否替换空格
        //    systemProperties.setValueToProperties(SystemConstant.TEXTAREA_REPLACE_BLANKSPACE_KEY, "false");
        //}
        if (StringUtils.isEmpty(systemProperties.getValueFromProperties(SystemConstant.SHOW_TOOL_BAR_KEY))) {
            // 显示工具栏
            systemProperties.setValueToProperties(SystemConstant.SHOW_TOOL_BAR_KEY, "true");
            systemProperties.setValueToProperties(SystemConstant.SHOW_MENU_BAR_KEY, "true");
        }
        // 屏幕尺寸大小初始化
        if (StringUtils.isEmpty(systemProperties.getValueFromProperties(SystemConstant.SCREEN_SIZE_WIDTH_KEY))) {
            systemProperties.setValueToProperties(SystemConstant.SCREEN_SIZE_WIDTH_KEY, "800");
        }
        if (StringUtils.isEmpty(systemProperties.getValueFromProperties(SystemConstant.SCREEN_SIZE_HEIGHT_KEY))) {
            systemProperties.setValueToProperties(SystemConstant.SCREEN_SIZE_HEIGHT_KEY, "600");
        }

        // 主题初始化
        if (StringUtils.isEmpty(systemProperties.getValueFromProperties(SystemConstant.SYSTEM_THEMES_KEY))) {
            systemProperties.setValueToProperties(SystemConstant.SYSTEM_THEMES_KEY, SystemThemesEnum.FlatLightLafThemesStyle.getThemesKey());
        }
    }
}
