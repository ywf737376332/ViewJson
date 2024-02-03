package com.ywf.action;

import com.ywf.component.DialogBuilder;
import com.ywf.component.JSONRSyntaxTextArea;
import com.ywf.component.JTabbedSplitEditor;
import com.ywf.framework.config.GlobalMenuKEY;
import com.ywf.framework.utils.ImageUtils;
import com.ywf.framework.utils.ObjectUtils;

import javax.swing.*;

/**
 * 二维码分享内容
 *
 * @Author YWF
 * @Date 2023/12/7 22:37
 */
public class QRCodeEventService {

    private JTabbedSplitEditor tabbedSplitEditor;

    volatile private static QRCodeEventService instance = null;

    private QRCodeEventService() {
        tabbedSplitEditor = ObjectUtils.getBean(GlobalMenuKEY.TABBED_SPLIT_EDITOR);
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
        JSONRSyntaxTextArea rSyntaxTextArea = tabbedSplitEditor.findComponentsByFocus();
        String text = rSyntaxTextArea.getText();
        if ("".equals(text)) {
            JOptionPane.showMessageDialog(frame, "内容不能为空！");
            return;
        }
        ImageIcon icon = ImageUtils.generatorQRCode(text);
        DialogBuilder.ShowImageDialog(frame, "二维码展示", icon).setVisible(true);
    }

}
