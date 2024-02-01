package com.ywf.framework.ioc;

import cn.hutool.core.lang.Assert;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.handle.ConfigLoadHandler;
import com.ywf.framework.init.SysConfigInit;
import com.ywf.framework.utils.ChangeUIUtils;
import com.ywf.framework.utils.ObjectUtils;
import com.ywf.view.MainFrame;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;

/**
 * 项目启动主类
 *
 * @Author YWF
 * @Date 2024/1/29 10:59
 */
public class ApplicationView {

    private static final Log LOG = LogFactory.getLog(ApplicationView.class);

    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    private static MainFrame applicationView;
    private ConfigLoadHandler configLoadHandler;

    /**
     * 本地资源加注入
     */
    public ApplicationView(Class<?> primarySource) {
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
        applicationView = configLoadHandler.appViewInit("com.ywf");
        ObjectUtils.setBean(primarySource, applicationView);
    }

    public static ApplicationContext run(Class<?> primarySource, String... args) {
        return new ApplicationView(primarySource).initThemesUI().run().cacheGlobalComponent();
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
            LOG.error("APP界面加载失败", e);
        }
        return this;
    }

    private ApplicationView initThemesUI() {
        try {
            SystemThemesEnum themesStyles = SystemThemesEnum.findThemesBykey(applicationContext.getLastSystemThemes());
            ChangeUIUtils.changeUIStyle(applicationView, themesStyles);
        } catch (Exception e) {
            LOG.error("初始化主题失败", e);
        }
        return this;
    }

    private ApplicationContext cacheGlobalComponent() {
        System.out.println("扫描缓存全局组件");
        return applicationContext;
    }


    public String getBasePackages(Class<?> primarySource) {
        return primarySource.getPackage().getName();
    }
}
