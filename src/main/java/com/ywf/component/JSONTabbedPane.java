package com.ywf.component;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

/**
 * 系统设置选项卡
 *
 * @Author YWF
 * @Date 2024/3/16 9:53
 */
public class JSONTabbedPane extends JTabbedPane {
    public static final Color SELECTED_BG = new Color(0xFF_96_00);
    public static final Color UNSELECTED_BG = new Color(0xFF_32_00);

    @Override
    public void updateUI() {
        super.updateUI();
        setUI(new BasicTabbedPaneUI() {
            @Override protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
                // Do not paint anything
            }

            @Override protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                // Do not paint anything
            }

            @Override protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                g.setColor(isSelected ? SELECTED_BG : UNSELECTED_BG);
                g.fillRect(x, y, w, h);
            }

            @Override protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
                g.setColor(SELECTED_BG);
                g.fillRect(x, y, w, h);
            }

            @Override protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
                g.setColor(SELECTED_BG);
                g.fillRect(x, y, w, h);
            }

            @Override protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
                g.setColor(SELECTED_BG);
                g.fillRect(x, y, w, h);
            }

            @Override protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
                g.setColor(SELECTED_BG);
                g.fillRect(x, y, w, h);
            }
        });
        setOpaque(true);
        setForeground(Color.WHITE);
        setBackground(UNSELECTED_BG);
        setTabPlacement(SwingConstants.LEFT);
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
}
