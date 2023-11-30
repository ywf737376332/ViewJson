package com.ywf.action;

import cn.hutool.core.util.StrUtil;
import com.ywf.framework.utils.JsonFormatUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/30 11:40
 */
public class TextAreaAction implements DocumentListener {

    private JTextArea textArea;

    public TextAreaAction(JTextArea textArea) {
        this.textArea = textArea;
        System.out.println("内容："+textArea.getText());
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
            System.out.println("插入更新： " + e.getDocument().getText(e.getOffset(), e.getLength()));        } catch (BadLocationException badLocationException) {
            badLocationException.printStackTrace();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            System.out.println("删除更新： " + e.getDocument().getText(e.getOffset(), e.getLength()));
        } catch (BadLocationException badLocationException) {
            badLocationException.printStackTrace();
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        try {
            System.out.println("更改更新： " + e.getDocument().getText(e.getOffset(), e.getLength()));
        } catch (BadLocationException badLocationException) {
            badLocationException.printStackTrace();
        }
    }

    /**
     * ToolBar按钮可用状态控制
     * @param content
     * @param state
     */
    //public static void btnStateChanged(String content, int state) {
    //
    //    if (state==1 && (StrUtil.isNotBlank(JsonFormatUtil.compressingStr(content))) && (!btnFormat.isEnabled())){
    //        btnFormat.setEnabled(true);
    //    }else if (state==0 && (StrUtil.isBlank(JsonFormatUtil.compressingStr(content))) && btnFormat.isEnabled()){
    //        btnFormat.setEnabled(false);
    //    }
    //}

}
