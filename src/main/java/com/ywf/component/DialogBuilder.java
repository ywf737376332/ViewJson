package com.ywf.component;

import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
     * @date 2023/12/12 23:54
     *
     * @param parentFrame
     * @param title
     * @param image
     */
    public static JDialog ShowImageDialog(JFrame parentFrame, String title, ImageIcon image){
        return createDialog(parentFrame, title, image);
    }

    /**
     * 图片组件创建
     * @date 2023/12/12 23:54
     *
     * @param parentFrame
     * @param title
     * @param imagePath
     */
    public static JDialog ShowImageDialog(JFrame parentFrame, String title, String imagePath){
        ImageIcon image = IconUtils.getImageIcon(imagePath);
        return createDialog(parentFrame, title, image);
    }

    private static JDialog createDialog(JFrame parentFrame, String title, ImageIcon image){
        JDialog dialog = new JDialog(parentFrame, title, true);
        JLabel imageLabel = new JLabel(image);
        dialog.add(imageLabel, BorderLayout.CENTER);
        dialog.setSize(image.getIconWidth() + 40, image.getIconHeight() + 40);
        dialog.setLayout(new BorderLayout());
        dialog.add(imageLabel, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setResizable(false);
        dialog.setVisible(true);
        closeDialog(dialog);
        return dialog;
    }

    private static void closeDialog(Dialog dialog) {
        System.out.println("这儿不知道怎么回事不起作用1");

        dialog.addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                System.out.println("这儿不知道怎么回事不起作用2");

            }

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                System.out.println("这儿不知道怎么回事不起作用3");
                dialog.dispose();
            }
        });
    }

}
