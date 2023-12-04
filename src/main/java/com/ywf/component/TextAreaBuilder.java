package com.ywf.component;

import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.utils.ChangeUIUtils;
import com.ywf.framework.utils.PropertiesUtil;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/25 18:58
 */
public class TextAreaBuilder {

    private static JTextArea textAreaSource;

    private static RSyntaxTextArea syntaxTextArea;

    private static PropertiesUtil systemProperties = PropertiesUtil.instance();

    /**
     * 带滚动条的多文本框
     */
    public static JScrollPane scrollTextArea(){
        textAreaSource = new JTextArea();
        textAreaSource.setLineWrap(true);
        textAreaSource.setBorder(null);
        textAreaSource.setForeground(new Color(200,96,17));
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(textAreaSource);
        return jScrollPane;
    }


    /**
     * Json编辑器
     */
    public static RTextScrollPane JsonScrollTextArea() {
        SystemThemesEnum themesStyles = SystemThemesEnum.findThemesBykey(systemProperties.getValueFromProperties(SystemConstant.SYSTEM_THEMES_KEY));
        String themesPath = themesStyles!=null ? themesStyles.getTextAreaStyles() : SystemThemesEnum.FlatLightLafThemesStyle.getTextAreaStyles();
        syntaxTextArea = createTextArea(SyntaxConstants.SYNTAX_STYLE_JSON, themesPath);
        RTextScrollPane rTextScrollPane = new RTextScrollPane(syntaxTextArea);
        rTextScrollPane.setFoldIndicatorEnabled(true);
        return rTextScrollPane;
    }

    private static RSyntaxTextArea createTextArea(String styleKey, String themesPath) {
        RSyntaxTextArea textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(styleKey);
        // 这行代码启用了代码折叠功能
        textArea.setCodeFoldingEnabled(true);
        // 启用了抗锯齿功能
        textArea.setAntiAliasingEnabled(true);
        // 启用了自动滚动功能
        textArea.setAutoscrolls(true);
        // 启用了自动换行功能
        textArea.setLineWrap(false);

        // 读取配置信息中的数据
        textArea.setEditable(Boolean.valueOf(systemProperties.getValueFromProperties(SystemConstant.TEXTAREA_EDIT_STATE_KEY)));
        textArea.setLineWrap(Boolean.valueOf(systemProperties.getValueFromProperties(SystemConstant.TEXTAREA_BREAK_LINE_KEY)));

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

    public static RSyntaxTextArea getSyntaxTextArea() {
        return syntaxTextArea;
    }

    public static void setSyntaxTextArea(RSyntaxTextArea syntaxTextArea) {
        TextAreaBuilder.syntaxTextArea = syntaxTextArea;
    }
}
