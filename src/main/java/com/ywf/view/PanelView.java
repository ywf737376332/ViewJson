package com.ywf.view;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.action.StateBarEventService;
import com.ywf.component.BasePanel;
import com.ywf.component.LabelBarBuilder;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

/**
 * 视图面板组件
 *
 * @Author YWF
 * @Date 2023/11/25 20:46
 */
public class PanelView {

    private static JLabel labelCopyright;

    public static JPanel createPanelMain() {
        JPanel panelMain = new BasePanel();
        panelMain.setMinimumSize(new Dimension(235, 600));
        panelMain.setOpaque(false); // 设置为透明，以便边框可见
        panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 设置外边距
        panelMain.setLayout(new BorderLayout());
        return panelMain;
    }

    public static JPanel createEditPanel() {
        JPanel editPanel = new BasePanel();
        editPanel.setOpaque(false); // 设置为透明，以便边框可见
        editPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 设置外边距
        editPanel.setLayout(new BorderLayout());
        return editPanel;
    }

    public static JPanel createPanelBottom(JFrame frame) {
        JPanel panelBottom = new BasePanel();
        // 设置边距
        JPanel panelBottomText = new BasePanel();
        panelBottomText.setBorder(BorderFactory.createEmptyBorder(1, 20, 0, 20)); // 设置外边距
        FlatLabel labelCopyrightLabel = new FlatLabel();
        labelCopyrightLabel.setIcon(IconUtils.getImageIcon("/icons/auth.png"));
        labelCopyright = new JLabel("<html><span style=\"font-family:'Microsoft YaHei UI';font-size:9px\">作者：莫斐鱼</span></html>", IconUtils.getSVGIcon("icons/auth.svg", 12, 12), SwingConstants.LEFT);
        labelCopyright.setForeground(new Color(156, 170, 207));
        labelCopyright.addMouseListener(new CopyrightMouseListener());

        // 状态栏
        JPanel panelStateBar = new JPanel();
        panelStateBar.setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0)); // 设置外边距
        panelStateBar.setPreferredSize(new Dimension(560, 20));
        panelStateBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        FlatLabel runTimeLabel = LabelBarBuilder.createLabel("运行时长：");
        runTimeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FlatLabel runTimeValue = LabelBarBuilder.createLabel();
        runTimeValue.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        Timer timer = new Timer(1000, e -> StateBarEventService.getInstance().stateBarTimeActionPerformed(runTimeValue));
        StateBarEventService.getInstance().frameFocusActionPerformed(frame, timer);

        FlatLabel fileTypeLabel = LabelBarBuilder.createLabel("内容类型：");
        fileTypeLabel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
        FlatLabel fileTypeValue = LabelBarBuilder.createLabel(true, GlobalKEY.STATE_BAR_TEXT_TYPE);
        fileTypeValue.setText("<html><span color=\"#389FD6\" style=\"font-size:9px\">文本类型</span></html>");
        fileTypeValue.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        FlatLabel fileLengthLabel = LabelBarBuilder.createLabel("字数统计：");
        fileLengthLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        FlatLabel fileLengthValue = LabelBarBuilder.createLabel(true, GlobalKEY.STATE_BAR_TEXT_LENGTH);
        fileLengthValue.setText("<html><span color=\"#107C41\" style=\"font-size:9px\">0词</span></html>");
        fileLengthValue.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        panelStateBar.add(runTimeLabel);
        panelStateBar.add(runTimeValue);
        panelStateBar.add(fileTypeLabel);
        panelStateBar.add(fileTypeValue);
        panelStateBar.add(fileLengthLabel);
        panelStateBar.add(fileLengthValue);

        panelBottomText.setLayout(new BorderLayout());
        panelBottomText.add(panelStateBar, BorderLayout.WEST);
        panelBottomText.add(labelCopyright, BorderLayout.EAST);

        panelBottom.setLayout(new BorderLayout());
        panelBottom.add(panelBottomText, BorderLayout.CENTER);

        return panelBottom;
    }

    /**
     * 多彩版权显示效果
     */
    static class CopyrightMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            labelCopyright.setForeground(updateRandomColor());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            labelCopyright.setForeground(updateRandomColor());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            labelCopyright.setForeground(updateRandomColor());
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            labelCopyright.setForeground(updateRandomColor());
        }

        @Override
        public void mouseExited(MouseEvent e) {
            labelCopyright.setForeground(updateRandomColor());
        }

        private Color updateRandomColor() {
            Random random = new Random();
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            return new Color(r, g, b);
        }
    }
}
