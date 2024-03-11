package com.ywf.view;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.action.StateBarEventService;
import com.ywf.component.BasePanel;
import com.ywf.component.LabelBarBuilder;
import com.ywf.component.StateLabel;
import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.SvgIconFactory;
import com.ywf.framework.base.ThemeColor;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.constant.MessageConstant;

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
        panelMain.setLayout(new BorderLayout());
        return panelMain;
    }

    public static JPanel createEditPanel() {
        JPanel editPanel = new BasePanel();
        editPanel.setOpaque(false); // 设置为透明，以便边框可见
        editPanel.setBorder(BorderBuilder.topAndBottomBorder(1, ThemeColor.themeColor));
        editPanel.setLayout(new BorderLayout());
        return editPanel;
    }

    public static JPanel createPanelBottom(JFrame frame) {
        JPanel panelBottom = new BasePanel();
        // 设置边距
        JPanel panelBottomText = new BasePanel();
        panelBottomText.setBorder(BorderBuilder.emptyBorder(7, 20, 7, 20)); // 设置外边距
        labelCopyright = new JLabel("<html><span style=\"font-family:'Microsoft YaHei UI';font-size:10px\">" + MessageConstant.AUTHOR_TAG + MessageConstant.AUTHOR + "</span></html>", SvgIconFactory.miniIcon(SvgIconFactory.SystemIcon.author), SwingConstants.LEFT);
        labelCopyright.setForeground(ThemeColor.authorColor);
        labelCopyright.addMouseListener(new CopyrightMouseListener());

        // 状态栏
        JPanel panelStateBar = new JPanel();
        panelStateBar.setBorder(BorderBuilder.topEmptyBorder(1)); // 设置外边距
        panelStateBar.setPreferredSize(new Dimension(700, 20));
        panelStateBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        FlatLabel runTimeLabel = LabelBarBuilder.createLabel(MessageConstant.RUNTIME_TAG);
        runTimeLabel.setBorder(BorderBuilder.emptyBorder(0));
        StateLabel runTimeValue = LabelBarBuilder.createGlobalLabel(GlobalKEY.STATE_BAR_RUN_TIME);
        runTimeValue.setBorder(BorderBuilder.rightEmptyBorder(20));

        Timer timer = new Timer(1000, e -> StateBarEventService.getInstance().stateBarTimeActionPerformed(runTimeValue));
        StateBarEventService.getInstance().frameFocusActionPerformed(frame, timer);

        FlatLabel fileTypeLabel = LabelBarBuilder.createLabel(MessageConstant.CONTEXT_TYPE_TAG);
        fileTypeLabel.setBorder(BorderBuilder.leftEmptyBorder(40));
        FlatLabel fileTypeValue = LabelBarBuilder.createGlobalLabel(GlobalKEY.STATE_BAR_TEXT_TYPE);
        fileTypeValue.setText("<html><span color=\"#389FD6\" style=\"font-size:10px\">" + MessageConstant.SYSTEM_DEFAULT_LANGUAGE_TYPE + "</span></html>");
        fileTypeValue.setBorder(BorderBuilder.rightEmptyBorder(20));

        FlatLabel fileLengthLabel = LabelBarBuilder.createLabel(MessageConstant.WORDS_NUMS_TAG);
        fileLengthLabel.setBorder(BorderBuilder.leftEmptyBorder(20));
        FlatLabel fileLengthValue = LabelBarBuilder.createGlobalLabel(GlobalKEY.STATE_BAR_TEXT_LENGTH);
        fileLengthValue.setText("<html><span color=\"#107C41\" style=\"font-size:10px\">0" + MessageConstant.SYSTEM_STATE_BAR_WORDS + "</span></html>");
        fileLengthValue.setBorder(BorderBuilder.rightEmptyBorder(20));

        panelStateBar.add(runTimeLabel);
        panelStateBar.add(runTimeValue);
        panelStateBar.add(fileTypeLabel);
        panelStateBar.add(fileTypeValue);
        panelStateBar.add(fileLengthLabel);
        panelStateBar.add(fileLengthValue);

        FlatLabel tipMessage = LabelBarBuilder.createGlobalLabel(ThemeColor.stateBarLabelColor, GlobalKEY.STATE_BAR_COST_TIME);
        tipMessage.setBorder(BorderBuilder.leftEmptyBorder(20));
        panelStateBar.add(tipMessage);

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
