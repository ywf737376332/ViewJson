package com.ywf.component;

import com.ywf.framework.base.SvgIconFactory;
import com.ywf.framework.base.ThemeColor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 图片组件工厂
 * 窗口最大设为比Frame大小小一点，里面的图片可进行放大和缩小，预览窗口的图片不能进行放大2倍
 * 图片可以进行上下滑动查看
 *
 * @Author YWF
 * @Date 2023/12/12 23:26
 */
public class DialogBuilder {

    /**
     * 图片组件创建
     *
     * @param parentFrame
     * @param title
     * @param image
     * @date 2023/12/12 23:54
     */
    public static JDialog showImageDialog(JFrame parentFrame, String title, ImageIcon image) {
        return createDialog(parentFrame, title, image, 20);
    }

    public static JDialog showImageDialog(JFrame parentFrame, String title, ImageIcon image, int timeout) {
        return createDialog(parentFrame, title, image, timeout);
    }

    /**
     * 图片组件创建
     *
     * @param parentFrame
     * @param title
     * @param imagePath
     * @date 2023/12/12 23:54
     */
    public static JDialog showImageDialog(JFrame parentFrame, String title, String imagePath) {
        return createDialog(parentFrame, title, SvgIconFactory.mediumIcon(imagePath), 30);
    }

    public static JDialog showDialog(JFrame parentFrame, String title, Component component, int timeout) {
        return createTimeDialog(parentFrame, title, component, timeout);
    }

    public static JDialog showMoadlDialog(JFrame parentFrame, boolean hasModalColor, int width, int height) {
        return createModalDialog(parentFrame, hasModalColor, width, height);
    }

    public static JDialog showMoadlDialog(JFrame parentFrame, boolean hasModalColor) {
        return createModalDialog(parentFrame, hasModalColor, parentFrame.getWidth() - 16, parentFrame.getHeight() - 7);
    }

    private static JDialog createDialog(JFrame parentFrame, String title, ImageIcon image, int timeout) {
        final JDialog dialog = new JDialog(parentFrame, title, true);
        JLabel imageLabel = new JLabel(image);
        // 设置标签的首选大小为图片的大小
        dialog.setSize(image.getIconWidth() + 25, image.getIconHeight() + 60);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(imageLabel);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setResizable(false);
        java.util.Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int count = timeout;

            @Override
            public void run() {
                dialog.setTitle("<html><span style=\"float:right\"><b>" + title + "</b>，关闭倒计时：" + count-- + "秒</span></html>");
                if (count < 0) {
                    dialog.setVisible(false);
                    timer.cancel();
                }
            }
        }, 0, 1000);
        return dialog;
    }

    private static JDialog createTimeDialog(JFrame parentFrame, String title, Component component, int timeout) {
        final JDialog dialog = new JDialog(parentFrame, title, false);
        // 设置标签的首选大小为图片的大小
        dialog.setSize(component.getWidth() + 25, component.getHeight() + 60);
        dialog.add(component);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setResizable(false);
        java.util.Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int count = timeout;

            @Override
            public void run() {
                dialog.setTitle("<html><span style=\"float:right\"><b>" + title + "</b>，关闭倒计时：" + count-- + "秒</span></html>");
                if (count < 0) {
                    dialog.setVisible(false);
                    timer.cancel();
                }
            }
        }, 0, 1000);
        dialog.add(component, BorderLayout.CENTER);
        return dialog;
    }

    private static JDialog createModalDialog(JFrame frame, boolean hasModalColor, int width, int height) {
        int radius = 20;
        JDialog dialog = new JDialog(frame, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);
        dialog.setResizable(false);
        dialog.setSize(width, height);
        dialog.setLocationRelativeTo(frame);
        //位置计算
        Dimension frameSize = frame.getSize();
        Dimension dialogSize = dialog.getSize();
        int x = (frameSize.width - dialogSize.width) / 2;
        int y = (frameSize.height - dialogSize.height) / 2;
        dialog.setLocation(frame.getX() + x, frame.getY() + y - 3);
        // 圆角
        Shape shape = new RoundRectangle2D.Double(0, 0, dialog.getWidth(), dialog.getHeight(), radius, radius);
        dialog.setShape(shape);
        dialog.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Color color = hasModalColor ? ThemeColor.loadingModalColor : ThemeColor.noColor;
        dialog.setBackground(color);
        return dialog;
    }

}
