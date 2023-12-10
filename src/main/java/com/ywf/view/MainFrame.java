package com.ywf.view;

import com.ywf.component.JPanelFun;
import com.ywf.component.JPanelLeft;
import com.ywf.component.JPanelRight;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/21 17:22
 */
public class MainFrame {

    public MainFrame() {
    }

    public void createAndShowGUI() {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("JSON格式化工具V2.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setSize(1125, 800);
        frame.setLocation((w - frame.getWidth()) / 2, (h - frame.getHeight()) / 2);
        frame.setMinimumSize(new Dimension(1125, 800));

        //设置图标
        URL logoUrl = this.getClass().getResource("/icons/logo.png"); // 打包后class的根目录有这个图
        Image image = new ImageIcon(logoUrl).getImage();
        frame.setIconImage(image);
        frame.setBackground(null);

        JPanel panelLeft = JPanelLeft.createPanelLeft();
        frame.add(panelLeft, BorderLayout.WEST);
        JPanel panelRight = JPanelRight.createPanelRight();

        JPanel panelFun = JPanelFun.createPanelFun();
        panelLeft.add(panelFun, BorderLayout.SOUTH);
        frame.add(panelRight, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.pack();
    }

}
