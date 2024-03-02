package com.ywf.component.toolToast;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 *
 * @author chen
 *
 */
public class RightCornerPopMessage extends JWindow implements Runnable,
        MouseListener {
    private static final long serialVersionUID = -3564453685861233338L;
    private Integer screenWidth;// 屏幕宽度
    private Integer screenHeight; // 屏幕高度
    private Integer windowWidth = 200; // 设置提示窗口宽度
    private Integer windowHeight = 100; // 设置提示窗口高度
    private Integer stayTime = 5000; // 提示框停留时间
    private Integer x; // 窗口起始X坐标
    private Integer y; // 窗口起始Y坐标
    private String title = "温馨提示";
    private String message = "testone";
    private JPanel mainPanel; // 主面板
    private JLabel titleLabel; // 标题栏标签
    private JPanel titlePanel; // 标题栏面板
    private JLabel messageLabel; // 内容标签
    private JPanel messagePanel; // 内容面板

    public RightCornerPopMessage() {
        this.init();
        Thread thread = new Thread(this);
        thread.start();
    }

    private void init() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = dimension.width;
        screenHeight = dimension.height;
        x = (screenWidth - windowWidth) / 2;
        y = -windowHeight;
        this.setLocation(x, y);
        mainPanel = new JPanel(new BorderLayout());
        titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.RED);
        titlePanel.add(titleLabel);
        messageLabel = new JLabel(message);
        messagePanel = new JPanel();
        messagePanel.add(messageLabel);
        messagePanel.setBackground(Color.YELLOW);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(messagePanel, BorderLayout.CENTER);
        this.setSize(windowWidth, windowHeight);
        this.setAlwaysOnTop(true);//置顶
        this.getContentPane().add(mainPanel);
        this.addMouseListener(this);
        Toolkit.getDefaultToolkit().beep(); // 播放系统声音，提示一下
        this.setVisible(true);

    }

    public void run() {
        Integer delay = 10;
        Integer step = 1;
        Integer end = windowHeight;
        try {
            while (true) {
                step++;
                y++;
                this.setLocation(x, y);
                if (step > end) {
                    Thread.sleep(stayTime);
                    break;
                }
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        step = 1;
        while (true) {
            step++;
            y--;
            this.setLocation(x, y);
            if (step > end) {
                this.dispose();
                break;
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }

    }

    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

    public static void main(String[] args) throws Exception {
        // Thread.sleep(5000);
        new RightCornerPopMessage();
    }

}