package com.ywf;

import com.formdev.flatlaf.IntelliJTheme;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.utils.ChangeUIUtils;
import com.ywf.framework.utils.SysConfigInfoUtils;
import com.ywf.view.MainFrame;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 * Hello world!
 *
 */
public class AppMain {
    public static void main(String[] args) {
        // 首先初始化配置信息
        SysConfigInfoUtils.initSysConfig();
        // 创建界面
        MainFrame mainFrame = new MainFrame();
        ChangeUIUtils.changeUIStyle(mainFrame, SystemThemesEnum.FlatLightLafThemesStyle);
        mainFrame.createAndShowGUI("JSON格式化工具V2.0");
    }
}
