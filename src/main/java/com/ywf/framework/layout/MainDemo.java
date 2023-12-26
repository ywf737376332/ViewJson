package com.ywf.framework.layout;

import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.ywf.component.PoPupFindPanel;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/25 15:33
 */
public class MainDemo extends JFrame {

    private JFrame _this = this;

    private static JToolBar toolBar;

    private static JTextArea textArea;

    private static JScrollPane jScrollPane;
    private static Popup popup;

    private static JButton btnFindRepl;

    public static void main(String[] args) {
        new MainDemo().createAndShowGUI(SystemConstant.WINDOWS_TITLE + SystemConstant.WINDOWS_VERSION);
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
        editPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); // 设置外边距
        editPanel.add(jScrollPane, BorderLayout.CENTER);

        toolBar = new JToolBar("工具栏");
        btnFindRepl = new JButton("查找替换");
        btnFindRepl.addActionListener(e -> {
            PoPupFindPanel.getInstance().showPopup(jScrollPane);
            JScrollBar verticalScrollBar = jScrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(-100);
        });
        toolBar.add(btnFindRepl);

        mainPanel.add(toolBar, BorderLayout.NORTH);
        mainPanel.add(editPanel, BorderLayout.CENTER);
        frame.add(mainPanel);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("textArea大小已改变，新尺寸为：" + textArea.getWidth());
                PoPupFindPanel.getInstance().setPreferredSize(new Dimension(textArea.getWidth(), 35));
            }
        });
    }


}
