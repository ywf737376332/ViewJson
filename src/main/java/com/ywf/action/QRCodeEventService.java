package com.ywf.action;

import com.ywf.component.DialogBuilder;
import com.ywf.component.JSONRSyntaxTextArea;
import com.ywf.component.JTabbedSplitEditor;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.utils.ImageUtils;
import com.ywf.framework.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * 二维码分享内容
 *
 * @Author YWF
 * @Date 2023/12/7 22:37
 */
public class QRCodeEventService {

    private final static Logger logger = LoggerFactory.getLogger(QRCodeEventService.class);

    private JTabbedSplitEditor tabbedSplitEditor;

    volatile private static QRCodeEventService instance = null;

    private QRCodeEventService() {
        tabbedSplitEditor = ObjectUtils.getBean(GlobalKEY.TABBED_SPLIT_EDITOR);
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
        JSONRSyntaxTextArea rSyntaxTextArea = tabbedSplitEditor.getFocusEditor();
        String text = rSyntaxTextArea.getText();
        if ("".equals(text)) {
            JOptionPane.showMessageDialog(frame, "内容不能为空！");
            return;
        }
        try {
            ImageIcon icon = ImageUtils.generatorQRCode(text);
            DialogBuilder.ShowImageDialog(frame, "二维码展示", icon).setVisible(true);
        } catch (RuntimeException e) {
            logger.error("二维码生成失败：{}",e.fillInStackTrace());
        }
    }
}
