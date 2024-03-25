package com.ywf.framework.ui;

import org.fife.ui.rtextarea.RTextScrollPane;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * JSON编辑框滚动面板重写
 *
 * @Author YWF
 * @Date 2024/3/12 20:46
 */
public class RJSONScrollPane extends RTextScrollPane {

    /**
     * 保存hashCode值，以确保每一个新的组件，hashCode都不相同
     */
    private static Set<Integer> hashCodes = new HashSet<Integer>();

    public RJSONScrollPane(Component view) {
        super(view);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        getVerticalScrollBar().setUI(new ArrowButtonlessScrollBarUI());
        getHorizontalScrollBar().setUI(new ArrowButtonlessScrollBarUI());
    }

    @Override
    public int hashCode() {
        int hashCode;
        do {
            hashCode = (int) (Math.random() * Integer.MAX_VALUE);
        } while (hashCodes.contains(hashCode));
        hashCodes.add(hashCode);
        return hashCode;
    }
}