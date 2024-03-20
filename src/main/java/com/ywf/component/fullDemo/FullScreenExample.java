package com.ywf.component.fullDemo;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/2/26 10:15
 */

import com.ywf.component.TreeBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FullScreenExample {

    static boolean isFullScreen = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Full Screen Example");
        frame.add(TreeBuilder.getInstance().initTree("{\"textAreaEditState\":true,\"marginLine\":{\"showMarginLine\":false,\"marginWidth\":0},\"fontStyle\":{\"name\":\"微软雅黑\",\"size\":16,\"editorFontStyle\":{\"name\":\"JetBrainsMono\",\"size\":16.0}}}"), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
        JButton cancelButton = new JButton("Cancel Full Screen");
        cancelButton.setBounds(20,20, 100, 30);
        frame.add(cancelButton);
        // 获取默认设备的GraphicsDevice对象
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFullScreen(frame,device,isFullScreen);
            }
        });
        // 设置全屏模式
        // 其他代码...
        frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (e.getPoint().getY()<50){
                    System.out.println("鼠标移动到： " + e.getPoint());
                }
            }
        });
    }

    private static void setFullScreen(JFrame frame, GraphicsDevice device,boolean fullScreen) {
        if (!fullScreen) {
            device.setFullScreenWindow(frame);
            isFullScreen = true;
            frame.revalidate();
        } else {
            device.setFullScreenWindow(null);
            isFullScreen = false;
        }
    }
}
