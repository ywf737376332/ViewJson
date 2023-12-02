package com.ywf.framework.utils;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/2 22:23
 */
public class SysConfigInfoUtils {
    public static void initSysConfig() {
        String configFilePath = configFileInit();
        configInitInit(configFilePath);
    }

    public static String configFileInit(){
        // 获取用户目录
        String userHome = System.getProperty("user.home");
        // 创建文件夹路径
        String folderPath = userHome + File.separator + "jsonView";
        // 创建文件夹
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        // 创建配置文件路径
        String configFilePath = folderPath + File.separator + "jsonView.properties";
        // 创建配置文件
        File configFile = new File(configFilePath);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return configFilePath;
    }
    private static void configInitInit(String configFilePath){
        // 首次启动时加载配置文件并设置组件的属性值
        if (StringUtils.isEmpty(PropertiesUtil.getValueFromProperties(configFilePath, "inEnableEdit"))){
            PropertiesUtil.setValueToProperties(configFilePath, "inEnableEdit","true");
        }
        PropertiesUtil.setValueToProperties(configFilePath, "breakLineSetup","false");
    }
}
