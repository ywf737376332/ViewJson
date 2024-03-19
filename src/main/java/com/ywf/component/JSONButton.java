package com.ywf.component;

import com.ywf.framework.base.BorderBuilder;

import javax.swing.*;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/3/15 19:33
 */
public class JSONButton extends JButton {

    public JSONButton() {
        super();
        setBorder(BorderBuilder.emptyBorder(10, 40, 10, 40));
    }

    public JSONButton(String text) {
        super(text);
        setBtnBorder();
    }

    public JSONButton(Icon icon) {
        super(icon);
        setBtnBorder();
    }

    public JSONButton(Action a) {
        super(a);
        setBtnBorder();
    }

    public JSONButton(String text, Icon icon) {
        super(text, icon);
        setBtnBorder();
    }

    private void setBtnBorder() {
        //setBorder(BorderBuilder.emptyBorder(6, 15, 6, 15));
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        preferredSize.setSize(preferredSize.width + 40, preferredSize.height + 10);
        super.setPreferredSize(preferredSize);
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }


}
