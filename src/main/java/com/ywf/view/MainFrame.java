package com.ywf.view;

import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.ywf.action.FrameWindowCloseEventService;
import com.ywf.action.WindowResizedEventService;
import com.ywf.action.WindowStateEventService;
import com.ywf.component.*;
import com.ywf.framework.constant.SystemConstant;
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
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        setSize(Integer.parseInt(systemProperties.getValueFromProperties(SystemConstant.SCREEN_SIZE_WIDTH_KEY)), Integer.parseInt(systemProperties.getValueFromProperties(SystemConstant.SCREEN_SIZE_HEIGHT_KEY)));
        setLocation((w - _this.getWidth()) / 2, (h - _this.getHeight()) / 2);
        setMinimumSize(new Dimension(SystemConstant.WINDOWS_MIN_WIDTH, SystemConstant.WINDOWS_MIN_HEIGHT));
        //设置图标
        setIconImages(FlatSVGUtils.createWindowIconImages("/icons/FlatLaf.svg"));
        // 初始化界面
        initUI(_this);
        setVisible(true);
        //pack();
    }

    private void initUI(JFrame frame) {
        JPanel mainPanel = PanelView.createPanelMain();

        // 右侧JSON格式化区域
        JPanel editPanel = PanelView.createEditPanel();
        RTextScrollPane rTextScrollPane = TextAreaBuilder.JsonScrollTextArea();
        editPanel.add(rTextScrollPane, BorderLayout.CENTER);

        // 创建菜单栏
        JMenuBar menuBar = MenuBarBuilder.createMenuBar(frame);
        // 可拖动工具栏
        JToolBar toolBar = ToolBarBuilder.createToolBar(frame);
        // 底部版权区域
        JPanel panelBottom = PanelView.createPanelBottom(frame);

        mainPanel.add(editPanel, BorderLayout.CENTER);
        editPanel.add(FindPanelBuilder.createFindPanel(), BorderLayout.SOUTH);
        mainPanel.add(panelBottom, BorderLayout.SOUTH);
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(toolBar, BorderLayout.NORTH);
        frame.getContentPane().add(mainPanel);
        // 窗口关闭事件监听
        frame.addWindowListener(new FrameWindowCloseEventService(frame));
        // 窗口右键菜单
        frame.addMouseListener(PopupMenuBuilder.getInstance().getPopupListener());
        // 窗口改变大小事件监听
        frame.addComponentListener(new WindowResizedEventService(frame));
        // 窗口激活状态事件监听
        frame.addWindowStateListener(new WindowStateEventService(frame));

    }


}
