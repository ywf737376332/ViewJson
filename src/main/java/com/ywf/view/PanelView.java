package com.ywf.view;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.action.StateBarEventService;
import com.ywf.component.BasePanel;
import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/25 20:46
 */
public class PanelView {

    private static FlatLabel runTimeLabel;
    private static FlatLabel fileTypeLabel;
    private static FlatLabel fileLengthLabel;

    public static JPanel createPanelLeft() {
        JPanel panelLeft = new BasePanel();
        panelLeft.setMinimumSize(new Dimension(235, 600));
        panelLeft.setOpaque(false); // 设置为透明，以便边框可见
        panelLeft.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 设置外边距
        panelLeft.setLayout(new BorderLayout());
        return panelLeft;
    }

    public static JPanel createEditPanel() {
        JPanel editPanel = new BasePanel();
        editPanel.setMinimumSize(new Dimension(235, 600));
        editPanel.setOpaque(false); // 设置为透明，以便边框可见
        editPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 设置外边距
        editPanel.setLayout(new BorderLayout());
        return editPanel;
    }

    public static JPanel createPanelBottom(JFrame frame) {
        JPanel panelBottom = new BasePanel();
        // 设置上面框线
        // panelBottom.setBorder(BorderFactory.createMatteBorder(10, 0, 0, 0, new Color(130, 128, 128, 130))); // 设置边框颜色和宽度
        // 设置边距
        JPanel panelBottomText = new BasePanel();
        panelBottomText.setBorder(BorderFactory.createEmptyBorder(1, 20, 0, 20)); // 设置外边距
        JLabel labelCopyright = new JLabel("作者：莫斐鱼", IconUtils.getSVGIcon("icons/banner02.svg"), SwingConstants.LEFT);
        labelCopyright.setForeground(new Color(156, 170, 207));

        // 状态栏
        JPanel panelStateBar = new JPanel();
        panelStateBar.setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0)); // 设置外边距
        panelStateBar.setPreferredSize(new Dimension(560, 20));
        panelStateBar.setLayout(null);

        runTimeLabel = new FlatLabel();
        runTimeLabel.setLabelType(FlatLabel.LabelType.medium);
        runTimeLabel.setBounds(0, 0, 255, 20);
        Timer timer = new Timer(1000, e -> StateBarEventService.getInstance().stateBarTimeActionPerformed(runTimeLabel));
        StateBarEventService.getInstance().frameFocusActionPerformed(frame, timer);

        fileTypeLabel = new FlatLabel();
        fileTypeLabel.setLabelType(FlatLabel.LabelType.medium);
        fileTypeLabel.setBounds(260, 0, 140, 20);

        fileLengthLabel = new FlatLabel();
        fileLengthLabel.setLabelType(FlatLabel.LabelType.medium);
        fileLengthLabel.setBounds(410, 0, 140, 20);

        panelStateBar.add(runTimeLabel);
        panelStateBar.add(fileTypeLabel);
        panelStateBar.add(fileLengthLabel);

        panelBottomText.setLayout(new BorderLayout());
        panelBottomText.add(panelStateBar, BorderLayout.WEST);
        panelBottomText.add(labelCopyright, BorderLayout.EAST);

        panelBottom.setLayout(new BorderLayout());
        panelBottom.add(panelBottomText, BorderLayout.CENTER);

        return panelBottom;
    }


    public static FlatLabel getFileTypeLabel() {
        return fileTypeLabel;
    }

    public static void setFileTypeLabel(FlatLabel fileTypeLabel) {
        PanelView.fileTypeLabel = fileTypeLabel;
    }

    public static FlatLabel getFileLengthLabel() {
        return fileLengthLabel;
    }

    public static void setFileLengthLabel(FlatLabel fileLengthLabel) {
        PanelView.fileLengthLabel = fileLengthLabel;
    }
}
