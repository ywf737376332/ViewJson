package com.ywf.component.toast;

import com.ywf.framework.constant.SystemConstant;
import sun.font.FontDesignMetrics;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * @Author yzx
 * @Description 自定义淡入淡出式对话框
 * @Date 2021/1/5
 */
public class TipDialog extends JDialog {
    Dimension size;
    Font font = SystemConstant.SYSTEM_DEFAULT_FONT;
    Color themeColor;

    // the auto closing option window constructor
    public TipDialog(Frame parentJFrame, String message) {
        super(parentJFrame);
        new Thread(() -> {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    close();
                }
            });
            // 设置主题色
            themeColor = Color.BLACK;
            FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
            int sWidth = metrics.stringWidth(message);
            int sHeight = metrics.getHeight();
            size = new Dimension(sWidth + 60, sHeight + 40);
            setSize(size);
            this.setUndecorated(true);
            // Dialog 背景透明
            setBackground(new Color(0,0,0,0));
            if (parentJFrame != null) {
                this.setLocationRelativeTo(parentJFrame);
            } else {
                this.setLocation(500, 300);
            }
            // the underground panel
            UndergroundPanel mainPanel = new UndergroundPanel();

            // the message label
            JLabel messageLabel = new JLabel(message, JLabel.CENTER);
            messageLabel.setForeground(Color.WHITE);
            messageLabel.setFont(font);
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(messageLabel, BorderLayout.CENTER);

            setLayout(new BorderLayout());
            add(mainPanel, BorderLayout.CENTER);

            // 渐隐效果显示
            float translucent = 0.0f;
            setVisible(true);
            while (translucent < 1) {
                setOpacity(translucent);
                translucent += 0.02f;
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 停留时间
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            close();
        }).start();
    }

    public void close() {
        // 渐隐效果
        float translucent = 1.0f;
        while (translucent > 0) {
            setOpacity(translucent);
            translucent -= 0.02f;
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        dispose();
    }


    private class UndergroundPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            // 避免锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2d.setColor(themeColor);
            g2d.fillRoundRect(0, 0, width, height, 10, 10);
        }
    }

    public static void main(String[] args) {
        TipDialog test = new TipDialog(null, "测试文本");
    }
}
