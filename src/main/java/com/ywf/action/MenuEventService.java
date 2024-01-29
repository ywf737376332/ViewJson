package com.ywf.action;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.formdev.flatlaf.FlatClientProperties;
import com.ywf.component.*;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.enums.TextConvertEnum;
import com.ywf.framework.enums.TextTypeEnum;
import com.ywf.framework.handle.ApplicationContext;
import com.ywf.framework.utils.*;
import org.fife.ui.rtextarea.RTextScrollPane;

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
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;
import java.util.List;


/**
 * 菜单事件
 * <p>
 * 懒汉模式：不安全的有读操作，也有写操作。如何保证懒汉模式的线程安全问题：
 * 加锁，把 if 和 new 变成原子操作。
 * 双重 if，减少不必要的加锁操作。
 * 使用 volatile 禁止指重排序，保证后续线程肯定拿到的是完整对象。
 * <p>
 * https://blog.csdn.net/wyd_333/article/details/130416315
 *
 * @Author YWF
 * @Date 2023/12/7 17:15
 */
public class MenuEventService {

    private static PropertiesUtil systemProperties;
    private static JSONRSyntaxTextArea rSyntaxTextArea;
    private static RTextScrollPane rTextScrollPane;

    /**
     * 保存图片放大倍数
     */
    private static int pictureScale;

    volatile private static MenuEventService instance = null;

    static {
        systemProperties = PropertiesUtil.getInstance();
        rSyntaxTextArea = TextAreaBuilder.getSyntaxTextArea();
        rTextScrollPane = TextAreaBuilder.getrTextScrollPane();
        pictureScale = NumberUtil.parseInt(systemProperties.getValue(ApplicationContext.SHARE_PICTURE_QUALITY_STATE_KEY));
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
     *
     * @param frame
     */
    public void formatJsonActionPerformed(JFrame frame) {
        if ("".equals(rSyntaxTextArea.getText())) {
            JOptionPane.showMessageDialog(frame, "请输入json字符串！");
            return;
        }
        SwingWorker<Boolean, String> swingWorker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() {
                String text = rSyntaxTextArea.getText();
                TextTypeEnum textType = rSyntaxTextArea.getTextType();
                int converState = rSyntaxTextArea.getChineseConverState();
                String formatAfterText = null;
                switch (TextConvertEnum.findConverEnumByState(converState)) {
                    case CH_TO_UN:
                        formatAfterText = JsonUtil.contentFormat(textType, UnicodeUtil.toUnicode(text));
                        break;
                    case UN_TO_CH:
                        formatAfterText = JsonUtil.contentFormat(textType, UnicodeUtil.toString(text));
                        break;
                    default:
                        formatAfterText = JsonUtil.contentFormat(textType, text);
                }
                publish(formatAfterText);
                return true;
            }

            @Override
            protected void process(List<String> chunks) {
                String formatAfterText = chunks.get(chunks.size() - 1);
                if (StrUtil.isNotBlank(formatAfterText)) {
                    rSyntaxTextArea.setText(formatAfterText);
                }
            }
        };
        swingWorker.execute();
    }

    /**
     * 清空文本内容
     */
    public void cleanJsonActionPerformed() {
        rSyntaxTextArea.setText("");
        // 保持光标的焦点
        rSyntaxTextArea.requestFocusInWindow();
    }

    /**
     * 压缩内容
     */
    public void compressionJsonActionPerformed() {
        SwingUtilities.invokeLater(() -> {
            String sourceText = rSyntaxTextArea.getText();
            rSyntaxTextArea.setText(JsonUtil.compressingStr(sourceText));
        });
    }

    /**
     * 转义
     */
    public void escapeJsonActionPerformed() {
        String sourceText = rSyntaxTextArea.getText();
        SwingUtilities.invokeLater(() -> {
            rSyntaxTextArea.setText(JsonUtil.escapeJSON(sourceText));
        });
    }

    /**
     * 去除转义
     */
    public void unEscapeJsonActionPerformed() {
        String sourceText = rSyntaxTextArea.getText();
        SwingUtilities.invokeLater(() -> {
            rSyntaxTextArea.setText(JsonUtil.unescapeJSON(sourceText));
        });
    }

    /**
     * 复制JSON内容
     *
     * @param frame
     */
    public void copyJsonActionPerformed(JFrame frame) {
        if ("".equals(rSyntaxTextArea.getText())) {
            JOptionPane.showMessageDialog(frame, "内容不能为空！");
            return;
        }
        try {
            SwingUtilities.invokeLater(() -> {
                StringSelection stringSelection = new StringSelection(rSyntaxTextArea.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                JOptionPane.showMessageDialog(frame, "已将格式化后的JSON结果复制到剪贴板！");
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "呢欧容复制失败！" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("呢欧容复制失败: " + e.getMessage());
        }
    }


    /**
     * json复制为图片
     *
     * @param frame
     */
    public void copyJsonToPictActionPerformed(JFrame frame) {
        if ("".equals(rSyntaxTextArea.getText())) {
            JOptionPane.showMessageDialog(frame, "内容不能为空！");
            return;
        }
        try {
            SwingUtilities.invokeLater(() -> {
                //绘制图片
                BufferedImage image = new BufferedImage(rSyntaxTextArea.getWidth() * pictureScale, rSyntaxTextArea.getHeight() * pictureScale, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = image.createGraphics();
                g2d.scale(pictureScale, pictureScale); // 根据画布大小调整缩放比例
                rSyntaxTextArea.print(g2d);
                g2d.dispose();
                // 保存图片到剪贴板
                ImageUtils.imageToClipboard(image);
                JOptionPane.showMessageDialog(frame, "图片已复制到剪贴板！");
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "图片复制失败！" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("图片复制失败: " + e.getMessage());
        }
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
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        FileFilter fileFilter = new FileNameExtensionFilter("JSON文件", "json");
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setDialogTitle("保存文件");
        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                FileWriter fileWriter = new FileWriter(fileToSave + SystemConstant.SAVE_JSON_EXTENSION);
                fileWriter.write(TextAreaBuilder.getSyntaxTextArea().getText());
                fileWriter.close();
                JOptionPane.showMessageDialog(frame, "文件已保存： " + fileToSave.getAbsolutePath() + SystemConstant.SAVE_JSON_EXTENSION);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "文件保存失败" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException("Error saving file: " + e.getMessage());
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
            JOptionPane.showMessageDialog(frame, "内容不能为空！");
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        FileFilter fileFilter = new FileNameExtensionFilter("图片文件", "png");
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setDialogTitle("保存文件");
        int userSelection = fileChooser.showSaveDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            BufferedImage image = new BufferedImage(rSyntaxTextArea.getWidth() * pictureScale, rSyntaxTextArea.getHeight() * pictureScale, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            g2d.scale(pictureScale, pictureScale); // 根据画布大小调整缩放比例
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

    /**
     * 关于对话框
     */
    public static void aboutActionPerformed() {
        JLabel titleLabel = new JLabel(SystemConstant.SYSTEM_TITLE + SystemConstant.SYSTEM_VERSION);
        titleLabel.setIcon(IconUtils.getSVGIcon("icons/logo01.svg", 50, 50));
        titleLabel.putClientProperty(FlatClientProperties.STYLE_CLASS, "H1");
        String link = "737376332@qq.com";
        JLabel linkLabel = new JLabel("<html><span>联系方式：</span><a href=737376332@qq.com>" + link + "</a></html>");
        linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(link));
                } catch (IOException | URISyntaxException ex) {
                    JOptionPane.showMessageDialog(linkLabel, "发送邮件到 '" + link + "' 邮箱，反馈问题、建议、或加入我们!", "提示", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        JOptionPane.showMessageDialog(null, new Object[]{titleLabel, " ", "作者：莫斐鱼", "座右铭：读万卷书，行万里路，阅无数人", linkLabel, "开发日期：2023年11月25日", "Copyright 2023-" + Year.now() + ""}, "关于", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * 更新日志对话框
     */
    public static void updateLogActionPerformed() {
        JLabel linkLabel = new JLabel("<html>" + "<span>最新更新：</span>" + "<ol type=\"1\" style=\"float:left\">" + "<li>工具条新增新建按钮，点击新建时，增加新的选项卡.</li>" + "<li>删除左侧富文本框，增加JSON树显示.</li>" + "<li>系统托盘驻留功能.</li>" + "<li>最下方显示，工具格式化次数.</li>" + "<li>系统托盘驻留功能.</li>" + "<li>搜索功能.</li>" + "<li>窗口大小本地文件记录.</li>" + "<li>增加记录上一次选定的主题颜色.</li>" + "<li>报文分享为二维码.</li>" + "<li>增加按钮栏工具是否显示，某个按钮是否显示功能.</li>" + "<li>增加复制图片功能.</li>" + "</ol>" + "</html>");
        JOptionPane.showMessageDialog(null, new Object[]{linkLabel}, "更新日志", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * 修改主题按钮组事件注册
     *
     * @param frame
     * @param themesMenu
     * @date 2023/12/2 21:30
     */
    public static void setupThemesActionPerformed(JFrame frame, JMenu themesMenu) {
        // 主题按钮选中
        SystemThemesEnum themesCss = SystemThemesEnum.findThemesBykey(systemProperties.getValue(SystemConstant.SYSTEM_THEMES_KEY));
        for (Component menuComponent : themesMenu.getMenuComponents()) {
            if (menuComponent instanceof JRadioButtonMenuItem) {
                JRadioButtonMenuItem radioButtonMenuItem = (JRadioButtonMenuItem) menuComponent;
                if (themesCss.getThemesKey().equals(radioButtonMenuItem.getText())) {
                    radioButtonMenuItem.setSelected(true);
                }
                radioButtonMenuItem.addActionListener(e -> {
                    String name = radioButtonMenuItem.getText();
                    SystemThemesEnum themesStyles = SystemThemesEnum.findThemesBykey(name);
                    ChangeUIUtils.changeUIStyle(frame, themesStyles);
                    // 改变多文本内容的主题
                    ChangeUIUtils.changeTextAreaThemes(frame, themesStyles.getTextAreaStyles());
                    // 保存上一次选定的主题
                    systemProperties.setValue(SystemConstant.SYSTEM_THEMES_KEY, themesStyles.getThemesKey());
                });
            }
        }
    }

    /**
     * 对多文本框进行是否可编辑设置
     *
     * @date 2023/12/2 21:44
     */
    public static void editSwitchActionPerformed() {
        boolean isEditable = rSyntaxTextArea.isEditable();
        rSyntaxTextArea.setEditable(!isEditable);
        systemProperties.setValue(ApplicationContext.TEXTAREA_EDIT_STATE_KEY, String.valueOf(!isEditable));
    }

    /**
     * 对多文本框进行换行设置
     *
     * @date 2023/12/2 21:44
     */
    public static void lineSetupActionPerformed() {
        boolean breakLine = rSyntaxTextArea.getLineWrap();
        rSyntaxTextArea.setLineWrap(!breakLine);
        systemProperties.setValue(ApplicationContext.TEXTAREA_BREAK_LINE_KEY, String.valueOf(!breakLine));
    }

    /**
     * 显示行号
     *
     * @param
     */
    public static void showLineNumActionPerformed() {
        boolean lineNumbersEnabled = rTextScrollPane.getLineNumbersEnabled();
        rTextScrollPane.setLineNumbersEnabled(!lineNumbersEnabled);
        systemProperties.setValue(ApplicationContext.TEXTAREA_SHOW_LINE_NUM_KEY, String.valueOf(!lineNumbersEnabled));
    }

    /**
     * 是否显示工具栏
     *
     * @date 2023/12/9 21:40
     */
    public static void showToolBarActionPerformed() {
        JToolBar toolBar = ToolBarBuilder.getToolBar();
        boolean showToolBar = toolBar.isVisible();
        toolBar.setVisible(!showToolBar);
        // 菜单栏和工具按钮联动修改状态
        MenuBarBuilder.getShowToolBarMenuItem().setSelected(!showToolBar);
        PopupMenuBuilder.getInstance().getToolBarShowState().setSelected(!showToolBar);
        systemProperties.setValue(ApplicationContext.SHOW_TOOL_BAR_KEY, String.valueOf(!showToolBar));
    }

    /**
     * 是否显示菜单栏
     *
     * @date 2023/12/9 21:40
     */
    public static void showMenuBarActionPerformed() {
        JMenuBar menuBar = MenuBarBuilder.getMenuBar();
        boolean showMenuBar = menuBar.isVisible();
        menuBar.setVisible(!showMenuBar);
        // 菜单栏和工具按钮联动修改状态
        MenuBarBuilder.getShowMenuBarMenuItem().setSelected(!showMenuBar);
        PopupMenuBuilder.getInstance().getMenuBarShowState().setSelected(!showMenuBar);
        systemProperties.setValue(ApplicationContext.SHOW_MENU_BAR_KEY, String.valueOf(!showMenuBar));
    }

    /**
     * 中文转码按钮组事件注册
     *
     * @param chineseConverMenu
     */
    public static void chineseConverActionPerformed(JMenu chineseConverMenu) {
        int chineseConverState = NumberUtil.parseInt(systemProperties.getValue(ApplicationContext.TEXTAREA_CHINESE_CONVERT_STATE_KEY));
        for (Component menuComponent : chineseConverMenu.getMenuComponents()) {
            if (menuComponent instanceof CHToCNRadioButtonMenuItem) {
                CHToCNRadioButtonMenuItem chineseConverMenuItem = (CHToCNRadioButtonMenuItem) menuComponent;
                if (chineseConverMenuItem.getChineseConverState() == chineseConverState) {
                    chineseConverMenuItem.setSelected(true);
                }
                chineseConverMenuItem.addActionListener(e -> {
                    systemProperties.setValue(ApplicationContext.TEXTAREA_CHINESE_CONVERT_STATE_KEY, String.valueOf(chineseConverMenuItem.getChineseConverState()));
                    rSyntaxTextArea.setChineseConverState(chineseConverMenuItem.getChineseConverState());
                });
            }
        }
    }

    /**
     * 图片质量按钮组事件注册
     *
     * @param pictureQualityMenu
     * @date 2023/12/16 22:13
     */
    public static void pictureQualityActionPerformed(JMenu pictureQualityMenu) {
        int pictureQuality = NumberUtil.parseInt(systemProperties.getValue(ApplicationContext.SHARE_PICTURE_QUALITY_STATE_KEY));
        for (Component menuComponent : pictureQualityMenu.getMenuComponents()) {
            if (menuComponent instanceof JSONRadioButtonMenuItem) {
                JSONRadioButtonMenuItem pictureQualityMenuitem = (JSONRadioButtonMenuItem) menuComponent;
                if (pictureQualityMenuitem.getPictureQualityState() == pictureQuality) {
                    pictureQualityMenuitem.setSelected(true);
                }
                pictureQualityMenuitem.addActionListener(e -> {
                    systemProperties.setValue(ApplicationContext.SHARE_PICTURE_QUALITY_STATE_KEY, String.valueOf(pictureQualityMenuitem.getPictureQualityState()));
                    pictureScale = pictureQualityMenuitem.getPictureQualityState();
                });
            }
        }
    }


    /**
     * 打开查找对话框
     *
     * @param title 打开的窗口标题名称
     */
    public static void showFindDialogActionPerformed(JFrame frame, String title) {
        //DialogBuilder.showFindDialog(frame, rSyntaxTextArea, title).setVisible(true);
        //PoPupFindPanel.getInstance().showPopup(rSyntaxTextArea);
        FindPanelBuilder.getLayout().showHideActionPerformed();
    }


}
