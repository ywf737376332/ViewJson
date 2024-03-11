package com.ywf.component.loading;

import com.ywf.component.JSONRSyntaxTextArea;
import com.ywf.component.toast.TipDialog;
import com.ywf.component.toast.ToastBuild;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.constant.MessageConstant;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import com.ywf.framework.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 后台执行事件集合
 *
 * @Author YWF
 * @Date 2024/3/10 20:06
 */
public class BackgroundTaskKit {

    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    /**
     * 复制内容到图片后台任务
     */
    public static class CopyJsonToPictAction extends BackgroundTask {

        private JSONRSyntaxTextArea rSyntaxTextArea;

        public CopyJsonToPictAction(JSONRSyntaxTextArea rSyntaxTextArea) {
            this.rSyntaxTextArea = rSyntaxTextArea;
        }

        @Override
        public void actionPerformedImpl() {
            try {
                //Thread.sleep(8000);
                int pictureScale = applicationContext.getPictureQualityState();
                //绘制图片
                BufferedImage image = ImageUtils.generateTextAreaImage(rSyntaxTextArea, pictureScale);
                // 保存图片到剪贴板
                ImageUtils.imageToClipboard(image);
            } catch (Exception e) {
                throw new RuntimeException("图片复制失败: " + e.getMessage());
            }
        }

        @Override
        public void successTips() {
            //TipDialog test = new TipDialog((JFrame)Window.getWindows()[0], "测试文本");
            ToastBuild.create((JFrame)Window.getWindows()[0]).showTips(MessageConstant.SYSTEM_COPY_IMAGE_SUCCESS_TIP);
            //JOptionPane.showMessageDialog(null, MessageConstant.SYSTEM_COPY_IMAGE_SUCCESS_TIP, MessageConstant.SYSTEM_WARN_TIP, JOptionPane.INFORMATION_MESSAGE);
        }
    }


}
