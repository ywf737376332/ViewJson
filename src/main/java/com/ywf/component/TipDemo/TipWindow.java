package com.ywf.component.TipDemo;

import com.ywf.action.MenuEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TipWindow extends JDialog implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(TipWindow.class);

    private static Dimension dim;

    private int x, y;

    private int width, height;

    private static Insets screenInsets;

    public TipWindow(int width, int height) {

        /*
        *                 int w = Toolkit.getDefaultToolkit().getScreenSize().width;
                int h = Toolkit.getDefaultToolkit().getScreenSize().height;
                this.setLocation((w - _this.getWidth()) / 2, (h - _this.getHeight()) / 2);
        * */

        dim = Toolkit.getDefaultToolkit().getScreenSize();

        screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());

        this.width = width;

        this.height = height;

        x = (int) ((dim.getWidth() - width)/2);

        y = (int) ((dim.getHeight() - height) /2);

        //x = (int) (dim.getWidth() - width-3);

        y = (int) (dim.getHeight()-screenInsets.bottom-3);

        initComponents();

    }

    /*

     * 开启渐入效果

     * 开启后3秒，窗口自动渐出

     * 若不需要渐出，注释掉，sleep(3000)和close()方法

     */

    public void run() {

        for (int i = 0; i <= height; i += 10) {

            try {
                this.setLocation(x, y - i);

                Thread.sleep(5);

            } catch (InterruptedException ex) {

                logger.error(ex.getMessage());

            }

        }

        try {

            Thread.sleep(3000);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

        close();

    }

    private void initComponents() {

        this.setSize(width, height);

        this.setLocation(x, y);

        this.setBackground(Color.black);

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                close();

            }

        });

    }

    public void close() {

        for (int i = 0; i <= height; i += 10) {

            try {

                setLocation(x, y - height + i);

                Thread.sleep(5);

            } catch (InterruptedException ex) {

                logger.error(ex.getMessage());

            }

        }

        dispose();

    }

}