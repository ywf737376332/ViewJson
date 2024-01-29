package com.ywf.framework.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import com.ywf.framework.init.SysConfigInit;
import com.ywf.pojo.ConfigurableApplicationContext;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.net.URL;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/13 16:45
 */
public class PropertiesUtil2 {

    private static PropertiesUtil2 instance;
    private PropertiesConfiguration properties;

    private PropertiesUtil2() {
        properties = getPropertiesConfiguration();
    }

    public static synchronized PropertiesUtil2 instance() {
        if (instance == null) {
            instance = new PropertiesUtil2();
        }
        return instance;
    }

    private PropertiesConfiguration getPropertiesConfiguration() {
        if (properties == null) {
            try {
                properties = new PropertiesConfiguration();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return properties;
    }

    /**
     * 有该键值就覆盖，没有就添加
     *
     * @param key
     * @param value
     */
    public void setValue(String key, String value) {
        try {
            properties.setAutoSave(true);
            properties.setProperty(key, value);
        } catch (Exception e) {
            System.out.println("setValueToProperties error : " + e.getMessage());
        }
    }

    public String getValue(String key) {
        String res = null;
        try {
            res = properties.getString(key);
        } catch (Exception e) {
            System.out.println("getValueFromProperties error : " + e.getMessage());
        }
        return res;
    }

    public String getResourcePath(String resourceName) {
        ClassLoader classLoader = ResourceUtil.class.getClassLoader();
        URL resourceUrl = classLoader.getResource(resourceName);
        if (resourceUrl != null) {
            return resourceUrl.getPath();
        } else {
            return null;
        }
    }

    public PropertiesConfiguration load(String fileName){
        try {
            properties.setFile(new File(fileName));
            properties.load(fileName);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public void store(String fileName,PropertiesConfiguration props) {
        try {
            props.setFile(new File(fileName));
            props.save();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {

            PropertiesUtil2 propertiesUtil = PropertiesUtil2.instance();
            String sysConfigFilePath = SysConfigInit.getApplicationRunRootPath();
            String rootPath = propertiesUtil.getResourcePath("config/application.properties");

            PropertiesConfiguration configuration = propertiesUtil.load(sysConfigFilePath);
            ConfigurableApplicationContext applicationConfig = new ConfigurableApplicationContext();
            //System.out.println("applicationConfig:"+applicationConfig.toString());
            //applicationConfig = propertiesToObject(configuration,ApplicationConfig.class);
            //System.out.println("applicationConfig:"+applicationConfig.toString());
            propertiesUtil.setValue("pictureQualityState","12412312");

            ConfigurableApplicationContext application = RelectionUtils.propConvertObject(configuration, applicationConfig);
            //propertiesToObject(configuration,applicationConfig.getClass());
            System.out.println("application:" + application.toString());
            application.setScreenSize(new ConfigurableApplicationContext.ScreenSize(50,200));
            PropertiesConfiguration targetProps = RelectionUtils.objectConvertProp(application);
            propertiesUtil.store(sysConfigFilePath,targetProps);
            //System.out.println("configuration:"+configuration.getString("pictureQualityState"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
