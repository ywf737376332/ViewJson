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

    private JButton formateButton;

    private JButton cleanButton;

    private JTextArea textAreaSourceData;


    // 面板组件组装
    public void initUI(JPanel parentPanel) {

        // 创建面板
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(null);
        panelLeft.setMinimumSize(new Dimension(420, 580));
        panelLeft.setBounds(10, 10, 420, 580);
        panelLeft.setBorder(new TitledBorder(new EtchedBorder(), "原始数据", TitledBorder.LEFT, TitledBorder.TOP));

        // 创建多文本框
        textAreaSourceData = new JTextArea();
        textAreaSourceData.setLineWrap(true);
        textAreaSourceData.setBorder(null);
        textAreaSourceData.setMargin(new Insets(5, 5, 5, 5));
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setBounds(15, 20, 390, 545);
        jScrollPane.setViewportView(textAreaSourceData);
        panelLeft.add(jScrollPane);

        // 将左侧原始数据面板添加到父组件
        parentPanel.add(panelLeft);

        // 绘制功能按钮区域
        JTextArea panelFun = new JTextArea();
        panelFun.setBounds(10, 600, 420, 150);
        panelFun.setBorder(new TitledBorder(new EtchedBorder(), "功能操作", TitledBorder.LEFT, TitledBorder.TOP));

        // 按钮
        formateButton = new JButton("格式化");
        formateButton.setBounds(230, 40, 120, 80);
        formateButton.setBackground(new Color(53, 116, 240));
        formateButton.setForeground(new Color(255, 255, 255));
        formateButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
        panelFun.add(formateButton);

        cleanButton = new JButton("清空");
        cleanButton.setBounds(60, 40, 120, 80);
        cleanButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
        //cleanButton.setBorder(new LineBorder(Color.BLUE));
        panelFun.add(cleanButton);
        // 将左侧功能按钮面板添加到父组件
        parentPanel.add(panelFun);
    }


    public JButton getFormateButton() {
        return formateButton;
    }

    public void setFormateButton(JButton formateButton) {
        this.formateButton = formateButton;
    }

    public JPanelLeft(JPanel parentPanel) {
        this.initUI(parentPanel);
    }

    public JTextArea getTextAreaSourceData() {
        return textAreaSourceData;
    }

    public void setTextAreaSourceData(JTextArea textAreaSourceData) {
        this.textAreaSourceData = textAreaSourceData;
    }

    public JButton getCleanButton() {
        return cleanButton;
    }

    public void setCleanButton(JButton cleanButton) {
        this.cleanButton = cleanButton;
    }
}
