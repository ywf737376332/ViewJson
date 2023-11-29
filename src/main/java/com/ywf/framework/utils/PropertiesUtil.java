package com.ywf.framework.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/13 16:45
 */
public class PropertiesUtil {

    private static final String CONFIG_FILE = "/config/config.properties";

    /**
     * 有该键值就覆盖，没有就添加
     * @param key
     * @param value
     */
    public static void setValueToProperties(String path, String key, String value) {
        try {
            PropertiesConfiguration propsConfig = new PropertiesConfiguration(path);
            propsConfig.setAutoSave(true);
            propsConfig.setProperty(key, value);
        } catch (Exception e) {
            System.out.println("setValueToProperties error : " + e.getMessage());
        }
    }

    /**
     * 直接添加键值，不会覆盖旧值
     * @param key
     * @param value
     */
    public static void addValueToProperties(String key, String value) {
        try {
            String path = getResourcePath(CONFIG_FILE);
            PropertiesConfiguration propsConfig = new PropertiesConfiguration(path);
            // 修改属性之后自动保存，省去了propsConfig.save()过程
            propsConfig.setAutoSave(true);
            propsConfig.addProperty(key, value);
        } catch (Exception e) {
            System.out.println("addValueToProperties error : " + e.getMessage());
        }
    }


    public static String getValueFromProperties(String path, String key) {
        String res = null;
        try {
            PropertiesConfiguration propsConfig = new PropertiesConfiguration(path);
            res = propsConfig.getString(key);
        } catch (Exception e) {
            System.out.println("getValueFromProperties error : " + e.getMessage());
        }
        return res;
    }

    public static String getResourcePath(String resourceName) {
        ClassLoader classLoader = ResourceUtil.class.getClassLoader();
        URL resourceUrl = classLoader.getResource(resourceName);
        if (resourceUrl != null) {
            return resourceUrl.getPath();
        } else {
            return null;
        }
    }
    public static void main(String[] args) {
        try {
            //String name2 = getValueFromProperties("name2");
            //System.out.println(name2);
            // 获取resource文件的路径
            String property = System.getProperty("user.dir"+"application.properties");
            System.out.println("resourcePath:"+property);

            String filePath2 = getResourcePath("/config/config.properties");
            System.out.println("filePath2:"+filePath2);

            ResourceBundle bundle = ResourceBundle.getBundle("/config/config.properties");
            System.out.printf("dsgfs:", bundle.getString("generateNumber"));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
