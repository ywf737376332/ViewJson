package com.ywf.framework.ioc;

import java.io.File;
import java.io.Serializable;

/**
 * 系统常量
 *
 * @Author YWF
 * @Date 2023/12/2 18:23
 */
public class ApplicationContext implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 系统配置文件路径
     */
    public static final String SYSTEM_CONFIG_HOME = System.getProperty("user.home");
    public static final String SYSTEM_CONFIG_FOLDER_PATH = File.separator + "jsonView";
    public static final String SYSTEM_CONFIG_FILE_PATH = File.separator + "jsonView.properties";

    /**
     * 系统设置配置参数
     */
    public static final String USER_PATH = "user:dir#";
    public static final String DEFAULT_PATH = "root#:dir";

}