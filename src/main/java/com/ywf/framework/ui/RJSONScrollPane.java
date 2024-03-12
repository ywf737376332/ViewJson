package com.ywf.framework.ui;

import org.fife.ui.rtextarea.RTextScrollPane;

import java.awt.*;

/**
 * JSON编辑框滚动面板重写
 *
 * @Author YWF
 * @Date 2024/3/12 20:46
 */
public class RJSONScrollPane extends RTextScrollPane {

    public RJSONScrollPane(Component view) {
        super(view);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        getVerticalScrollBar().setUI(new ArrowButtonlessScrollBarUI());
        getHorizontalScrollBar().setUI(new ArrowButtonlessScrollBarUI());
    }
}