package com.ywf;

import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.utils.ChangeUIUtils;
import com.ywf.view.MainFrame;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 * Hello world!
 *
 */
public class AppMain {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        ChangeUIUtils.changeUIStyle(mainFrame, SystemThemesEnum.FlatLightLafThemesStyle.getThemesStyles());
        //IntelliJTheme.setup( AppMain.class.getResourceAsStream("/themes/SolarizedLightTheme.json" ) );
        mainFrame.createAndShowGUI("JSON格式化工具V2.0");
    }
}
