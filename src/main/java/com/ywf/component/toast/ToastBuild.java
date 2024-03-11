package com.ywf.component.toast;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.component.DialogBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 提示框
 *
 * @Author YWF
 * @Date 2024/3/10 19:35
 */
public class ToastBuild {

    private JFrame frame;
    private JDialog dialog;
    private final FlatLabel tipLabel;

    private ToastBuild(JFrame frame) {
        this.frame = frame;
        tipLabel = new FlatLabel();
    }

    /**
     * 初始化遮罩层
     *
     * @param frame
     * @return
     */
    public static ToastBuild create(JFrame frame) {
        return new ToastBuild(frame);
    }

    /**
     * 展示模态遮罩层
     *
     * @return
     */
    public void showTips(String message) {
        int radius = 20;
        dialog = DialogBuilder.showMoadlDialog(frame);
        dialog.setModal(false);
        tipLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tipLabel.setText(message);
        FontMetrics metrics = tipLabel.getFontMetrics(tipLabel.getFont());
        int width = metrics.stringWidth(tipLabel.getText());
        dialog.setSize(width + 20, 60);
        //大小变化后，位置重新计算
        Dimension frameSize = frame.getSize();
        Dimension dialogSize = dialog.getSize();
        int x = (frameSize.width - dialogSize.width) / 2;
        int y = (frameSize.height - dialogSize.height) / 2;
        dialog.setLocation(frame.getX() + x, frame.getY() + y - 5);

        //大小变化后圆角重新计算
        Shape shape = new RoundRectangle2D.Double(0, 0, dialog.getWidth(), dialog.getHeight(), radius, radius);
        dialog.setShape(shape);
        dialog.add(tipLabel);
        //定时关闭
        this.closeTips();
        dialog.setVisible(true);
    }

    /**
     * 关闭当前弹框
     */
    private void closeTips() {
        // 创建一个新计时器
        Timer timer = new Timer();
        // 30秒 后执行该任务
        timer.schedule(new TimerTask() {
            public void run() {
                dialog.setVisible(false);
                dialog.dispose();
            }
        }, 3000);
    }
}

