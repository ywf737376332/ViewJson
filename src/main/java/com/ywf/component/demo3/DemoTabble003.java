package com.ywf.component.demo3;

import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.formdev.flatlaf.ui.FlatScrollPaneUI;
import com.ywf.component.JSONRSyntaxTextArea;
import com.ywf.component.TextAreaBuilder;
import com.ywf.component.demo2.JTabbedSplitEditor;
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
public class DemoTabble003 extends AbstractWindow {

    private static TabEditor tabEditor;

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        new DemoTabble003().createAndShowGUI(SystemConstant.SYSTEM_TITLE + SystemConstant.SYSTEM_VERSION);
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
        tabEditor = new TabEditor(new BorderLayout(),_this);
        //editPanel.add(tabbedSplitPane.addTab(createJsonScrollTextArea()), BorderLayout.CENTER);
        //JPanel editMy = tabbedSplitPane.addTab(createJsonScrollTextArea());
        //editPanel.add(editMy, BorderLayout.CENTER);

        editPanel.add(tabEditor,BorderLayout.CENTER);
        JToolBar toolBar = new JToolBar("工具栏");
        JButton btnNew = new JButton("新建");
        btnNew.setIcon(IconUtils.getSVGIcon("icons/newEditer.svg"));
        btnNew.addActionListener(e -> createNewTabActionPerformed());

        JButton btnFormat = new JButton("格式化");
        btnFormat.setIcon(IconUtils.getSVGIcon("icons/formatCode.svg"));
        //btnFormat.addActionListener(e -> formatActionPerformed());

        JButton btnComp = new JButton("压 缩");
        btnComp.setIcon(IconUtils.getSVGIcon("icons/comp.svg"));
        //btnComp.addActionListener(e -> compressionJsonActionPerformed());

        JButton btnClean = new JButton("清空");
        btnClean.setIcon(IconUtils.getSVGIcon("icons/delete.svg"));
        //btnClean.addActionListener(e -> cleanJsonActionPerformed());

        JButton btnClose = new JButton("关闭");
        btnClose.setIcon(IconUtils.getSVGIcon("icons/closeTab.svg"));
        //btnClose.addActionListener(e -> closeActionPerformed(tabEditor));
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

        mainPanel.add(toolBar, BorderLayout.NORTH);
        mainPanel.add(editPanel, BorderLayout.CENTER);

        frame.add(mainPanel);

    }

    public static void createNewTabActionPerformed() {
        tabEditor.addTab();
    }

}
