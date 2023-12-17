package com.ywf.action;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.component.JSONRSyntaxTextArea;
import com.ywf.framework.utils.JsonUtil;
import com.ywf.view.PanelView;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/16 23:45
 */
public class StateBarEventService {


    volatile private static StateBarEventService instance = null;

    static {
    }

    private StateBarEventService() {
    }

    public static StateBarEventService getInstance() {
        if (instance == null) {
            synchronized (StateBarEventService.class) {
                if (instance == null) {
                    instance = new StateBarEventService();
                }
            }
        }
        return instance;
    }

    /**
     * 文本框监听事件
     * @date 2023/12/17 14:35
     *
     * @param rSyntaxTextArea
     */
    public void textAreaDocumentActionPerformed(JSONRSyntaxTextArea rSyntaxTextArea) {
        rSyntaxTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = rSyntaxTextArea.getText();
                boolean typeJSON = JsonUtil.isJsonString(text);
                boolean isUrl = JsonUtil.isURL(text);
                FlatLabel labelTypeLabel = PanelView.getFileTypeLabel();
                FlatLabel fileLengthLabel = PanelView.getFileLengthLabel();
                labelTypeLabel.setText("<html><span color=\"#A7B3D3\">内容类型：</span>" + (typeJSON == true ? "<span color=\"#21901C\">JSON类型</span></html>" : (isUrl == true ? "<span color=\"#1541F8\">网址类型</span></html>" : "<span color=\"#389FD6\">文本类型</span></html>")));
                fileLengthLabel.setText("<html><span color=\"#A7B3D3\">字数统计：</span>" + text.length() + "词");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = rSyntaxTextArea.getText();
                boolean typeJSON = JsonUtil.isJsonString(text);
                boolean isUrl = JsonUtil.isURL(text);
                FlatLabel labelTypeLabel = PanelView.getFileTypeLabel();
                FlatLabel fileLengthLabel = PanelView.getFileLengthLabel();
                labelTypeLabel.setText("<html><span color=\"#A7B3D3\">内容类型：</span>" + (typeJSON == true ? "<span color=\"#21901C\">JSON类型</span></html>" : (isUrl == true ? "<span color=\"#1541F8\">网址类型</span></html>" : "<span color=\"#389FD6\">文本类型</span></html>")));
                fileLengthLabel.setText("<html><span color=\"#A7B3D3\">字数统计：</span>" + "<span color=\"#A7B3D3\">" + text.length() + "词</span>");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // 不需要处理文档更改事件
            }
        });
    }

    /**
     * 状态栏时间统计监听事件
     * @date 2023/12/17 14:38
     *
     */
    public void stateBarTimeActionPerformed() {

    }



}
