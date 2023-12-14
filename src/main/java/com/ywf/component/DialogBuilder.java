package com.ywf.component;

import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import java.awt.*;

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
    public static JDialog ShowImageDialog(JFrame parentFrame, String title, ImageIcon image) {
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
    public static JDialog ShowImageDialog(JFrame parentFrame, String title, String imagePath) {
        ImageIcon image = IconUtils.getImageIcon(imagePath);
        return createDialog(parentFrame, title, image);
    }

    private static JDialog createDialog(JFrame parentFrame, String title, ImageIcon image) {
        JDialog dialog = new JDialog(parentFrame, title, true);
        JLabel imageLabel = new JLabel(image);
        imageLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xDE_DE_DE)),
                BorderFactory.createLineBorder(Color.white, 4)));
        dialog.setSize(image.getIconWidth(), image.getIconHeight());
        dialog.setLayout(new BorderLayout());
        dialog.add(imageLabel, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setResizable(false);
        dialog.setVisible(true);
        return dialog;
    }

}
