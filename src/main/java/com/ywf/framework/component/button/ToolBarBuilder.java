package com.ywf.framework.component.button;

import com.ywf.component.TextAreaBuilder;
import com.ywf.framework.utils.IconUtils;
import com.ywf.framework.utils.JsonFormatUtil;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/27 22:11
 */
public class ToolBarBuilder {

    private static JButton btnClean;
    private static JButton btnFormat;
    private static JButton btnComp;
    private static JButton btnEscape;
    private static JButton btnUnescape;
    private static JButton btnCopy;
    private static JButton btnSave;
    private static JButton btnSavePict;
    private static JButton btnFindRepl;

    public static JToolBar createToolBar(JFrame frame) {
        JToolBar toolBar = new JToolBar("工具栏");
        // 可移动工具栏
        toolBar.setFloatable(true);
        // 需要绘制边框
        toolBar.setBorderPainted(true);
        //toolBar.setBorder();
        toolBar.setMargin(new Insets(2,10,2,10));
        // 将默认大小的分隔符添加到工具栏的末尾
        toolBar.addSeparator(new Dimension(10,10));
        toolBar.setRollover(true); // 鼠标悬停时高亮显示按钮
        toolBar.setBorderPainted(false); // 隐藏边框
        toolBar.setBackground(Color.WHITE); // 设置背景颜色

        btnClean = new JButton("清 空");
        btnFormat = new JButton("格式化");
        btnComp = new JButton("压 缩");
        btnEscape = new JButton("转 义");
        btnUnescape = new JButton("去除转义");
        btnCopy = new JButton("复 制");
        btnSave = new JButton("保 存");
        btnSavePict = new JButton("保存图片");
        btnFindRepl = new JButton("查找替换");

        btnClean.setIcon(IconUtils.getSVGIcon("ico/Basket.svg"));
        btnFormat.setIcon(IconUtils.getSVGIcon("ico/formatCode.svg"));
        btnComp.setIcon(IconUtils.getSVGIcon("ico/Layers.svg"));
        btnEscape.setIcon(IconUtils.getSVGIcon("ico/escapeCode.svg"));
        btnUnescape.setIcon(IconUtils.getSVGIcon("ico/unEscapeCode.svg"));
        btnCopy.setIcon(IconUtils.getSVGIcon("ico/copyCode.svg"));
        btnSave.setIcon(IconUtils.getSVGIcon("ico/saveCode.svg"));
        btnSavePict.setIcon(IconUtils.getSVGIcon("ico/cutPict.svg"));
        btnFindRepl.setIcon(IconUtils.getSVGIcon("ico/findCode.svg"));
        toolBar.add(btnClean);
        toolBar.add(btnFormat);
        toolBar.add(btnComp);
        toolBar.add(btnEscape);
        toolBar.add(btnUnescape);
        toolBar.add(btnCopy);
        toolBar.add(btnSave);
        toolBar.add(btnSavePict);
        toolBar.add(btnFindRepl);
        //
        toolBarForButtonListener(toolBar);
        //绑定事件
        bindServiceEvent(frame, btnClean, btnFormat);
        return toolBar;
    }

    // 绑定业务处理事件
    private static void bindServiceEvent(JFrame frame, JButton buttonClean, JButton buttonformat){
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

    /**
     * 按钮初始化事件
     * @param toolbar
     */
    private static void toolBarForButtonListener(JToolBar toolbar){
        for (Component component : toolbar.getComponents()) {
            if (component instanceof JButton) {
                component.addMouseListener(new MouseAdapter(){
                    JButton button = (JButton) component;
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        button.setForeground(Color.WHITE);
                        button.setBackground(new Color(30, 173, 250));
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        button.setForeground(Color.BLACK);
                        button.setBackground(UIManager.getColor("defaultlaf"));
                    }
                });
            }
        }
    }

}
