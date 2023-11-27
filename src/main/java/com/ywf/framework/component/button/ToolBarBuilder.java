package com.ywf.framework.component.button;

import com.ywf.framework.component.TextAreaBuilder;
import com.ywf.utils.IconUtils;
import com.ywf.utils.JsonFormatUtil;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/27 22:11
 */
public class ToolBarBuilder {

    private static JButton btnClean;

    private static JButton btnFormat;

    public static JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar("工具栏");
        // 可移动工具栏
        toolBar.setFloatable(true);
        toolBar.setMargin(new Insets(2, 2, 2, 10));
        // 需要绘制边框
        toolBar.setBorderPainted(false);
        // 将默认大小的分隔符添加到工具栏的末尾
        toolBar.addSeparator(new Dimension(5,5));
        toolBar.setRollover(true); // 鼠标悬停时高亮显示按钮
        toolBar.setBorderPainted(false); // 隐藏边框
        toolBar.setBackground(Color.WHITE); // 设置背景颜色

        btnClean = new JButton("清空");
        btnFormat = new JButton("格式化");
        JButton btnComp = new JButton("压缩");
        JButton btnFind = new JButton("查找");
        JButton btnRepl = new JButton("替换");
        btnClean.setIcon(IconUtils.getSVGIcon("ico/Basket.svg"));
        btnFormat.setIcon(IconUtils.getSVGIcon("ico/Grid.svg"));
        btnComp.setIcon(IconUtils.getSVGIcon("ico/Layers.svg"));
        btnFind.setIcon(IconUtils.getSVGIcon("ico/findR.svg"));
        btnRepl.setIcon(IconUtils.getSVGIcon("ico/replace.svg"));

        toolBar.add(btnClean);
        toolBar.add(btnFormat);
        toolBar.add(btnComp);
        toolBar.add(btnFind);
        toolBar.add(btnRepl);

        return toolBar;
    }

    public static void bindEvent(JFrame frame, JButton buttonClean, JButton buttonformat){
        JTextArea textAreaSource = TextAreaBuilder.getTextAreaSource();
        RSyntaxTextArea rSyntaxTextArea = TextAreaBuilder.getSyntaxTextArea();
        buttonformat.addActionListener(e -> {
            if ("".equals(textAreaSource.getText())) {
                JOptionPane.showMessageDialog(frame, "请输入json字符串");
                return;
            }
            String json = JsonFormatUtil.formatJson(textAreaSource.getText());
            rSyntaxTextArea.setText(json);
        });
        buttonClean.addActionListener(e -> {
            textAreaSource.setText("");
            rSyntaxTextArea.setText("");
            // 保持光标的焦点
            textAreaSource.requestFocusInWindow();
        });
    }

    public static JButton getBtnClean() {
        return btnClean;
    }

    public static void setBtnClean(JButton btnClean) {
        ToolBarBuilder.btnClean = btnClean;
    }

    public static JButton getBtnFormat() {
        return btnFormat;
    }

    public static void setBtnFormat(JButton btnFormat) {
        ToolBarBuilder.btnFormat = btnFormat;
    }
}
