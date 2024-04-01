package com.ywf.framework.ioc;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.ywf.action.FrameWindowCloseEventService;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.constant.MessageConstant;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.handle.ConfigLoadHandler;
import com.ywf.framework.init.SysConfigInit;
import com.ywf.framework.utils.ChangeUIUtils;
import com.ywf.framework.utils.ObjectUtils;
import com.ywf.framework.utils.StrUtils;
import com.ywf.view.MainFrame;
import com.ywf.view.Welcome;
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
    private final static Welcome welcome = new Welcome();

    /**
     * 本地资源加注入
     */
    public ApplicationView(Class<?> primarySource) {
        startTime = System.nanoTime();
        welcome.start();
        logger.info("应用程序启动开始,当前时间：{}~", DateUtil.now());
        String basePackages = getBasePackages(primarySource);
        String applicationRootPath = SysConfigInit.getSystemRootFilePath();
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
        welcome.setAppRunLog("容器初始化完成，属性注入成功...");
        /**
         * 国际化语言设置,必须在构造方法中执行,提前到国际化类加载之前
         */
        i18NInit();
        /**
         * 主界面实例化
         */
        Assert.notNull(primarySource, "PrimarySources must not be null");
        applicationView = configLoadHandler.appViewInit(basePackages);
        ObjectUtils.setBean(GlobalKEY.MAIN_FRAME, applicationView._this);
        // 注册系统关闭保存配置信息的钩子方法
        FrameWindowCloseEventService.saveApplicationConfiguration(applicationContext);
    }

    public static ApplicationContext run(Class<?> primarySource, String... args) {
        return new ApplicationView(primarySource)
                .initThemesUI()
                .initGUIGlobalFont()
                .appGuiInit()
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
                logger.info("程序UI界面绘制~~~");
                applicationView.createAndShowGUI(MessageConstant.SYSTEM_TITLE + SystemConstant.SYSTEM_VERSION);
                welcome.setAppRunLog("程序UI界面加载成功...");
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
    public void i18NInit() {
        try {
            String[] language = StrUtils.strSplit(applicationContext.getSystemLanguage());
            Locale.setDefault(new Locale(language[0], language[1]));
            JComponent.setDefaultLocale(Locale.getDefault());
            welcome.setAppRunLog("国际化资源加载成功...");
            logger.info("国际化资源语言设置：{}，{}", language, Locale.getDefault());
        } catch (Exception e) {
            logger.error("APP国际化失败", e);
        }
    }

    private ApplicationView initThemesUI() {
        SwingWorker<Boolean, Void> swingWorker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                try {
                    logger.info("程序UI主题初始化,当前主题{}~", applicationContext.getLastSystemThemes());
                    // 全局主题设置
                    SystemThemesEnum themesStyles = SystemThemesEnum.findThemesBykey(applicationContext.getLastSystemThemes());
                    ChangeUIUtils.changeUIStyle(applicationView, themesStyles);
                } catch (Exception e) {
                    logger.error("初始化主题失败", e);
                }
                welcome.setAppRunLog("主题加载成功...");
                return true;
            }
        };
        swingWorker.execute();
        return this;
    }

    private ApplicationView initGUIGlobalFont() {
        try {
            logger.info("程序UI字体初始化,当前字体{}", applicationContext.getFontStyle());
            // 全局字体设置
            ConfigurableApplicationContext.FontStyle fontStyle = applicationContext.getFontStyle();
            ChangeUIUtils.changeGlobalFont(new Font(fontStyle.getName(), fontStyle.getStyle(), fontStyle.getSize()));
        } catch (Exception e) {
            logger.error("初始化字体失败", e);
        }
        welcome.setAppRunLog("字体初始化成功...");
        return this;
    }

    private ApplicationContext cacheGlobalComponent() {
        logger.info("扫描缓存全局组件,实现真正意义上的IOC容器,此功能暂未完善");
        long endTime = System.nanoTime();
        logger.info("应用程序启动结束,当前时间:{},实际耗时:{}毫秒~~", DateUtil.now(), (endTime - startTime) / 1_000_000);
        welcome.destory();
        return applicationContext;
    }


    public String getBasePackages(Class<?> primarySource) {
        return primarySource.getPackage().getName();
    }
}
