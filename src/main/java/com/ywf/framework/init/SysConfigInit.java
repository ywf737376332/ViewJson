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




    }
}
