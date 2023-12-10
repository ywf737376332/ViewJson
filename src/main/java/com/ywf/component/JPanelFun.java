package com.ywf.component;

import com.ywf.utils.JsonFormatUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/10 12:49
 */
public class JPanelFun {

    private static JButton buttonFun;

    private static JButton buttonClean;

    public static JPanel createPanelFun(){
        return initUI();
    }

    // 面板组件组装
    private static JPanel initUI() {

        JPanel panelFun = new JPanel();
        panelFun.setPreferredSize(new Dimension(600, 150));
        panelFun.setBorder(new TitledBorder(new EtchedBorder(), "功能区域", TitledBorder.LEFT, TitledBorder.TOP));
        panelFun.setLayout(null);

        buttonClean = new JButton("清空");
        buttonClean.setBounds(50, 50, 120, 60);
        buttonClean.setFont(new Font("微软雅黑", Font.BOLD, 16));
        panelFun.add(buttonClean);

        buttonFun = new JButton("格式化");
        buttonFun.setBackground(new Color(53, 116, 240));
        buttonFun.setForeground(new Color(255, 255, 255));
        buttonFun.setFont(new Font("微软雅黑", Font.BOLD, 16));
        buttonFun.setBounds(220, 50, 120, 60);
        panelFun.add(buttonFun);
        JTextArea textAreaSource = JPanelLeft.getTextAreaSource();
        RSyntaxTextArea rSyntaxTextArea = JPanelRight.getTextAreaEditor();
        buttonFun.addActionListener(e -> {
            if ("".equals(textAreaSource.getText())) {
                JOptionPane.showMessageDialog(null, "请输入json字符串");
                return;
            }
            String json = new JsonFormatUtils().formatJson(textAreaSource.getText());
            rSyntaxTextArea.setText(json);
        });
        buttonClean.addActionListener(e -> {
            textAreaSource.setText("");
            rSyntaxTextArea.setText("");
            // 保持光标的焦点
            textAreaSource.requestFocusInWindow();
        });
        return panelFun;
    }

}
