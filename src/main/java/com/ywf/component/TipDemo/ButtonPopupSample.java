package com.ywf.component.TipDemo;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.Timer;

public class ButtonPopupSample {

    static final Random random = new Random();

    // define ActionListener
    static class ButtonActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            System.out.println("Selected: "+event.getActionCommand());
        }
    };

    // define show popu ActionListener
    static class ShowPopupActionListener implements ActionListener {
        private Component component;
        ShowPopupActionListener(Component component) {
            this.component = component;
        }
        public synchronized void actionPerformed(ActionEvent event) {
            JButton button = new JButton("Hello, world");
            ActionListener listener = new ButtonActionListener();
            button.addActionListener(listener);
            PopupFactory factory = PopupFactory.getSharedInstance();
            int x = random.nextInt(200);
            int y = random.nextInt(200);
            final Popup popup = factory.getPopup(component, button, x, y);
            popup.show();
            ActionListener hider = new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    popup.hide();
                }
            };
            // hide popup in 3 seconds
            Timer timer = new Timer(3000, hider);
            timer.start();
        }
    };
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        Runnable runner = new Runnable() {
            public void run() {
                // create frame
                JFrame frame = new JFrame("Button Popup Sample");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                ActionListener actionListener = new ShowPopupActionListener(frame);

                JButton start = new JButton("Pick Me for Popup");
                start.addActionListener(actionListener);
                frame.add(start);

                frame.setSize(350, 250);
                frame.setVisible(true);
            }
        };
        EventQueue.invokeLater(runner);
    }

}