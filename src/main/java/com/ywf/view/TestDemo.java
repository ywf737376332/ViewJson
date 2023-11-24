package com.ywf.view;

import com.ywf.utils.JsonFormatUtil;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/21 17:22
 */
public class TestDemo {

    private JTextArea textAreaSource;

    private RSyntaxTextArea editorPane;

    private JButton buttonFun;

    private JButton buttonClean;

    public TestDemo() {
    }

    public void createAndShowGUI() {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("JSON格式化工具V2.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        frame.setSize(1125, 800);
        frame.setLocation((w - frame.getWidth()) / 2, (h - frame.getHeight()) / 2);
        frame.setMinimumSize(new Dimension(1125, 800));

        //设置图标
        URL logoUrl = this.getClass().getResource("/ico/logo.png"); // 打包后class的根目录有这个图
        Image image = new ImageIcon(logoUrl).getImage();
        frame.setIconImage(image);
        frame.setBackground(null);

        JPanel panelLeft = new JPanel();
        panelLeft.setPreferredSize(new Dimension(400, 0));
        //panelLeft.setBackground(Color.blue);
        panelLeft.setBorder(new TitledBorder(new EtchedBorder(), "原始数据", TitledBorder.LEFT, TitledBorder.TOP));
        panelLeft.setLayout(new BorderLayout());

        textAreaSource = new JTextArea();
        textAreaSource.setLineWrap(true);
        textAreaSource.setBorder(null);
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setPreferredSize(new Dimension(550, 600));
        jScrollPane.setViewportView(textAreaSource);
        panelLeft.add(jScrollPane,BorderLayout.CENTER);

        JPanel panelFun = new JPanel();
        panelFun.setPreferredSize(new Dimension(600, 150));
        panelFun.setBorder(new TitledBorder(new EtchedBorder(), "功能区域", TitledBorder.LEFT, TitledBorder.TOP));
        panelFun.setLayout(null);

        buttonClean = new JButton("清空");
        buttonClean.setBounds(50, 50, 120, 60);
        buttonClean.setFont(new Font("微软雅黑", Font.BOLD, 16));
        buttonClean.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelFun.add(buttonClean);

        buttonFun = new JButton("格式化");
        buttonFun.setBackground(new Color(53, 116, 240));
        buttonFun.setForeground(new Color(255, 255, 255));
        buttonFun.setFont(new Font("微软雅黑", Font.BOLD, 16));
        buttonFun.setBounds(220, 50, 120, 60);
        buttonFun.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelFun.add(buttonFun);

        //鼠标移到按钮上改变颜色
        buttonFun.addActionListener(e -> {
            if ("".equals(textAreaSource.getText())) {
                JOptionPane.showMessageDialog(frame, "请输入json字符串");
                return;
            }
            String json = JsonFormatUtil.formatJson(textAreaSource.getText());
            editorPane.setText(json);
        });
        buttonFun.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonFun.setBackground(new Color(2,101,210));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonFun.setBackground(new Color(53, 116, 240));
            }
        });
        buttonClean.addActionListener(e -> {
            textAreaSource.setText("");
            editorPane.setText("");
            // 保持光标的焦点
            textAreaSource.requestFocusInWindow();
        });
        buttonClean.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonClean.setBackground(new Color(255,51,51));
                buttonClean.setForeground(new Color(255,255,255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonClean.setBackground(new Color(255,255,255));
                buttonClean.setForeground(new Color(0,0,0));
            }
        });
        panelLeft.add(panelFun, BorderLayout.SOUTH);

        frame.add(panelLeft, BorderLayout.WEST);


        JPanel panelRight = new JPanel();
        panelRight.setPreferredSize(new Dimension(600, 0));
        panelRight.setBorder(new TitledBorder(new EtchedBorder(), "JSON数据", TitledBorder.LEFT, TitledBorder.TOP));
        panelRight.setLayout(new BorderLayout());

        editorPane = newTextArea();
        RTextScrollPane rTextScrollPane = new RTextScrollPane(editorPane);
        rTextScrollPane.setFoldIndicatorEnabled(true);
        panelRight.add(rTextScrollPane, BorderLayout.CENTER);

        frame.add(panelRight, BorderLayout.CENTER);

        frame.setVisible(true);
        frame.pack();
    }


    private RSyntaxTextArea newTextArea() {
        RSyntaxTextArea textArea = new RSyntaxTextArea();
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
        textArea.setCodeFoldingEnabled(true);
        textArea.setAntiAliasingEnabled(true);
        textArea.setAutoscrolls(true);
        textArea.setLineWrap(true);
        textArea.revalidate();
        try {
            Theme theme = Theme.load(getClass().getResourceAsStream("/themes/textAreaThemes.xml"));
            theme.apply(textArea);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return textArea;
    }

}
