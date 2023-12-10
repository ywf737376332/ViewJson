package com.ywf.view;


import com.ywf.component.JPanelLeft;
import com.ywf.component.JPanelRight;
import com.ywf.utils.JsonFormatUtils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/21 9:42
 */
public class JsonGUI {

    private JFrame frame;

    private JPanel panel;

    public JsonGUI() {
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);
        JPanelLeft panelLeft = new JPanelLeft(panel);
        JPanelRight panelRight = new JPanelRight(panel);
        JButton formateButton = panelLeft.getFormateButton();
        JButton cleanButton = panelLeft.getCleanButton();
        JTextArea textAreaSourceData = panelLeft.getTextAreaSourceData();
        JEditorPane editorPane = panelRight.getEditorPane();
        formateButton.addActionListener(e -> {
            if ("".equals(textAreaSourceData.getText())) {
                JOptionPane.showMessageDialog(panel, "请输入json字符串");
                return;
            }
            String json = new JsonFormatUtils().formatJson(textAreaSourceData.getText());
            editorPane.setText(json);
        });
        cleanButton.addActionListener(e -> {
            textAreaSourceData.setText("");
            editorPane.setText("");
            // 保持光标的焦点
            textAreaSourceData.requestFocusInWindow();
        });
    }


    public void createAndShowGUI() {

        // 创建 JFrame 实例
        frame = new JFrame("JSON格式化工具V1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //去掉窗口的装饰
        //frame.setUndecorated(true);
        //frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        //设置图标
        URL logoUrl = this.getClass().getResource("/icons/logo.png"); // 打包后class的根目录有这个图
        Image image = new ImageIcon(logoUrl).getImage();
        frame.setIconImage(image);
        frame.setBackground(null);

        //取消缩放
        frame.setResizable(true);
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setSize(1125, 800);
        frame.setLocation((w - frame.getWidth()) / 2, (h - frame.getHeight()) / 2);
        frame.setMinimumSize(new Dimension(1115, 800));

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        panel = new JPanel();
        //panel.setBorder(new TitledBorder(new EtchedBorder(),"",TitledBorder.LEFT,TitledBorder.TOP));
        // 添加面板
        frame.add(panel, BorderLayout.CENTER);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);
        // 圆角设置
        //AWTUtilities.setWindowShape(frame,
        //        new RoundRectangle2D.Double(0.0D, 0.0D, frame.getWidth(),
        //                frame.getHeight(), 6.0D, 6.0D));

        // 设置界面可见
        frame.setVisible(true);
    }


}
