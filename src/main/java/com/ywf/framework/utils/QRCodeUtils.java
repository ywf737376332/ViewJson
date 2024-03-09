package com.ywf.framework.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ywf.framework.constant.MessageConstant;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码生成工具类
 *
 * @Author YWF
 * @Date 2024/3/7 15:21
 */
public class QRCodeUtils {

    /**
     * 普通二维码生成
     *
     * @param qrcodeStr
     * @date 2023/12/7 22:34
     */
    public static ImageIcon generateQRCode(String qrcodeStr, int imageSize) {
        ImageIcon icon;
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);// 边距值
            BitMatrix bitMatrix = qrCodeWriter.encode(qrcodeStr, BarcodeFormat.QR_CODE, imageSize, imageSize, hints);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
            // 返回二进制图片
            icon = new ImageIcon(image);
        } catch (WriterException e) {
            JOptionPane.showMessageDialog(null, MessageConstant.SYSTEM_GRENT_QRCODE_FAIL + e.getMessage(), MessageConstant.SYSTEM_ERROR_TIP, JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("二维码生成失败！" + e);
        }
        return icon;
    }


    /**
     * 生成彩色过渡色二维码
     *
     * @param qrcodeStr
     * @param imageSize
     * @return
     */
    public static ImageIcon generateColorQRCode(String qrcodeStr, int imageSize) {
        ImageIcon icon;
        MultiFormatWriter mutiWriter = new MultiFormatWriter();
        //定义二维码内容参数
        Map<EncodeHintType, Object> hints = new HashMap<>();
        try {
            //设置字符集编码格式
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //设置容错等级，在这里我们使用M级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            // 生成二维码，参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            BitMatrix matrix = mutiWriter.encode(qrcodeStr, BarcodeFormat.QR_CODE, imageSize, imageSize, hints);
            // 二维矩阵转为一维像素数组
            int[] pixels = new int[imageSize * imageSize];
            for (int y = 0; y < matrix.getHeight(); y++) {
                for (int x = 0; x < matrix.getWidth(); x++) {
                    // 二维码颜色（RGB）
                    int num1 = (int) (50 - (50 - 2.0) / matrix.getHeight()
                            * (y + 1));
                    int num2 = (int) (165 - (165.0 - 150.0) / matrix.getHeight()
                            * (y + 1));
                    int num3 = (int) (162 - (162.0 - 210.0)
                            / matrix.getHeight() * (y + 1));
                    Color color = new Color(num1, num2, num3);
                    int colorInt = color.getRGB();
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
                    pixels[y * imageSize + x] = matrix.get(x, y) ? colorInt : 16777215;// 0x000000:0xffffff
                }
            }
            BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
            image.getRaster().setDataElements(0, 0, imageSize, imageSize, pixels);
            // 返回二进制图片
            icon = new ImageIcon(image);
        } catch (WriterException e) {
            throw new RuntimeException("二维码生成失败！" + e);
        }
        return icon;
    }

}
