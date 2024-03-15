package com.ywf.component.toolToast;

import javax.swing.*;
import java.awt.*;

public class TRotateButton extends JButton {
    private double theta = 0;
    private Dimension dimension;

    public TRotateButton(String text, int theta,Dimension dimension) {
        super(text);
        this.theta = theta;
        this.dimension = dimension;
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {

        super.setPreferredSize(new Dimension(100,0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        int w = getWidth(), h = getHeight();
        double theta = Math.toRadians(this.theta);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        // 消除锯齿
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        renderingHints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHints(renderingHints);
        g2d.rotate(theta, w / 2, h / 2);
        super.paintComponent(g);
    }





    public static void main(String[] args) {
        JFrame jf = new JFrame("测试旋转按钮");
        JToolBar toolBar = new JToolBar();
        toolBar.setLayout(new FlowLayout());
        toolBar.setOrientation(JToolBar.VERTICAL);
        toolBar.setBackground(Color.BLUE);
        jf.setSize(300, 300);
        JButton btn = new TRotateButton("旋转按钮", 90,new Dimension(120,440));
        //btn.setPreferredSize(new Dimension(80,40));
        //toolBar.add(btn);
        jf.getContentPane().add(btn, BorderLayout.EAST);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}