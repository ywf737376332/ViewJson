package com.ywf;

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
        mainFrame.createAndShowGUI("JSON格式化工具V2.0");
    }
}
