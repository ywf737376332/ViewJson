package com.ywf.view;

import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.ywf.action.FrameWindowCloseEventService;
import com.ywf.action.WindowStateEventService;
import com.ywf.component.*;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.annotation.MainView;
import com.ywf.framework.base.AbstractWindow;
import com.ywf.framework.base.SvgIconFactory;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.ioc.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;

/**
 * 启动界面
 *
 * @Author YWF
 * @Date 2023/11/25 18:21
 */
@MainView
public class MainFrame extends AbstractWindow {

    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    public void createAndShowGUI(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        setSize(applicationContext.getScreenSize().getWidth(), applicationContext.getScreenSize().getHeight());
        setLocation((w - _this.getWidth()) / 2, (h - _this.getHeight()) / 2);
        setMinimumSize(new Dimension(SystemConstant.WINDOWS_MIN_WIDTH, SystemConstant.WINDOWS_MIN_HEIGHT));
        //设置图标
        setIconImages(FlatSVGUtils.createWindowIconImages(SvgIconFactory.SystemIcon.logo));
        // 初始化界面
        initUI(_this);
        // 窗口关闭事件监听
        addWindowListener(new FrameWindowCloseEventService(_this));
        // 窗口右键菜单
        addMouseListener(PopupMenuBuilder.getInstance().getPopupListener());
        // 窗口激活状态事件监听
        addWindowStateListener(new WindowStateEventService(_this));
        setVisible(true);
    }

    private void initUI(JFrame frame) {
        JPanel mainPanel = PanelView.createPanelMain();
        // 右侧JSON格式化区域
        JPanel editPanel = PanelView.createEditPanel();
        JTabbedSplitEditor tabbedSplitEditor = new JTabbedSplitEditor(new BorderLayout(), _this);
        this.addComponent(editPanel, GlobalKEY.TABBED_SPLIT_EDITOR, tabbedSplitEditor, BorderLayout.CENTER);
        // 创建菜单栏
        JMenuBar menuBar = MenuBarBuilder.getInstance().createMenuBar(frame);
        // 可拖动工具栏
        JToolBar toolBar = ToolBarBuilder.getInstance().createToolBar(frame);
        // 底部版权区域
        JPanel panelBottom = PanelView.createPanelBottom(frame);
        editPanel.add(FindPanelBuilder.createFindPanel(), BorderLayout.SOUTH);
        mainPanel.add(editPanel, BorderLayout.CENTER);
        mainPanel.add(panelBottom, BorderLayout.SOUTH);
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(toolBar, applicationContext.getToolBarLocation());
        frame.getContentPane().add(LabelBarBuilder.createEmptyLabel(), BorderLayout.SOUTH);
        frame.getContentPane().add(LabelBarBuilder.createEmptyLabel(), BorderLayout.EAST);
        frame.getContentPane().add(mainPanel);
    }

}
