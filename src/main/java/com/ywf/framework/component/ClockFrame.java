package com.ywf.framework.component;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClockFrame extends JFrame {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;
    private static final int DIGITS_SIZE = 50;
    private static final int HOUR_HAND_LENGTH = 60;
    private static final int MINUTE_HAND_LENGTH = 80;
    private static final int SECOND_HAND_LENGTH = 90;

    private Timer timer;
    private SimpleDateFormat dateFormat;

    public ClockFrame() {
        setTitle("数字时钟");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawClock(g);
            }
        };
        add(panel);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.repaint();
            }
        });
        timer.start();
    }

    private void drawClock(Graphics g) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        dateFormat = new SimpleDateFormat("HH:mm:ss");
        String timeStr = dateFormat.format(calendar.getTime());

        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString(timeStr, WIDTH / 2 - 70, HEIGHT / 2 + 100);

        int xCenter = WIDTH / 2;
        int yCenter = HEIGHT / 2;
        int hourAngle = (int) ((hour % 12 + minute / 60.0f) * 30 - 90);
        int minuteAngle = minute * 6 - 90;
        int secondAngle = second * 6 - 90;

        g.setColor(Color.BLACK);
        g.drawLine(xCenter, yCenter, (int) (xCenter + HOUR_HAND_LENGTH * Math.sin(Math.toRadians(hourAngle))), (int) (yCenter - HOUR_HAND_LENGTH * Math.cos(Math.toRadians(hourAngle))));
        g.drawLine(xCenter, yCenter, (int) (xCenter + MINUTE_HAND_LENGTH * Math.sin(Math.toRadians(minuteAngle))), (int) (yCenter - MINUTE_HAND_LENGTH * Math.cos(Math.toRadians(minuteAngle))));
        g.drawLine(xCenter, yCenter, (int) (xCenter + SECOND_HAND_LENGTH * Math.sin(Math.toRadians(secondAngle))), (int) (yCenter - SECOND_HAND_LENGTH * Math.cos(Math.toRadians(secondAngle))));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClockFrame().setVisible(true);
            }
        });
    }
}
