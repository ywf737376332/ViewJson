package com.ywf.framework.ui;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义滚动面板
 *
 * @Author YWF
 * @Date 2024/3/12 17:53
 */
public class EditScrollPane extends JScrollPane {

    /**
     * 保存hashCode值，以确保每一个新的组件，hashCode都不相同
     */
    private static Set<Integer> hashCodes = new HashSet<Integer>();

    public EditScrollPane(Component view) {
        super(view);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        this.getVerticalScrollBar().setUI(new ArrowButtonlessScrollBarUI());
        this.getHorizontalScrollBar().setUI(new ArrowButtonlessScrollBarUI());
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
