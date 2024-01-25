package com.ywf.component.demo;

import javax.accessibility.Accessible;
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
public class JTabbedSplitPane extends JComponent implements Accessible {

    private LinkedList<JScrollPane> componentList = new LinkedList<>();

    private JFrame parentFrame;
    private JPanel container;
    private JSplitPane splitPane;

    public JTabbedSplitPane() {
    }

    public JTabbedSplitPane(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public JPanel addComponent(JScrollPane component) {
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
        //for (int i = componentCount; i <= componentCount; i++) {
        switch (componentCount) {
            case 1:
                container.add(getComment(1), BorderLayout.CENTER);
                System.out.println("组件大小1：" + (parentFrame.getWidth() / 2 - 30));
                break;
            case 2:
                //disposeSplitPane(splitPane);
                splitPane = new JSplitPane();
                splitPane.setLeftComponent(getComment(1));
                splitPane.setDividerLocation(parentFrame.getWidth() / 2 - 20);
                splitPane.setRightComponent(getComment(2));
                container.add(splitPane, BorderLayout.CENTER);
                break;
            case 3:
                //disposeSplitPane(splitPane);
                JSplitPane splitPane3 = new JSplitPane();
                splitPane3.setLeftComponent(getComment(2));
                splitPane3.setRightComponent(getComment(3));
                splitPane3.setDividerLocation(parentFrame.getWidth() / 3 - 15);
                splitPane3.setContinuousLayout(true);

                // 添加左侧面板
                splitPane.setLeftComponent(getComment(1));
                // 添加右侧面板
                splitPane.setRightComponent(splitPane3);
                splitPane.setDividerLocation(parentFrame.getWidth() / 3 - 15);
                splitPane.setContinuousLayout(true);
                splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
                addSplitWidthChangeListener(parentFrame,splitPane,splitPane3);
                container.add(splitPane, BorderLayout.CENTER);
                break;
            default:
        }
        //}
        return container;
    }

    /**
     * 鼠标拖动窗口大小，优化子组件大小改变速率
     * @param mainFrame 窗口主面板
     * @param slpParent 父组件
     * @param slpChild 子组件
     */
    private void addSplitWidthChangeListener(JFrame mainFrame,JSplitPane slpParent,JSplitPane slpChild) {
        slpParent.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, evt -> {
            if (JSplitPane.DIVIDER_LOCATION_PROPERTY.equals(evt.getPropertyName())) {
                int newLocation = (int) evt.getNewValue();
                // 在这里执行宽度调整后的相关操作
                slpChild.setDividerLocation((mainFrame.getWidth() - newLocation) / 2-25);
            }
        });
    }

    public void removeComponents(JComponent container) {
        if (container != null) {
            container.removeAll();
            revalidate();
            repaint();
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
        if (getComponentCount() == 3) {
            System.out.println("目前支持最多3个组件");
            return component;
        }
        componentList.add(component);
        System.out.println("组件数量：" + getComponentCount());
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

    //获取当前激活组件的宽度
}

