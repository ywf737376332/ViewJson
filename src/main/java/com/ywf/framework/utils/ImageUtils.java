package com.ywf.framework.utils;

import cn.hutool.core.swing.clipboard.ImageSelection;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/13 14:57
 */
public class ImageUtils {

    /**
     * 二维码生成
     *
     * @param qrcodeStr
     * @date 2023/12/7 22:34
     */
    public static ImageIcon generatorQRCode(String qrcodeStr) {
        ImageIcon icon;
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);// 边距值
            BitMatrix bitMatrix = qrCodeWriter.encode(qrcodeStr, BarcodeFormat.QR_CODE, 500, 500, hints);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
            icon = new ImageIcon(image);
        } catch (WriterException e) {
            JOptionPane.showMessageDialog(null, "二维码生成失败！" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("二维码生成失败！" + e);
        }
        return icon;
    }

    public static void imageToClipboard(BufferedImage image) {
        try {
            // 保存图片到剪贴板
            // ImageSelection 类糊涂工具类里面的实现，如果要自己实现，复制糊涂代码，写内部类或者单独的类也可以
            Transferable transferable = new ImageSelection(image);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(transferable, null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "图片复制失败！" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("图片复制失败: " + e.getMessage());
        }
    }

}
