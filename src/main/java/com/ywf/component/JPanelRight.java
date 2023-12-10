package com.ywf.component;

import com.ywf.component.textAreaEditor.JavaCodeEditor;

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

    private JEditorPane editorPane;

    public JPanelRight(JPanel parentPanel) {
        this.initUI(parentPanel);
    }

    // 面板组件组装
    public void initUI(JPanel parentPanel) {

        // 创建面板
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(null);
        panelLeft.setBounds(440, 10, 660, 740);
        panelLeft.setBorder(new TitledBorder(new EtchedBorder(), "JSON数据", TitledBorder.LEFT, TitledBorder.TOP));

        // 创建多文本框
        editorPane = new JEditorPane();
        editorPane.setBounds(15, 20, 630, 705);
        editorPane.setEditorKit(new JavaCodeEditor());
        editorPane.setFont(new Font(null, Font.PLAIN, 18));
        //editorPane.setText(  new JsonFormatTool().formatJson("{\"header\":{\"channelId\":\"tar\",\"requestTime\":\"2021-03-17 16:56:52.316\",\"requestBranchCode\":538530,\"termNo\":\"0000000000\",\"operationOrg\":\"003\",\"ip\":\"162.16.52.10\",\"mac\":\"93DAFBA2-B573-477B-9780-629D7F385609\",\"infSeqNum \":\"111112111\",\"serviceCode\":\"DCP2000\",},\"body\":{\"userId\":\"103099091\",\"cerType\":\"1111\",\"certNo\":\"150202199304079035\",\"chCustName\":\"明四八\",\"phoneNo\":\"18221211098\"}}"));
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setBounds(15, 20, 630, 705);
        jScrollPane.setViewportView(editorPane);
        panelLeft.add(jScrollPane);
        //panelLeft.add(editorPane);
        // 将左侧原始数据面板添加到父组件
        parentPanel.add(panelLeft);
    }

    public JEditorPane getEditorPane() {
        return editorPane;
    }

    public void setEditorPane(JEditorPane editorPane) {
        this.editorPane = editorPane;
    }


}
