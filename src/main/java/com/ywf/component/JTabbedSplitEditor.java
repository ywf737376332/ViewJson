package com.ywf.component;

import com.ywf.action.StateBarEventService;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.ui.ScrollBackToTopLayerUI;
import com.ywf.framework.utils.ComponentUtils;
import com.ywf.framework.utils.ObjectUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 多排并列可调整宽度组件
 *
 * @Author YWF
 * @Date 2024/1/24 22:50
 */
public class JTabbedSplitEditor extends JPanel {

    private final static Logger logger = LoggerFactory.getLogger(JTabbedSplitEditor.class);

    private final LinkedList<JScrollPane> pages;

    private JFrame parentFrame;
    private JPanel page;
    private JSplitPane splitPane;

    public JTabbedSplitEditor(LayoutManager layout, JFrame parentFrame) {
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
        // page.setBorder(BorderBuilder.leftAndRightEmptyBorder(5, 3)); // 设置外边距
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
            case 4: // 3-编辑框 2-JSplitPane
                JSplitPane splitPaneA = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
                splitPaneA.setLeftComponent(getComment(1));
                splitPaneA.setRightComponent(getComment(2));
                splitPaneA.setDividerLocation(parentFrame.getWidth() / 4 - 15);

                JSplitPane splitPaneB = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
                splitPaneB.setLeftComponent(getComment(3));
                splitPaneB.setRightComponent(getComment(4));
                splitPaneB.setDividerLocation(parentFrame.getWidth() / 4 - 15);

                splitPane.setLeftComponent(splitPaneA);
                splitPane.setRightComponent(splitPaneB);
                splitPane.setDividerLocation(parentFrame.getWidth() / 2 - 15);
                addSplitWidthChangeListener(parentFrame, splitPane, splitPaneA, splitPaneB);
                page.add(splitPane, BorderLayout.CENTER);
                break;
            default:
                logger.info("没有逻辑被执行");
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

    private void addSplitWidthChangeListener(JFrame mainFrame, JSplitPane slpParent, JSplitPane slpChild1, JSplitPane slpChild2) {
        slpParent.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, evt -> {
            if (JSplitPane.DIVIDER_LOCATION_PROPERTY.equals(evt.getPropertyName())) {
                int newLocation = (int) evt.getNewValue();
                // 在这里执行宽度调整后的相关操作
                slpChild1.setDividerLocation(newLocation / 2 - 10);
                slpChild2.setDividerLocation((mainFrame.getWidth() - newLocation) / 2 - 25);
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
    public JLayer<JScrollPane> getComment(int index) {
        return new JLayer<>(pages.get(index - 1), new ScrollBackToTopLayerUI());
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
        if (getEditorComponentCount() == 4) {
            logger.info("目前支持最多4个组件");
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
        if (rSyntaxTextArea == null) {
            return;
        }
        RTextScrollPane rTextScrollPane = (RTextScrollPane) rSyntaxTextArea.getParent().getParent();
        int componentCount = getEditorComponentCount();
        for (int i = 0; i < pages.size(); i++) {
            if (rTextScrollPane.equals(pages.get(i))) {
                if (componentCount > 1) {
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
    private RTextScrollPane initScrollEditor() {
        RTextScrollPane scrollTextArea = TextAreaBuilder.createJsonScrollTextArea();
        requestFocusToScrollEditor(scrollTextArea);
        //缓存滚动框
        ObjectUtils.addGroupBean(GlobalKEY.COMPONENT_SCROLL_GROUP, scrollTextArea);
        return scrollTextArea;
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
            } else if (pages.size() == 4) {
                JSplitPane leftSplitComponent = (JSplitPane) splitPane.getLeftComponent();
                JSplitPane rightSplitComponent = (JSplitPane) splitPane.getRightComponent();
                splitPane.setDividerLocation(parentFrame.getWidth() / 2 - 15);
                int dividerLocation = splitPane.getDividerLocation();
                leftSplitComponent.setDividerLocation(dividerLocation / 2);
                rightSplitComponent.setDividerLocation(dividerLocation / 2);
            }
        }
    }

    /**
     * 给当前有滚动框的编辑框组件设置焦点
     * 组件初始化的时候清除其他组件焦点，设置当前组件焦点
     * 鼠标点击编辑框的清除其他组件焦点，设置当前组件焦点
     *
     * @param scrollPane
     */
    public void requestFocusToScrollEditor(JScrollPane scrollPane) {
        JSONRSyntaxTextArea rSyntaxTextArea = ComponentUtils.convertEditor(scrollPane);
        // 给当前组件绑定焦点
        requestFocusToEditor(rSyntaxTextArea);
        // 给当前组件点击事件，点击时绑定焦点
        rSyntaxTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                //设置焦点
                requestFocusToEditor(rSyntaxTextArea);
                //监听文档变化
                StateBarEventService.getInstance().updateStateUI(rSyntaxTextArea);
                // 查找内容高亮显示
                FindPanelBuilder.findAllContentHighlight();
            }
        });
    }

    /**
     * 给当前编辑框组件设置焦点
     *
     * @param syntaxTextArea
     */
    private void requestFocusToEditor(JSONRSyntaxTextArea syntaxTextArea) {
        LinkedList<JScrollPane> scpList = getPages();
        for (JScrollPane scrollPane : scpList) {
            JSONRSyntaxTextArea rSyntaxTextAreaOld = ComponentUtils.convertEditor(scrollPane);
            rSyntaxTextAreaOld.setEditorFocus(false);
        }
        // 保持光标的焦点
        syntaxTextArea.requestFocus();
        syntaxTextArea.setEditorFocus(true);
    }

    /**
     * 获取当前焦点编辑框组件
     *
     * @return
     */
    public JSONRSyntaxTextArea getFocusEditor() {
        LinkedList<JScrollPane> scpList = getPages();
        for (JScrollPane scrollPane : scpList) {
            JSONRSyntaxTextArea rSyntaxTextArea = ComponentUtils.convertEditor(scrollPane);
            if (rSyntaxTextArea.isEditorFocus()) {
                return rSyntaxTextArea;
            }
        }
        return null;
    }

    public LinkedList<JScrollPane> getPages() {
        return pages;
    }
}

