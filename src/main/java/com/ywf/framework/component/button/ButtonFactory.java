package com.ywf.framework.component.button;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @description: 快捷button
 * @author: jee
 */
public class ButtonFactory {

    private static JButton DEFAULT_BUTTON = new JButton();

    private static int RANDIUS = 10;


    public static JButton roundButton(String text) {
        ButtonColorEnum buttonColor = ButtonColorEnum.DEFAULT_BUTTON_COLOR;
        RoundButton button = new RoundButton(text, RANDIUS, buttonColor.getBackgroundColor(),buttonColor.getForegroundColor());
        //button.addMouseListener(new MouseAdapter() {
        //    @Override
        //    public void mouseEntered(MouseEvent e) {  //鼠标移动到上面时
        //        button.setForeground(buttonColor.getForegroundFontColor());
        //        button.setBorder(new RoundBorder(buttonColor.getForegroundBorderColor(), RANDIUS));
        //        button.setBackground(buttonColor.getForegroundColor());
        //        button.repaint();
        //        System.out.println("鼠标移入");
        //    }
        //    @Override
        //    public void mouseExited(MouseEvent e) {  //鼠标移开时
        //        button.setForeground(buttonColor.getBackgroundFontColor());
        //        button.setBorder(new RoundBorder(buttonColor.getBackgroundBorderColor(), RANDIUS));
        //        button.setBackground(buttonColor.getBackgroundColor());
        //        button.repaint();
        //        System.out.println("鼠标移出");
        //    }
        //});
        return button;
    }

    /**
     * 主要按钮
     *
     * @param text
     * @return
     */
    public static JButton defaultButton(String text) {
        return createButton(text, ButtonColorEnum.DEFAULT_BUTTON_COLOR);
    }

    /**
     * 主要按钮
     *
     * @param text
     * @return
     */
    public static JButton primaryButton(String text) {
        return createButton(text, ButtonColorEnum.PRIMARY_BUTTON_COLOR);
    }

    /**
     * 危险按钮
     *
     * @param text
     * @return
     */
    public static JButton dangerButton(String text) {
        return createButton(text, ButtonColorEnum.DANGER_BUTTON_COLOR);
    }

    /**
     * 成功按钮
     *
     * @param text
     * @return
     */
    public static JButton successButton(String text) {
        return createButton(text, ButtonColorEnum.SUCCESS_BUTTON_COLOR);
    }


    /**
     * 椭圆按钮
     */
    private static class RoundButton extends JButton {
        private int radius;
        private Color bgColor;
        private Color fgColor;


        public RoundButton(String text, int radius, Color bgColor, Color fgColor) {
            super(text);
            this.radius = radius;
            this.bgColor = bgColor;
            this.fgColor = fgColor;
            //super.setBackground(bgColor);
            // 取消画矩形
            super.setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(fgColor);
            g2.setBackground(bgColor);
            g2.fillRoundRect(0, 0, super.getSize().width - 1, super.getSize().height - 1, radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }

        /**
         * 创建与默认按钮大小相同的 内边距
         *
         * @param color
         * @param radius
         * @return
         */
        public Border createRoundBorder(Color color, int radius) {
            return new RoundBorder(color, radius);
        }
    }

    /**
     * 创建按钮
     */
    public static JButton createButton(String text,ButtonColorEnum buttonColor) {
        JButton button = new JButton(text);
        //设置背景边框
        button.setForeground(buttonColor.getBackgroundFontColor());
        button.setBorder(new RoundBorder(buttonColor.getBackgroundBorderColor(), RANDIUS));
        button.setBackground(buttonColor.getBackgroundColor());
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {  //鼠标移动到上面时
                button.setForeground(buttonColor.getForegroundFontColor());
                button.setBorder(new RoundBorder(buttonColor.getForegroundBorderColor(), RANDIUS));
                button.setBackground(buttonColor.getForegroundColor());
                button.repaint();
                System.out.println("鼠标移入");
            }
            @Override
            public void mouseExited(MouseEvent e) {  //鼠标移开时
                button.setForeground(buttonColor.getBackgroundFontColor());
                button.setBorder(new RoundBorder(buttonColor.getBackgroundBorderColor(), RANDIUS));
                button.setBackground(buttonColor.getBackgroundColor());
                button.repaint();
                System.out.println("鼠标移出");
            }
        });
        return button;
    }



    /**
     * 椭圆边框
     */
    private static class RoundBorder implements Border {

        private Color color;

        private int radius;

        public RoundBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return DEFAULT_BUTTON.getBorder().getBorderInsets(c);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }


        // 实现Border（父类）方法
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width,
                                int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, radius, radius);
            g2.dispose();
        }

    }

}

