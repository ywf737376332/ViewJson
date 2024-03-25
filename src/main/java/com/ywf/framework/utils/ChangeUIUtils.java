package com.ywf.framework.utils;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.ywf.component.JSONRSyntaxTextArea;
import com.ywf.component.JTabbedSplitEditor;
import com.ywf.component.PopupMenuBuilder;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import com.ywf.framework.ioc.ResourceContext;
import com.ywf.framework.ui.ArrowButtonlessScrollBarUI;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedList;

/**
 * 全局主题应用
 *
 * @Author YWF
 * @Date 2023/11/30 22:17
 */
public class ChangeUIUtils {

    private final static Logger logger = LoggerFactory.getLogger(ChangeUIUtils.class);

    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    private static Font fileBaseFont;

    /**
     * 主题改变工具类类
     *
     * @param frame
     * @param themesStyles
     * @date 2023/12/2 15:35
     */
    public static void changeUIStyle(JFrame frame, SystemThemesEnum themesStyles) {
        initUIStyle();
        // 全局主题应用
        initGlobalTheme(themesStyles);
        changeTextAreaThemes(themesStyles);
        // 其他个别组件主题应用
        initGlobalOtherTheme(frame);
        // 刷新UI界面
        updateViewUI();
    }

    /**
     * FlatAnimatedLafChange.hideSnapshotWithAnimation()这个方法的主要作用是在切换 Look and Feel 时提供一个视觉上的过渡效果，提升用户体验。
     * 如果你不需要动画效果，也可以使用其他方法，如 FlatLaf.updateUI()，直接更新 UI 而没有动画过渡。
     * <p>
     * 此代码的执行顺序不能变，必须在界面刷新后，再重新初始化一次编辑框字体，避免编辑框字体被重置
     */
    public static void updateViewUI() {
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
        FlatLaf.updateUI();
        // 改变TextArea编辑框的字体,避免全局字体改变后,编辑框字体也发生变化(主题xml中未指定字体，所以在每一次刷新界面就得设置字体)
        initTextAreaFont();
    }


    public static void initGlobalTheme(SystemThemesEnum themesStyles) {
        try {
            if (SystemConstant.THEMES_TYPE_SYSTEM == themesStyles.getThemeType()) {
                // 系统主题整体外观
                UIManager.setLookAndFeel(themesStyles.getThemesStyles());
            } else {
                //第三方主题
                IntelliJTheme.setup(ChangeUIUtils.class.getResourceAsStream(themesStyles.getThemesStyles()));
            }
        } catch (Exception ex) {
            logger.error("皮肤应用失败，请检查：{}", ex.getMessage());
        }
    }

    /**
     * 更新传入组件的UI
     *
     * @param frame
     */
    private static void initGlobalOtherTheme(JFrame frame) {
        if (PopupMenuBuilder.getInstance().getContextMenu() != null) {
            // 菜单主题应用
            SwingUtilities.updateComponentTreeUI(PopupMenuBuilder.getInstance().getContextMenu());
        }
    }

    /**
     * 富文本组件主题改变
     *
     * @param themesStyles
     * @date 2023/12/2 15:32
     */
    public static void changeTextAreaThemes(SystemThemesEnum themesStyles) {
        JTabbedSplitEditor tabbedSplitEditor = ObjectUtils.getBean(GlobalKEY.TABBED_SPLIT_EDITOR);
        if (tabbedSplitEditor != null) {
            LinkedList<JScrollPane> sp = tabbedSplitEditor.getPages();
            for (JScrollPane scrollPane : sp) {
                JSONRSyntaxTextArea rSyntaxTextArea = ComponentUtils.convertEditor(scrollPane);
                try {
                    Theme theme = Theme.load(ChangeUIUtils.class.getResourceAsStream(themesStyles.getTextAreaStyles()), SystemConstant.SYSTEM_DEFAULT_FONT);
                    theme.apply(rSyntaxTextArea);
                } catch (IOException e) {
                    logger.error("textAreaThemes apply error,{}" + e.getMessage());
                }
            }
        }
    }

    /**
     * 改变多文本框的字体
     */
    public static void initTextAreaFont() {
        changeTextAreaFont(getReadFileFonts());
    }

    /**
     * 改变多文本框的字体
     */
    public static void changeTextAreaFont(Font font) {
        JTabbedSplitEditor tabbedSplitEditor = ObjectUtils.getBean(GlobalKEY.TABBED_SPLIT_EDITOR);
        if (tabbedSplitEditor != null) {
            LinkedList<JScrollPane> sp = tabbedSplitEditor.getPages();
            for (JScrollPane scrollPane : sp) {
                // 重置滚动条UI,避免重新应用主题后，滚动条UI被还原
                initScrollBarUi(scrollPane);
                // 重置滚动条UI,避免重新应用主题后，编辑框字体被还原
                initJSONAreaFontStyle(scrollPane, font);
            }
        }
    }

    /**
     * 重新设置JScrollPane的下拉条UI
     */
    private static void initScrollBarUi(JScrollPane editorScrollPane) {
        //编辑框滚动条UI重置
        editorScrollPane.getVerticalScrollBar().setUI(new ArrowButtonlessScrollBarUI());
        editorScrollPane.getHorizontalScrollBar().setUI(new ArrowButtonlessScrollBarUI());
        // 循环遍历当前界面创建的所有滚动条，进行UI重置，避免主题更换后失效，必须提前缓存滚动条组件
        ObjectUtils.getGroupBean(GlobalKEY.COMPONENT_SCROLL_GROUP).forEach((k, v) -> {
            if (v instanceof JScrollPane) {
                JScrollPane viewScrollPane = (JScrollPane) v;
                viewScrollPane.getVerticalScrollBar().setUI(new ArrowButtonlessScrollBarUI());
                viewScrollPane.getHorizontalScrollBar().setUI(new ArrowButtonlessScrollBarUI());
            }
        });
    }

    /**
     * 重新设置编辑框字体
     *
     * @param scrollPane
     */
    private static void initJSONAreaFontStyle(JScrollPane scrollPane, Font font) {
        JSONRSyntaxTextArea rSyntaxTextArea = ComponentUtils.convertEditor(scrollPane);
        //logger.warn("编辑框字体改变前：{}", rSyntaxTextArea.getFont());
        rSyntaxTextArea.setFont(font);
        //logger.warn("编辑框字体改变后：{}", rSyntaxTextArea.getFont());
    }

    /**
     * Set the font for all token types.
     *
     * @param font The font to use.
     */
    public static void setTextEditorFont(Font font) {
        JTabbedSplitEditor tabbedSplitEditor = ObjectUtils.getBean(GlobalKEY.TABBED_SPLIT_EDITOR);
        if (tabbedSplitEditor != null) {
            LinkedList<JScrollPane> sp = tabbedSplitEditor.getPages();
            for (JScrollPane scrollPane : sp) {
                JSONRSyntaxTextArea rSyntaxTextArea = ComponentUtils.convertEditor(scrollPane);
                if (font != null) {
                    SyntaxScheme syntaxScheme = rSyntaxTextArea.getSyntaxScheme();
                    syntaxScheme = (SyntaxScheme) syntaxScheme.clone();
                    for (int i = 0; i < syntaxScheme.getStyleCount(); i++) {
                        if (syntaxScheme.getStyle(i) != null) {
                            syntaxScheme.getStyle(i).font = font;
                        }
                    }
                    rSyntaxTextArea.setSyntaxScheme(syntaxScheme);
                    rSyntaxTextArea.setFont(font);
                }
            }
        }
    }

    /**
     * 统一设置字体，父界面设置之后，所有由父界面进入的子界面都不需要再次设置字体
     */
    public static void changeGlobalFont(Font font) {
        // 改变全局界面布局元素的字体
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
        // 刷新UI界面
        updateViewUI();
    }

    /**
     * 获取系统字体列表
     */
    public static boolean getSystemFonts(String fontName) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] availableFontFamilyNames = ge.getAvailableFontFamilyNames();
        for (String fontFamilyName : availableFontFamilyNames) {
            //logger.info("系统字体：{}", fontFamilyName);
            if (fontName.equals(fontFamilyName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 读取字体文件
     *
     * @return
     */
    public static Font getReadFileFonts() {
        /**
         * DejaVuSansMono.ttf
         * JetBrainsMono.ttf
         * Monaco.ttf
         */
        ConfigurableApplicationContext.EditorFontStyle editorFontStyle = applicationContext.getEditorFontStyle();
        String fontFileName = "fonts/" + editorFontStyle.getName() + ".ttf";
        try (InputStream inputStream = ResourceContext.class.getClassLoader().getResourceAsStream(fontFileName)) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            fileBaseFont = font.deriveFont(editorFontStyle.getSize());
            return fileBaseFont;
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException("资源文件加载失败：" + e.getMessage());
        }
    }

    /**
     * 系统组件整体样式定义
     *
     * @date 2023/12/2 15:35
     */
    public static void initUIStyle() {
        //滚动条的默认宽度为 。要使它们更宽（或更小），请使用：10
        //UIManager.put("ScrollBar.width", 4);
        //UIManager.put("SplitPane.background", new Color(0, 0, 0, 0));//设置分隔条为红色
        //UIManager.put("SplitPane.background", UIManager.getColor("control"));
        // 设置滚动条背景色为透明
        //UIManager.put("ScrollBar.background", new Color(1,22,39,200));
        // 设置滚动条颜色为红色
        //UIManager.put("ScrollBar.thumb", new Color(255, 0, 0));
        //UIManager.put("ScrollBar.track", new Color(255, 0, 0, 0));
        //UIManager.put("ScrollBar.thumbInsets", new Insets(20, 20, 20, 20));
        //默认情况下，滚动条的上一个/下一个箭头按钮处于隐藏状态。要使 它们对应用程序使用中的所有滚动条可见：
        //UIManager.put("ScrollBar.showButtons", true);
        //按钮组件的弧度
        //UIManager.put("ProgressBar.arc", 0);
        //UIManager.put("Button.arc", 0);
        //UIManager.put("Component.arc", 0);
        //UIManager.put("TextComponent.arc", 0);
        //聚焦组件的特殊边框表示聚焦状态
        //UIManager.put("Component.focusWidth", 0);
        //控制右侧滑块
        //UIManager.put("ScrollBar.trackArc", 20);
        //UIManager.put("ScrollBar.thumbArc", 20);
        //UIManager.put("ScrollBar.trackInsets", new Insets(20, 40, 20, 40));
        //UIManager.put("ScrollBar.thumbInsets", new Insets(20, 20, 20, 20));
    }

}
