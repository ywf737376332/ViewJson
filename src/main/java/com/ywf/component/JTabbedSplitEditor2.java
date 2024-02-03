package com.ywf.component;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 多排并列可调整宽度组件
 *
 * @Author YWF
 * @Date 2024/1/24 22:50
 */
public class JTabbedSplitEditor2 extends JPanel implements Serializable {

    private final LinkedList<JScrollPane> pages;

    private JFrame parentFrame;
    private JPanel page;
    private JSplitPane splitPane;

    public JTabbedSplitEditor2(LayoutManager layout, JFrame parentFrame) {
        super(layout);
        pages = new LinkedList<>();
        this.parentFrame = parentFrame;
        if (page == null) {
            page = new JPanel(new BorderLayout());
        }
        addTab();
        // 自适应屏幕大小变化，调整组件宽度
        parentFrame.addComponentListener(new SplitResizedEventService());
    }

    public void addTab() {
        insertTab(initScrollEditor());
    }

    private void insertTab(JScrollPane component) {
        removeComponents(page);
        cacheComment(component);
        int componentCount = getEditorComponentCount();
        page.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(130, 128, 128, 130)));
        switch (componentCount) {
            case 1:
                page.add(getComment(1), BorderLayout.CENTER);
                break;
            case 2:
                splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
                splitPane.setLeftComponent(getComment(1));
                splitPane.setRightComponent(getComment(2));
                splitPane.setDividerLocation(parentFrame.getWidth() / 2 - 21);
                page.add(splitPane, BorderLayout.CENTER);
                break;
            case 3:
                JSplitPane splitPane3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
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
                addSplitWidthChangeListener(parentFrame, splitPane, splitPane3);
                page.add(splitPane, BorderLayout.CENTER);
                break;
            default:
                System.out.println("没有逻辑被执行");
        }
        // 重新绘制
        this.add(page);
        this.revalidate();
        this.repaint();
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

    // 移除当前容器的所有组件
    public void removeComponents(JComponent container) {
        if (container != null) {
            container.removeAll();
        }
    }

    /**
     * 获取组件数量
     */
    public int getEditorComponentCount() {
        return pages.size();
    }

    /**
     * 根据组件序列号获取组件
     *
     * @param index
     * @return
     */
    public JComponent getComment(int index) {
        return pages.get(index - 1);
    }

    /**
     * 组件缓存
     *
     * @param component
     */
    private void cacheComment(JScrollPane component) {
        if (component == null) {
            return;
        }
        if (getEditorComponentCount() == 3) {
            System.out.println("目前支持最多3个组件");
            return;
        }
        pages.add(component);
        component.setName(String.valueOf(component.hashCode()));
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

    /**
     * 获取当前焦点组件
     *
     * @param frame
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T findComponentsByFocus(JFrame frame, Class<T> clazz) {
        Component focusOwner = frame.getFocusOwner();
        if (focusOwner == null) {
            return null;
        } else {
            return clazz.isInstance(focusOwner) ? (T) focusOwner : null;
        }
    }

    /**
     * 关闭最后一次打开的组件
     */
    public void closeAbleTabbed(RTextArea rSyntaxTextArea) {
        System.out.println("执行关闭操作:传进来的组件：" + rSyntaxTextArea);
        rSyntaxTextArea = rSyntaxTextArea == null ? findComponentsByFocus(parentFrame, RSyntaxTextArea.class) : rSyntaxTextArea;
        if (rSyntaxTextArea == null) {
            return;
        }
        System.out.println("当前组件：" + rSyntaxTextArea.getName());
        RTextScrollPane rTextScrollPane = (RTextScrollPane) rSyntaxTextArea.getParent().getParent();
        int componentCount = getEditorComponentCount();
        System.out.println("当前激活的:" + rTextScrollPane.getName());
        for (int i = 0; i < pages.size(); i++) {
            if (rTextScrollPane.equals(pages.get(i))) {
                System.out.println("找到相同的:" + pages.get(i).getName());
                if (componentCount > 1) {
                    System.out.println("关闭了:" + pages.get(i).getName());
                    pages.remove(i);
                    // 刷新组件布局
                    insertTab(null);
                }
            }
        }
    }

    /**
     * 创建组件
     *
     * @return
     */
    private static RTextScrollPane initScrollEditor() {
        //return TextAreaBuilder.createJsonScrollTextArea();
        return createJsonScrollTextArea();
    }

    /**
     * 窗口最大化，自适应调整每个分隔栏宽度
     */
    class SplitResizedEventService extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            if (pages.size() == 2) {
                splitPane.setDividerLocation(parentFrame.getWidth() / 2 - 21);
            } else if (pages.size() == 3) {
                splitPane.setDividerLocation(parentFrame.getWidth() / 3 - 15);
            }
        }
    }


    public static RTextScrollPane createJsonScrollTextArea() {
        JSONRSyntaxTextArea syntaxTextArea = createTextArea(SyntaxConstants.SYNTAX_STYLE_JSON, "/themes/textAreaThemes/ideaLight.xml");
        RTextScrollPane rTextScrollPane = new RTextScrollPane(syntaxTextArea);
        rTextScrollPane.setBorder(BorderFactory.createEmptyBorder());
        // 显示行号
        rTextScrollPane.setLineNumbersEnabled(true);
        rTextScrollPane.setFoldIndicatorEnabled(true);
        return rTextScrollPane;
    }

    private static JSONRSyntaxTextArea createTextArea(String styleKey, String themesPath) {
        JSONRSyntaxTextArea textArea = new JSONRSyntaxTextArea();
        textArea.setSyntaxEditingStyle(styleKey);
        // 这行代码启用了代码折叠功能
        textArea.setCodeFoldingEnabled(true);
        // 启用了抗锯齿功能
        textArea.setAntiAliasingEnabled(true);
        // 启用了自动滚动功能
        textArea.setAutoscrolls(true);
        // 读取配置信息中的数据
        textArea.setEditable(true);
        //组件名称
        textArea.setName("#rst:" + textArea.hashCode());
        // 自动换行功能
        textArea.setLineWrap(false);
        textArea.revalidate();
        try {
            Theme theme = Theme.load(TextAreaBuilder.class.getResourceAsStream(themesPath));
            theme.apply(textArea);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return textArea;
    }

}

