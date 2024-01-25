package com.ywf.component.demo;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Test {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 创建 JSplitPane 和其内容
            JPanel panel1 = new JPanel();
            panel1.setBackground(Color.BLUE);
            JPanel panel2 = new JPanel();
            panel2.setBackground(Color.RED);
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel1, panel2);

            // 添加 PropertyChangeListener 监听 dividerLocation 属性
            splitPane.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (JSplitPane.DIVIDER_LOCATION_PROPERTY.equals(evt.getPropertyName())) {
                        int newLocation = (int) evt.getNewValue();
                        System.out.println("Divider location changed to: " + newLocation);
                        // 在这里执行宽度调整后的相关操作
                    }
                }
            });

            // 设置窗口内容并显示
            JFrame frame = new JFrame("SplitPane Resize Listener Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(splitPane, BorderLayout.CENTER);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
