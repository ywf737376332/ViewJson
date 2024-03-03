package com.ywf.action;

import cn.hutool.json.JSONUtil;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.init.SysConfigInit;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import com.ywf.framework.utils.PropertiesUtil;
import com.ywf.framework.utils.ReflectUtils;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final static Logger logger = LoggerFactory.getLogger(FrameWindowCloseEventService.class);

    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    public FrameWindowCloseEventService(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        MenuEventService.getInstance().closeWindowsFrameActionPerformed(frame);
    }

    /**
     * 将APP运行参数保存到本地
     */
    public static void saveApplicationConfiguration() {
        PropertiesConfiguration targetProps = ReflectUtils.objectConvertProp(applicationContext);
        String applicationRootPath = SysConfigInit.getSystemRootFilePath();
        PropertiesUtil.getInstance().store(applicationRootPath, targetProps);
        logger.info("当前用户配置信息保存成功：{}", JSONUtil.toJsonStr(applicationContext));
    }
}
