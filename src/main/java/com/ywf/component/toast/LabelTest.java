package com.ywf.component.toast;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * JLabel 支持GIF图像 very excited
 * 吐司组件的使用
 * @author CCW
 * @date 2014-2-23
 * @since v1.0
 */
public class LabelTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setAlignmentX(0.5f);
        panel.setAlignmentY(1.0f);
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.X_AXIS);

        panel.setLayout(boxLayout);

        /**
         * 1-吐司组件的使用
         */
        final Toast toast = new Toast(frame, "北京市没有权限访问", 5000, Toast.error);

        JLabel label = new JLabel("text");
        panel.add(label);
        JButton start = new JButton("start");
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * 2-显示吐司组件
                 */
                toast.start();


            }
        });
        panel.add(start);

        JButton update = new JButton("update message");
        update.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * 3-修改组件消息提示内容
                 */
                toast.setMessage(Math.random() + "");

            }
        });

        panel.add(update);
        frame.add(panel);
        frame.setSize(960, 660);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }

}
