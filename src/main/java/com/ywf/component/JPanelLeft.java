package com.ywf.component;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/21 10:01
 */
public class JPanelLeft extends JPanel {

    private static JTextArea textAreaSource;


    public static JPanel createPanelLeft(){
        return initUI();
    }

    // 面板组件组装
    private static JPanel initUI() {

        JPanel panelLeft = new JPanel();
        panelLeft.setPreferredSize(new Dimension(400, 0));
        panelLeft.setBorder(new TitledBorder(new EtchedBorder(), "原始数据", TitledBorder.LEFT, TitledBorder.TOP));
        panelLeft.setLayout(new BorderLayout());

        textAreaSource = new JTextArea();
        textAreaSource.setLineWrap(true);
        textAreaSource.setBorder(null);
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setPreferredSize(new Dimension(550, 600));
        jScrollPane.setViewportView(textAreaSource);
        panelLeft.add(jScrollPane,BorderLayout.CENTER);
        return panelLeft;
    }

    public static JTextArea getTextAreaSource() {
        return textAreaSource;
    }

    public static void setTextAreaSource(JTextArea textAreaSource) {
        JPanelLeft.textAreaSource = textAreaSource;
    }
}
