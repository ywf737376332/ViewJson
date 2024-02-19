package com.ywf.framework.utils;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/25 18:28
 */
public class IconUtils {

    /**
     * 获取指定路径的图标
     *
     * @param iconPath 图标路径
     * @return Image对象
     */
    public static Image getIcon(String iconPath) {
        Image image;
        try {
            image = ImageIO.read(IconUtils.class.getResource(iconPath));
        } catch (IOException e) {
            throw new RuntimeException("图标文件未找到："+iconPath);
        }
        return image;
    }

    public static ImageIcon getSVGIcon(String svgPath) {
        ClassLoader classLoader = IconUtils.class.getClassLoader();
        FlatSVGIcon flatSVGIcon = new FlatSVGIcon(svgPath, classLoader);
        return flatSVGIcon;
    }

    public static ImageIcon getSVGIcon(String svgPath, int width, int height) {
        ClassLoader classLoader = IconUtils.class.getClassLoader();
        FlatSVGIcon flatSVGIcon = new FlatSVGIcon(svgPath, width, height, classLoader);
        return flatSVGIcon;
    }

    public static ImageIcon getImageIcon(String imagePath) {
        return new ImageIcon(Toolkit.getDefaultToolkit().getImage(IconUtils.class.getResource(imagePath)));
    }

}
