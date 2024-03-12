package com.ywf.framework.ui;

import javax.swing.*;
import java.awt.*;

/**
 * 自定义滚动面板
 *
 * @Author YWF
 * @Date 2024/3/12 17:53
 */
public class EditScrollPane extends JScrollPane {

    public EditScrollPane(Component view) {
        super(view);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        getVerticalScrollBar().setUI(new ArrowButtonlessScrollBarUI());
        getHorizontalScrollBar().setUI(new ArrowButtonlessScrollBarUI());
    }
}
