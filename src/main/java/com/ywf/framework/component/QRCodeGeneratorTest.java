package com.ywf.framework.component;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeGeneratorTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("二维码生成器");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);
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
        JLabel label = new JLabel("输入文本：");
        label.setBounds(10, 20, 80, 25);
        panel.add(label);

        JTextField textField = new JTextField(20);
        textField.setBounds(100, 20, 165, 25);
        panel.add(textField);

        JButton generateButton = new JButton("生成二维码");
        generateButton.setBounds(10, 80, 120, 25);
        panel.add(generateButton);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String text = textField.getText();
                if (!text.isEmpty()) {
                    try {
                        QRCodeWriter qrCodeWriter = new QRCodeWriter();
                        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 550, 550);
                        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
                        ImageIcon icon = new ImageIcon(image);
                        JOptionPane.showMessageDialog(null, "二维码已生成", "提示", JOptionPane.INFORMATION_MESSAGE, icon);
                    } catch (WriterException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请输入文本", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
}
