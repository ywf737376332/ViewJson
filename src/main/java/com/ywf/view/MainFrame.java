package com.ywf.view;

import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.ywf.component.MenuBarBuilder;
import com.ywf.component.TextAreaBuilder;
import com.ywf.component.ToolBarBuilder;
import com.ywf.framework.utils.IconUtils;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/25 18:21
 */
public class MainFrame extends JFrame {
    private JFrame _this = this;

    public void createAndShowGUI(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        setSize(800, 600);
        setLocation((w - _this.getWidth()) / 2, (h - _this.getHeight()) / 2);
        setMinimumSize(new Dimension(800, 600));
        //设置图标
        // 使用Image.getScaledInstance()方法缩放图标
        //Image icon = IconUtils.getIcon("/ico/logo003.png");
        ImageIcon icon = IconUtils.getSVGIcon("icons/colors.svg");
        // 获取原始图标的宽度和高度
        int originalWidth = icon.getIconWidth();
        int originalHeight = icon.getIconHeight();
        // 计算缩放比例
        double scaleFactor = 200; // 例如，将图标大小缩小为原来的50%
        // 使用Image.getScaledInstance()方法缩放图标
        Image scaledIcon = icon.getImage().getScaledInstance((int) (originalWidth * scaleFactor), (int) (originalHeight * scaleFactor), Image.SCALE_SMOOTH);
        //setIconImage(scaledIcon);
        //setIconImage(IconUtils.getIcon("/icons/logo009.png"));
        setIconImages( FlatSVGUtils.createWindowIconImages( "/icons/FlatLaf.svg" ) );
        // 初始化界面
        initUI(_this);
        setVisible(true);
        pack();
    }

    private void initUI(JFrame frame) {
        // 左侧多文本框
        JPanel panelLeft = PanelView.createPanelLeft();
        JScrollPane scrollTextArea = TextAreaBuilder.scrollTextArea();
        panelLeft.add(scrollTextArea, BorderLayout.CENTER);
        frame.add(panelLeft, BorderLayout.WEST);

        // 右侧JSON格式化区域
        JPanel panelRight = PanelView.createPanelRight();
        RTextScrollPane rTextScrollPane = TextAreaBuilder.JsonScrollTextArea();
        panelRight.add(rTextScrollPane, BorderLayout.CENTER);

        // 左右布局可变工具条
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLeft, panelRight);
        splitPane.setDividerLocation(235); // 设置分隔线位置为窗口宽度的1/3处

        // 可拖动工具栏
        JToolBar toolBar = ToolBarBuilder.createToolBar(frame);

        // 底部版权区域
        JPanel panelBottom = PanelView.createPanelBottom();

        frame.setJMenuBar(MenuBarBuilder.createMenuBar(frame));
        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(splitPane, BorderLayout.CENTER);
        frame.add(panelBottom, BorderLayout.SOUTH);
    }


}
