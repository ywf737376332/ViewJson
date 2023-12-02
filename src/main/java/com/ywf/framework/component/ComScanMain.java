package com.ywf.framework.component;

import javax.swing.*;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/2 15:11
 */
public class ComScanMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame("获取组件类型");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        panel.add(new JButton("按钮1"));
        panel.add(new JLabel("标签1"));
        panel.add(new JTextField("文本框1"));

        frame.add(panel);
        frame.setVisible(true);

        Component component = getComponentByType(frame, JButton.class);
        System.out.println("找到的组件：" + component);
        component.setBackground(Color.BLUE);
    }

    public static <T extends Component> T getComponentByType(Container container, Class<T> type) {
        for (Component component : container.getComponents()) {
            if (type.isInstance(component)) {
                return type.cast(component);
            }
            if (component instanceof Container) {
                T found = getComponentByType((Container) component, type);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
