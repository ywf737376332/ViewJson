package com.ywf.component.setting;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.SvgIconFactory;
import com.ywf.framework.base.ThemeColor;

import javax.swing.*;
import java.awt.*;

/**
 * 字体设置面板
 *
 * @Author YWF
 * @Date 2024/3/17 14:45
 */
public class FontsPanel extends JPanel {

    public FontsPanel() {
        super();
        init();
        //setBackground(Color.GRAY);
        setPreferredSize(new Dimension(485, 400));
    }

    private void init() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JPanel frameFontPanel = new JPanel();
        frameFontPanel.setPreferredSize(new Dimension(462,200));
        frameFontPanel.setBorder(BorderFactory.createTitledBorder(BorderBuilder.border(1, ThemeColor.themeColor), "界面字体设置"));

        JList fontList = new JList();
        ListModel jListModel =  new DefaultComboBoxModel(new String[] { "张三", "李四" });  //数据模型
        fontList.setModel(jListModel);
        frameFontPanel.add(fontList);
        add(frameFontPanel);
    }

    /**
     * 图标
     *
     * @param themeIcon
     * @return
     */
    private FlatSVGIcon getThemeIcon(String themeIcon) {
        return SvgIconFactory.icon(themeIcon, 40, 40);
    }

}
