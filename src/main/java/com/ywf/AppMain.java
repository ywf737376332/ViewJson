package com.ywf;

import com.formdev.flatlaf.IntelliJTheme;
import com.ywf.framework.enums.ThemesEnum;
import com.ywf.framework.utils.ChangeUIUtils;
import com.ywf.view.MainFrame;

/**
 * Hello world!
 *
 */
public class AppMain {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        ChangeUIUtils.changeUIStyle(mainFrame, ThemesEnum.FlatLightLafThemesStyle.getThemesStyles());
        //IntelliJTheme.setup( AppMain.class.getResourceAsStream("/themes/SolarizedLightTheme.json" ) );
        mainFrame.createAndShowGUI("JSON格式化工具V2.0");
    }
}
