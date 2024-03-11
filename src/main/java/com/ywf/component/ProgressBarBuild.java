package com.ywf.component;

import com.ywf.framework.base.ThemeColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 进度条
 *
 * @Author YWF
 * @Date 2023/12/7 22:37
 */
public class ProgressBarBuild extends JProgressBar {

    private final static Logger logger = LoggerFactory.getLogger(ProgressBarBuild.class);
    private JProgressBar progressBar = this;
    private JFrame frame;
    private Timer timer;

    volatile private static ProgressBarBuild instance = null;

    private ProgressBarBuild() {
    }

    private ProgressBarBuild(JFrame frame) {
        super();
        this.frame = frame;
        // 显示当前进度值信息
        //progressbar.setStringPainted(true);
        this.setMinimum(0);
        this.setMaximum(100);
        this.setValue(0);
        // 设置进度条边框不显示
        this.setBorderPainted(false);
        // 设置进度条的前景色
        //progressbar.setForeground(new Color(0,118,255));//这里点击左边的颜色按钮可以换进度条的已加载颜色
        this.setForeground(new Color(61, 182, 78, 200));//这里点击左边的颜色按钮可以换进度条的已加载颜色
        // 设置进度条的背景色
        this.setBackground(ThemeColor.noColor);
        this.setPreferredSize(new Dimension(frame.getWidth(), 2));
        //progressbar.setOrientation(SwingConstants.VERTICAL); // 设置进度条为竖向
        //this.setIndeterminate(true); // 设置进度条为不确定模式
    }

    private static ProgressBarBuild getInstance(JFrame frame) {
        if (instance == null) {
            synchronized (ProgressBarBuild.class) {
                if (instance == null) {
                    instance = new ProgressBarBuild(frame);
                }
            }
        }
        return instance;
    }

    public static ProgressBarBuild create(JFrame frame) {
        frame.getContentPane().add(getInstance(frame), BorderLayout.SOUTH);
        return getInstance(frame);
    }

    public void startRuning() {
        startUpdateProgressBar(frame);
    }

    public void stopRuning() {
        progressBar.setPreferredSize(new Dimension(frame.getWidth(), 0));
        timer.stop();
        frame.getContentPane().remove(progressBar);
    }

    private void startUpdateProgressBar(JFrame frame) {
        if (timer != null) {
            timer = null;
        }
        timer = new Timer(200, new ActionListener() {
            int value = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (value < 20) {
                    value = value + 2;
                    progressBar.setValue(value);
                } else if (value >= 20 && value < 70) {
                    value++;
                    progressBar.setValue(value);
                } else if (value >= 70 && value < 100) {
                    value = value + 10;
                    progressBar.setValue(value);
                } else {
                    ((Timer) e.getSource()).stop();
                    frame.getContentPane().remove(progressBar);
                }
            }
        });
        // 启动定时器
        timer.start();
    }

}
