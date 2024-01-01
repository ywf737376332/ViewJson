package com.ywf.framework.layout;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatTextField;
import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/30 22:39
 */
public class FindPanelBuilder {

    private static FindPanelLayout layout;
    private static JLabel btnClose;


    public static JPanel createFindPanel() {
        JPanel rootFindPanel = new JPanel();
        layout = new FindPanelLayout(rootFindPanel, 0, 5);
        rootFindPanel.setLayout(layout);
        rootFindPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(130, 128, 128, 130)));

        JPanel findPanel = new JPanel(new BorderLayout());
        JLabel findLabel = new JLabel("查找:  ", IconUtils.getSVGIcon("icons/search.svg", 16, 16), SwingConstants.RIGHT);
        findPanel.add(findLabel, BorderLayout.WEST);
        FlatTextField fieldFind = new FlatTextField();
        fieldFind.setPadding(new Insets(0, 5, 0, 5));
        fieldFind.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, new Color(130, 128, 128, 130)));
        fieldFind.setShowClearButton(true);

        // search toolbar
        JToolBar searchToolbar = new JToolBar();
        JToggleButton matchCaseButton = new JToggleButton(new FlatSVGIcon("icons/matchCase.svg"));
        matchCaseButton.setRolloverIcon(new FlatSVGIcon("icons/matchCaseHovered.svg"));
        matchCaseButton.setSelectedIcon(new FlatSVGIcon("icons/matchCaseSelected.svg"));
        matchCaseButton.setToolTipText("Match Case");
        // regex button
        JToggleButton regexButton = new JToggleButton(new FlatSVGIcon("icons/regex.svg"));
        regexButton.setRolloverIcon(new FlatSVGIcon("icons/regexHovered.svg"));
        regexButton.setSelectedIcon(new FlatSVGIcon("icons/regexSelected.svg"));
        regexButton.setToolTipText("Regular Expression");
        searchToolbar.add(matchCaseButton);
        searchToolbar.addSeparator();
        searchToolbar.add(regexButton);
        fieldFind.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, searchToolbar);


        findPanel.add(fieldFind, BorderLayout.CENTER);

        JPanel findBtnPanel = new JPanel(new FlowLayout(5, 5, FlowLayout.RIGHT));
        JButton buttonNext = new JButton("下一个");
        JButton buttonHighLight = new JButton("全部高亮显示");
        findBtnPanel.add(buttonNext);
        findBtnPanel.add(buttonHighLight);

        JPanel findRight = new JPanel(new BorderLayout());
        findRight.setPreferredSize(new Dimension(100, 20));
        JCheckBox checkBox = new JCheckBox("替换");
        findRight.add(checkBox, BorderLayout.EAST);
        findBtnPanel.add(findRight);

        btnClose = new JLabel(IconUtils.getSVGIcon("icons/close.svg", 16, 16));
        btnClose.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 7)); // 设置边框为10像素的空白边框
        btnClose.setBounds(535, 5, 25, 25);
        btnClose.addMouseListener(new ClosePopupMouseListener());
        rootFindPanel.add(btnClose, BorderLayout.WEST);
        rootFindPanel.add(findPanel, BorderLayout.CENTER);
        rootFindPanel.add(findBtnPanel, BorderLayout.EAST);
        return rootFindPanel;
    }

    /**
     * 关闭事件
     */
    static class ClosePopupMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            layout.showHideActionPerformed();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            btnClose.setIcon(IconUtils.getSVGIcon("icons/closeRed.svg", 16, 16));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            btnClose.setIcon(IconUtils.getSVGIcon("icons/close.svg", 16, 16));
        }
    }

    public static FindPanelLayout getLayout() {
        return layout;
    }

    public static void setLayout(FindPanelLayout layout) {
        FindPanelBuilder.layout = layout;
    }
}
