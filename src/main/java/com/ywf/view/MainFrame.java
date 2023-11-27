package com.ywf.view;

import com.ywf.framework.component.ButtonBuilder;
import com.ywf.framework.component.TextAreaBuilder;
import com.ywf.framework.component.button.ToolBarBuilder;
import com.ywf.utils.IconUtils;
import com.ywf.utils.JsonFormatUtil;
import com.ywf.utils.PropertiesUtil;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
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
    //private JButton buttonClean;
    //private JButton buttonformat;

    private int num =0;

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
        setIconImage(IconUtils.getIcon("/ico/logo.png"));
        initUI(_this);
        setVisible(true);
        pack();
    }

    private void initUI(JFrame frame) {
        // 左侧上部多文本框
        JPanel panelLeft = PanelView.createPanelLeft();
        JScrollPane scrollTextArea = TextAreaBuilder.scrollTextArea();
        panelLeft.add(scrollTextArea,BorderLayout.CENTER);
        // 左侧下部功能区
        //JPanel panelFun = PanelView.createPanelFun();
        //buttonClean = ButtonBuilder.clearButton();
        //buttonformat = ButtonBuilder.formatButton();
        //panelFun.add(buttonClean);
        //panelFun.add(buttonformat);
        //panelLeft.add(panelFun, BorderLayout.SOUTH);
        frame.add(panelLeft, BorderLayout.WEST);
        // 右侧JSON格式化区域
        JPanel panelRight = PanelView.createPanelRight();
        RTextScrollPane rTextScrollPane = TextAreaBuilder.JsonScrollTextArea();
        panelRight.add(rTextScrollPane, BorderLayout.CENTER);
        //frame.add(panelRight, BorderLayout.CENTER);

        JPanel panelBottom = PanelView.createPanelBottom();
        //frame.add(panelBottom, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelLeft, panelRight);
        splitPane.setDividerLocation(235); // 设置分隔线位置为窗口宽度的1/3处
        JToolBar toolBar = ToolBarBuilder.createToolBar();
        ToolBarBuilder.bindEvent(_this,ToolBarBuilder.getBtnClean(),ToolBarBuilder.getBtnFormat());
        frame.add(toolBar,BorderLayout.NORTH);
        frame.add(splitPane, BorderLayout.CENTER);
    }


}
