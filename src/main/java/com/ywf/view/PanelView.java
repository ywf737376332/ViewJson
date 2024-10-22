package com.ywf.view;

import com.ywf.component.BasePanel;
import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/25 20:46
 */
public class PanelView {


    public static JPanel createPanelLeft() {
        JPanel panelLeft = new BasePanel();
        panelLeft.setMinimumSize(new Dimension(235, 600));
        panelLeft.setOpaque(false); // 设置为透明，以便边框可见
        panelLeft.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 设置外边距
        panelLeft.setLayout(new BorderLayout());
        return panelLeft;
    }

    public static JPanel createPanelRight() {
        JPanel panelRight = new BasePanel();
        panelRight.setMinimumSize(new Dimension(235, 600));
        panelRight.setOpaque(false); // 设置为透明，以便边框可见
        panelRight.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 设置外边距
        panelRight.setLayout(new BorderLayout());
        return panelRight;
    }

    public static JPanel createPanelBottom() {
        JPanel panelBottom = new BasePanel();
        // 设置上面框线
        panelBottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(130, 128, 128, 130))); // 设置边框颜色和宽度
        // 设置边距
        JPanel panelBottomText = new BasePanel();
        panelBottomText.setBorder(BorderFactory.createEmptyBorder(1, 20, 0, 20)); // 设置外边距
        JLabel labelCopyright = new JLabel("作者：莫斐鱼  日期：2023/11/25", IconUtils.getSVGIcon("icons/banner02.svg"), SwingConstants.LEFT);
        labelCopyright.setForeground(new Color(156, 170, 207));
        labelCopyright.setUI(new BasicLabelUI());

        // 状态栏
        JLabel labelStateBar = new JLabel("");
        panelBottomText.setLayout(new BorderLayout());
        panelBottomText.add(labelStateBar, BorderLayout.WEST);
        panelBottomText.add(labelCopyright, BorderLayout.EAST);

        panelBottom.setLayout(new BorderLayout());
        panelBottom.add(panelBottomText, BorderLayout.CENTER);

        return panelBottom;
    }

}
