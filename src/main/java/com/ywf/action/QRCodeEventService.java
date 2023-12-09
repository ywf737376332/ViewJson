package com.ywf.action;

import com.ywf.component.TextAreaBuilder;
import com.ywf.framework.utils.QRCodeUtils;

import javax.swing.*;

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
            JOptionPane.showMessageDialog(frame, "内容不能为空！");
            return;
        }
        ImageIcon icon = QRCodeUtils.GeneratorQRCode(text);
        JOptionPane.showMessageDialog(frame, null, "二维码展示", JOptionPane.INFORMATION_MESSAGE, icon);
    }

}
