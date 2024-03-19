package com.ywf.framework.ui;


import javax.swing.*;
import java.awt.*;

/**
 * 方块状单选按钮
 *
 * @Author YWF
 * @Date 2024/3/18 16:44
 */
public class ColorRadioButton extends JRadioButton {

    protected static Color DEFAULT_COLOR;
    protected static Color PRESSED_COLOR;
    protected static Color SELECTED_COLOR;
    protected static Color ROLLOVER_COLOR;
    protected static final int ICON_SIZE = 16;

    private String msgKey;
    private Integer msgValue;

    public ColorRadioButton(String text) {
        super(text);
    }

    public ColorRadioButton(String text, String msgKey) {
        super(text);
        this.msgKey = msgKey;
    }

    public ColorRadioButton(String text, Integer msgValue) {
        super(text);
        this.msgValue = msgValue;
    }

    private void initThemeColor() {
        DEFAULT_COLOR = UIManager.getColor("RadioButton.foreground");
        PRESSED_COLOR = UIManager.getColor("RadioButton.selectionEnabledShadowColor");
        SELECTED_COLOR = UIManager.getColor("RadioButton.selectionEnabledColor");
        ROLLOVER_COLOR = UIManager.getColor("RadioButton.focusColor");
    }

    @Override
    public void updateUI() {
        super.updateUI();
        initThemeColor();
        setForeground(DEFAULT_COLOR);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setIcon(new DefaultIcon());
        setPressedIcon(new PressedIcon());
        setSelectedIcon(new SelectedIcon());
        setRolloverIcon(new RolloverIcon());
    }

    @Override
    protected void fireStateChanged() {
        ButtonModel model = getModel();
        if (model.isEnabled()) {
            if (model.isPressed() && model.isArmed()) {
                setForeground(PRESSED_COLOR);
            } else if (model.isSelected()) {
                setForeground(SELECTED_COLOR);
            } else if (isRolloverEnabled() && model.isRollover()) {
                setForeground(ROLLOVER_COLOR);
            } else {
                setForeground(DEFAULT_COLOR);
            }
        } else {
            setForeground(Color.GRAY);
        }
        super.fireStateChanged();
    }

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }

    public Integer getMsgValue() {
        return msgValue;
    }

    public void setMsgValue(Integer msgValue) {
        this.msgValue = msgValue;
    }
}


class DefaultIcon implements Icon {

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(x, y);
        g2.setPaint(ColorRadioButton.DEFAULT_COLOR);
        g2.drawRect(0, 0, getIconWidth() - 1, getIconHeight() - 1);
        g2.drawRect(1, 1, getIconWidth() - 2 - 1, getIconHeight() - 2 - 1);
        g2.dispose();
    }

    @Override
    public int getIconWidth() {
        return ColorRadioButton.ICON_SIZE * 2;
    }

    @Override
    public int getIconHeight() {
        return ColorRadioButton.ICON_SIZE;
    }
}


class PressedIcon extends DefaultIcon {
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(x, y);
        g2.setPaint(ColorRadioButton.PRESSED_COLOR);
        g2.drawRect(0, 0, getIconWidth() - 1, getIconHeight() - 1);
        g2.drawRect(1, 1, getIconWidth() - 2 - 1, getIconHeight() - 2 - 1);

        g2.setPaint(ColorRadioButton.SELECTED_COLOR);
        g2.fillRect(4, 4, getIconWidth() - 8, getIconHeight() - 8);
        g2.dispose();
    }
}

class SelectedIcon extends DefaultIcon {
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(x, y);
        g2.setPaint(ColorRadioButton.SELECTED_COLOR);
        g2.drawRect(0, 0, getIconWidth() - 1, getIconHeight() - 1);
        g2.drawRect(1, 1, getIconWidth() - 2 - 1, getIconHeight() - 2 - 1);

        g2.setPaint(ColorRadioButton.PRESSED_COLOR);
        g2.fillRect(6, 6, getIconWidth() - 12, getIconHeight() - 12);
        g2.dispose();
    }
}

class RolloverIcon extends DefaultIcon {
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(x, y);
        g2.setPaint(ColorRadioButton.ROLLOVER_COLOR);
        g2.drawRect(0, 0, getIconWidth() - 1, getIconHeight() - 1);
        g2.drawRect(1, 1, getIconWidth() - 2 - 1, getIconHeight() - 2 - 1);
        g2.dispose();
    }
}