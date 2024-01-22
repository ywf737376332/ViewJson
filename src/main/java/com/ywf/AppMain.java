package com.ywf;

import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.init.SysConfigInit;
import com.ywf.framework.utils.ChangeUIUtils;
import com.ywf.framework.utils.PropertiesUtil;
import com.ywf.view.MainFrame;

import javax.swing.*;

/**
 * Hello world!
 */
public class AppMain {

    private static PropertiesUtil systemProperties = PropertiesUtil.instance();
    private static MainFrame mainFrame;

    public static void main(String[] args) {

        // 首先初始化配置信息
        SysConfigInit.initSysConfig();
        // 创建界面
        SwingUtilities.invokeLater(() -> {
            mainFrame = new MainFrame();
            SystemThemesEnum themesStyles = SystemThemesEnum.findThemesBykey(systemProperties.getValueFromProperties(SystemConstant.SYSTEM_THEMES_KEY));
            ChangeUIUtils.changeUIStyle(mainFrame, themesStyles != null ? themesStyles : SystemThemesEnum.FlatLightLafThemesStyle);
            mainFrame.createAndShowGUI(SystemConstant.SYSTEM_TITLE + SystemConstant.SYSTEM_VERSION);
        });


    }
}
