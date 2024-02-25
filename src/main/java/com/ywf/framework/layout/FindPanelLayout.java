package com.ywf.framework.layout;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * 搜索框布局样式与动画
 *
 * @Author YWF
 * @Date 2023/12/23 12:27
 */
public class FindPanelLayout extends BorderLayout {
    private boolean isHidden = true;
    private final Container controls;
    private final Timer animator = new Timer(5, null);
    private int controlsHeight;

    /**
     * @param controls
     * @param hgap     水平间距
     * @param vgap     垂直间距
     * @date 2023/12/31 23:41
     */
    public FindPanelLayout(Container controls, int hgap, int vgap) {
        super(hgap, vgap);
        this.controls = Objects.requireNonNull(controls, "controls must not be null");
        animator.addActionListener(e -> controls.revalidate());
    }

    /**
     * 控制窗口的显示隐藏动画事件
     *
     * @date 2023/12/23 16:29
     */
    public void showHideActionPerformed() {
        if (!animator.isRunning()) {
            isHidden = controls.getHeight() == 0;
            animator.start();
        }
    }

    /**
     * 控制窗口的隐藏动画事件
     * 此事件，解决修改字体后，搜索框界面布局混乱问题
     *
     * @date 2023/12/23 16:29
     */
    public void hideFindPanelActionPerformed() {
        if (!animator.isRunning()) {
            if (controls.getHeight() != 0) {
                isHidden = true;
                animator.start();
            }
        }
    }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        Dimension ps = super.preferredLayoutSize(target);
        int defaultHeight = ps.height;
        if (animator.isRunning()) {
            if (isHidden) {
                if (controls.getHeight() < defaultHeight) {
                    controlsHeight += 5;
                }
            } else {
                if (controls.getHeight() > 0) {
                    controlsHeight -= 5;
                }
            }
            if (controlsHeight <= 0) {
                controlsHeight = 0;
                animator.stop();
            } else if (controlsHeight >= defaultHeight) {
                controlsHeight = defaultHeight;
                animator.stop();
            }
        }
        ps.height = controlsHeight;
        return ps;
    }
}
