package com.ywf.component.loading;

import com.ywf.component.JSONRSyntaxTextArea;
import com.ywf.framework.constant.MessageConstant;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import com.ywf.framework.utils.ImageUtils;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * 后台执行事件集合
 *
 * @Author YWF
 * @Date 2024/3/10 20:06
 */
public class BackgroundTaskKit {

    public static class NewTabAction extends BackgroundTask {
        @Override
        public void actionPerformedImpl() {
            try {
                Thread.sleep(5_000);
                System.out.println("执行到了这");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 复制内容到图片后台任务
     */
    public static class CopyJsonToPictAction extends BackgroundTask {
        private ConfigurableApplicationContext applicationContext;
        private JSONRSyntaxTextArea rSyntaxTextArea;

        public CopyJsonToPictAction(ConfigurableApplicationContext applicationContext, JSONRSyntaxTextArea rSyntaxTextArea) {
            this.applicationContext = applicationContext;
            this.rSyntaxTextArea = rSyntaxTextArea;
        }

        @Override
        public void actionPerformedImpl() {
            try {

                Thread.sleep(8000);

                /*int pictureScale = applicationContext.getPictureQualityState();
                //绘制图片
                BufferedImage image = ImageUtils.generateTextAreaImage(rSyntaxTextArea, pictureScale);
                // 保存图片到剪贴板
                ImageUtils.imageToClipboard(image);
                JOptionPane.showMessageDialog(null, MessageConstant.SYSTEM_COPY_IMAGE_SUCCESS_TIP, MessageConstant.SYSTEM_WARN_TIP, JOptionPane.INFORMATION_MESSAGE);*/
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, MessageConstant.SYSTEM_COPY_IMAGE_FAIL_TIP + e.getMessage(), MessageConstant.SYSTEM_ERROR_TIP, JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("图片复制失败: " + e.getMessage());
            }
        }
    }


}
