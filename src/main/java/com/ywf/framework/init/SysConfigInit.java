package com.ywf.framework.init;

import com.ywf.framework.config.GlobalMenuKEY;
import com.ywf.framework.ioc.ApplicationContext;
import com.ywf.framework.ioc.ResourceContext;
import com.ywf.framework.utils.ObjectUtils;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;

;

/**
 * 启动资源加载
 *
 * @Author YWF
 * @Date 2023/12/2 22:23
 */
public class SysConfigInit extends ApplicationContext {

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
     * 首次启动时加载配置文件并设置组件的属性值
     */
    private static void appRootConfigInitInit() {
        /**
         * 获取程序运行的根目录
         */
        String appRunUserPath = getApplicationRunRootPath();
        ResourceContext resourceUserRunContext = new ResourceContext(appRunUserPath, ResourceContext.FILE_TYPE);
        PropertiesConfiguration userRunProperties = resourceUserRunContext.getResource();
        ObjectUtils.setBean(GlobalMenuKEY.USER_PRPPERTIES_CONFIG, userRunProperties);
        /**
         * 读取系统默认的配置文件
         */
        String resourcePath = getApplicationResourcePath(DEFAULT_RESOURCE_PATH);
        ResourceContext resourceDefaultContext = new ResourceContext(resourcePath, ResourceContext.STREAM_TYPE);
        PropertiesConfiguration defaultProperties = resourceDefaultContext.getResource();
        ObjectUtils.setBean(GlobalMenuKEY.DEFAULT_PRPPERTIES_CONFIG, defaultProperties);
        Iterator<String> iterator = defaultProperties.getKeys();
        int counts = 0;
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (!userRunProperties.containsKey(key)) {
                userRunProperties.setProperty(key, defaultProperties.getProperty(key));
                counts++;
            }
        }
        if (counts > 0) {
            resourceUserRunContext.store();
        }
    }

    /**
     * 获取用户目录配置文件
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
     * 获取系统配置文件
     * config/application.properties
     *
     * @date 2023/12/3 20:04
     */
    /*public static String getApplicationResourcePath(String resourceName) {
        ClassLoader classLoader = ResourceUtil.class.getClassLoader();
        URL resourceUrl = classLoader.getResource(resourceName);
        if (resourceUrl != null) {
            return resourceUrl.getPath();
        } else {
            return null;
        }
    }*/
    public static String getApplicationResourcePath(String resourceName) {
        ClassLoader classLoader = SysConfigInit.class.getClassLoader();
        URL resourceUrl = classLoader.getResource(resourceName);
        if (resourceUrl != null) {
            return resourceUrl.getPath();
        } else {
            return null;
        }
    }


}
