package com.ywf.component.demo2;

import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.formdev.flatlaf.ui.FlatScrollPaneUI;
import com.ywf.component.JSONRSyntaxTextArea;
import com.ywf.component.TextAreaBuilder;
import com.ywf.framework.base.AbstractWindow;
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

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/1/24 22:52
 */
public class DemoTabble002 extends AbstractWindow {

    private static JTabbedSplitEditor tabbedSplitPane;

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        new DemoTabble002().createAndShowGUI(SystemConstant.SYSTEM_TITLE + SystemConstant.SYSTEM_VERSION);
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

        //初始化可创建多个的多文本编辑区
        tabbedSplitPane = new JTabbedSplitEditor(_this);
        //editPanel.add(tabbedSplitPane.addTab(createJsonScrollTextArea()), BorderLayout.CENTER);
        JPanel editMy = tabbedSplitPane.addTab(createJsonScrollTextArea());
        //editPanel.add(editMy, BorderLayout.CENTER);

        this.addComponent(editPanel,"#tabbedSplitEditor",editMy,BorderLayout.CENTER);

        JToolBar toolBar = new JToolBar("工具栏");
        JButton btnNew = new JButton("新建");
        btnNew.setIcon(IconUtils.getSVGIcon("icons/newEditer.svg"));
        btnNew.addActionListener(e -> createNewTabActionPerformed());

        JButton btnFormat = new JButton("格式化");
        btnFormat.setIcon(IconUtils.getSVGIcon("icons/formatCode.svg"));
        btnFormat.addActionListener(e -> formatActionPerformed());

        JButton btnComp = new JButton("压 缩");
        btnComp.setIcon(IconUtils.getSVGIcon("icons/comp.svg"));
        btnComp.addActionListener(e -> compressionJsonActionPerformed());

        JButton btnClean = new JButton("清空");
        btnClean.setIcon(IconUtils.getSVGIcon("icons/delete.svg"));
        btnClean.addActionListener(e -> cleanJsonActionPerformed());

        JButton btnClose = new JButton("关闭");
        btnClose.setIcon(IconUtils.getSVGIcon("icons/closeTab.svg"));
        btnClose.addActionListener(e -> closeActionPerformed(tabbedSplitPane));
        toolBar.add(btnNew);
        toolBar.addSeparator();
        toolBar.add(btnFormat);
        toolBar.addSeparator();
        toolBar.add(btnComp);
        toolBar.addSeparator();
        toolBar.add(btnClean);
        toolBar.addSeparator();
        toolBar.add(btnClose);
        toolBar.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.LIGHT_GRAY));

        //mainPanel.add(toolBar, BorderLayout.NORTH);
        //mainPanel.add(editPanel, BorderLayout.CENTER);
        this.addComponent(mainPanel,"#toolBar",toolBar,BorderLayout.NORTH);
        this.addComponent(mainPanel,"#editPanel",editPanel,BorderLayout.CENTER);

        frame.addComponentListener(frameResizedEventService());
        frame.add(mainPanel);

    }

    private ComponentAdapter frameResizedEventService() {
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                //System.out.println("窗口大小改变了");
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

    private void formatActionPerformed() {
        // 获取当前具有焦点的组件
        JTextArea focusArea = findComponentsByFocus(_this, JTextArea.class);
        if (focusArea != null) {
            System.out.println("组件宽度："+focusArea.getWidth());
            focusArea.setText(JsonUtil.formatJson(focusArea.getText()));
        }

        //遍历寻找组件测试
        /*List<RSyntaxTextArea> rstList = findComponentsByType(_this.getContentPane());
        System.out.println("获取到RSyntaxTextArea的组件数量：" + rstList.size());
        for (RSyntaxTextArea rSyntaxTextArea : rstList) {
            System.out.println("获取到的组件名称：" + rSyntaxTextArea.getName());
        }*/
    }

    /**
     * 清空文本内容
     */
    public void cleanJsonActionPerformed() {
        JTextArea focusArea = findComponentsByFocus(_this, JTextArea.class);
        if (focusArea != null) {
            focusArea.setText("");
            focusArea.requestFocusInWindow();
        }
    }

    /**
     * 压缩内容
     */
    public void compressionJsonActionPerformed() {
        SwingUtilities.invokeLater(() -> {
            JTextArea focusArea = findComponentsByFocus(_this, JTextArea.class);
            if (focusArea != null) {
                focusArea.setText(JsonUtil.compressingStr(focusArea.getText()));
            }
        });
    }

    public static void createNewTabActionPerformed() {
        //tabbedSplitPane.addTab(createJsonScrollTextArea());
        System.out.println("组件添加成功");
        tabbedSplitPane.createNewTabbed(createJsonScrollTextArea());
    }


    private void closeActionPerformed(JTabbedSplitEditor tabbedSplitPane) {
        tabbedSplitPane.closeAbleTabbed(null);
    }

    public <T> T findComponentsByFocus(JFrame frame, Class<T> clazz) {
        Component focusOwner = frame.getFocusOwner();
        if (focusOwner == null) {
            return null;
        } else {
            return clazz.isInstance(focusOwner) ? (T) focusOwner : null;
        }
    }

    public static void closeAbleTabbedSplitPane(RSyntaxTextArea rSyntaxTextArea){
        if (rSyntaxTextArea==null){
            return;
        }
        tabbedSplitPane.closeAbleTabbed(rSyntaxTextArea);
    }

}
