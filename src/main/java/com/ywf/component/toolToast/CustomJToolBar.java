package com.ywf.component.toolToast;

import javax.swing.*;
import java.awt.*;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class CustomJToolBar {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(300,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new FlowLayout());
        JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
        frame.getContentPane().add(toolBar);
        toolBar.setLayout(new GridLayout(1,3));//这里是关键
        JToggleButton button1 = new JToggleButton("Clicked");
        JToggleButton button2 = new JToggleButton("Clicked123");
        JToggleButton button3 = new JToggleButton("Clicked1234567");
        toolBar.add(button1);
        toolBar.add(button2);
        toolBar.add(button3);
        frame.setVisible(true);
    }
}
