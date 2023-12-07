package com.ywf.framework.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/7 22:28
 */
public class QRCodeUtils {

    /**
     * 二维码生成
     * @date 2023/12/7 22:34
     *
     * @param qrcodeStr
     */
    public static ImageIcon GeneratorQRCode(String qrcodeStr){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        ImageIcon icon;
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(qrcodeStr, BarcodeFormat.QR_CODE, 550, 550);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
            icon = new ImageIcon(image);
        } catch (WriterException e) {
            throw new RuntimeException("二维码生成失败！"+ e);
        }
        return icon;
    }

}
