package com.ywf.component.splitDemo;

import com.ywf.component.SwiftButton;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SyncMenuExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sync Menu Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("Menu 1");
        JMenu menu2 = new JMenu("Menu 2");

        SyncMenuAction syncAction = new SyncMenuAction("Sync Item");

        JRadioButtonMenuItem menuItem1 = new JRadioButtonMenuItem(syncAction);
        JRadioButtonMenuItem menuItem2 = new JRadioButtonMenuItem(syncAction);

        menu1.add(menuItem1);
        menu2.add(menuItem2);

        menuBar.add(menu1);
        menuBar.add(menu2);

        JRadioButton radioButton = new JRadioButton(syncAction);
        frame.add(radioButton);
        frame.setJMenuBar(menuBar);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}


class SyncMenuAction extends AbstractAction {
    private boolean selected;

    public SyncMenuAction(String text) {
        super(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //selected = !selected;
        setSelected(!selected);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        putValue(SELECTED_KEY, selected);
    }
}