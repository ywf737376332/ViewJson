package com.ywf.framework.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/7 22:28
 */
public class QRCodeUtils {

    /**
     * 二维码生成
     *
     * @param qrcodeStr
     * @date 2023/12/7 22:34
     */
    public static ImageIcon GeneratorQRCode(String qrcodeStr) {
        ImageIcon icon;
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);// 边距值
            BitMatrix bitMatrix = qrCodeWriter.encode(qrcodeStr, BarcodeFormat.QR_CODE, 580, 580, hints);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
            icon = new ImageIcon(image);
        } catch (WriterException e) {
            JOptionPane.showMessageDialog(null, "二维码生成失败！" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("二维码生成失败！" + e);
        }
        return icon;
    }

}
