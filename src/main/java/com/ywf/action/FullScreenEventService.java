package com.ywf.action;

import com.ywf.framework.utils.IconUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * 全屏事件处理
 *
 * @Author YWF
 * @Date 2023/12/7 22:37
 */
public class FullScreenEventService {

    private final static Logger logger = LoggerFactory.getLogger(FullScreenEventService.class);

    private boolean isFullScreen = false;

    volatile private static FullScreenEventService instance = null;

    private FullScreenEventService() {

    }

    public static FullScreenEventService getInstance() {
        if (instance == null) {
            synchronized (FullScreenEventService.class) {
                if (instance == null) {
                    instance = new FullScreenEventService();
                }
            }
        }
        return instance;
    }

    /**
     * 清空文本内容
     */
    public void fullScreenActionPerformed(JFrame frame,JButton btnFullScreen) {
        // 获取默认设备的GraphicsDevice对象
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.fullScreen(frame,btnFullScreen,device,isFullScreen);
    }

    private void fullScreen(JFrame frame, JButton btnFullScreen,GraphicsDevice device, boolean isFullScreen){
        if (!isFullScreen){
            // 设置全屏模式
            device.setFullScreenWindow(frame);
            int w = Toolkit.getDefaultToolkit().getScreenSize().width;
            int h = Toolkit.getDefaultToolkit().getScreenSize().height;
            btnFullScreen.setIcon(IconUtils.getSVGIcon("icons/nofullScreen.svg"));
            frame.setSize(new Dimension(w+20, h+10));
            frame.setLocation(-10,0);
            frame.validate();
            this.isFullScreen = true;
        }else {
            btnFullScreen.setIcon(IconUtils.getSVGIcon("icons/fullScreen.svg"));
            // 设置全屏模式
            device.setFullScreenWindow(null);
            frame.validate();
            this.isFullScreen = false;
        }
    }

}
