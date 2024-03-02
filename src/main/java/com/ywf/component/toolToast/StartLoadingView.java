package com.ywf.component.toolToast;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

public class StartLoadingView extends JWindow implements Runnable {

    // 定义加载窗口大小
    public static final int LOAD_WIDTH = 1075;//这是页面宽度
    public static final int LOAD_HEIGHT = 604;//这是页面高度
    // 获取屏幕窗口大小
    public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    // 定义进度条组件
    public JProgressBar progressbar;
    // 定义标签组件
    public JLabel label;

    // 构造函数
    public StartLoadingView() {

        // 创建标签,并在标签上放置一张图片
        label = new JLabel(new ImageIcon("D:\\素材\\07.jpg"));//这里放页面要展示的图片
        label.setBounds(0, 0, LOAD_WIDTH, LOAD_HEIGHT - 15);
        // 创建进度条
        progressbar = new JProgressBar();
        // 显示当前进度值信息
        progressbar.setStringPainted(true);
        // 设置进度条边框不显示
        progressbar.setBorderPainted(false);
        // 设置进度条的前景色
        progressbar.setForeground(new Color(210, 0, 80, 255));//这里点击左边的颜色按钮可以换进度条的已加载颜色
        // 设置进度条的背景色
        progressbar.setBackground(new Color(188, 190, 194));//这里点击左边的颜色按钮可以换进度条的未加载颜色
        progressbar.setBounds(0, LOAD_HEIGHT - 15, LOAD_WIDTH, 15);
        // 添加组件
        this.add(label);
        this.add(progressbar);
        // 设置布局为空
        this.setLayout(null);
        // 设置窗口初始位置
        this.setLocation((WIDTH - LOAD_WIDTH) / 2, (HEIGHT - LOAD_HEIGHT) / 2);
        // 设置窗口大小
        this.setSize(LOAD_WIDTH, LOAD_HEIGHT);
        // 设置窗口显示
        this.setVisible(true);

    }

    public static void main(String[] args) {
        StartLoadingView t = new StartLoadingView();
        new Thread(t).start();
    }
    @Override
    public void run() {

        for (int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(20);//加载进度条需要用到多少秒，20就是2秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressbar.setValue(i);
        }
        JOptionPane.showMessageDialog(this, "程序加载成功");
        this.dispose();
        //如果这里不继续执行代码，将关闭本次运行
    }
}