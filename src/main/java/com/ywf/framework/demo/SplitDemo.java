package com.ywf.framework.demo;

import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.layout.FindPanelLayout;

import javax.swing.*;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/25 15:33
 */
public class SplitDemo extends JFrame {

    private JFrame _this = this;

    private static JToolBar toolBar;
    private static JButton btnFindRepl;
    private static JButton btnFindRepl2;

    private static FindPanelLayout layout;

    public static void main(String[] args) {
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
        //editPanel.setBackground(Color.RED);
        editPanel.setLayout(new BorderLayout());

        editPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 20, 40)); // 设置外边距

        editPanel.add(createMainEditorPanel(2), BorderLayout.CENTER);

        toolBar = new JToolBar("工具栏");
        btnFindRepl = new JButton("两列布局");
        btnFindRepl.addActionListener(e -> {
            editPanel.add(createMainEditorPanel(2), BorderLayout.CENTER);
        });
        toolBar.add(btnFindRepl);

        btnFindRepl2 = new JButton("查找替换2");
        toolBar.add(btnFindRepl2);

        JPanel findPanels = createFindPanel();

        btnFindRepl2.addActionListener(e -> layout.showHideActionPerformed());

        mainPanel.add(toolBar, BorderLayout.NORTH);
        mainPanel.add(editPanel, BorderLayout.CENTER);
        mainPanel.add(findPanels, BorderLayout.SOUTH);
        frame.add(mainPanel);

    }

    private JScrollPane createScrollPane() {
        JTextArea textArea = new JTextArea("3224234234");
        textArea.setLineWrap(true);
        textArea.setBorder(null);
        textArea.setForeground(new Color(200, 96, 17));
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 设置边框为10像素的空白边框
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(textArea);
        return jScrollPane;
    }

    private JPanel createFindPanel() {
        JPanel findPanel = new JPanel();
        layout = new FindPanelLayout(findPanel, 5, 5);
        findPanel.setLayout(layout);
        findPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 40)); // 设置外边距
        findPanel.setBackground(Color.GREEN);
        findPanel.add(new JLabel("Find what:"), BorderLayout.WEST);
        JTextField field = new JTextField("asd", 10);
        JButton button = new JButton("下一个");
        findPanel.add(field);
        findPanel.add(button, BorderLayout.EAST);
        return findPanel;
    }

    private JPanel createMainEditorPanel(int type) {
        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new BorderLayout());
        if (type == 1){
            JScrollPane scrollPane1 = createScrollPane();
            editorPanel.add(scrollPane1,BorderLayout.CENTER);
        } else if (type == 2) {
            JSplitPane splitPane = new JSplitPane();
            JScrollPane scrollPane1 = createScrollPane();
            JScrollPane scrollPane2 = createScrollPane();
            splitPane.setLeftComponent(scrollPane1);
            splitPane.setRightComponent(scrollPane2);
            editorPanel.add(splitPane,BorderLayout.CENTER);
        }
        return editorPanel;
    }

}
