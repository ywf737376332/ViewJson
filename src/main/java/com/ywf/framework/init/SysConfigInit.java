package com.ywf.framework.init;

import cn.hutool.core.util.StrUtil;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.PictureQualityEnum;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.enums.TextConvertEnum;
import com.ywf.framework.handle.ApplicationContext;
import com.ywf.framework.utils.PropertiesUtil;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/2 22:23
 */
public class SysConfigInit extends ApplicationContext {

    private static PropertiesUtil systemProperties = PropertiesUtil.getInstance();
    public final static Date startTime = new Date();


    public static void initConfigToApplicationRunRoot() {
        // 系统配置文件初始化
        configFileInit(getApplicationRunRootPath());
        // 系统配置属性初始化
        appRootConfigInitInit();
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
    public static String getApplicationRunRootPath() {
        // 获取用户目录
        String userHome = SYSTEM_CONFIG_HOME;
        // 创建文件夹路径
        String folderPath = userHome + SYSTEM_CONFIG_FOLDER_PATH;
        // 创建配置文件路径
        return folderPath + SYSTEM_CONFIG_FILE_PATH;
    }

    /**
     * 首次启动时加载配置文件并设置组件的属性值
     */
    private static void appRootConfigInitInit() {

        /*ConfigurableApplicationContext application = (ConfigurableApplicationContext)instanceObject;
        application.setScreenSize(new ConfigurableApplicationContext.ScreenSize(500, 200));
        PropertiesConfiguration targetProps = RelectionUtils.objectConvertProp(application);
        propertiesUtil.store(rootPath, targetProps);*/

        /**
         * 判断文件是否存在，不存在，则直接Copy一份
         */
        // 编辑框是否可编辑
        cashValue(TEXTAREA_EDIT_STATE_KEY, "true");
        // 是否换行
        cashValue(TEXTAREA_BREAK_LINE_KEY, "false");
        // 是否转换中文
        cashValue(TEXTAREA_CHINESE_CONVERT_STATE_KEY, StrUtil.toString(TextConvertEnum.CONVERT_CLOSED.getConverType()));
        // 图片质量
        cashValue(SHARE_PICTURE_QUALITY_STATE_KEY, StrUtil.toString(PictureQualityEnum.MIDDLE_PICTURE_QUALITY.getPictureQualityState()));
        // 是否显示行号
        cashValue(TEXTAREA_SHOW_LINE_NUM_KEY, "false");
        // 显示工具栏
        cashValue(SHOW_TOOL_BAR_KEY, "true");
        cashValue(SHOW_MENU_BAR_KEY, "true");
        // 屏幕尺寸大小初始化
        cashValue(SCREEN_SIZE_WIDTH_KEY, StrUtil.toString(SystemConstant.WINDOWS_MIN_WIDTH));
        cashValue(SCREEN_SIZE_HEIGHT_KEY, StrUtil.toString(SystemConstant.WINDOWS_MIN_HEIGHT));
        // 主题初始化
        cashValue(SystemConstant.SYSTEM_THEMES_KEY, SystemThemesEnum.FlatLightLafThemesStyle.getThemesKey());


    }

    private static boolean isExists(String key) {
        return StrUtil.isEmpty(systemProperties.getValue(key));
    }

    private static void cashValue(String key, String value) {
        if (isExists(key)) {
            systemProperties.setValue(key, value);
        }
    }
}
