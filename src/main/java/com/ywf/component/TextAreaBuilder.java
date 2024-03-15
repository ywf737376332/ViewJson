package com.ywf.component;

import com.ywf.action.MenuEventService;
import com.ywf.action.StateBarEventService;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.config.JSONRSyntaxTextAreaDocumentFilter;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import com.ywf.framework.ui.EditScrollPane;
import com.ywf.framework.ui.LineNumberView;
import com.ywf.framework.ui.RJSONScrollPane;
import com.ywf.framework.utils.ChangeUIUtils;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.io.IOException;

/**
 * 文本框组件
 *
 * @Author YWF
 * @Date 2023/11/25 18:58
 */
public class TextAreaBuilder {

    private final static Logger logger = LoggerFactory.getLogger(TextAreaBuilder.class);

    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    /**
     * 带滚动条的多文本框
     */
    public static JScrollPane scrollTextArea() {
        JTextArea textAreaSource = new JTextArea();
        UndoManager manager = new UndoManager();
        textAreaSource.getDocument().addUndoableEditListener(manager);
        textAreaSource.setLineWrap(true);
        textAreaSource.setBorder(null);
        textAreaSource.setForeground(new Color(200, 96, 17));
        textAreaSource.setBorder(BorderBuilder.emptyBorder(5)); // 设置边框为10像素的空白边框
        EditScrollPane jScrollPane = new EditScrollPane(textAreaSource);
        jScrollPane.setRowHeaderView(new LineNumberView(textAreaSource));
        return jScrollPane;
    }

    /**
     * 创建带滚动条的富文本框
     *
     * @param context
     * @param width
     * @param height
     * @return
     */
    public static JScrollPane createScrollEditorPane(String context, int width, int height) {
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setBorder(BorderBuilder.emptyBorder(3));
        editorPane.setFocusable(false);
        editorPane.setContentType("text/html");
        editorPane.setText(context);
        EditScrollPane scrollPane = new EditScrollPane(editorPane);
        scrollPane.setSize(new Dimension(width, height));
        return scrollPane;
    }

    /**
     * 带滚动框的Json编辑器
     */
    public static RTextScrollPane createJsonScrollTextArea() {
        SystemThemesEnum themesStyles = SystemThemesEnum.findThemesBykey(applicationContext.getLastSystemThemes());
        String themesPath = themesStyles != null ? themesStyles.getTextAreaStyles() : SystemThemesEnum.FlatLightLafThemesStyle.getTextAreaStyles();
        JSONRSyntaxTextArea syntaxTextArea = createTextArea(SyntaxConstants.SYNTAX_STYLE_JSON, themesPath);
        RJSONScrollPane rTextScrollPane = new RJSONScrollPane(syntaxTextArea);
        rTextScrollPane.setBorder(BorderBuilder.emptyBorder(0));
        // 显示行号
        rTextScrollPane.setLineNumbersEnabled(applicationContext.getTextAreaShowlineNumState());
        rTextScrollPane.setFoldIndicatorEnabled(true);
        Font baseFont = ChangeUIUtils.getReadFileFonts();
        try {
            Theme theme = Theme.load(TextAreaBuilder.class.getResourceAsStream(themesPath));
            theme.apply(syntaxTextArea);
        } catch (IOException ioe) {
            logger.error("JSONRSyntaxTextArea主题应用失败，请检查！" + ioe.getMessage());
        }
        // 必须等Xml初始化结束后，在设置字体，不然xml没设置字体，先用代码设置后，会被覆盖
        //syntaxTextArea.setFont(new Font(applicationContext.getEditorFontStyle().getName(), Font.PLAIN, applicationContext.getEditorFontStyle().getSize()));
        syntaxTextArea.setFont(baseFont);
        logger.info("编辑框字体加载成功,当前字体：{}", syntaxTextArea.getFont());
        // 给文本编辑器增加过滤器
        ((AbstractDocument) syntaxTextArea.getDocument()).setDocumentFilter(new JSONRSyntaxTextAreaDocumentFilter(syntaxTextArea));
        //监听文档变化,统计输入的字符数量和当前文档内容类型识别，同步显示到状态栏
        StateBarEventService.getInstance().textAreaDocumentActionPerformed(syntaxTextArea);
        //监听鼠标的行和列位置
        StateBarEventService.getInstance().mouseLineAndColumnDocumentActionPerformed(syntaxTextArea);
        return rTextScrollPane;
    }

    private static JSONRSyntaxTextArea createTextArea(String styleKey, String themesPath) {
        JSONRSyntaxTextArea textArea = new JSONRSyntaxTextArea();
        textArea.setSyntaxEditingStyle(styleKey);
        // 这行代码启用了代码折叠功能
        textArea.setCodeFoldingEnabled(true);
        // 启用了抗锯齿功能
        textArea.setAntiAliasingEnabled(true);
        // 启用了自动滚动功能
        textArea.setAutoscrolls(true);
        // 读取配置信息中的数据
        textArea.setEditable(applicationContext.getTextAreaEditState());
        // 自动换行功能
        textArea.setLineWrap(applicationContext.getTextAreaBreakLineState());
        textArea.setChineseConverState(applicationContext.getChineseConverState());
        textArea.revalidate();
        return textArea;
    }
}
