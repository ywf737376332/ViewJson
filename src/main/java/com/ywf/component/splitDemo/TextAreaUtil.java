package com.ywf.component.splitDemo;

import com.ywf.component.JSONRSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.io.IOException;
import java.util.LinkedList;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/2/5 10:50
 */
public class TextAreaUtil {

    private static LinkedList<RTextScrollPane> list = new LinkedList<>();

    /**
     * 创建组件
     *
     * @return
     */
    public static RTextScrollPane initScrollEditor() {
        RTextScrollPane scrollTextArea = TextAreaUtil.createJsonScrollTextArea();
        list.add(scrollTextArea);
        return scrollTextArea;
    }

    private static RTextScrollPane createJsonScrollTextArea() {
        JSONRSyntaxTextArea syntaxTextArea = createTextArea(SyntaxConstants.SYNTAX_STYLE_JSON, "/themes/textAreaThemes/ideaLight.xml");
        RTextScrollPane rTextScrollPane = new RTextScrollPane(syntaxTextArea);
        rTextScrollPane.setBorder(BorderFactory.createEmptyBorder());
        // 显示行号
        rTextScrollPane.setLineNumbersEnabled(true);
        rTextScrollPane.setFoldIndicatorEnabled(true);
        rTextScrollPane.setName(syntaxTextArea.hashCode() + "");
        return rTextScrollPane;
    }

    private static JSONRSyntaxTextArea createTextArea(String styleKey, String themesPath) {
        UIManager.put("ScrollBar.width", new Integer(2));
        JSONRSyntaxTextArea textArea = new JSONRSyntaxTextArea();
        textArea.setSyntaxEditingStyle(styleKey);
        // 这行代码启用了代码折叠功能
        textArea.setCodeFoldingEnabled(true);
        // 启用了抗锯齿功能
        textArea.setAntiAliasingEnabled(true);
        // 启用了自动滚动功能
        textArea.setAutoscrolls(true);
        // 读取配置信息中的数据
        textArea.setEditable(true);
        //组件名称
        textArea.setName("#rst:" + textArea.hashCode());
        // 自动换行功能
        textArea.setLineWrap(false);
        textArea.revalidate();
        try {
            Theme theme = Theme.load(TextAreaUtil.class.getResourceAsStream(themesPath));
            theme.apply(textArea);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return textArea;
    }

    public static LinkedList<RTextScrollPane> getList() {
        return list;
    }
}
