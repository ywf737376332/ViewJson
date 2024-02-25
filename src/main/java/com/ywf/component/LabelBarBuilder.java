package com.ywf.component;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.utils.ObjectUtils;

import javax.swing.*;
import java.awt.*;

/**
 * 状态栏组件创建
 *
 * @Author YWF
 * @Date 2024/2/22 21:37
 */
public class LabelBarBuilder {

    public static FlatLabel createLabel(boolean isCashe, String componentName) {
        FlatLabel label = new FlatLabel();
        label.setLabelType(FlatLabel.LabelType.medium);
        label.setFont(SystemConstant.SYSTEM_STATE_BAR_FONT);
        label.setForeground(new Color(167, 179, 211));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        casheComponent(isCashe, label, componentName);
        return label;
    }

    public static FlatLabel createLabel() {
        FlatLabel label = new FlatLabel();
        label.setLabelType(FlatLabel.LabelType.medium);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }


    public static FlatLabel createLabel(String text) {
        FlatLabel label = new FlatLabel();
        label.setText("<html><span style=\"font-family:'Microsoft YaHei UI';font-size:9px\">" + text + "</span></html>");
        label.setLabelType(FlatLabel.LabelType.medium);
        label.setForeground(new Color(167, 179, 211));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }

    public static FlatLabel createLabel(String text, Color foreground) {
        FlatLabel label = new FlatLabel();
        label.setText(text);
        label.setLabelType(FlatLabel.LabelType.medium);
        label.setFont(SystemConstant.SYSTEM_STATE_BAR_FONT);
        label.setForeground(foreground);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }

    public static FlatLabel createLabel(String text, Font font, Color foreground) {
        FlatLabel label = new FlatLabel();
        label.setText(text);
        label.setLabelType(FlatLabel.LabelType.medium);
        label.setFont(font);
        label.setForeground(foreground);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }

    public static FlatLabel createLabel(String text, FlatLabel.LabelType labelType, Font font, Color foreground) {
        FlatLabel label = new FlatLabel();
        label.setText(text);
        label.setLabelType(labelType);
        label.setFont(font);
        label.setForeground(foreground);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }

    public static void casheComponent(boolean isCashe, JLabel label, String componentName) {
        if (isCashe) {
            ObjectUtils.setBean(componentName, label);
        }
    }

    public static FlatLabel getLabel(String componentName) {
        return ObjectUtils.getBean(componentName);
    }

}