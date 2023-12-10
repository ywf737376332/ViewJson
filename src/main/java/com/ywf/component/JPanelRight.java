package com.ywf.component;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/21 10:01
 */
public class JPanelRight extends JPanel {

    private static RSyntaxTextArea textAreaEditor;

    public static JPanel createPanelRight() {
        return initUI();
    }

    // 面板组件组装
    private static JPanel initUI() {
        JPanel panelRight = new JPanel();
        panelRight.setPreferredSize(new Dimension(600, 0));
        panelRight.setBorder(new TitledBorder(new EtchedBorder(), "JSON数据", TitledBorder.LEFT, TitledBorder.TOP));
        panelRight.setLayout(new BorderLayout());
        textAreaEditor = RSyntaxTextAreaEditor.createPanelLeft();
        RTextScrollPane rTextScrollPane = new RTextScrollPane(textAreaEditor);
        rTextScrollPane.setFoldIndicatorEnabled(true);
        panelRight.add(rTextScrollPane, BorderLayout.CENTER);
        return panelRight;
    }

    public static RSyntaxTextArea getTextAreaEditor() {
        return textAreaEditor;
    }

    public static void setTextAreaEditor(RSyntaxTextArea textAreaEditor) {
        JPanelRight.textAreaEditor = textAreaEditor;
    }
}
