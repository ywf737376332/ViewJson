package com.ywf.component.toolToast;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.ywf.component.TextAreaBuilder;
import com.ywf.framework.constant.MessageConstant;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.ui.ScrollBackToTopIcon;
import com.ywf.framework.ui.ScrollBackToTopLayerUI;
import com.ywf.framework.utils.IconUtils;
import com.ywf.framework.utils.UIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

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
        new MainTest().createAndShowGUI(MessageConstant.SYSTEM_TITLE + SystemConstant.SYSTEM_VERSION);
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

    private JPanel mainPanel;
    //private JToolBar toolBar;
    private boolean showToolBarText = true;

    private void initUI(JFrame frame) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        UIManager.put("Toast.useEffectss", true);

        System.out.println("测试：" + UIUtils.getString("ToolTipManager.enableToolTipMode", "aaa"));

        JPanel editPanel = new JPanel();
        editPanel.setLayout(new BorderLayout());
        editPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10)); // 设置外边距
        editPanel.add(new JLayer<>(TextAreaBuilder.scrollTextArea(), new ScrollBackToTopLayerUI()));
        //初始化可创建多个的多文本编辑区
        JToolBar toolBar = createToolBar(frame);
        mainPanel.add(toolBar, BorderLayout.NORTH);
        mainPanel.add(editPanel, BorderLayout.CENTER);
        frame.add(mainPanel);

    }

    private void fullScreen(JFrame frame, JButton btnFullScreen, GraphicsDevice device, boolean isFullScreen) {
        if (!isFullScreen) {
            // 设置全屏模式
           /* device.setFullScreenWindow(frame);
            int w = Toolkit.getDefaultToolkit().getScreenSize().width;
            int h = Toolkit.getDefaultToolkit().getScreenSize().height;
            btnFullScreen.setIcon(IconUtils.getSVGIcon("icons/nofullScreen.svg"));
            frame.setSize(new Dimension(w+20, h+10));
            frame.setLocation(-10,0);*/
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.isFullScreen = true;
        } else {
            /*btnFullScreen.setIcon(IconUtils.getSVGIcon("icons/fullScreen.svg"));
            // 设置全屏模式
            device.setFullScreenWindow(null);*/
            frame.setExtendedState(JFrame.NORMAL);
            this.isFullScreen = false;
        }
    }

    boolean isFullScreen = false;

    private static void showOverlay(JButton button) {
        // 获取当前窗体的尺寸和位置
        Window window = SwingUtilities.getWindowAncestor(button);
        Point location = window.getLocation();
        Dimension size = window.getSize();

        // 创建覆盖组件（JDialog）
        JDialog overlay = new JDialog((Frame) null, "Overlay", true);
        overlay.setUndecorated(true); // 移除标题栏
        overlay.setLayout(new BorderLayout());

        // 设置圆角形状
        //int width = 830;
        //int height = 600;
        //int cornerRadius = 20;
        //overlay.setShape(new RoundRectangle2D.Double(0, 0, width, height, cornerRadius, cornerRadius));
        // 添加自定义内容到覆盖组件
        JLabel label = new JLabel("This is an overlay component.");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        overlay.add(label, BorderLayout.CENTER);

        // 设置覆盖组件的位置和大小
        overlay.setBounds(location.x, location.y, size.width, size.height);

        // 显示覆盖组件
        overlay.setVisible(true);
    }

    private JToolBar createToolBar(JFrame frame) {
        JToolBar toolBar = new JToolBar("工具栏");
        toolBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));

        JButton btnFormat = new JButton();
        if (showToolBarText) {
            btnFormat.setText("格式化");
        }
        btnFormat.setIcon(IconUtils.getSVGIcon("icons/formatCode.svg"));
        btnFormat.addActionListener(e -> {
            //JOptionPane.showMessageDialog(frame, MessageConstant.SYSTEM_EMPTY_CONTENT_TIP);
            mainPanel.remove(toolBar);
            JToolBar tool = createToolBar(frame);
            mainPanel.add(tool, BorderLayout.NORTH);
            mainPanel.revalidate();
            mainPanel.repaint();
            showToolBarText = !showToolBarText;
        });
        JButton btnFullScreen = new JButton();
        if (showToolBarText) {
            btnFullScreen.setText("全屏");
        }
        btnFullScreen.setIcon(IconUtils.getSVGIcon("icons/fullScreen.svg"));
        btnFullScreen.addActionListener(e -> {
            // 获取默认设备的GraphicsDevice对象
            GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            fullScreen(frame, btnFullScreen, device, isFullScreen);
        });
        JButton btnLockScreen = new JButton();
        if (showToolBarText) {
            btnLockScreen.setText("锁屏");
        }
        btnLockScreen.setIcon(IconUtils.getSVGIcon("icons/formatCode.svg"));
        btnLockScreen.addActionListener(e -> {
            showOverlay(btnLockScreen);
        });
        toolBar.add(btnFormat);
        toolBar.add(btnFullScreen);
        toolBar.add(btnLockScreen);
        toolBar.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.LIGHT_GRAY));
        return toolBar;
    }

}
