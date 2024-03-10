package com.ywf.component.loading;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 加载中图标
 *
 * @Author YWF
 * @Date 2024/3/10 21:16
 */
public class LoadingLabel extends JLabel {
    private final transient AnimeIcon icon = new AnimeIcon();
    public static long nowTime = 1L;
    private final Timer animator = new Timer(100, e -> {
        this.setText(" 加载中...当前耗时: " + nowTime++ + " 毫秒");
        icon.next();
        repaint();
    });

    protected LoadingLabel() {
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

class AnimeIcon implements Icon {
    private static final Color ELLIPSE_COLOR = Color.BLUE;
    private final java.util.List<Shape> list = new ArrayList<>();
    private final Dimension dim;
    private boolean running;
    private int rotate = 45;

    protected AnimeIcon() {
        super();
        int r = 6;//半径
        Shape s = new Ellipse2D.Double(0d, 0d, 2d * r, 2d * r);
        for (int i = 0; i < 8; i++) {
            AffineTransform at = AffineTransform.getRotateInstance(i * 2 * Math.PI / 8);
            at.concatenate(AffineTransform.getTranslateInstance(r, r));
            list.add(at.createTransformedShape(s));
        }
        int d = r * 2 * (1 + 3);
        dim = new Dimension(d, d);
    }

    @Override
    public int getIconWidth() {
        return dim.width;
    }

    @Override
    public int getIconHeight() {
        return dim.height;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(Optional.ofNullable(c).map(Component::getBackground).orElse(Color.WHITE));
        g2.fillRect(x, y, getIconWidth(), getIconHeight());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(ELLIPSE_COLOR);
        int xx = x + dim.width / 2;
        int yy = y + dim.height / 2;
        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(rotate), xx, yy);
        at.concatenate(AffineTransform.getTranslateInstance(xx, yy));
        int size = list.size();
        for (int i = 0; i < size; i++) {
            float alpha = running ? (i + 1) / (float) size : .5f;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(at.createTransformedShape(list.get(i)));
        }
        g2.dispose();
    }

    public void next() {
        if (running) {
            rotate = (rotate + 45) % 360; // 45 = 360 / 8
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}