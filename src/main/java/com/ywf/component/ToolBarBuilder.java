package com.ywf.component;

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
        // 需要绘制边框
        toolBar.setBorderPainted(true);
        // 可移动工具栏
        toolBar.setFloatable(true);
        // 鼠标悬停时高亮显示按钮
        toolBar.setRollover(true);
        //toolBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // 设置边框颜色和宽度

        toolBar.setBorder(BorderFactory.createMatteBorder(1,0,1,0,new Color(220,220,220))); // 设置边框颜色和宽度
        toolBar.setMargin(new Insets(2,10,2,10));

        btnClean = new JButton("清 空");
        btnFormat = new JButton("格式化");
        btnComp = new JButton("压 缩");
        btnEscape = new JButton("转 义");
        btnUnescape = new JButton("去除转义");
        btnCopy = new JButton("复制结果");
        btnSave = new JButton("保存本地");
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

        btnClean.setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
        toolBar.add(btnClean);
        // 将默认大小的分隔符添加到工具栏的末尾
        toolBar.addSeparator();

        toolBar.add(btnFormat);
        toolBar.addSeparator();

        toolBar.add(btnComp);
        toolBar.addSeparator();

        toolBar.add(btnEscape);
        // 将默认大小的分隔符添加到工具栏的末尾
        toolBar.addSeparator(new Dimension(5,5));

        toolBar.add(btnUnescape);
        toolBar.addSeparator();

        toolBar.add(btnCopy);
        toolBar.addSeparator();

        toolBar.add(btnSave);
        toolBar.addSeparator();

        toolBar.add(btnSavePict);
        toolBar.addSeparator();

        toolBar.add(btnFindRepl);
        // 绑定鼠标焦点事件
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
                        button.setBackground(UIManager.getColor("control"));
                    }
                });
            }
        }
    }

}
