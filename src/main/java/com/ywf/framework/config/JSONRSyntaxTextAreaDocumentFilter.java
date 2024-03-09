package com.ywf.framework.config;

import com.ywf.component.JSONRSyntaxTextArea;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * 给文本编辑器增加过滤器,限制过滤器容纳的文本内容，超长自动截断
 *
 * @Author YWF
 * @Date 2024/03/09 14:48
 */
public class JSONRSyntaxTextAreaDocumentFilter extends DocumentFilter {

    private JSONRSyntaxTextArea syntaxTextArea;

    public JSONRSyntaxTextAreaDocumentFilter(JSONRSyntaxTextArea syntaxTextArea) {
        this.syntaxTextArea = syntaxTextArea;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String content, AttributeSet attr) throws BadLocationException {
        super.insertString(fb, offset, content, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String content, AttributeSet attrs) throws BadLocationException {
        super.replace(fb, offset, length, content, attrs);
    }

    /**
     * 编辑器内容过滤规则
     * 只允许输入字母、数字和下划线
     *
     * @param content
     * @return
     */
    private boolean contentFilterRule(String content) {
        if (content != null && content.matches("[a-zA-Z0-9_]*")) {
            return true;
        }
        return true;
    }

    /**
     * 字符超过长度截断处理
     *
     * @param content
     * @return
     */
    private String contentLengthBreak(String content) {
        return content;
    }
}
