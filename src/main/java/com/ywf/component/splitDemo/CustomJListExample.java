package com.ywf.component.splitDemo;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class CustomJListExample {
    public static void main(String[] args) {
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

            CustomListModel model = new CustomListModel(displayValues, actualValues);
            JList<String> list = new JList<>(model);
            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting()){
                        System.out.println("当前值："+list.getSelectedValue());
                    }
                }
            });
            frame.add(new JScrollPane(list), BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}

class CustomListModel extends AbstractListModel<String> {
    private final ArrayList<String> displayValues;
    private final ArrayList<String> actualValues;

    public CustomListModel(ArrayList<String> displayValues, ArrayList<String> actualValues) {
        this.displayValues = displayValues;
        this.actualValues = actualValues;
    }

    @Override
    public int getSize() {
        return displayValues.size();
    }

    @Override
    public String getElementAt(int index) {
        return displayValues.get(index);
    }

    public String getActualValueAt(int index) {
        return actualValues.get(index);
    }
}