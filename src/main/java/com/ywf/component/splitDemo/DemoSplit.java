package com.ywf.component.splitDemo;

import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.formdev.flatlaf.extras.components.FlatProgressBar;
import com.ywf.component.loading.BackgroundTaskKit;
import com.ywf.component.loading.LoadingBuild;
import com.ywf.framework.constant.MessageConstant;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.utils.ConvertUtils;
import com.ywf.framework.utils.IconUtils;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.JXStatusBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/2/5 10:39
 */
public class DemoSplit extends JFrame {

    static Logger logger = LoggerFactory.getLogger(DemoSplit.class);
    private JFrame _this = this;

    private static JXMultiSplitPane msp;
    private static FiveHorizontalSplitPaneModel splitPaneModel;

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        new DemoSplit().createAndShowGUI(MessageConstant.SYSTEM_TITLE + SystemConstant.SYSTEM_VERSION);
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
        mainPanel.setLayout(new BorderLayout());

        JPanel editPanel = new JPanel();
        editPanel.setLayout(new BorderLayout());
        editPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10)); // 设置外边距

        //初始化可创建多个的多文本编辑区
        msp = new JXMultiSplitPane();
        splitPaneModel = new FiveHorizontalSplitPaneModel(true);
        msp.setModel(splitPaneModel);
        msp.add(TextAreaUtil.initScrollEditor(), FiveHorizontalSplitPaneModel.P1);

        editPanel.add(msp, BorderLayout.CENTER);
        JToolBar toolBar = new JToolBar("工具栏");
        toolBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
        JButton btnNew = new JButton("新建");
        btnNew.setIcon(IconUtils.getSVGIcon("icons/newEditer.svg"));
        btnNew.addActionListener(e -> createNewTabActionPerformed());

        JButton btnFormat = new JButton("格式化");
        btnFormat.setIcon(IconUtils.getSVGIcon("icons/formatCode.svg"));
        btnFormat.addActionListener(e -> formatActionPerformed());

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
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.LIGHT_GRAY));
        statusPanel.setLayout(new BorderLayout());

        JXStatusBar statusInfoBar = new JXStatusBar();
        statusInfoBar.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JLabel labelTime = new JLabel("时间：2004年02月05日");
        labelTime.setBorder(BorderFactory.createEmptyBorder(0, 29, 0, 20));
        statusInfoBar.add(labelTime);
        JLabel labelFileType = new JLabel("文件类型：JSON类型");
        labelFileType.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        statusInfoBar.add(labelFileType);
        JLabel labelFontCounts = new JLabel("字数统计：1280字");
        labelFontCounts.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        statusInfoBar.add(labelFontCounts);

        JXStatusBar statusAuthBar = new JXStatusBar();
        FlatProgressBar progressBar = new FlatProgressBar();
        //progressBar.setStringPainted(true);
        progressBar.setIndeterminate(true);
        //this.progressBarUpdate(progressBar);
        statusAuthBar.add(progressBar);
        statusAuthBar.add(new JLabel("作者：莫斐鱼"));

        statusPanel.add(statusInfoBar, BorderLayout.WEST);
        statusPanel.add(statusAuthBar, BorderLayout.EAST);
        mainPanel.add(toolBar, BorderLayout.NORTH);
        mainPanel.add(editPanel, BorderLayout.CENTER);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        frame.add(mainPanel);

    }

    private void formatActionPerformed() {
        LoadingBuild.create(_this, new BackgroundTaskKit.NewTabAction()).showModal();
    }


    public static void createNewTabActionPerformed() {
        RTextScrollPane tp = TextAreaUtil.initScrollEditor();
        LinkedList<RTextScrollPane> list = TextAreaUtil.getList();
        int counts = list.size();
        double weight = 1.0 / counts;
        logger.info("当前宽度:{},当前计数：{}", weight, counts);
        if (counts == 2) {
            msp.add(tp, FiveHorizontalSplitPaneModel.P2);
            splitPaneModel.p1.setWeight(weight);
            splitPaneModel.p2.setWeight(weight);
            msp.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        double s = ConvertUtils.toDouble("0.0" + list.get(0).getWidth());
                        System.out.println("分隔条位置已改变！" + s);
                        splitPaneModel.p2.setWeight(0.2);
                        msp.revalidate();
                        msp.repaint();
                    }
                }
            });
        } else if (counts == 3) {
            msp.add(tp, FiveHorizontalSplitPaneModel.P3);
            splitPaneModel.p1.setWeight(weight);
            splitPaneModel.p2.setWeight(weight);
            splitPaneModel.p3.setWeight(weight);
            msp.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        double s = ConvertUtils.toDouble("0.0" + list.get(0).getWidth());
                        System.out.println("分隔条位置已改变！" + s);
                        splitPaneModel.p3.setWeight(s);
                    }
                }
            });
        } else if (counts == 4) {
            msp.add(tp, FiveHorizontalSplitPaneModel.P4);
            splitPaneModel.p1.setWeight(weight);
            splitPaneModel.p2.setWeight(weight);
            splitPaneModel.p3.setWeight(weight);
            splitPaneModel.p4.setWeight(weight);
        }
        msp.revalidate();
        msp.repaint();
    }

    private void progressBarUpdate(JProgressBar progressBar) {
        Timer timer = new Timer(100, new ActionListener() {
            int value = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (value < 100) {
                    value++;
                    progressBar.setValue(value);
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        // 启动定时器
        timer.start();
    }

}
