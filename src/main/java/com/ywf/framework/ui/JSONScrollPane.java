package com.ywf.framework.ui;

import javax.swing.*;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/3/12 17:53
 */
public class JSONScrollPane extends JScrollPane {

    public JSONScrollPane(Component view) {
        super(view);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        getVerticalScrollBar().setUI(new ArrowButtonlessScrollBarUI());
        getHorizontalScrollBar().setUI(new ArrowButtonlessScrollBarUI());
    }
}
