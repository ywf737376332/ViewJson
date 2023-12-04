package com.ywf.framework.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import com.ywf.framework.init.SysConfigInit;
import org.apache.commons.configuration.ConfigurationException;
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

    private static PropertiesUtil instance;
    private PropertiesConfiguration propertiesConfiguration;

    public PropertiesUtil() {
    }

    public static synchronized PropertiesUtil instance() {
        if (instance == null) {
            instance = new PropertiesUtil();
        }
        return instance;
    }

    /**
     * 有该键值就覆盖，没有就添加
     *
     * @param key
     * @param value
     */
    public void setValueToProperties(String key, String value) {
        try {
            PropertiesConfiguration configuration = getPropertiesConfiguration();
            configuration.setAutoSave(true);
            configuration.setProperty(key, value);
        } catch (Exception e) {
            System.out.println("setValueToProperties error : " + e.getMessage());
        }
    }

    public String getValueFromProperties(String key) {
        String res = null;
        try {
            PropertiesConfiguration configuration = getPropertiesConfiguration();
            res = configuration.getString(key);
        } catch (Exception e) {
            System.out.println("getValueFromProperties error : " + e.getMessage());
        }
        return res;
    }

    private PropertiesConfiguration getPropertiesConfiguration() {
        if (propertiesConfiguration == null) {
            try {
                propertiesConfiguration = new PropertiesConfiguration(SysConfigInit.getSysConfigFilePath());
            } catch (ConfigurationException e) {
                throw new RuntimeException(e);
            }
        }
        return propertiesConfiguration;
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
            String property = System.getProperty("user.dir" + "application.properties");
            System.out.println("resourcePath:" + property);

            String filePath2 = getResourcePath("/config/config.properties");
            System.out.println("filePath2:" + filePath2);

            ResourceBundle bundle = ResourceBundle.getBundle("/config/config.properties");
            System.out.printf("dsgfs:", bundle.getString("generateNumber"));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
