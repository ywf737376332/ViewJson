package com.ywf.component.toolToast;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.ywf.component.TextAreaBuilder;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.utils.IconUtils;
import com.ywf.framework.utils.UIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/2/25 19:41
 */
public class MainTest extends JFrame {

    static Logger logger = LoggerFactory.getLogger(MainTest.class);
    private JFrame _this = this;

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        FlatLaf.registerCustomDefaultsSource("com.zhk.toast.theme");
        FlatLightLaf.setup();
        new MainTest().createAndShowGUI(SystemConstant.SYSTEM_TITLE + SystemConstant.SYSTEM_VERSION);
    }

    public void createAndShowGUI(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        int w = Toolkit.getDefaultToolkit().getScreenSize().width;
        int h = Toolkit.getDefaultToolkit().getScreenSize().height;
        setMinimumSize(new Dimension(830, 600));
        setLocation((w - _this.getWidth()) / 2, (h - _this.getHeight()) / 2);
        setMinimumSize(new Dimension(SystemConstant.WINDOWS_MIN_WIDTH, SystemConstant.WINDOWS_MIN_HEIGHT));
        //设置图标
        setIconImages(FlatSVGUtils.createWindowIconImages("/icons/FlatLaf.svg"));
        // 初始化界面
        initUI(_this);
        setVisible(true);
    }

    private void initUI(JFrame frame) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        UIManager.put("Toast.useEffectss", true);

        System.out.println("测试：" + UIUtils.getString("ToolTipManager.enableToolTipMode", "aaa"));

        JPanel editPanel = new JPanel();
        editPanel.setLayout(new BorderLayout());
        editPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10)); // 设置外边距
        editPanel.add(TextAreaBuilder.scrollTextArea());
        //初始化可创建多个的多文本编辑区
        JToolBar toolBar = new JToolBar("工具栏");
        toolBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));

        JButton btnFormat = new JButton("格式化");
        btnFormat.setIcon(IconUtils.getSVGIcon("icons/formatCode.svg"));
        //Notifications.getInstance().setJFrame(frame);
        btnFormat.addActionListener(e -> {
            //Notifications.getInstance().show(Notifications.Type.WARNING,"Hello World\n"+"请输入需要格式化的内容");
        });

        toolBar.add(btnFormat);
        toolBar.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.LIGHT_GRAY));
        mainPanel.add(toolBar, BorderLayout.NORTH);
        mainPanel.add(editPanel, BorderLayout.CENTER);
        frame.add(mainPanel);

    }


}
