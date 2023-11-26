package com.ywf.framework.component.button;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/25 8:26
 */
public class TestMain {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            new TestMain().initUI();
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

    }

    private void initUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setSize(1125, 800);
        frame.setLocation((w - frame.getWidth()) / 2, (h - frame.getHeight()) / 2);
        frame.setMinimumSize(new Dimension(1125, 800));
        JPanel panel = new JPanel();
        panel.setSize(520,300);


        JButton button1 = ButtonFactory.defaultButton("默认按钮");
        JButton button2 = ButtonFactory.primaryButton("主要按钮");
        JButton button3 = ButtonFactory.dangerButton("警告按钮");
        JButton button4 = ButtonFactory.successButton("成功按钮");
        MyButton button5 = new MyButton("我的按钮");
        //JButton button7 = ButtonBuilder.roundButton("圆角按钮");


        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);
        frame.add(panel);
        frame.setVisible(true);
    }
}
