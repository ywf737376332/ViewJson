package com.ywf.view;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.action.StateBarEventService;
import com.ywf.component.BasePanel;
import com.ywf.component.LabelBuilder;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

/**
 * TODO
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

    /*public static JPanel createPanelBottom(JFrame frame) {
        JPanel panelBottom = new BasePanel();
        // 设置上面框线
        // panelBottom.setBorder(BorderFactory.createMatteBorder(10, 0, 0, 0, new Color(130, 128, 128, 130))); // 设置边框颜色和宽度
        // 设置边距
        JPanel panelBottomText = new BasePanel();
        panelBottomText.setBorder(BorderFactory.createEmptyBorder(1, 20, 0, 20)); // 设置外边距
        labelCopyright = new JLabel("作者：莫斐鱼", IconUtils.getSVGIcon("icons/auth.svg", 14, 14), SwingConstants.LEFT);
        labelCopyright.setForeground(new Color(156, 170, 207));
        labelCopyright.addMouseListener(new CopyrightMouseListener());

        // 状态栏
        JPanel panelStateBar = new JPanel();
        panelStateBar.setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0)); // 设置外边距
        panelStateBar.setPreferredSize(new Dimension(560, 20));
        panelStateBar.setLayout(null);

        FlatLabel runTimeText = new FlatLabel();
        runTimeText.setText("运行时长：");
        runTimeText.setForeground(new Color(167, 179, 211));
        runTimeText.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        runTimeText.setBounds(0, 0, 65, 20);
        runTimeLabel = new FlatLabel();
        runTimeLabel.setLabelType(FlatLabel.LabelType.medium);
        runTimeLabel.setBounds(60, 0, 255, 20);
        runTimeLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        Timer timer = new Timer(1000, e -> StateBarEventService.getInstance().stateBarTimeActionPerformed(runTimeLabel));
        StateBarEventService.getInstance().frameFocusActionPerformed(frame, timer);

        fileTypeLabel = new FlatLabel();
        fileTypeLabel.setLabelType(FlatLabel.LabelType.medium);
        fileTypeLabel.setBounds(260, 0, 140, 20);
        fileTypeLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        fileTypeLabel.setForeground(new Color(167, 179, 211));

        fileLengthLabel = new FlatLabel();
        fileLengthLabel.setLabelType(FlatLabel.LabelType.medium);
        fileLengthLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        fileLengthLabel.setForeground(new Color(167, 179, 211));
        fileLengthLabel.setBounds(410, 0, 140, 20);

        panelStateBar.add(runTimeText);
        panelStateBar.add(runTimeLabel);
        panelStateBar.add(fileTypeLabel);
        panelStateBar.add(fileLengthLabel);

        panelBottomText.setLayout(new BorderLayout());
        panelBottomText.add(panelStateBar, BorderLayout.WEST);
        panelBottomText.add(labelCopyright, BorderLayout.EAST);

        panelBottom.setLayout(new BorderLayout());
        panelBottom.add(panelBottomText, BorderLayout.CENTER);

        return panelBottom;
    }*/

    public static JPanel createPanelBottom(JFrame frame) {
        JPanel panelBottom = new BasePanel();
        // 设置边距
        JPanel panelBottomText = new BasePanel();
        panelBottomText.setBorder(BorderFactory.createEmptyBorder(1, 20, 0, 20)); // 设置外边距
        labelCopyright = new JLabel("作者：莫斐鱼", IconUtils.getSVGIcon("icons/auth.svg", 14, 14), SwingConstants.LEFT);
        labelCopyright.setForeground(new Color(156, 170, 207));
        labelCopyright.addMouseListener(new CopyrightMouseListener());

        // 状态栏
        JPanel panelStateBar = new JPanel();
        panelStateBar.setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0)); // 设置外边距
        panelStateBar.setPreferredSize(new Dimension(560, 20));
        panelStateBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        FlatLabel runTimeLabel = LabelBuilder.createLabel("运行时长：");
        runTimeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FlatLabel runTimeValue = LabelBuilder.createLabel();
        runTimeValue.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        Timer timer = new Timer(1000, e -> StateBarEventService.getInstance().stateBarTimeActionPerformed(runTimeValue));
        StateBarEventService.getInstance().frameFocusActionPerformed(frame, timer);

        FlatLabel fileTypeLabel = LabelBuilder.createLabel("内容类型：");
        fileTypeLabel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
        FlatLabel fileTypeValue = LabelBuilder.createLabel(true, GlobalKEY.STATE_BAR_TEXT_TYPE);
        fileTypeValue.setText("<html><span color=\"#389FD6\" style=\"font-size:9px\">文本类型</span></html>");
        fileTypeValue.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        FlatLabel fileLengthLabel = LabelBuilder.createLabel("字数统计：");
        fileLengthLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        FlatLabel fileLengthValue = LabelBuilder.createLabel(true, GlobalKEY.STATE_BAR_TEXT_LENGTH);
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

    /*public static JPanel createPanelBottom002(JFrame frame) {
        // 状态栏
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.LIGHT_GRAY));
        statusPanel.setLayout(new BorderLayout());
        //statusPanel.setBackground(Color.BLUE);
        JXStatusBar statusBar = new JXStatusBar();
        statusBar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

        FlatLabel runTimeText = new FlatLabel();
        runTimeText.setText("运行时长：");
        runTimeText.setForeground(new Color(167, 179, 211));
        runTimeText.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));

        runTimeLabel = new FlatLabel();
        runTimeLabel.setLabelType(FlatLabel.LabelType.medium);
        runTimeLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        Timer timer = new Timer(1000, e -> StateBarEventService.getInstance().stateBarTimeActionPerformed(runTimeLabel));
        StateBarEventService.getInstance().frameFocusActionPerformed(frame, timer);

        fileTypeLabel = new FlatLabel();
        fileTypeLabel.setLabelType(FlatLabel.LabelType.medium);
        fileTypeLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        fileTypeLabel.setForeground(new Color(167, 179, 211));

        fileLengthLabel = new FlatLabel();
        fileLengthLabel.setLabelType(FlatLabel.LabelType.medium);
        fileLengthLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        fileLengthLabel.setForeground(new Color(167, 179, 211));

        JPanel runTime = new JPanel();
        runTime.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        runTime.add(runTimeText);
        runTime.add(runTimeLabel);
        statusBar.add(runTime);
        fileTypeLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        statusBar.add(fileTypeLabel);
        fileLengthLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        statusBar.add(fileLengthLabel);

        JXStatusBar authBar = new JXStatusBar();
        labelCopyright = new JLabel("作者：莫斐鱼", IconUtils.getSVGIcon("icons/auth.svg", 14, 14), SwingConstants.LEFT);
        labelCopyright.setForeground(new Color(156, 170, 207));
        labelCopyright.addMouseListener(new CopyrightMouseListener());
        authBar.add(labelCopyright);

        statusPanel.add(statusBar, BorderLayout.WEST);
        statusPanel.add(authBar, BorderLayout.EAST);

        return statusPanel;
    }*/


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
