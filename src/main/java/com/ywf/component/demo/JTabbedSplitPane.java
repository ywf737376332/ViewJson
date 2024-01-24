package com.ywf.component.demo;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

/**
 * 多排并列可调整宽度组件
 *
 * @Author YWF
 * @Date 2024/1/24 22:50
 */
public class JTabbedSplitPane extends JFrame {

    private LinkedList<JScrollPane> componentList = new LinkedList<>();

    private JFrame parentFrame;
    private JPanel container;
    private JSplitPane splitPane;

    public JTabbedSplitPane() {
    }

    public JTabbedSplitPane(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public JPanel add(JScrollPane component) {
        if (component == null) {
            return null;
        }
        cacheComment(component);
        removeComponents(container);
        if (container == null) {
            container = new JPanel();
        }
        container.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(130, 128, 128, 130)));
        container.setLayout(new BorderLayout());
        int componentCount = getComponentCount();
        for (int i = 0; i < componentCount; i++) {
            switch (componentCount) {
                case 1:
                    container.add(getComment(1), BorderLayout.CENTER);
                    break;
                case 2:
                    disposeSplitPane(splitPane);
                    splitPane = new JSplitPane();
                    splitPane.setLeftComponent(getComment(1));
                    splitPane.setDividerLocation(parentFrame.getWidth() / 2 - 30);
                    splitPane.setRightComponent(getComment(2));
                    container.add(splitPane, BorderLayout.CENTER);
                    System.out.println("组件大小：" + (parentFrame.getWidth() / 2 - 30));
                    break;
                default:
            }
        }
        return container;
    }

    public void removeComponents(JComponent container) {
        if (container != null) {
            container.removeAll();
            System.out.println("组件移除成功");
        }
    }

    /**
     * 获取组件数量
     */
    public int getComponentCount() {
        return componentList.size();
    }

    public JComponent getComment(int index) {
        return componentList.get(index - 1);
    }

    private JComponent cacheComment(JScrollPane component) {
        if (getComponentCount() == 2) {
            System.out.println("目前支持最多两个组件");
            return component;
        }
        componentList.add(component);
        component.setName(String.valueOf(component.hashCode()));
        return component;
    }

    private void disposeSplitPane(JSplitPane splitPane) {
        if (splitPane != null) {
            splitPane.removeAll();
            splitPane.setDividerLocation(0);
            splitPane.revalidate();
            splitPane.repaint();
        }
    }

    private Color updateRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

}

