package com.ywf.action;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ywf.component.TextAreaBuilder;
import com.ywf.framework.utils.JsonFormatUtil;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/7 22:37
 */
public class QRCodeEventService {

    private static JTextArea rSyntaxTextArea;

    volatile private static QRCodeEventService instance = null;

    static {
        rSyntaxTextArea = TextAreaBuilder.getSyntaxTextArea();
    }

    private QRCodeEventService() {
    }

    public static QRCodeEventService getInstance() {
        if (instance == null) {
            synchronized (QRCodeEventService.class) {
                if (instance == null) {
                    instance = new QRCodeEventService();
                }
            }
        }
        return instance;
    }

    /**
     * 二维码展示
     *
     * @param frame
     */
    public void showQrcodeActionPerformed(JFrame frame) {
        String text = rSyntaxTextArea.getText();
        if ("".equals(text)) {
            JOptionPane.showMessageDialog(frame, "请输入json字符串", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);// 边距值
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 580, 580, hints);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ImageIcon icon = new ImageIcon(image);
            JOptionPane.showMessageDialog(frame, null, "二维码展示", JOptionPane.INFORMATION_MESSAGE, icon);
        } catch (WriterException ex) {
            ex.printStackTrace();
        }
    }

}
