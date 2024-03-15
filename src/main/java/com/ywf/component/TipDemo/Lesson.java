package com.ywf.component.TipDemo;

import com.ywf.component.TextAreaBuilder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import java.awt.FlowLayout;

public class Lesson extends JFrame {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    System.setProperty("awt.useSystemAAFontSettings", "on");
                    System.setProperty("swing.aatext", "true");
                    Lesson frame = new Lesson();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Lesson() {
        initialize();
    }

    private JRootPane rootPane;
    private JLayeredPane layeredPane;
    private JButton btnNewButton_1;

    void initialize() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 833, 477);

        rootPane = new JRootPane();
        getContentPane().add(rootPane, BorderLayout.CENTER);

        layeredPane = new JLayeredPane();
        rootPane.getContentPane().add(layeredPane);

        btnNewButton_1 = new JButton("打开遮罩层");
        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rootPane.getGlassPane().show(true);
            }
        });
        layeredPane.setLayout(new BorderLayout(0, 0));
        layeredPane.add(btnNewButton_1, BorderLayout.NORTH);
        layeredPane.add(TextAreaBuilder.scrollTextArea(), BorderLayout.CENTER);


        JPanel jPanel = new JPanel();
        // 半透明面板
        jPanel.setBackground(new Color(102, 0, 51, 180));
        JButton xxButton = new JButton("解锁");
        xxButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rootPane.getGlassPane().show(false);
            }
        });
        jPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        jPanel.add(xxButton);

        rootPane.setGlassPane(jPanel);

    }
}
