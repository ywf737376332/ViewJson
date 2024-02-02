package com.ywf.component.demo3;

import com.ywf.component.JSONRSyntaxTextArea;
import com.ywf.component.TextAreaBuilder;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

/**
 * 多排并列可调整宽度富文本编辑框组件
 *
 * 1、把JSplitPane组件保存在LinkedList中
 *      规律：1-编辑框 0-JSplitPane
 *           2-编辑框 1-JSplitPane
 *           3-编辑框 2-JSplitPane
 *           4-编辑框 3-JSplitPane
 *           5-编辑框 4-JSplitPane
 * 2、给每一个JSplitPane组件增加index索引值
 *
 * @Author YWF
 * @Date 2024/2/2 11:21
 */
public class TabEditor extends JPanel {

    private final LinkedList<JScrollPane> pages;
    private final LinkedList<JSplitPane> jsL = new LinkedList<>();

    private JFrame parentFrame;
    private JPanel page;
    private int indexTab = 1;

    public TabEditor(LayoutManager layout, JFrame parentFrame) {
        super(layout);
        pages = new LinkedList<>();
        this.parentFrame = parentFrame;
        if (page == null) {
            page = new JPanel(new BorderLayout());
        }
        //insertTab();
        insertTab(indexTab);
    }

    public void addTab() {
        //insertTab();
        insertTab(indexTab);
    }

    public void insertTab() {
        removeComponents(page);
        cacheComment(createJsonScrollTextArea());
        int componentCount = getEditorComponentCount();
        System.out.println("获取到的组件数量："+componentCount);
        switch (componentCount) {
            case 1:
                page.add(getComment(1), BorderLayout.CENTER);
                break;
            case 2: // 2-编辑框 1-JSplitPane
                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
                splitPane.setLeftComponent(getComment(1));
                splitPane.setRightComponent(getComment(2));
                splitPane.setDividerLocation(parentFrame.getWidth() / 2 - 21);
                page.add(splitPane, BorderLayout.CENTER);
                break;
            case 3: // 3-编辑框 2-JSplitPane
                JSplitPane splitPaneM = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
                JSplitPane splitPane3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
                splitPane3.setLeftComponent(getComment(2));
                splitPane3.setRightComponent(getComment(3));
                splitPane3.setDividerLocation(parentFrame.getWidth() / 3 - 15);
                splitPane3.setContinuousLayout(true);
                // 添加左侧面板
                splitPaneM.setLeftComponent(getComment(1));
                // 添加右侧面板
                splitPaneM.setRightComponent(splitPane3);
                splitPaneM.setDividerLocation(parentFrame.getWidth() / 3 - 15);
                splitPaneM.setContinuousLayout(true);
                page.add(splitPaneM, BorderLayout.CENTER);
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

                JSplitPane splitPaneF = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
                splitPaneF.setLeftComponent(splitPaneA);
                splitPaneF.setRightComponent(splitPaneB);
                splitPaneF.setDividerLocation(parentFrame.getWidth() / 2 - 15);

                System.out.println("总宽度："+parentFrame.getWidth() + ",四分之一："+(parentFrame.getWidth() / 4 - 15) +",二分之一："+(parentFrame.getWidth() / 2 - 15));

                page.add(splitPaneF, BorderLayout.CENTER);
                break;
            default:
                System.out.println("没有逻辑被执行");
        }

        this.add(page);
        this.revalidate();
        this.repaint();
    }


    public void insertTab(int indexTab) {
        removeComponents(page);
        cacheComment(createJsonScrollTextArea());
        int componentCount = getEditorComponentCount();
        System.out.println("获取到的组件数量："+componentCount);
        if (indexTab == 1){
            page.add(getComment(1), BorderLayout.CENTER);
            System.out.println("默认初始化一个组件");
        }else{
            JSplitPane splitPane = addComponentToPage();
            page.add(splitPane, BorderLayout.CENTER);
        }
        this.indexTab ++;
        this.add(page);
        this.revalidate();
        this.repaint();
    }

    private void cacheComment(JScrollPane component) {
        if (component == null) {
            return;
        }
        if (getEditorComponentCount() == 4) {
            System.out.println("目前支持最多4个组件");
            return;
        }
        pages.add(component);
        component.setName(String.valueOf(component.hashCode()));
    }

    public void removeComponents(JComponent container) {
        if (container != null) {
            container.removeAll();
            container.revalidate();
            container.repaint();
        }
    }

    /**
     * 获取组件数量
     */
    public int getEditorComponentCount() {
        return pages.size();
    }













    public static RTextScrollPane createJsonScrollTextArea() {
        JSONRSyntaxTextArea syntaxTextArea = createTextArea(SyntaxConstants.SYNTAX_STYLE_JSON, "/themes/textAreaThemes/ideaLight.xml");
        RTextScrollPane rTextScrollPane = new RTextScrollPane(syntaxTextArea);
        rTextScrollPane.setBorder(BorderFactory.createEmptyBorder());
        // 显示行号
        rTextScrollPane.setLineNumbersEnabled(true);
        rTextScrollPane.setFoldIndicatorEnabled(true);
        rTextScrollPane.setName(syntaxTextArea.hashCode()+"");
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
        textArea.setName("#rst:"+textArea.hashCode());
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

    public JComponent getComment(int index) {
        return pages.get(index - 1);
    }

    private void instanceJSplitPane(int num){
        jsL.clear();
        for (int i = 0; i < num; i++) {
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            jsL.add(splitPane);
        }
    }

    private LinkedList<JSplitPane> getJSplitPaneList(){
        return jsL;
    }

    private JSplitPane addComponentToPage() {
        JSplitPane splitPaneParent = null;
        RSyntaxTextArea syntaxTextArea = this.findComponentsByFocus(RSyntaxTextArea.class);
        System.out.println("syntaxTextArea:"+syntaxTextArea.getText());
        RTextScrollPane rTextScrollPane = (RTextScrollPane) syntaxTextArea.getParent().getParent();
        Container split = rTextScrollPane.getParent();
        if (split instanceof JSplitPane){
            splitPaneParent = (JSplitPane)rTextScrollPane.getParent();
            int rightWidth = splitPaneParent.getWidth() - splitPaneParent.getDividerLocation();
            JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            splitPane2.setLeftComponent(rTextScrollPane);
            splitPane2.setRightComponent(getComment(indexTab));
            splitPane2.setDividerLocation(rTextScrollPane.getWidth() / 2);
            splitPaneParent.setLeftComponent(splitPane2);
            splitPaneParent.setRightComponent(getComment(indexTab-1));
            splitPaneParent.setDividerLocation(rightWidth);
        }else{
            splitPaneParent = new JSplitPane();
            splitPaneParent.setLeftComponent(rTextScrollPane);
            splitPaneParent.setRightComponent(getComment(indexTab));
            splitPaneParent.setDividerLocation(rTextScrollPane.getWidth() / 2);
        }
        return splitPaneParent;
    }

    public <T> T findComponentsByFocus(Class<T> clazz) {
        Component focusOwner = parentFrame.getFocusOwner();
        if (focusOwner == null) {
            return null;
        } else {
            return clazz.isInstance(focusOwner) ? (T) focusOwner : null;
        }
    }

}
