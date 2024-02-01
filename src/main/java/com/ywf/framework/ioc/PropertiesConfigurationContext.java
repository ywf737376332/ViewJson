package com.ywf.framework.ioc;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.Serializable;

/**
 * 配置类加载器
 *
 * @Author YWF
 * @Date 2024/1/31 14:59
 */
public class PropertiesConfigurationContext implements Serializable {

    private static final long serialVersionUID = 1L;

    private PropertiesConfiguration propertiesConfiguration;

    private PropertiesConfigurationContext() {
    }

    /**
     * 初始化PropertiesConfiguration类
     *
     * @param resourceUrl
     */
    public PropertiesConfigurationContext(String resourceUrl) {
        if (resourceUrl == null || resourceUrl.isEmpty()) {
            throw new RuntimeException("资源路径不能为空");
        }
        try {
            propertiesConfiguration = new PropertiesConfiguration(resourceUrl);
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载资源
     *
     * @return
     */
    public PropertiesConfiguration load() {
        try {
            propertiesConfiguration.clear();
            propertiesConfiguration.load();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
        return propertiesConfiguration;
    }

    /**
     * 保存资源
     */
    public void store() {
        try {
            propertiesConfiguration.save();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
