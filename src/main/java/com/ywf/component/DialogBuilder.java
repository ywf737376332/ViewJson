package com.ywf.component;

import com.ywf.framework.base.SvgIconFactory;
import com.ywf.framework.base.ThemeColor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

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
        return createDialog(parentFrame, title, image);
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
        return createDialog(parentFrame, title, SvgIconFactory.mediumIcon(imagePath));
    }

    public static JDialog showMoadlDialog(JFrame parentFrame, int width, int height) {
        return createModalDialog(parentFrame, width, height);
    }

    public static JDialog showMoadlDialog(JFrame parentFrame) {
        return createModalDialog(parentFrame, parentFrame.getWidth() - 16, parentFrame.getHeight() - 8);
    }

    private static JDialog createDialog(JFrame parentFrame, String title, ImageIcon image) {
        final JDialog dialog = new JDialog(parentFrame, title, true);
        JLabel imageLabel = new JLabel(image);
        // 设置标签的首选大小为图片的大小
        dialog.setSize(image.getIconWidth() + 25, image.getIconHeight() + 60);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(imageLabel);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setResizable(false);
        return dialog;
    }

    private static JDialog createModalDialog(JFrame frame, int width, int height) {
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
        dialog.setLocation(frame.getX() + x, frame.getY() + y - 5);
        // 圆角
        Shape shape = new RoundRectangle2D.Double(0, 0, dialog.getWidth(), dialog.getHeight(), radius, radius);
        dialog.setShape(shape);
        dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        int color = 0x22_FF_00_00;
        //dialog.setBackground(new Color(color, true));
        dialog.setBackground(ThemeColor.loadingModalColor);
        return dialog;
    }

}
