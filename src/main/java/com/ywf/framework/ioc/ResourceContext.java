package com.ywf.framework.ioc;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * 配置类加载器
 *
 * @Author YWF
 * @Date 2024/1/31 14:59
 */
public class ResourceContext implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int FILE_TYPE = 1;
    public static final int STREAM_TYPE = 2;

    private PropertiesConfiguration propertiesConfiguration;

    private ResourceContext() {
    }

    /**
     * 初始化PropertiesConfiguration类
     *
     * @param resourceUrl
     */
    public ResourceContext(String resourceUrl, int resourceType) {
        if (resourceUrl == null || resourceUrl.isEmpty()) {
            throw new RuntimeException("资源路径不能为空");
        }
        switch (resourceType) {
            case FILE_TYPE:
                try {
                    propertiesConfiguration = new PropertiesConfiguration(resourceUrl);
                } catch (ConfigurationException e) {
                    throw new RuntimeException("资源文件加载失败：" + e.getMessage());
                }
                break;
            case STREAM_TYPE:
                propertiesConfiguration = new PropertiesConfiguration();
                try (InputStream inputStream = ResourceContext.class.getClassLoader().getResourceAsStream(resourceUrl)) {
                    propertiesConfiguration.load(inputStream);
                } catch (IOException | ConfigurationException e) {
                    throw new RuntimeException("资源文件加载失败：" + e.getMessage());
                }
                break;
            default:
        }
    }

    /**
     * 加载资源
     *
     * @return
     */
    private PropertiesConfiguration load() {
        if (propertiesConfiguration == null) {
            throw new RuntimeException("请先创建[ PropertiesConfigurationContext ] 对象");
        }
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
        if (propertiesConfiguration == null) {
            throw new RuntimeException("请先创建[ PropertiesConfigurationContext ] 对象");
        }
        try {
            propertiesConfiguration.save();
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取资源唯一类
     *
     * @return
     */
    public PropertiesConfiguration getResource() {
        if (propertiesConfiguration == null) {
            throw new RuntimeException("请先创建[ PropertiesConfigurationContext ] 对象");
        }
        return propertiesConfiguration;
    }

}
