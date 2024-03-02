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
import com.ywf.framework.utils.StrUtils;
import com.ywf.view.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

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

    private MainFrame applicationView;
    private ConfigLoadHandler configLoadHandler;

    /**
     * 本地资源加注入
     */
    public ApplicationView(Class<?> primarySource) {
        startTime = System.nanoTime();
        logger.info("应用程序启动开始,当前时间：{}~", DateUtil.now());
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
                .i18NInit()
                .appGuiInit()
                .initGUIGlobalFont()
                .cacheGlobalComponent();
    }

    /**
     * APP界面加载渲染
     *
     * @return
     */
    public ApplicationView appGuiInit() {
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

    /**
     * 国际化
     *
     * @return
     */
    public ApplicationView i18NInit() {
        try {
            String[] language = StrUtils.strSplit(applicationContext.getSystemLanguage());
            Locale.setDefault(new Locale(language[0], language[1]));
        } catch (Exception e) {
            logger.error("APP国际化失败", e);
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

    private ApplicationView initGUIGlobalFont() {
        try {
            logger.info("程序UI界面字体初始化,当前字体{}", applicationContext.getFontStyle());
            // 全局字体设置
            ConfigurableApplicationContext.FontStyle fontStyle = applicationContext.getFontStyle();
            ChangeUIUtils.changeGlobalFont(new Font(fontStyle.getName(), Font.PLAIN, fontStyle.getSize()));
            ChangeUIUtils.updateViewUI();
        } catch (Exception e) {
            logger.error("初始化字体失败", e);
        }
        return this;
    }

    private ApplicationContext cacheGlobalComponent() {
        logger.info("扫描缓存全局组件,实现真正意义上的IOC容器,此功能暂未完善");
        long endTime = System.nanoTime();
        logger.info("应用程序启动结束,当前时间:{},实际耗时:{}毫秒~~", DateUtil.now(), (endTime - startTime) / 1_000_000);
        return applicationContext;
    }


    public String getBasePackages(Class<?> primarySource) {
        return primarySource.getPackage().getName();
    }
}
