package com.ywf.component.loading;

import com.ywf.component.JSONRSyntaxTextArea;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import com.ywf.framework.utils.ImageUtils;

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
            int pictureScale = applicationContext.getPictureQualityState();
            //绘制图片
            BufferedImage image = ImageUtils.generateTextAreaImage(rSyntaxTextArea, pictureScale);
            // 保存图片到剪贴板
            ImageUtils.imageToClipboard(image);
        }
    }


}
