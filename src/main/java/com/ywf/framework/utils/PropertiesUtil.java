package com.ywf.framework.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import com.ywf.action.StateBarEventService;
import com.ywf.framework.init.SysConfigInit;
import com.ywf.pojo.ConfigurableApplicationContext;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.net.URL;
import java.util.Iterator;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/13 16:45
 */
public class PropertiesUtil {

    private static final Log LOG = LogFactory.getLog(PropertiesUtil.class);

    private PropertiesConfiguration properties;
    volatile private static PropertiesUtil instance = null;

    private PropertiesUtil() {
        getPropertiesConfiguration();
    }

    public static PropertiesUtil getInstance() {
        if (instance == null) {
            synchronized (StateBarEventService.class) {
                if (instance == null) {
                    instance = new PropertiesUtil();
                }
            }
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
            LOG.error("setValueToProperties error :", e);
        }
    }

    public String getValue(String key) {
        String res = null;
        try {
            res = properties.getString(key);
        } catch (Exception e) {
            LOG.error("getValueFromProperties error :", e);
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

    public PropertiesConfiguration load(String fileName) {
        try {
            properties.setFile(new File(fileName));
            properties.clear();
            properties.load(fileName);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public void store(String fileName, PropertiesConfiguration props) {
        try {
            props.setFile(new File(fileName));
            props.save();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {

            PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();
            String sysConfigFilePath = SysConfigInit.getApplicationRunRootPath();
            String rootPath = propertiesUtil.getResourcePath("config/application.properties");

            PropertiesConfiguration configuration = propertiesUtil.load(sysConfigFilePath);
            ConfigurableApplicationContext applicationConfig = new ConfigurableApplicationContext();
            //System.out.println("applicationConfig:"+applicationConfig.toString());
            //applicationConfig = propertiesToObject(configuration,ApplicationConfig.class);
            //System.out.println("applicationConfig:"+applicationConfig.toString());
            propertiesUtil.setValue("pictureQualityState", "12412312");

            ConfigurableApplicationContext application = ReflectUtils.propConvertObject(configuration, applicationConfig);
            //propertiesToObject(configuration,applicationConfig.getClass());
            System.out.println("application:" + application.toString());
            Iterator<String> iterator = configuration.getKeys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                System.out.println("K:" + key+" V:"+configuration.getProperty(key));
            }
            application.setScreenSize(new ConfigurableApplicationContext.ScreenSize(50, 200));
            PropertiesConfiguration targetProps = ReflectUtils.objectConvertProp(application);
            propertiesUtil.store(sysConfigFilePath, targetProps);
            //System.out.println("configuration:"+configuration.getString("pictureQualityState"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
