package com.ywf.component.setting;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.action.ResourceBundleService;
import com.ywf.component.ScrollPaneBuilder;
import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.ThemeColor;
import com.ywf.framework.enums.FontEnum;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 字体设置面板
 *
 * @Author YWF
 * @Date 2024/3/17 14:45
 */
public class FontsPanel extends JPanel {

    private static ResourceBundle resourceBundle;

    public FontsPanel() {
        super();
        //setBackground(Color.GRAY);
        //setPreferredSize(new Dimension(480, 400));
        setLayout(new BorderLayout(10, 10));
        init();
    }

    private void init() {
        resourceBundle = ResourceBundleService.getInstance().getResourceBundle();
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

        //JList fontNameList = createJList(Arrays.stream(FontEnum.Name.values()).map(FontEnum.Name::getName).toArray(String[]::new));
        JList fontNameList = createJList(Arrays.stream(FontEnum.Name.values()).map(FontEnum.Name::getMsgKey).collect(Collectors.toCollection(ArrayList::new)),Arrays.stream(FontEnum.Name.values()).map(FontEnum.Name::getName).collect(Collectors.toCollection(ArrayList::new)));
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

        //JList fontNameList = createJList(Arrays.stream(FontEnum.Name.values()).map(FontEnum.Name::getName).toArray(String[]::new));
        JList fontNameList = createJList(Arrays.stream(FontEnum.Name.values()).map(FontEnum.Name::getMsgKey).limit(3).collect(Collectors.toCollection(ArrayList::new)),Arrays.stream(FontEnum.Name.values()).map(FontEnum.Name::getName).collect(Collectors.toCollection(ArrayList::new)));
        JScrollPane fontNameScrollPane = ScrollPaneBuilder.createScrollPane(fontNameList, "字体：", new Dimension(220, 140));
        editorFontPanel.add(fontNameScrollPane, BorderLayout.WEST);

        JList fontStyleList = createJList((Stream.of("常规", "粗体", "斜体").toArray(String[]::new)));
        JScrollPane fontStyleScrollPane = ScrollPaneBuilder.createScrollPane(fontStyleList, "字形：", new Dimension(100, 140));
        editorFontPanel.add(fontStyleScrollPane, BorderLayout.CENTER);

        JList fontSizeList = createJList(Stream.of(FontEnum.Size.values()).map(name -> name.getSize()).limit(3).toArray(Integer[]::new));
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
        viewList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()){
                System.out.println("当前实际值："+FontEnum.Name.getFontNameByKey((String) viewList.getSelectedValue()));
            }
        });
        return viewList;
    }

    private <E> JList createJList(final ArrayList<String> items,final ArrayList<String> values) {
        JList viewList = new JList();
        FontListModel fontStyleListModel = new FontListModel(items,values);  //数据模型
        viewList.setModel(fontStyleListModel);
        viewList.setCellRenderer(new ViewListRenderer());
        viewList.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()){
                System.out.println("当前实际值："+fontStyleListModel.getValueAt(viewList.getSelectedIndex()));
            }
        });
        return viewList;
    }

    public static String getMessage(Object keyRoot) {
        return resourceBundle.getString(keyRoot + ".Name");
    }

}

/**
 * JList数据模型
 */
class FontListModel extends AbstractListModel<String> {
    private final ArrayList<String> names;
    private final ArrayList<String> values;

    public FontListModel(ArrayList<String> names, ArrayList<String> values) {
        this.names = names;
        this.values = values;
    }
    @Override
    public int getSize() {
        return names.size();
    }

    @Override
    public String getElementAt(int index) {
        return names.get(index);
    }

    public String getValueAt(int index) {
        return values.get(index);
    }
}

/**
 * 显示值的处理
 */
class ViewListRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        return super.getListCellRendererComponent(list, FontsPanel.getMessage(value), index, isSelected, cellHasFocus);
    }
}