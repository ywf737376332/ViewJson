package com.ywf.action;

import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.init.SysConfigInit;
import com.ywf.framework.utils.PropertiesUtil;
import com.ywf.framework.utils.ReflectUtils;
import com.ywf.pojo.ConfigurableApplicationContext;
import org.apache.commons.configuration.PropertiesConfiguration;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 主窗口事件
 *
 * @Author YWF
 * @Date 2023/12/10 13:17
 */
public class FrameWindowCloseEventService extends WindowAdapter {

    private JFrame frame;
    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    public FrameWindowCloseEventService(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        int confirmed = JOptionPane.showConfirmDialog(frame,
                "您是否想退出当前应用？", "确认关闭",
                JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            // 屏幕尺寸大小保存
            if ((frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
                // 窗口最大换状态不记录屏幕大小
            } else {
                applicationContext.setScreenSize(new ConfigurableApplicationContext.ScreenSize(frame.getWidth(), frame.getHeight()));
                // 退出应用时，保存所有配置项到本地
                saveApplicationConfiguration();
            }
            frame.dispose();
            System.exit(0); // 退出程序
        }
    }

    /**
     * 将APP运行参数保存到本地
     */
    private static void saveApplicationConfiguration() {
        PropertiesConfiguration targetProps = ReflectUtils.objectConvertProp(applicationContext);
        String applicationRootPath = SysConfigInit.getApplicationRunRootPath();
        PropertiesUtil.getInstance().store(applicationRootPath,targetProps);
    }


}
