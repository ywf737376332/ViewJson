package com.ywf.component;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;

import java.awt.*;
import java.io.IOException;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/10 12:42
 */
public class RSyntaxTextAreaEditor {

    private static RSyntaxTextArea textArea;

    public static RSyntaxTextArea createPanelLeft() {
        return initUI();
    }

    private static RSyntaxTextArea initUI() {
        textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
        textArea.setCodeFoldingEnabled(true);
        textArea.setAntiAliasingEnabled(true);
        textArea.setAutoscrolls(true);
        textArea.setLineWrap(true);
        textArea.setFont(new Font("Courier New",Font.PLAIN,30));
        textArea.revalidate();
        try {
            //Theme theme = Theme.load(RSyntaxTextAreaEditor.class.getResourceAsStream("/org/fife/ui/rsyntaxtextarea/themes/idea.xml"));
            Theme theme = Theme.load(RSyntaxTextAreaEditor.class.getResourceAsStream("/themes/idea.xml"));
            theme.apply(textArea);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return textArea;
    }

    public static RSyntaxTextArea getTextArea() {
        return textArea;
    }

    public static void setTextArea(RSyntaxTextArea textArea) {
        RSyntaxTextAreaEditor.textArea = textArea;
    }
}
