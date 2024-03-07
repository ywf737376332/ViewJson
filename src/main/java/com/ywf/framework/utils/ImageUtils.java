package com.ywf.framework.utils;

import cn.hutool.core.swing.clipboard.ImageSelection;
import com.ywf.framework.constant.MessageConstant;

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

}
