package com.ywf.component.setting;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.component.ScrollPaneBuilder;
import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.ThemeColor;
import com.ywf.framework.enums.FontEnum;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 字体设置面板
 *
 * @Author YWF
 * @Date 2024/3/17 14:45
 */
public class FontsPanel extends JPanel {

    public FontsPanel() {
        super();
        //setBackground(Color.GRAY);
        //setPreferredSize(new Dimension(480, 400));
        setLayout(new BorderLayout(10, 10));
        init();
    }

    private void init() {
        JPanel frameFontPanel = createFrameFontPanel();
        JPanel editorFontPanel = createEditorFontPanel();
        JPanel viewFontPanel = createViewFontPanel();
        this.add(frameFontPanel, BorderLayout.NORTH);
        this.add(viewFontPanel, BorderLayout.CENTER);
        this.add(editorFontPanel, BorderLayout.SOUTH);
    }

    /**
     * 创建软件界面字体设置面板
     *
     * @return
     */
    private JPanel createFrameFontPanel() {
        JPanel frameFontPanel = new JPanel(new BorderLayout(10, 20));
        frameFontPanel.setPreferredSize(new Dimension(462, 185));
        frameFontPanel.setBorder(BorderFactory.createTitledBorder(BorderBuilder.border(1, ThemeColor.themeColor), "界面字体设置"));

        JList fontNameList = createJList(Arrays.stream(FontEnum.Name.values()).map(FontEnum.Name::getName).toArray(String[]::new));
        JScrollPane fontNameScrollPane = ScrollPaneBuilder.createScrollPane(fontNameList, "字体：", new Dimension(220, 140));
        frameFontPanel.add(fontNameScrollPane, BorderLayout.WEST);

        JList fontStyleList = createJList(Stream.of("常规", "粗体", "斜体").toArray(String[]::new));  //数据模型
        JScrollPane fontStyleScrollPane = ScrollPaneBuilder.createScrollPane(fontStyleList, "字形：", new Dimension(100, 140));
        frameFontPanel.add(fontStyleScrollPane, BorderLayout.CENTER);

        JList fontSizeList = createJList(Stream.of(FontEnum.Size.values()).map(name -> name.getSize()).toArray(Integer[]::new));  //数据模型
        JScrollPane fontSizeScrollPane = ScrollPaneBuilder.createScrollPane(fontSizeList, "字号：", new Dimension(80, 140));
        frameFontPanel.add(fontSizeScrollPane, BorderLayout.EAST);
        return frameFontPanel;
    }

    /**
     * 创建编辑器字体设置面板
     *
     * @return
     */
    private JPanel createEditorFontPanel() {
        JPanel editorFontPanel = new JPanel(new BorderLayout(10, 20));
        editorFontPanel.setPreferredSize(new Dimension(462, 185));
        editorFontPanel.setBorder(BorderFactory.createTitledBorder(BorderBuilder.border(1, ThemeColor.themeColor), "编辑器字体设置"));

        JList fontNameList = createJList(Arrays.stream(FontEnum.Name.values()).map(FontEnum.Name::getName).toArray(String[]::new));
        JScrollPane fontNameScrollPane = ScrollPaneBuilder.createScrollPane(fontNameList, "字体：", new Dimension(220, 140));
        editorFontPanel.add(fontNameScrollPane, BorderLayout.WEST);

        JList fontStyleList = createJList((Stream.of("常规", "粗体", "斜体").toArray(String[]::new)));
        JScrollPane fontStyleScrollPane = ScrollPaneBuilder.createScrollPane(fontStyleList, "字形：", new Dimension(100, 140));
        editorFontPanel.add(fontStyleScrollPane, BorderLayout.CENTER);

        JList fontSizeList = createJList(Stream.of(FontEnum.Size.values()).map(name -> name.getSize()).toArray(Integer[]::new));
        JScrollPane fontSizeScrollPane = ScrollPaneBuilder.createScrollPane(fontSizeList, "字号：", new Dimension(80, 140));
        editorFontPanel.add(fontSizeScrollPane, BorderLayout.EAST);

        return editorFontPanel;
    }

    /**
     * 创建字体预览面板
     *
     * @return
     */
    private JPanel createViewFontPanel() {
        JPanel viewFontPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        viewFontPanel.setPreferredSize(new Dimension(462, 100));
        viewFontPanel.setBorder(BorderFactory.createTitledBorder(BorderBuilder.border(1, ThemeColor.themeColor), "字体预览"));
        FlatLabel viewFontLabel1 = new FlatLabel();
        viewFontLabel1.setText("abcdefghijklmn ABCDEFGHIJKLMN 0123456789 (){}[]");
        viewFontLabel1.setBorder(BorderBuilder.emptyBorder(10));
        FlatLabel viewFontLabel2 = new FlatLabel();
        viewFontLabel2.setText("每个程序员都渴望的自主可控工具箱 +-*/= .,;:!? #&$%@|^");
        viewFontLabel2.setBorder(BorderBuilder.emptyBorder(10));
        viewFontPanel.add(viewFontLabel1);
        viewFontPanel.add(viewFontLabel2);
        return viewFontPanel;
    }

    private <E> JList createJList(final E items[]) {
        JList viewList = new JList();
        ListModel fontStyleListModel = new DefaultComboBoxModel(items);  //数据模型
        viewList.setModel(fontStyleListModel);
        return viewList;
    }

}
