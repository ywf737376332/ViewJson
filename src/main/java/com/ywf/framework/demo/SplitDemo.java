package com.ywf.framework.demo;

import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.formdev.flatlaf.ui.FlatScrollPaneUI;
import com.ywf.component.TextAreaBuilder;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.layout.FindPanelLayout;
import com.ywf.framework.utils.IconUtils;
import com.ywf.framework.utils.JsonUtil;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/25 15:33
 */
public class SplitDemo extends JFrame {

    private JFrame _this = this;
    private static LinkedList<JScrollPane> scrollAreaList = new LinkedList<>();

    private static JToolBar toolBar;
    private static JButton btnLayoutOne;
    private static JButton btnLayoutTwo;
    private static JButton btnFindSraech;
    private static JPanel mainEditorPanel;
    private static FindPanelLayout layout;

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        new SplitDemo().createAndShowGUI(SystemConstant.SYSTEM_TITLE + SystemConstant.SYSTEM_VERSION);
    }

    public void createAndShowGUI(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        setMinimumSize(new Dimension(235, 600));
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
        mainEditorPanel = createMainEditorPanel(1);
        editPanel.add(mainEditorPanel, BorderLayout.CENTER);

        toolBar = new JToolBar("工具栏");
        btnLayoutTwo = new JButton("两列布局");
        btnLayoutTwo.setIcon(IconUtils.getSVGIcon("icons/layoutTwo.svg"));
        btnLayoutOne = new JButton("单列布局");
        btnLayoutOne.setIcon(IconUtils.getSVGIcon("icons/layoutOne.svg"));
        btnLayoutOne.addActionListener(e -> {
            removeComponents(mainEditorPanel);
            mainEditorPanel = createMainEditorPanel(1);
            editPanel.add(mainEditorPanel, BorderLayout.CENTER);
            editPanel.revalidate();
            editPanel.repaint();
            System.out.println("重新布局-单列");
        });
        btnLayoutTwo.addActionListener(e -> {
            removeComponents(mainEditorPanel);
            mainEditorPanel = createMainEditorPanel(2);
            editPanel.add(mainEditorPanel, BorderLayout.CENTER);
            editPanel.revalidate();
            editPanel.repaint();
            System.out.println("重新布局-双列");
        });


        JButton btnFormat = new JButton("格式化");
        btnFormat.setIcon(IconUtils.getSVGIcon("icons/formatCode.svg"));
        btnFormat.addActionListener(e ->{
            // 获取当前具有焦点的组件
            Component focusOwner = frame.getFocusOwner();
            if (focusOwner instanceof JTextArea) {
                RSyntaxTextArea activeTextArea = (RSyntaxTextArea) focusOwner;
                activeTextArea.setText(JsonUtil.formatJson(activeTextArea.getText()));
            }
            for (JScrollPane jScrollPane : scrollAreaList) {
                System.out.println("组件名称:"+jScrollPane.getName());
            }
        });
        btnFindSraech = new JButton("搜索栏");
        btnFindSraech.setIcon(IconUtils.getSVGIcon("icons/find.svg"));
        btnFindSraech.addActionListener(e -> layout.showHideActionPerformed());
        toolBar.add(btnLayoutOne);
        toolBar.addSeparator();
        toolBar.add(btnLayoutTwo);
        toolBar.addSeparator();
        toolBar.add(btnFindSraech);
        toolBar.addSeparator();
        toolBar.add(btnFormat);
        toolBar.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.LIGHT_GRAY));

        JPanel findPanels = createFindPanel();
        mainPanel.add(toolBar, BorderLayout.NORTH);
        mainPanel.add(editPanel, BorderLayout.CENTER);
        mainPanel.add(findPanels, BorderLayout.SOUTH);
        frame.add(mainPanel);

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

    private JPanel createFindPanel() {
        JPanel findPanel = new JPanel();
        layout = new FindPanelLayout(findPanel, 5, 5);
        findPanel.setLayout(layout);
        findPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40)); // 设置外边距
        findPanel.add(new JLabel("Find what:"), BorderLayout.WEST);
        JTextField field = new JTextField("asd", 10);
        JButton button = new JButton("下一个");
        findPanel.add(field);
        findPanel.add(button, BorderLayout.EAST);
        return findPanel;
    }

    private JPanel createMainEditorPanel(int type) {
        JPanel editorPanel = new JPanel();
        //editorPanel.setBackground(updateRandomColor());
        editorPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(130, 128, 128, 130)));
        editorPanel.setLayout(new BorderLayout());
        if (type == 1) {
            addAreaTextList();
            int componentCount = editorPanel.getComponentCount();
            if (componentCount == 0) {
                editorPanel.add(scrollAreaList.get(0));
            }
        } else if (type == 2) {
            addAreaTextList();
            JSplitPane splitPane = new JSplitPane();
            splitPane.setLeftComponent(scrollAreaList.get(0));
            splitPane.setDividerLocation(_this.getWidth() / 2 - 30);
            splitPane.setRightComponent(scrollAreaList.get(1));
            editorPanel.add(splitPane, BorderLayout.CENTER);
        }
        return editorPanel;
    }

    private void removeComponents(Component childrenComp) {
        if (childrenComp != null && childrenComp instanceof JPanel) {
            JPanel childrenPanel = (JPanel) childrenComp;
            Container compParent = childrenComp.getParent();
            // 父组件为空的情况，证明子组件已经被移除
            if (compParent != null) {
                compParent.remove(childrenPanel);
            }
            System.out.println("组件移除成功");
        }
    }

    private static void addAreaTextList() {
        if (scrollAreaList.size() < 3) {
            scrollAreaList.add(createJsonScrollTextArea());
        }
        for (JScrollPane jScrollPane : scrollAreaList) {
            System.out.println("组件:"+jScrollPane.hashCode());
            jScrollPane.setName(String.valueOf(jScrollPane.hashCode()));
        }
        System.out.println("最多允许创建三个组件");
    }

    private Color updateRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    /**
     * Json编辑器
     */
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

}
