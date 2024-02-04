package com.ywf.component;

import com.ywf.action.StateBarEventService;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
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

    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    private static JTextArea textAreaSource;

    private static JSONRSyntaxTextArea syntaxTextArea;

    private static RTextScrollPane rTextScrollPane;

    /**
     * 带滚动条的多文本框
     */
    public static JScrollPane scrollTextArea() {
        textAreaSource = new JTextArea();
        UndoManager manager = new UndoManager();
        textAreaSource.getDocument().addUndoableEditListener(manager);
        textAreaSource.setLineWrap(true);
        textAreaSource.setBorder(null);
        textAreaSource.setForeground(new Color(200, 96, 17));
        textAreaSource.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 设置边框为10像素的空白边框
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(textAreaSource);
        return jScrollPane;
    }


    /**
     * Json编辑器
     */
    public static RTextScrollPane createJsonScrollTextArea() {
        SystemThemesEnum themesStyles = SystemThemesEnum.findThemesBykey(applicationContext.getLastSystemThemes());
        String themesPath = themesStyles != null ? themesStyles.getTextAreaStyles() : SystemThemesEnum.FlatLightLafThemesStyle.getTextAreaStyles();
        syntaxTextArea = createTextArea(SyntaxConstants.SYNTAX_STYLE_JSON, themesPath);
        rTextScrollPane = new RTextScrollPane(syntaxTextArea);
        rTextScrollPane.setBorder(BorderFactory.createEmptyBorder());
        // 显示行号
        rTextScrollPane.setLineNumbersEnabled(Boolean.valueOf(applicationContext.getTextAreaShowlineNumState()));
        rTextScrollPane.setFoldIndicatorEnabled(true);
        //监听文档变化
        StateBarEventService.getInstance().textAreaDocumentActionPerformed(syntaxTextArea);
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
        try {
            Theme theme = Theme.load(TextAreaBuilder.class.getResourceAsStream(themesPath));
            theme.apply(textArea);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return textArea;
    }

    public static JTextArea getTextAreaSource() {
        return textAreaSource;
    }

    public static void setTextAreaSource(JTextArea textAreaSource) {
        TextAreaBuilder.textAreaSource = textAreaSource;
    }

    public static JSONRSyntaxTextArea getSyntaxTextArea() {
        return syntaxTextArea;
    }

    public static void setSyntaxTextArea(JSONRSyntaxTextArea syntaxTextArea) {
        TextAreaBuilder.syntaxTextArea = syntaxTextArea;
    }

    public static RTextScrollPane getrTextScrollPane() {
        return rTextScrollPane;
    }

    public static void setrTextScrollPane(RTextScrollPane rTextScrollPane) {
        TextAreaBuilder.rTextScrollPane = rTextScrollPane;
    }
}
