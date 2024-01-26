package com.ywf.component.demo;

import com.ywf.component.TextAreaPopupMenuBuilder;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.accessibility.Accessible;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 多排并列可调整宽度组件
 *
 * @Author YWF
 * @Date 2024/1/24 22:50
 */
public class JTabbedSplitPane extends JPanel implements Serializable {

    private final LinkedList<JScrollPane> pages;

    private JFrame parentFrame;
    private JPanel page;
    private JSplitPane splitPane;

    public JTabbedSplitPane(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        pages = new LinkedList<>();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10)); // 设置外边距
    }

    public JPanel addTab(JScrollPane component) {
        return insertTab(component);
    }

    private JPanel insertTab(JScrollPane component) {
        if (component == null) {
            return null;
        }
        // 缓存SyntaxTextArea组件
        cacheComment(component);
        // 给SyntaxTextArea组件添加右键菜单
        addSyntaxTextAreaPopupMenu(component);
        removeComponents(page);
        if (page == null) {
            page = new JPanel();
        }
        page.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(130, 128, 128, 130)));
        page.setLayout(new BorderLayout());
        int componentCount = getComponentCount();
        switch (componentCount) {
            case 1:
                page.add(getComment(1), BorderLayout.CENTER);
                break;
            case 2:
                splitPane = new JSplitPane();
                splitPane.setLeftComponent(getComment(1));
                splitPane.setRightComponent(getComment(2));
                splitPane.setDividerLocation(parentFrame.getWidth() / 2 - 21);
                page.add(splitPane, BorderLayout.CENTER);
                break;
            case 3:
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
                addSplitWidthChangeListener(parentFrame, splitPane, splitPane3);
                page.add(splitPane, BorderLayout.CENTER);
                break;
            default:
        }
        // 重新绘制
        page.revalidate();
        page.repaint();
        return page;
    }

    /**
     * 鼠标拖动窗口大小，优化子组件大小改变速率
     *
     * @param mainFrame 窗口主面板
     * @param slpParent 父组件
     * @param slpChild  子组件
     */
    private void addSplitWidthChangeListener(JFrame mainFrame, JSplitPane slpParent, JSplitPane slpChild) {
        slpParent.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, evt -> {
            if (JSplitPane.DIVIDER_LOCATION_PROPERTY.equals(evt.getPropertyName())) {
                int newLocation = (int) evt.getNewValue();
                // 在这里执行宽度调整后的相关操作
                slpChild.setDividerLocation((mainFrame.getWidth() - newLocation) / 2 - 25);
            }
        });
    }

    public void removeComponents(JComponent container) {
        if (container != null) {
            container.removeAll();
            revalidate();
            repaint();
        }
    }

    /**
     * 获取组件数量
     */
    public int getComponentCount() {
        return pages.size();
    }

    public JComponent getComment(int index) {
        return pages.get(index - 1);
    }

    private JComponent cacheComment(JScrollPane component) {
        if (getComponentCount() == 3) {
            System.out.println("目前支持最多3个组件");
            return component;
        }
        pages.add(component);
        System.out.println("组件数量：" + getComponentCount());
        component.setName(String.valueOf(component.hashCode()));
        return component;
    }

    private void disposeSplitPane(JSplitPane splitPane) {
        if (splitPane == null) {
            return;
        }
        splitPane.removeAll();
        splitPane.setDividerLocation(0);
        splitPane.revalidate();
        splitPane.repaint();
    }

    /**
     * 获取相应容器中所有的相应类型组件及子组件
     *
     * @param container
     */
    public <T> List<T> findComponentsByType(Container container) {
        List<T> rstList = new ArrayList<>();
        // 遍历当前容器的所有组件
        for (Component component : container.getComponents()) {
            if (component instanceof RSyntaxTextArea) {
                // 如果找到名称匹配的JTextArea，直接返回
                rstList.add((T) component);
            } else if (component instanceof Container) {
                // 如果组件本身也是一个容器（如 JPanel、JScrollPane 等），递归搜索其内部组件
                List<T> foundTextAreaList = findComponentsByType((Container) component);
                if (foundTextAreaList != null && foundTextAreaList.size() > 0) {
                    rstList.addAll(foundTextAreaList);
                }
            }
        }
        return rstList;
    }

    private Color updateRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    private void addSyntaxTextAreaPopupMenu(JScrollPane component) {
        if (component == null) {
            return;
        }
        try {
            RSyntaxTextArea rSyntaxTextArea = (RSyntaxTextArea) component.getViewport().getView();
            rSyntaxTextArea.addMouseListener(TextAreaPopupMenuBuilder.getInstance().getPopupListener());
            System.out.println("获取到的组件名称：" + rSyntaxTextArea.getName());
        } catch (RuntimeException e) {
            System.out.println("获取到的组件失败：" + e);
        }
    }

    //获取当前激活组件的宽度

}

