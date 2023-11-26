package com.ywf.view;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/25 20:46
 */
public class PanelView {

    public static JPanel createPanelLeft(){
        JPanel panelLeft = new JPanel();
        //panelLeft.setPreferredSize(new Dimension(300, 0));
        panelLeft.setMinimumSize(new Dimension(235,0));
        panelLeft.setBorder(new TitledBorder(new EtchedBorder(), "", TitledBorder.LEFT, TitledBorder.TOP));
        panelLeft.setLayout(new BorderLayout());
        return panelLeft;
    }

    public static JPanel createPanelRight(){
        JPanel panelRight = new JPanel();
        //panelRight.setPreferredSize(new Dimension(600, 0));
        panelRight.setBorder(new TitledBorder(new EtchedBorder(), "", TitledBorder.LEFT, TitledBorder.TOP));
        panelRight.setLayout(new BorderLayout());
        return panelRight;
    }

    public static JPanel createPanelFun(){
        JPanel panelFun = new JPanel();
        panelFun.setPreferredSize(new Dimension(0, 60));
        //panelFun.setBorder(new TitledBorder(new EtchedBorder(), "功能区域", TitledBorder.LEFT, TitledBorder.TOP));
        panelFun.setLayout(null);
        return panelFun;
    }

    public static JPanel createPanelBottom(){
        JPanel panelBottom = new JPanel();
        panelBottom.setPreferredSize(new Dimension(600, 16));
        JLabel label = new JLabel("作者：莫斐鱼  日期：2023/11/25        ");
        label.setForeground(new Color(89,127,239));
        panelBottom.setLayout(new BorderLayout());
        panelBottom.add(label, BorderLayout.EAST);
        return panelBottom;
    }

}
