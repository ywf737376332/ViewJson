package com.ywf.framework.component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {

    public static void main(String[] args) {
        String text = "{\n" +
                "    \"header\":  {\n" +
                "        \"channelId\":  \"E01\",\n" +
                "        \"externalReference\":  \"20220705{{$randomInt}}DCP4003\",\n" +
                "        \"requestTime\":  \"2022-07-0517:  08:  48\",\n" +
                "        \"requestBranchCode\":  \"538530\",\n" +
                "        \"requestOperatorId\":  \"\",\n" +
                "        \"requestOperatorType\":  \"0\",\n" +
                "        \"authorizerID\":  \"\",\n" +
                "        \"termType\":  \"00000\",\n" +
                "        \"termNo\":  \"0000000000\",\n" +
                "        \"operationOrg\":  \"003\",\n" +
                "        \"ip\":  \"162.16.52.10\",\n" +
                "        \"mac\":  \"9F-B5-4B-97-9D-7F\",\n" +
                "        \"globSeqNum\":  \"11111111\",\n" +
                "        \"infSeqNum\":  \"123abc456def\",\n" +
                "        \"serviceCode\":  \"DCP1000\",\n" +
                "        \"relatedserialno\":  \"11111111_SYS_vtgdolonf6z\",\n" +
                "        \"originalserialno\":  \"\",\n" +
                "        \"serialNo\":  \"11111111\"\n" +
                "    },\n" +
                "    \"body\":  {\n" +
                "        \"payeeWalletId\":  \"0052200402569233\",\n" +
                "        \"payeeWalletName\":  \"公司三一\",\n" +
                "        \"amount\":  \"3.71\",\n" +
                "        \"qrCode\":  \"https:  //qr.pbcdci.cn/005031050000078010721050000078010720001\",\n" +
                "        \"qrType\":  \"3\",\n" +
                "        \"merchantNo\":  \"105000007801072\",\n" +
                "        \"merchantName\":  \"云朵服饰\",\n" +
                "        \"merchantShortName\":  \"云朵服饰\"\n" +
                "    }\n" +
                "}";
        int width = 500;
        int height = 500;
        String filePath = "C:\\Users\\Administrator\\Desktop\\小工具\\QRCode.png";

        try {
            generateQRCodeImage(text, width, height, filePath);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        ImageIO.write(image, "png", new File(filePath));
    }
}
