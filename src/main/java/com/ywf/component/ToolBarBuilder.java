package com.ywf.component;

import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.utils.IconUtils;
import com.ywf.framework.utils.JsonFormatUtil;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicToolBarUI;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/27 22:11
 */
public class ToolBarBuilder {

    private static JButton btnFormat;
    private static JButton btnComp;
    private static JButton btnEscape;
    private static JButton btnUnescape;
    private static JButton btnCopy;
    private static JButton btnSave;
    private static JButton btnSavePict;
    private static JButton btnFindRepl;
    private static JButton btnClean;

    public static JToolBar createToolBar(JFrame frame) {
        JToolBar toolBar = new JToolBar("工具栏");
        toolBar.setUI(new BasicToolBarUI());
        // 需要绘制边框
        toolBar.setBorderPainted(true);
        // 可移动工具栏
        toolBar.setFloatable(true);
        // 鼠标悬停时高亮显示按钮
        toolBar.setRollover(true);
        toolBar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(130, 128, 128, 130))); // 设置边框颜色和宽度
        toolBar.setMargin(new Insets(2, 10, 2, 10));

        btnFormat = new JButton("格式化");
        btnComp = new JButton("压 缩");
        btnEscape = new JButton("转 义");
        btnUnescape = new JButton("去除转义");
        btnCopy = new JButton("复制结果");
        btnSave = new JButton("保存本地");
        btnSavePict = new JButton("保存图片");
        btnSavePict.addActionListener(e -> saveJsonToimageActionPerformed(frame));

        btnFindRepl = new JButton("查找替换");
        btnFindRepl.setEnabled(false);
        btnClean = new JButton("清空内容");

        btnFormat.setIcon(IconUtils.getSVGIcon("icons/formatCode.svg"));
        btnComp.setIcon(IconUtils.getSVGIcon("icons/comp.svg"));
        btnEscape.setIcon(IconUtils.getSVGIcon("icons/escapeCode.svg"));
        btnUnescape.setIcon(IconUtils.getSVGIcon("icons/unEscapeCode.svg"));
        btnCopy.setIcon(IconUtils.getSVGIcon("icons/copyCode.svg"));
        btnSave.setIcon(IconUtils.getSVGIcon("icons/saveCode.svg"));
        btnSavePict.setIcon(IconUtils.getSVGIcon("icons/cutPict.svg"));
        btnFindRepl.setIcon(IconUtils.getSVGIcon("icons/findCode.svg"));
        btnClean.setIcon(IconUtils.getSVGIcon("icons/Basket.svg"));
        toolBar.add(btnFormat);
        toolBar.addSeparator();

        toolBar.add(btnComp);
        toolBar.addSeparator();

        toolBar.add(btnEscape);
        // 将默认大小的分隔符添加到工具栏的末尾
        toolBar.addSeparator(new Dimension(5, 5));

        toolBar.add(btnUnescape);
        toolBar.addSeparator();

        toolBar.add(btnCopy);
        toolBar.addSeparator();

        toolBar.add(btnSave);
        toolBar.addSeparator();

        toolBar.add(btnSavePict);
        toolBar.addSeparator();

        toolBar.add(btnFindRepl);
        // 将默认大小的分隔符添加到工具栏的末尾
        toolBar.addSeparator();

        //btnClean.setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
        toolBar.add(btnClean);

        // 绑定鼠标焦点事件
        //toolBarForButtonListener(toolBar);
        //绑定事件
        bindServiceEvent(frame, btnClean, btnFormat);

        saveFileDialog(frame, btnSave);
        return toolBar;
    }


    // 绑定业务处理事件
    private static void bindServiceEvent(JFrame frame, JButton buttonClean, JButton buttonformat) {
        JTextArea textAreaSource = TextAreaBuilder.getTextAreaSource();
        RSyntaxTextArea rSyntaxTextArea = TextAreaBuilder.getSyntaxTextArea();
        // 格式化JSON事件
        buttonformat.addActionListener(e -> {
            if ("".equals(textAreaSource.getText())) {
                JOptionPane.showMessageDialog(frame, "请输入json字符串！");
                return;
            }
            rSyntaxTextArea.setText(JsonFormatUtil.formatJson(textAreaSource.getText()));
        });
        // 去除转义符号
        btnUnescape.addActionListener(e -> {
            String sourceText = textAreaSource.getText();
            textAreaSource.setText(JsonFormatUtil.unescapeJSON(sourceText));
            //rSyntaxTextArea.setText(JsonFormatUtil.unescapeJSON(sourceText));
        });
        // 转义
        btnEscape.addActionListener(e -> {
            String sourceText = textAreaSource.getText();
            textAreaSource.setText(JsonFormatUtil.escapeJSON(sourceText));
            //rSyntaxTextArea.setText(JsonFormatUtil.escapeJSON(sourceText));
        });
        // 压缩代码
        btnComp.addActionListener(e -> {
            String sourceText = textAreaSource.getText();
            textAreaSource.setText(JsonFormatUtil.compressingStr(sourceText));
            //rSyntaxTextArea.setText(JsonFormatUtil.compressingStr(sourceText));
        });
        // 复制格式化后的JSON
        btnCopy.addActionListener(e -> {
            StringSelection stringSelection = new StringSelection(rSyntaxTextArea.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            JOptionPane.showMessageDialog(frame, "已将格式化后的JSON结果复制到剪贴板！");
        });
        // 清空内容事件
        buttonClean.addActionListener(e -> {
            textAreaSource.setText("");
            rSyntaxTextArea.setText("");
            // 保持光标的焦点
            textAreaSource.requestFocusInWindow();
        });
    }

    /**
     * 按钮初始化事件
     *
     * @param toolbar
     */
    private static void toolBarForButtonListener(JToolBar toolbar) {
        for (Component component : toolbar.getComponents()) {
            if (component instanceof JButton) {
                component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                component.addMouseListener(new MouseAdapter() {
                    JButton button = (JButton) component;

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        button.setForeground(Color.WHITE);
                        if ("清空内容".equals(button.getText())) {
                            button.setBackground(new Color(255, 87, 34));
                        } else {
                            button.setBackground(new Color(30, 173, 250));
                        }
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

    /**
     * 保存文件
     *
     * @param frame
     * @param button
     */
    public static void saveFileDialog(JFrame frame, JButton button) {
        button.addActionListener(e -> {
            if ("".equals(TextAreaBuilder.getSyntaxTextArea().getText())) {
                JOptionPane.showMessageDialog(frame, "保存的内容不能为空！");
                return;
            }
            JFileChooser fileChooser = new JFileChooser();
            FileFilter fileFilter = new FileNameExtensionFilter("JSON文件", "json");
            fileChooser.setFileFilter(fileFilter);
            fileChooser.setDialogTitle("保存文件");
            int userSelection = fileChooser.showSaveDialog(frame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                try {
                    FileWriter fileWriter = new FileWriter(fileToSave + SystemConstant.SAVE_JSON_EXTENSION);
                    fileWriter.write(TextAreaBuilder.getSyntaxTextArea().getText());
                    fileWriter.close();
                    JOptionPane.showMessageDialog(frame, "文件已保存： " + fileToSave.getAbsolutePath() + SystemConstant.SAVE_JSON_EXTENSION);
                } catch (IOException ex) {
                    throw new RuntimeException("Error saving file: " + ex.getMessage());
                }
            }
        });
    }

    /**
     * RSyntaxTextArea 内容按照相应的格式保存为图片
     *
     * @param frame
     * @date 2023/12/3 22:00
     */
    private static void saveJsonToimageActionPerformed(JFrame frame) {
        RSyntaxTextArea textArea = TextAreaBuilder.getSyntaxTextArea();
        if ("".equals(textArea.getText())) {
            JOptionPane.showMessageDialog(frame, "保存的内容不能为空！");
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        FileFilter fileFilter = new FileNameExtensionFilter("图片文件", "png");
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setDialogTitle("保存文件");
        int userSelection = fileChooser.showSaveDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            BufferedImage image = new BufferedImage(textArea.getWidth(), textArea.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            textArea.print(g2d);
            g2d.dispose();
            try {
                ImageIO.write(image, "png", new File(fileToSave.getPath() + SystemConstant.SAVE_IMAGE_EXTENSION));
                JOptionPane.showMessageDialog(frame, "图片已保存： " + fileToSave.getAbsolutePath() + SystemConstant.SAVE_IMAGE_EXTENSION);
            } catch (IOException e) {
                throw new RuntimeException("Error saving image: " + e.getMessage());
            }
        }


    }

}
