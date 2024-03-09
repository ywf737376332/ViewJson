package com.ywf.framework.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.swing.clipboard.ImageSelection;
import com.ywf.framework.constant.MessageConstant;
import com.ywf.framework.constant.SystemConstant;
import org.fife.ui.rtextarea.RTextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;

/**
 * 图片保存工具
 *
 * @Author YWF
 * @Date 2023/12/13 14:57
 */
public class ImageUtils {

    private final static Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    public static BufferedImage generateTextAreaImage(RTextArea textArea, int pictureScale) {
        //绘制文本框的内容到图片上
        BufferedImage originalImage = new BufferedImage(textArea.getWidth() * pictureScale, textArea.getHeight() * pictureScale, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = originalImage.createGraphics();
        g2d.scale(pictureScale, pictureScale); // 根据画布大小调整缩放比例
        textArea.print(g2d);
        // 设置水印文字、颜色、字体和透明度等属性
        String markDateText = DateUtil.now();
        drawWatermarkText(originalImage, g2d, markDateText, pictureScale, 30, 50);
        String markAuthorText = MessageConstant.AUTHOR;
        drawWatermarkText(originalImage, g2d, markAuthorText, pictureScale, 30, 5);
        g2d.dispose();
        return originalImage;
    }

    /**
     * 保存图片到剪贴板
     *
     * @param image
     */
    public static void imageToClipboard(BufferedImage image) {
        try {
            // 保存图片到剪贴板
            // ImageSelection 类糊涂工具类里面的实现，如果要自己实现，复制糊涂代码，写内部类或者单独的类也可以
            Transferable transferable = new ImageSelection(image);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(transferable, null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, MessageConstant.SYSTEM_COPY_IMAGE_FAIL_TIP + e.getMessage(), MessageConstant.SYSTEM_ERROR_TIP, JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("图片复制失败: " + e.getMessage());
        }
    }

    /**
     * 在图片上绘制水印文字
     *
     * @param originalImage
     * @param graphics2D
     * @param markText
     * @param rightMargin
     * @param bottomMargin
     */
    private static void drawWatermarkText(BufferedImage originalImage, Graphics2D graphics2D, String markText, int pictureScale, int rightMargin, int bottomMargin) {
        Color watermarkColor = new Color(130, 128, 128, 130);
        Font watermarkFont = SystemConstant.SYSTEM_WATERMARK_FONT;
        float transparency = 0.5f; // 透明度，范围从0.0（完全透明）到1.0（完全不透明）
        // 设置水印文字的透明度
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
        // 设置水印文字的颜色和字体
        graphics2D.setColor(watermarkColor);
        graphics2D.setFont(watermarkFont);
        // 计算水印文字的位置
        int x = originalImage.getWidth() / pictureScale - graphics2D.getFontMetrics().stringWidth(markText) - rightMargin;
        int y = originalImage.getHeight() / pictureScale - graphics2D.getFontMetrics().getHeight() - bottomMargin;
        // 在新的BufferedImage对象上绘制水印文字
        graphics2D.drawString(markText, x, y);
    }

}
