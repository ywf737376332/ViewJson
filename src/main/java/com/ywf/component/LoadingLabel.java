package com.ywf.component;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.*;
/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/13 17:58
 */
public class LoadingLabel extends JLabel{
    private final transient LoadingIcon icon = new LoadingIcon();
    private final Timer animator = new Timer(100, e -> {
        icon.next();
        repaint();
    });

    public LoadingLabel() {
        super();
        setIcon(icon);
        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.DISPLAYABILITY_CHANGED) != 0 && !e.getComponent().isDisplayable()) {
                animator.stop();
            }
        });
    }

    public void startAnimation() {
        icon.setRunning(true);
        animator.start();
    }

    public void stopAnimation() {
        icon.setRunning(false);
        animator.stop();
    }
}

class LoadingIcon implements Icon {
    private static final Color ELLIPSE_COLOR = new Color(0x80_80_80);
    private static final double R = 2d;
    private static final double SX = 1d;
    private static final double SY = 1d;
    private static final int WIDTH = (int) (R * 8 + SX * 2);
    private static final int HEIGHT = (int) (R * 8 + SY * 2);
    private final List<Shape> list = new ArrayList<>(Arrays.asList(
            new Ellipse2D.Double(SX + 3 * R, SY + 0 * R, 2 * R, 2 * R),
            new Ellipse2D.Double(SX + 5 * R, SY + 1 * R, 2 * R, 2 * R),
            new Ellipse2D.Double(SX + 6 * R, SY + 3 * R, 2 * R, 2 * R),
            new Ellipse2D.Double(SX + 5 * R, SY + 5 * R, 2 * R, 2 * R),
            new Ellipse2D.Double(SX + 3 * R, SY + 6 * R, 2 * R, 2 * R),
            new Ellipse2D.Double(SX + 1 * R, SY + 5 * R, 2 * R, 2 * R),
            new Ellipse2D.Double(SX + 0 * R, SY + 3 * R, 2 * R, 2 * R),
            new Ellipse2D.Double(SX + 1 * R, SY + 1 * R, 2 * R, 2 * R)));
    private boolean running;

    public void next() {
        if (running) {
            // list.add(list.remove(0));
            Collections.rotate(list, 1);
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(x, y);
        // g2.setPaint(Objects.nonNull(c) ? c.getBackground() : Color.WHITE);
        g2.setPaint(Optional.ofNullable(c).map(Component::getBackground).orElse(Color.WHITE));
        g2.fillRect(0, 0, getIconWidth(), getIconHeight());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(ELLIPSE_COLOR);
        float size = (float) list.size();
        list.forEach(s -> {
            float alpha = running ? (list.indexOf(s) + 1) / size : .5f;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(s);
        });
        g2.dispose();
    }

    @Override public int getIconWidth() {
        return WIDTH;
    }

    @Override public int getIconHeight() {
        return HEIGHT;
    }
}
