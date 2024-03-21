package com.ywf.component.setting;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.action.ResourceBundleService;
import com.ywf.component.ScrollPaneBuilder;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.ThemeColor;
import com.ywf.framework.enums.EditorFontEnum;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import com.ywf.framework.utils.ConvertUtils;
import com.ywf.pojo.FontListModel;
import com.ywf.pojo.ModelEntity;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * 字体设置面板
 *
 * @Author YWF
 * @Date 2024/3/17 14:45
 */
public class EditorFontsPanel extends JPanel {

    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    private static ResourceBundle resourceBundle;

    public EditorFontsPanel() {
        super();
        setLayout(new BorderLayout(10, 10));
        init();
    }

    private void init() {
        resourceBundle = ResourceBundleService.getInstance().getResourceBundle();
        JPanel editorFontPanel = createEditorFontPanel();
        JPanel viewFontPanel = createViewFontPanel();
        this.add(editorFontPanel, BorderLayout.CENTER);
        this.add(viewFontPanel, BorderLayout.SOUTH);
    }

    /**
     * 创建编辑器字体设置面板
     *
     * @return
     */
    private JPanel createEditorFontPanel() {
        JPanel editorFontPanel = new JPanel(new BorderLayout(10, 20));
        editorFontPanel.setPreferredSize(new Dimension(450, 185));
        TitledBorder titledBorder = BorderFactory.createTitledBorder(BorderBuilder.border(1, ThemeColor.themeColor), "编辑器字体设置");
        editorFontPanel.setBorder(titledBorder);

        ArrayList<ModelEntity> fontNameModelList = Arrays.stream(EditorFontEnum.Name.values()).map((EditorFontEnum.Name fontName) -> new ModelEntity(fontName.getMsgKey(), fontName.getName())).collect(Collectors.toCollection(ArrayList::new));
        JList fontNameList = createJList(fontNameModelList, EditorFontEnum.Type.FONT_NAME);
        JScrollPane fontNameScrollPane = ScrollPaneBuilder.createScrollPane(fontNameList, "字体：", new Dimension(200, 140));
        editorFontPanel.add(fontNameScrollPane, BorderLayout.WEST);
        //
        ArrayList<ModelEntity> fontStyleModelList = Arrays.stream(EditorFontEnum.Style.values()).map((EditorFontEnum.Style fontStyle) -> new ModelEntity(fontStyle.getMsgKey(), fontStyle.getStyle())).collect(Collectors.toCollection(ArrayList::new));
        JList fontStyleList = createJList(fontStyleModelList, EditorFontEnum.Type.FONT_STYLE);
        JScrollPane fontStyleScrollPane = ScrollPaneBuilder.createScrollPane(fontStyleList, "字形：", new Dimension(140, 140));
        editorFontPanel.add(fontStyleScrollPane, BorderLayout.CENTER);

        ArrayList<ModelEntity> fontSizeModelList = Arrays.stream(EditorFontEnum.Size.values()).map((EditorFontEnum.Size fontSize) -> new ModelEntity(fontSize.getMsgKey(), fontSize.getSize())).collect(Collectors.toCollection(ArrayList::new));
        JList fontSizeList = createJList(fontSizeModelList, EditorFontEnum.Type.FONT_SIZE);
        JScrollPane fontSizeScrollPane = ScrollPaneBuilder.createScrollPane(fontSizeList, "字号：", new Dimension(120, 140));
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
        viewFontPanel.setPreferredSize(new Dimension(450, 100));
        viewFontPanel.setBorder(BorderFactory.createTitledBorder(BorderBuilder.border(1, ThemeColor.themeColor), "字体预览"));
        //获取默认字体
        ConfigurableApplicationContext.EditorFontStyle editorFontStyle = applicationContext.getEditorFontStyle();
        Font editorFont = new Font(editorFontStyle.getName(), editorFontStyle.getStyle(), ConvertUtils.toInt(editorFontStyle.getSize()));
        FlatLabel viewFontLabel1 = new FlatLabel();
        viewFontLabel1.setFont(editorFont);
        viewFontLabel1.setText("abcdefghijklmn ABCDEFGHIJKLMN 0123456789 (){}[]");
        viewFontLabel1.setBorder(BorderBuilder.emptyBorder(10));
        FlatLabel viewFontLabel2 = new FlatLabel();
        viewFontLabel2.setFont(editorFont);
        viewFontLabel2.setText("每个程序员都渴望的自主可控工具箱 +-*/= .,;:!? #&$%@|^");
        viewFontLabel2.setBorder(BorderBuilder.emptyBorder(10));
        viewFontPanel.add(viewFontLabel1);
        viewFontPanel.add(viewFontLabel2);
        return viewFontPanel;
    }

    private <E> JList<E> createJList(ArrayList<E> items) {
        DefaultListModel<E> listModel = new DefaultListModel<>();
        for (E item : items) {
            listModel.addElement(item);
        }
        return new JList<>(listModel);
    }

    private JList createJList(final ArrayList<ModelEntity> items, EditorFontEnum.Type type) {
        JList viewList = new JList();
        FontListModel fontStyleListModel = new FontListModel(items);  //数据模型
        viewList.setModel(fontStyleListModel);
        // 禁止编辑
        viewList.setEnabled(false);
        // 修改鼠标样式
        viewList.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        setDefaultValue(viewList, items, type);
        // 监听事件有Bug
        //viewList.addListSelectionListener(e -> MenuEventService.getInstance().applyEditorFontActionPerformed(e, viewList, fontStyleListModel, type));
        return viewList;
    }

    private void setDefaultValue(final JList viewList, final ArrayList<ModelEntity> items, EditorFontEnum.Type type) {
        int selectedIndex = -1;
        // 查找要选择的值在数据模型中的索引
        for (int i = 0; i < items.size(); i++) {
            ModelEntity modelEntity = items.get(i);
            if (modelEntity.getValue().equals(getItemName(type))) {
                selectedIndex = i;
                break;
            }
        }
        // 如果找到了要选择的值，则将其设置为默认选中项
        if (selectedIndex != -1) {
            viewList.setSelectedIndex(selectedIndex);
        }
    }

    private <T> T getItemName(EditorFontEnum.Type type) {
        T itemValue = null;
        switch (type) {
            case FONT_NAME:
                itemValue = (T) applicationContext.getEditorFontStyle().getName();
                break;
            case FONT_STYLE:
                itemValue = (T) applicationContext.getEditorFontStyle().getStyle();
                break;
            case FONT_SIZE:
                itemValue = (T) applicationContext.getEditorFontStyle().getSize();
                break;
            default:
        }
        return itemValue;
    }

    public static String getMessage(Object keyRoot) {
        return resourceBundle.getString(keyRoot + ".Name");
    }

}