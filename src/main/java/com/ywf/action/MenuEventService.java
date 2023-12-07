package com.ywf.action;

import com.ywf.component.TextAreaBuilder;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.utils.JsonFormatUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
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
 * 菜单事件
 *
 * 懒汉模式：不安全的有读操作，也有写操作。如何保证懒汉模式的线程安全问题：
 * 加锁，把 if 和 new 变成原子操作。
 * 双重 if，减少不必要的加锁操作。
 * 使用 volatile 禁止指重排序，保证后续线程肯定拿到的是完整对象。
 *
 * https://blog.csdn.net/wyd_333/article/details/130416315
 *
 * @Author YWF
 * @Date 2023/12/7 17:15
 */
public class MenuEventService {


    private static JTextArea textAreaSource;
    private static JTextArea rSyntaxTextArea;

    volatile private static MenuEventService instance = null;

    static {
        textAreaSource = TextAreaBuilder.getTextAreaSource();
        rSyntaxTextArea = TextAreaBuilder.getSyntaxTextArea();
    }

    private MenuEventService() {
    }

    public static MenuEventService getInstance() {
        if (instance == null) {
            synchronized (MenuEventService.class) {
                if (instance == null) {
                    instance = new MenuEventService();
                }
            }
        }
        return instance;
    }

    /**
     * 格式化JSON
     * @param frame
     */
    public void formatJsonActionPerformed(JFrame frame){
        if ("".equals(textAreaSource.getText())) {
            JOptionPane.showMessageDialog(frame, "请输入json字符串！");
            return;
        }
        rSyntaxTextArea.setText(JsonFormatUtil.formatJson(textAreaSource.getText()));
    }

    /**
     * 清空文本内容
     * @param frame
     */
    public void cleanJsonActionPerformed(JFrame frame){
        textAreaSource.setText("");
        rSyntaxTextArea.setText("");
        // 保持光标的焦点
        textAreaSource.requestFocusInWindow();
    }

    /**
     * 压缩内容
     * @param frame
     */
    public void compressionJsonActionPerformed(JFrame frame){
        String sourceText = textAreaSource.getText();
        textAreaSource.setText(JsonFormatUtil.compressingStr(sourceText));
    }

    /**
     * 转义
     * @param frame
     */
    public void escapeJsonActionPerformed(JFrame frame){
        String sourceText = textAreaSource.getText();
        textAreaSource.setText(JsonFormatUtil.escapeJSON(sourceText));
    }

    /**
     * 去除转义
     * @param frame
     */
    public void unEscapeJsonActionPerformed(JFrame frame){
        String sourceText = textAreaSource.getText();
        textAreaSource.setText(JsonFormatUtil.unescapeJSON(sourceText));
    }

    /**
     * 复制JSON内容
     * @param frame
     */
    public void copyJsonActionPerformed(JFrame frame){
        StringSelection stringSelection = new StringSelection(rSyntaxTextArea.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        JOptionPane.showMessageDialog(frame, "已将格式化后的JSON结果复制到剪贴板！");
    }

    /**
     * json分享为二维码
     * @param frame
     */
    public void shareJsonToQrcodeActionPerformed(JFrame frame){

    }

    /**
     * json复制为图片
     * @param frame
     */
    public void copyJsonToPictActionPerformed(JFrame frame){

    }



    /**
     * 按钮初始化事件,改变焦点颜色（没用）
     *
     * @param toolbar
     */
    public void toolBarForButtonListener(JToolBar toolbar) {
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
     */
    public void saveJsonToFileActionPerformed(JFrame frame) {
        if ("".equals(rSyntaxTextArea.getText())) {
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
    }

    /**
     * RSyntaxTextArea 内容按照相应的格式保存为图片
     *
     * @param frame
     * @date 2023/12/3 22:00
     */
    public void saveJsonToImageActionPerformed(JFrame frame) {
        if ("".equals(rSyntaxTextArea.getText())) {
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
            BufferedImage image = new BufferedImage(rSyntaxTextArea.getWidth(), rSyntaxTextArea.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            rSyntaxTextArea.print(g2d);
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
