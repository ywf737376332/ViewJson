package com.ywf.framework.component.button;

import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/26 0:21
 */
public class TestToolBar {
    public static JPanel p_1 = null;
    public static void main(String[] args) throws UnsupportedLookAndFeelException {

        UIManager.setLookAndFeel(new FlatIntelliJLaf());

        JFrame jf = new JFrame("测试窗口");
        jf.setSize(600, 600);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        p_1 = new JPanel(new BorderLayout());

        // 创建选项卡面板
        JTabbedPane tabbedPane = new JTabbedPane();

        JToolBar toolBar3 = new JToolBar();

        toolBar3.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        JToolBar toolBar1 = new JToolBar();

        toolBar1.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        JToolBar toolBar2 = new JToolBar();

        toolBar2.setLayout(new FlowLayout(FlowLayout.LEFT,15, 10));

        tabbedPane.addTab("菜单1", toolBar1);
        JButton vertical = new JButton("选项1");
        JButton horizontal = new JButton("选项2");
        toolBar1.add(vertical);
        toolBar1.add(horizontal);

        tabbedPane.addTab("菜单2", toolBar2);
        JButton button8 = new JButton("选项1");
        JButton button9 = new JButton("选项2");
        JButton button10 = new JButton("选项3");
        toolBar2.add(button8);
        toolBar2.add(button9);
        toolBar2.add(button10);

        tabbedPane.addTab("菜单3", toolBar3);
        JButton button1 = new JButton("选项1");
        JButton button2 = new JButton("选项2");
        toolBar3.add(button1);
        toolBar3.add(button2);
        jf.getContentPane().add(tabbedPane, BorderLayout.NORTH);
        jf.getContentPane().add(p_1,BorderLayout.CENTER);


        // 添加选项卡选中状态改变的监听器
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println("当前选中的选项卡: " + tabbedPane.getSelectedIndex());
            }
        });

        // 设置默认选中的选项卡
        tabbedPane.setSelectedIndex(1);


        jf.setVisible(true);
    }
}
