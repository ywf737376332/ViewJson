package com.ywf.framework.ioc;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.handle.ConfigLoadHandler;
import com.ywf.framework.init.SysConfigInit;
import com.ywf.framework.utils.ChangeUIUtils;
import com.ywf.framework.utils.ObjectUtils;
import com.ywf.view.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * 项目启动主类
 *
 * @Author YWF
 * @Date 2024/1/29 10:59
 */
public class ApplicationView {

    private final static Logger logger = LoggerFactory.getLogger(ApplicationView.class);
    private long startTime;

    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    private static MainFrame applicationView;
    private ConfigLoadHandler configLoadHandler;

    /**
     * 本地资源加注入
     */
    public ApplicationView(Class<?> primarySource) {
        startTime = System.nanoTime();
        logger.info("应用程序启动开始,当前时间：{}~", DateUtil.format(DateUtil.date(startTime/1000000000), "yyyy-MM-dd HH:mm:ss"));
        String basePackages = getBasePackages(primarySource);
        String applicationRootPath = SysConfigInit.getApplicationRunRootPath();
        /**
         * 首次启动时，加载系统配置文件到应用运行的用户根目录
         */
        SysConfigInit.initConfigToApplicationRunRoot();
        /**
         * 1、加载本地Properties配置文件到Properties对象
         * 2、然后根据配置文件和配置类的绑定关系，将相应Properties对象转换为相应的配置类
         * 3、然后将这些配置类注入到系统容器
         */
        configLoadHandler = new ConfigLoadHandler(basePackages);
        configLoadHandler.configLoadAutowired(basePackages, applicationRootPath);
        /**
         * 主界面实例化
         */
        Assert.notNull(primarySource, "PrimarySources must not be null");
        applicationView = configLoadHandler.appViewInit(basePackages);
        ObjectUtils.setBean(GlobalKEY.MAIN_FRAME, applicationView._this);
    }

    public static ApplicationContext run(Class<?> primarySource, String... args) {
        return new ApplicationView(primarySource)
                .initThemesUI()
                .run()
                .initGlobalFont()
                .cacheGlobalComponent();
    }

    /**
     * APP界面加载渲染
     *
     * @return
     */
    public ApplicationView run() {
        // 创建界面
        try {
            SwingUtilities.invokeLater(() -> {
                applicationView.createAndShowGUI(SystemConstant.SYSTEM_TITLE + SystemConstant.SYSTEM_VERSION);
            });
        } catch (Exception e) {
            logger.error("APP界面加载失败", e);
        }
        return this;
    }

    private ApplicationView initThemesUI() {
        try {
            logger.info("程序UI界面初始化,当前主题{}~", applicationContext.getLastSystemThemes());
            // 全局主题设置
            SystemThemesEnum themesStyles = SystemThemesEnum.findThemesBykey(applicationContext.getLastSystemThemes());
            ChangeUIUtils.changeUIStyle(applicationView, themesStyles);
        } catch (Exception e) {
            logger.error("初始化主题失败", e);
        }
        return this;
    }

    private ApplicationView initGlobalFont() {
        try {
            logger.info("程序UI界面初始化,当前字体{}~", applicationContext.getLastSystemThemes());
            // 全局主题设置
            ChangeUIUtils.initGlobalFont(SystemConstant.SYSTEM_DEFAULT_FONT);
            ChangeUIUtils.updateViewUI();
        } catch (Exception e) {
            logger.error("初始化字体失败", e);
        }
        return this;
    }

    private ApplicationContext cacheGlobalComponent() {
        logger.info("扫描缓存全局组件,实现真正意义上的IOC容器,此功能暂未完善");
        long endTime = System.nanoTime();
        logger.info("应用程序启动结束,当前时间:{},实际耗时：{}~", DateUtil.format(DateUtil.date(endTime/1000000000), "yyyy-MM-dd HH:mm:ss"),(endTime - startTime) / 1_000_000_000.0);
        return applicationContext;
    }


    public String getBasePackages(Class<?> primarySource) {
        return primarySource.getPackage().getName();
    }
}
