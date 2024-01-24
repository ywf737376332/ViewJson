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
public class PopuFindDemo extends JFrame {

    private JFrame _this = this;

    private static JToolBar toolBar;

    private static JTextArea textArea;

    private static JScrollPane jScrollPane;
    private static Popup popup;

    private static JButton btnFindRepl;
    private static JButton btnFindRepl2;

    private static FindPanelLayout layout;

    public static void main(String[] args) {
        new PopuFindDemo().createAndShowGUI(SystemConstant.SYSTEM_TITLE + SystemConstant.SYSTEM_VERSION);
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

        textArea = new JTextArea("3224234234");
        textArea.setLineWrap(true);
        textArea.setBorder(null);
        textArea.setForeground(new Color(200, 96, 17));
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 设置边框为10像素的空白边框
        jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(textArea);
        editPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 20, 40)); // 设置外边距
        editPanel.add(jScrollPane, BorderLayout.CENTER);

        toolBar = new JToolBar("工具栏");
        btnFindRepl = new JButton("查找替换");
        btnFindRepl.addActionListener(e -> {
            PoPupFindPanel.getInstance().showPopup(jScrollPane);
            JScrollBar verticalScrollBar = jScrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(-100);
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

}