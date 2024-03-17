package com.ywf.component.setting;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.SvgIconFactory;
import com.ywf.framework.base.ThemeColor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.stream.Stream;

/**
 * 主题设置面板UI
 *
 * @Author YWF
 * @Date 2024/3/17 14:45
 */
public class ThemesPanel extends JPanel {

    public ThemesPanel() {
        super();
        init();
        setPreferredSize(new Dimension(400, 300));
        setBorder(BorderBuilder.emptyBorder(0,20,20,20));
    }

    private void init() {
        setLayout(new GridLayout(3, 3, 0, 0));
        JRadioButton r1 = makeRadioButton("FlatLaf Light", getThemeIcon(SvgIconFactory.ThemesIcon.flatLafLightTheme));
        r1.setSelectedIcon(new SelectedIcon(r1.getIcon(), ThemeColor.authorColor));

        JRadioButton r2 = makeRadioButton("Arc Light Orange", getThemeIcon(SvgIconFactory.ThemesIcon.arcLightOrange));
        r2.setSelectedIcon(new SelectedIcon(r2.getIcon(), ThemeColor.authorColor));

        JRadioButton r3 = makeRadioButton("Solarized Light", getThemeIcon(SvgIconFactory.ThemesIcon.solarizedLight));
        r3.setSelectedIcon(new SelectedIcon(r3.getIcon(), ThemeColor.authorColor));

        JRadioButton r4 = makeRadioButton("Arc Dark Orange", getThemeIcon(SvgIconFactory.ThemesIcon.arcDarkOrange));
        r4.setSelectedIcon(new SelectedIcon(r4.getIcon(), ThemeColor.authorColor));

        JRadioButton r5 = makeRadioButton("Gruvbox Dark Medium", getThemeIcon(SvgIconFactory.ThemesIcon.gruvboxDarkMedium));
        r5.setSelectedIcon(new SelectedIcon(r5.getIcon(), ThemeColor.authorColor));

        JRadioButton r6 = makeRadioButton("Material Darker", getThemeIcon(SvgIconFactory.ThemesIcon.materialDarker));
        r6.setSelectedIcon(new SelectedIcon(r6.getIcon(), ThemeColor.authorColor));

        JRadioButton r7 = makeRadioButton("Material Deep Ocean", getThemeIcon(SvgIconFactory.ThemesIcon.materialDeepOcean));
        r7.setSelectedIcon(new SelectedIcon(r7.getIcon(), ThemeColor.authorColor));

        JRadioButton r8 = makeRadioButton("Night Owl", getThemeIcon(SvgIconFactory.ThemesIcon.nightOwl));
        r8.setSelectedIcon(new SelectedIcon(r8.getIcon(), ThemeColor.authorColor));

        ButtonGroup buttonGroup = new ButtonGroup();
        Stream.of(r1, r2, r3, r4, r5, r6, r7, r8).forEach(r -> {
            buttonGroup.add(r);
            add(r);
        });
    }

    /**
     * 创建单选按钮
     *
     * @param text
     * @param icon
     * @return
     */
    private static JRadioButton makeRadioButton(String text, Icon icon) {
        JRadioButton radio = new JRadioButton(text, icon);
        radio.setToolTipText(text);
        radio.setVerticalAlignment(SwingConstants.BOTTOM);
        radio.setVerticalTextPosition(SwingConstants.BOTTOM);
        radio.setHorizontalAlignment(SwingConstants.CENTER);
        radio.setHorizontalTextPosition(SwingConstants.CENTER);
        return radio;
    }

    /**
     * 主题图标
     *
     * @param themeIcon
     * @return
     */
    private FlatSVGIcon getThemeIcon(String themeIcon) {
        return SvgIconFactory.icon(themeIcon, 40, 40);
    }

}

/**
 * 单选按钮图标生成器
 */
class ColorIcon implements Icon {
    private final Color color;

    protected ColorIcon(Color color) {
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 开启抗锯齿
        g2.translate(x, y);
        g2.setPaint(color);
        g2.fillRoundRect(0, 0, getIconWidth(), getIconHeight(), 20, 20);
        g2.dispose();
    }

    @Override
    public int getIconWidth() {
        return 64;
    }

    @Override
    public int getIconHeight() {
        return 64;
    }
}

/**
 * 按钮选中图标
 */
class SelectedIcon implements Icon {
    private final Icon icon;
    private final Color color;

    protected SelectedIcon(Icon icon, Color color) {
        this.icon = icon;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(x, y);
        icon.paintIcon(c, g2, 0, 0);
        Path2D triangle = new Path2D.Double();
        triangle.moveTo(getIconWidth(), getIconHeight() / 2d);
        triangle.lineTo(getIconWidth(), getIconHeight());
        triangle.lineTo(getIconWidth() - getIconHeight() / 2d, getIconHeight());
        triangle.closePath();

        g2.setPaint(color);
        g2.fill(triangle);
        g2.setStroke(new BasicStroke(3f));
        g2.drawRoundRect(0, 0, getIconWidth(), getIconHeight(), 20, 20);
        g2.setPaint(Color.WHITE);
        Font f = g2.getFont();
        g2.drawString("√", getIconWidth() - f.getSize(), getIconHeight() - 3);
        g2.dispose();
    }

    @Override
    public int getIconWidth() {
        return icon.getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return icon.getIconHeight();
    }
}