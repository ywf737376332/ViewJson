package com.ywf.view;

import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.ywf.action.FrameMouseEventService;
import com.ywf.action.FrameWindowEventService;
import com.ywf.component.MenuBarBuilder;
import com.ywf.component.TextAreaBuilder;
import com.ywf.component.ToolBarBuilder;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.utils.IconUtils;
import com.ywf.framework.utils.PropertiesUtil;
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

    private static PropertiesUtil systemProperties = PropertiesUtil.instance();

    public void createAndShowGUI(String title) {
        setTitle(title);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        setSize(Integer.parseInt(systemProperties.getValueFromProperties(SystemConstant.SCREEN_SIZE_WIDTH_KEY)), Integer.parseInt(systemProperties.getValueFromProperties(SystemConstant.SCREEN_SIZE_HEIGHT_KEY)));
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
        setIconImages(FlatSVGUtils.createWindowIconImages("/icons/FlatLaf.svg"));
        // 初始化界面
        initUI(_this);
        setVisible(true);
        //pack();
    }

    private void initUI(JFrame frame) {
        JPanel mainPanel = PanelView.createPanelLeft();

        // 右侧JSON格式化区域
        JPanel editPanel = PanelView.createEditPanel();
        RTextScrollPane rTextScrollPane = TextAreaBuilder.JsonScrollTextArea();
        editPanel.add(rTextScrollPane, BorderLayout.CENTER);

        // 创建菜单栏
        JMenuBar menuBar = MenuBarBuilder.createMenuBar(frame);
        // 可拖动工具栏
        JToolBar toolBar = ToolBarBuilder.createToolBar(frame);
        // 底部版权区域
        JPanel panelBottom = PanelView.createPanelBottom();

        mainPanel.add(editPanel, BorderLayout.CENTER);
        mainPanel.add(panelBottom, BorderLayout.SOUTH);
        frame.setJMenuBar(menuBar);
        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(mainPanel);
        frame.addWindowListener(new FrameWindowEventService(frame));
        frame.addMouseListener(new FrameMouseEventService(frame));
    }


}
