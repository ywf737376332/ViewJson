package com.ywf.component.TipDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * @author LMH
 * @version 1.0
 */
public class SwitchButton extends JToggleButton {

    private static final int SPACE_WIDTH = 2; // 空隙

    private int borderWidth = 0; // 边宽

    private int buttonPanelWidth = 50; // 按钮面板宽度

    private int buttonWidth = 25; // 按钮宽度

    private int buttonHeight = 25; // 按钮高度

    private int buttonArc = 25; // 按钮圆角

    protected float buttonShapeX = 0, buttonShapeY = 0, buttonShapeStart, buttonShapeEnd; // 位置描述，前两个用于记录变化，后两个用于计算

    private RoundRectangle2D.Float borderShape; // 边框形状

    private RoundRectangle2D.Float backgroundShape; // 背景形状

    private BufferedImage insideShadow; // 内阴影

    private RoundRectangle2D.Float buttonShape; // 按钮形状

    private BufferedImage buttonShadow; // 按钮阴影

    private int buttonShadowRadius = 5; // 按钮阴影半径

    private Color borderColor = Color.LIGHT_GRAY; // 边框颜色

    private Color backgroundColor = Color.GRAY; // 实时背景颜色

    private Color backgroundEnabledColor = Color.decode("#7EBC59"); // 背景颜色 - 开启状态

    private Color backgroundDisabledColor = Color.GRAY; // 背景颜色 - 关闭状态

    private Color buttonColor = Color.WHITE; // 按钮颜色

    private Thread thread = Thread.currentThread(); // 当前线程，用来判断动画线程是否继续

    private static final int BTN_PRESSED_ANIM_DELAY = 15; // 长按判断，按下的延时系数

    private static final int BTN_PRESSED_ANIM_SUM_COUNT = 75; // 75 * 5ms，理想状态下约 3.75s动画时长

    private int btnPressedAnimCount = 0; // 长按动画进度

    private static final int BTN_POSITION_ANIM_SUM_COUNT = 75; // 75 * 5ms，理想状态下约 3.75s动画时长

    private int btnPositionAnimCount = 0; // 按钮位移动画进度

    private static final int BACKGROUND_ANIM_SUM_COUNT = 50; // 50 * 5ms，理想状态下约 2.5s动画时长

    private int backgroundAnimCount = 0; // 背景动画进度

    public SwitchButton() {
        init();
        setBorder(null);
        setFocusable(false);
        setContentAreaFilled(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        });
        createAnimThread().start();
    }

    /**
     * 动画线程及其逻辑
     *
     * @return 动画线程
     */
    private Thread createAnimThread() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                int delay = 0;
                float start, end;
                float tempButtonShapeWidth, tempButtonShapeX;
                boolean isNeedRepaint;
                while (thread.isAlive()) {
                    isNeedRepaint = false;
                    start = System.nanoTime();
                    if (getModel().isPressed()) { // 简易的 按钮长按延时判断，消除单击造成的微颤
                        if (delay <= BTN_PRESSED_ANIM_DELAY) delay++;
                    } else {
                        delay = 0;
                    }
                    if (getModel().isPressed() && delay > BTN_PRESSED_ANIM_DELAY && btnPressedAnimCount <= BTN_PRESSED_ANIM_SUM_COUNT) {
                        isNeedRepaint = true;
                        if (!getModel().isSelected()) {
                            buttonShape.width = quartEaseInOut(btnPressedAnimCount++, buttonWidth, buttonWidth / 2, BTN_PRESSED_ANIM_SUM_COUNT);
                        } else {
                            tempButtonShapeWidth = quartEaseInOut(btnPressedAnimCount++, buttonWidth, buttonWidth / 2, BTN_PRESSED_ANIM_SUM_COUNT);
                            buttonShape.width = tempButtonShapeWidth;
                            buttonShape.x = buttonShapeEnd - (tempButtonShapeWidth - buttonWidth);
                        }
                    }
                    if (!getModel().isPressed() && btnPressedAnimCount >= 0) {
                        isNeedRepaint = true;
                        buttonShape.width = quartEaseInOut(btnPressedAnimCount--, buttonWidth, buttonWidth / 2, BTN_PRESSED_ANIM_SUM_COUNT);
                        if (getModel().isSelected()) {
                            buttonShape.x = buttonShapeEnd - (buttonShape.width - buttonWidth);
                        }
                    }
                    if (getModel().isSelected() && btnPositionAnimCount <= BTN_POSITION_ANIM_SUM_COUNT) {
                        isNeedRepaint = true;
                        buttonShape.x = quartEaseInOut(btnPositionAnimCount++, buttonShapeStart, buttonShapeEnd - buttonShapeStart, BTN_POSITION_ANIM_SUM_COUNT);
                    }
                    if (getModel().isSelected() && backgroundAnimCount <= BACKGROUND_ANIM_SUM_COUNT) {
                        try {
                            backgroundColor = gradient(backgroundDisabledColor, backgroundEnabledColor, backgroundAnimCount++, BACKGROUND_ANIM_SUM_COUNT);
                            isNeedRepaint = true;
                        } catch (Exception ignored) {
                            // ignored
                        }
                    }
                    if (!getModel().isSelected() && btnPositionAnimCount >= 0) {
                        tempButtonShapeX = quartEaseInOut(btnPositionAnimCount--, buttonShapeStart, buttonShapeEnd - buttonShapeStart, BTN_POSITION_ANIM_SUM_COUNT);
                        buttonShape.x = tempButtonShapeX - (buttonShape.width - buttonWidth);
                        isNeedRepaint = true;
                    }
                    if (!getModel().isSelected() && backgroundAnimCount >= 0) {
                        try {
                            backgroundColor = gradient(backgroundDisabledColor, backgroundEnabledColor, backgroundAnimCount--, BACKGROUND_ANIM_SUM_COUNT);
                            isNeedRepaint = true;
                        } catch (Exception ignored) {
                            // ignored
                        }
                    }
                    if (isNeedRepaint) {
                        buttonShadow = createButtonShadow();
                        repaint();
                    }
                    end = (System.nanoTime() - start) / 1000000;
                    try {
                        Thread.sleep(end > 5 ? 0 : (int) (5 - end)); // 控制在 5ms，超时就不睡眠了
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 四次方的缓动算法 - 前半段从0开始加速，后半段减速到0的缓动。
     *
     * @param currentTime 当前时间
     * @param beginning   初始值
     * @param changeIn    变化量
     * @param duration    持续时间
     * @return 变化
     * @see <a href="https://github.com/jesusgollonet/processing-penner-easing">缓动算法</a>
     * @see <a href="http://www.cnblogs.com/cloudgamer/archive/2009/01/06/Tween.html">Tween算法及缓动效果</a>
     */
    private float quartEaseInOut(float currentTime, float beginning, float changeIn, float duration) {
        if ((currentTime /= duration / 2) < 1)
            return changeIn / 2 * currentTime * currentTime * currentTime * currentTime + beginning;
        return -changeIn / 2 * ((currentTime -= 2) * currentTime * currentTime * currentTime - 2) + beginning;
    }

    /**
     * 颜色过渡计算
     *
     * @param color1 开始颜色
     * @param color2 结束颜色
     * @param count  计数
     * @param sum    总数
     * @return 过渡色
     * @throws IllegalArgumentException 计数必须大于0且小于总数
     */
    private Color gradient(Color color1, Color color2, int count, int sum) throws IllegalArgumentException {

        // 排除错误参数
        if (count < 0 || count > sum) throw new IllegalArgumentException("错误的参数");

        // 渐变计算
        int r = color1.getRed() + (color2.getRed() - color1.getRed()) * count / sum;
        int g = color1.getGreen() + (color2.getGreen() - color1.getGreen()) * count / sum;
        int b = color1.getBlue() + (color2.getBlue() - color1.getBlue()) * count / sum;

        // 固化范围
        r = r > 255 ? 255 : (r < 0 ? 0 : r);
        g = g > 255 ? 255 : (g < 0 ? 0 : g);
        b = b > 255 ? 255 : (b < 0 ? 0 : b);

        return new Color(r, g, b);
    }

    /**
     * 初始化参数
     *
     * @param buttonPanelWidth 按钮托盘宽度
     * @param buttonWidth      按钮宽度
     * @param buttonHeight     按钮高度
     * @param buttonArc        按钮圆角
     */
    public void init(int buttonPanelWidth, int buttonWidth, int buttonHeight, int buttonArc) {
        this.buttonPanelWidth = buttonPanelWidth;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.buttonArc = buttonArc;
        init();
    }

    /**
     * 初始化
     */
    public void init() {
        float spaceSize = SPACE_WIDTH * 2; // 空隙大小
        float borderSize = borderWidth * 2; // 边框大小

        // ==============> borderShape
        if (borderWidth > 0) {
            float borderShapeWidth = buttonPanelWidth + borderSize + spaceSize;
            float borderShapeHeight = buttonHeight + borderSize + spaceSize;
            float borderShapeArc = buttonArc * borderShapeHeight / buttonHeight; // 简易的圆角等比计算

            if (borderShape == null) borderShape = new RoundRectangle2D.Float();
            borderShape.setRoundRect(0, 0, borderShapeWidth, borderShapeHeight, borderShapeArc, borderShapeArc);
        }
        // borderShape <==============

        // ==============> backgroundShape
        float backgroundShapeWidth = buttonPanelWidth + spaceSize;
        float backgroundShapeHeight = buttonHeight + spaceSize;
        float backgroundShapeArc = buttonArc * backgroundShapeHeight / buttonHeight; // 简易的圆角等比计算

        if (backgroundShape == null) backgroundShape = new RoundRectangle2D.Float();
        backgroundShape.setRoundRect(borderWidth, borderWidth, backgroundShapeWidth, backgroundShapeHeight, backgroundShapeArc, backgroundShapeArc);
        insideShadow = createInsideShadow(backgroundShape, 5); // 创建内阴影
        // backgroundShape <==============

        buttonShapeX = buttonShapeY = buttonShapeStart = borderWidth + SPACE_WIDTH;
        buttonShapeEnd = (borderShape == null ? backgroundShape.width : borderShape.width) - borderWidth - SPACE_WIDTH - buttonWidth;

        // ==============> buttonShape
        if (buttonShape == null) buttonShape = new RoundRectangle2D.Float();
        buttonShape.setRoundRect(buttonShapeX, buttonShapeY, buttonWidth, buttonHeight, buttonArc, buttonArc);
        // buttonShape <==============

        buttonShadow = createButtonShadow();

        // 设置合适的大小
        Dimension dimension = getPreferredSize();
        if (dimension == null) dimension = new Dimension();
        if (borderShape != null) { // 选择最大的 Shape 作为宽高
            dimension.setSize(borderShape.width, borderShape.height);
        } else {
            // 总是会有 backgroundShape
            dimension.setSize(backgroundShape.width, backgroundShape.height);
        }
        setPreferredSize(dimension);
    }

    /**
     * 内阴影
     *
     * @param shape  源形状
     * @param radius 阴影半径
     * @return 阴影 BufferedImage
     */
    private BufferedImage createInsideShadow(RoundRectangle2D.Float shape, int radius) {

        RoundRectangle2D.Float _shape = (RoundRectangle2D.Float) shape.clone();

        _shape.x = _shape.y = radius; // 偏移出适当的大小留给高斯模糊
        int width = _shape.getBounds().width + radius * 2;
        int height = _shape.getBounds().height + radius * 2;

        BufferedImage shadow = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = shadow.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Area area = new Area(_shape);
        Area fill = new Area(new Rectangle2D.Float(0, 0, width, height));
        fill.exclusiveOr(area); // 根据borderShape创建了一个镂空area
        g2d.fill(fill);

        g2d.dispose();

        // 镂空的 Area 的阴影即内阴影
        shadow = getGaussianBlurFilter(radius, true).filter(shadow, null);
        shadow = getGaussianBlurFilter(radius, false).filter(shadow, null);

        _shape.x = _shape.y = 0; // 复原坐标

        // 消除模糊带来的多余部分
        shadow = shadow.getSubimage(radius, radius, _shape.getBounds().width, _shape.getBounds().height);
        g2d = shadow.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        area = new Area(_shape);
        fill = new Area(new Rectangle2D.Float(0, 0, _shape.width, _shape.height));
        fill.exclusiveOr(area);
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fill(fill);
        g2d.setComposite(AlphaComposite.SrcIn.derive(0.75F));
        g2d.fill(_shape);
        g2d.dispose();

        return shadow;
    }

    private BufferedImage createButtonShadow() {
        return createButtonShadow(buttonShape, buttonShadowRadius);
    }

    private BufferedImage createButtonShadow(RoundRectangle2D.Float shape, int radius) {

        RoundRectangle2D.Float buttonShadowShape = (RoundRectangle2D.Float) shape.clone();

        int width = (int) (borderShape == null ? backgroundShape.width : borderShape.width);
        int height = buttonShadowShape.getBounds().height + radius * 2;

        BufferedImage shadow = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = shadow.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.75F));
        buttonShadowShape.y += 2;
        g2d.fill(buttonShadowShape);
        g2d.dispose();

        shadow = getGaussianBlurFilter(radius, true).filter(shadow, null);
        shadow = getGaussianBlurFilter(radius, false).filter(shadow, null);

        return shadow;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (borderShape != null) {
            g2d.setColor(borderColor);
            g2d.fill(borderShape);
        }
        if (backgroundShape != null) {
            g2d.setColor(backgroundColor);
            g2d.fill(backgroundShape);
        }
        if (insideShadow != null) {
            g2d.drawImage(insideShadow, borderWidth, borderWidth, null);
        }
        if (buttonShadow != null) {
            g2d.drawImage(buttonShadow, 0, 0, null);
        }
        if (buttonShape != null) {
            g2d.setColor(buttonColor);
            g2d.fill(buttonShape);
        }
        g2d.dispose();
        super.paintComponent(g);
    }

    /**
     * 高斯模糊
     *
     * @param radius     阴影半径
     * @param horizontal 是否横向
     * @return 高斯模糊 ConvolveOp
     */
    private ConvolveOp getGaussianBlurFilter(int radius, boolean horizontal) {

        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be >= 1");
        }

        int size = radius * 2 + 1;
        float[] data = new float[size];

        float sigma = radius / 3.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;

        for (int i = -radius; i <= radius; i++) {
            float distance = i * i;
            int index = i + radius;
            data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
            total += data[index];
        }

        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }

        Kernel kernel;
        if (horizontal) {
            kernel = new Kernel(size, 1, data);
        } else {
            kernel = new Kernel(1, size, data);
        }
        return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    }

    public int getButtonShadowRadius() {
        return buttonShadowRadius;
    }

    public void setButtonShadowRadius(int buttonShadowRadius) {
        this.buttonShadowRadius = buttonShadowRadius;
    }

    public void setBackgroundEnabledColor(Color color) {
        backgroundEnabledColor = color;
    }

    public void setBackgroundDisabledColor(Color color) {
        backgroundDisabledColor = color;
    }
}