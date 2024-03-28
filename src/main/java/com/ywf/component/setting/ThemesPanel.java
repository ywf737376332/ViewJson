package com.ywf.component.setting;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.ywf.action.MenuEventService;
import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.SvgIconFactory;
import com.ywf.framework.base.ThemeColor;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.utils.ObjectUtils;

import javax.swing.*;
import java.awt.*;

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
        setPreferredSize(new Dimension(400, 200));
        setBorder(BorderBuilder.emptyBorder(0, 20, 80, 20));
        setLayout(new GridLayout(3, 4, 0, 0));
    }

    private void init() {
        ButtonGroup buttonGroup = new ButtonGroup();
        for (SystemThemesEnum themesEnum : SystemThemesEnum.values()) {
            JRadioButton radioButton = makeRadioButton(themesEnum.getThemesKey(), getThemeIcon(themesEnum.getThemesIcon()));
            radioButton.setSelectedIcon(new SelectedIcon(radioButton.getIcon(), ThemeColor.authorColor));
            buttonGroup.add(radioButton);
            add(radioButton);
        }
        MenuEventService.getInstance().setupThemesActionPerformed(ObjectUtils.getBean(GlobalKEY.MAIN_FRAME), buttonGroup);
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
        //Path2D triangle = new Path2D.Double();
        //triangle.moveTo(getIconWidth(), getIconHeight() / 2d);
        //triangle.lineTo(getIconWidth(), getIconHeight());
        //triangle.lineTo(getIconWidth() - getIconHeight() / 2d, getIconHeight());
        //triangle.closePath();

        g2.setPaint(color);
        //g2.fill(triangle);
        g2.setStroke(new BasicStroke(3f));
        g2.drawRoundRect(-6, -3, getIconWidth() + 12, getIconHeight() + 6, 20, 20);
        //g2.setPaint(Color.WHITE);
        //Font f = g2.getFont();
        //g2.drawString("★", getIconWidth() - f.getSize(), getIconHeight() - 3);
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