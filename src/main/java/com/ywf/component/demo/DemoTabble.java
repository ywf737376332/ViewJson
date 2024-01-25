package com.ywf.component.demo;

import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.formdev.flatlaf.ui.FlatScrollPaneUI;
import com.ywf.component.TextAreaBuilder;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.utils.IconUtils;
import com.ywf.framework.utils.JsonUtil;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/1/24 22:52
 */
public class DemoTabble extends JFrame {

    private JFrame _this = this;

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        new DemoTabble().createAndShowGUI(SystemConstant.SYSTEM_TITLE + SystemConstant.SYSTEM_VERSION);
    }

    public void createAndShowGUI(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        setMinimumSize(new Dimension(830, 600));
        setLocation((w - _this.getWidth()) / 2, (h - _this.getHeight()) / 2);
        setMinimumSize(new Dimension(SystemConstant.WINDOWS_MIN_WIDTH, SystemConstant.WINDOWS_MIN_HEIGHT));
        //设置图标
        setIconImages(FlatSVGUtils.createWindowIconImages("/icons/FlatLaf.svg"));
        // 初始化界面
        initUI(_this);
        setVisible(true);
    }

    private void initUI(JFrame frame) {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLUE);
        mainPanel.setLayout(new BorderLayout());

        JPanel editPanel = new JPanel();
        editPanel.setLayout(new BorderLayout());

        editPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10)); // 设置外边距
        JTabbedSplitPane tabbedSplitPane = new JTabbedSplitPane(_this);
        editPanel.add(tabbedSplitPane.addComponent(createJsonScrollTextArea()), BorderLayout.CENTER);

        JToolBar toolBar = new JToolBar("工具栏");
        JButton btnNew = new JButton("新建");
        btnNew.setIcon(IconUtils.getSVGIcon("icons/layoutOne.svg"));
        btnNew.addActionListener(e -> createNewTabActionPerformed(tabbedSplitPane, editPanel));

        JButton btnFormat = new JButton("格式化");
        btnFormat.setIcon(IconUtils.getSVGIcon("icons/formatCode.svg"));
        btnFormat.addActionListener(e -> formatActionPerformed());
        JButton btnClose = new JButton("关闭");
        btnClose.setIcon(IconUtils.getSVGIcon("icons/closeTab.svg"));
        btnClose.addActionListener(e -> closeActionPerformed());
        toolBar.add(btnNew);
        toolBar.addSeparator();
        toolBar.add(btnFormat);
        toolBar.addSeparator();
        toolBar.add(btnClose);
        toolBar.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.LIGHT_GRAY));

        mainPanel.add(toolBar, BorderLayout.NORTH);
        mainPanel.add(editPanel, BorderLayout.CENTER);
        frame.addComponentListener(frameResizedEventService());
        frame.add(mainPanel);

    }

    private ComponentAdapter frameResizedEventService() {
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("窗口大小改变了");
            }
        };
    }

    private static JScrollPane createScrollPane() {
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setForeground(new Color(200, 96, 17));
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 设置边框为10像素的空白边框
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setUI(new FlatScrollPaneUI());
        jScrollPane.setViewportView(textArea);
        return jScrollPane;
    }

    public static RTextScrollPane createJsonScrollTextArea() {
        RSyntaxTextArea syntaxTextArea = createTextArea(SyntaxConstants.SYNTAX_STYLE_JSON, "/themes/textAreaThemes/ideaLight.xml");
        RTextScrollPane rTextScrollPane = new RTextScrollPane(syntaxTextArea);
        rTextScrollPane.setBorder(BorderFactory.createEmptyBorder());
        // 显示行号
        rTextScrollPane.setLineNumbersEnabled(true);
        rTextScrollPane.setFoldIndicatorEnabled(true);
        return rTextScrollPane;
    }

    private static RSyntaxTextArea createTextArea(String styleKey, String themesPath) {
        RSyntaxTextArea textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(styleKey);
        // 这行代码启用了代码折叠功能
        textArea.setCodeFoldingEnabled(true);
        // 启用了抗锯齿功能
        textArea.setAntiAliasingEnabled(true);
        // 启用了自动滚动功能
        textArea.setAutoscrolls(true);
        // 读取配置信息中的数据
        textArea.setEditable(true);
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

    private void formatActionPerformed() {
        // 获取当前具有焦点的组件
        Component focusOwner = _this.getFocusOwner();
        JTextArea focusArea = findComponentsByFocus(focusOwner, JTextArea.class);
        if (focusArea != null) {
            focusArea.setText(JsonUtil.formatJson(focusArea.getText()));
        }
        /*if (focusOwner instanceof RSyntaxTextArea) {
            RSyntaxTextArea activeTextArea = (RSyntaxTextArea) focusOwner;
            activeTextArea.setText(JsonUtil.formatJson(activeTextArea.getText()));
            System.out.println("父组件宽度：" + activeTextArea.getParent().getParent().getWidth());
            java.util.List<RSyntaxTextArea> rstList = findComponentsByType(_this.getContentPane());
            for (RSyntaxTextArea rSyntaxTextArea : rstList) {
                rSyntaxTextArea.setText("哈哈哈哈哈");
                System.out.println("获取到的组件："+rSyntaxTextArea);
            }
        }*/
    }

    private void createNewTabActionPerformed(JTabbedSplitPane tabbedSplitPane, JPanel editPanel) {
        editPanel.add(tabbedSplitPane.addComponent(createJsonScrollTextArea()), BorderLayout.CENTER);
        editPanel.revalidate();
        editPanel.repaint();
    }


    private void closeActionPerformed() {

    }

    public <T> java.util.List<T> findComponentsByType(Container container) {
        java.util.List<T> rstList = new ArrayList<>();
        // 遍历当前容器的所有组件
        for (Component component : container.getComponents()) {
            if (component instanceof RSyntaxTextArea) {
                // 如果找到名称匹配的JTextArea，直接返回
                System.out.println("找到组件：1");
                rstList.add((T) component);
            } else if (component instanceof Container) {
                // 如果组件本身也是一个容器（如 JPanel、JScrollPane 等），递归搜索其内部组件
                java.util.List<T> foundTextAreaList = findComponentsByType((Container) component);
                if (foundTextAreaList != null && foundTextAreaList.size() > 0) {
                    System.out.println("找到组件：2");
                    rstList.addAll(foundTextAreaList);
                }
            }
        }
        // 当前容器及其子组件中没有找到目标JTextArea，返回null
        return rstList;
    }

    public <T> T findComponentsByFocus(Component focusOwner,Class<T> clazz){
        if (focusOwner == null){
            System.out.println("没有找到焦点组件");
            return null;
        }
        if (clazz.isInstance(focusOwner)) {
            T focusComponent = (T) focusOwner;
            return focusComponent;
        }
        return null;
    }

}
