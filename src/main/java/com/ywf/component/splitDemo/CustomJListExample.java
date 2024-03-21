package com.ywf.component.splitDemo;

import com.ywf.action.ResourceBundleService;
import com.ywf.framework.enums.FontEnum;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CustomJListExample {

    private static ResourceBundle resourceBundle;
    public static void main(String[] args) {
        resourceBundle = ResourceBundleService.getInstance().getResourceBundle();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Custom JList Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);

            ArrayList<String> displayValues = new ArrayList<>();
            displayValues.add("选项1");
            displayValues.add("选项2");
            displayValues.add("选项3");

            ArrayList<String> actualValues = new ArrayList<>();
            actualValues.add("实际值1");
            actualValues.add("实际值2");
            actualValues.add("实际值3");
            ArrayList<FontEnum.Name> collect = Arrays.stream(FontEnum.Name.values()).collect(Collectors.toCollection(ArrayList::new));
            CustomListModel model = new CustomListModel(collect);
            JList<FontEnum.Name> list = new JList(FontEnum.Name.values());
            list.setCellRenderer(new EnumListRenderer());
            // 添加选择监听器
            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) { // 忽略调整事件
                        // 获取选中的值
                        System.out.println(e.getValueIsAdjusting());
                        FontEnum.Name selectedValue = list.getSelectedValue();
                        System.out.println("选中的值为：" + selectedValue);
                    }
                }
            });
            frame.add(new JScrollPane(list), BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
    public static String getMessage(Object keyRoot) {
        return resourceBundle.getString(keyRoot + ".Name");
    }

}

class CustomListModel extends AbstractListModel<Enum> {
    private final ArrayList<FontEnum.Name> displayValues;

    public CustomListModel(ArrayList<FontEnum.Name> displayValues) {
        this.displayValues = displayValues;
    }

    @Override
    public int getSize() {
        return displayValues.size();
    }

    @Override
    public Enum getElementAt(int index) {
        System.out.println("显示值："+displayValues.get(index));
        return displayValues.get(index);
    }

    public String getActualValueAt(int index) {
        return displayValues.get(index).name();
    }
}

class EnumListRenderer extends JLabel implements ListCellRenderer<FontEnum.Name> {

    @Override
    public Component getListCellRendererComponent(JList<? extends FontEnum.Name> list, FontEnum.Name value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(CustomJListExample.getMessage(value.getMsgKey()));
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        return this;
    }
}