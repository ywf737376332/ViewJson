package com.ywf.action;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.StrUtil;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.AppMain;
import com.ywf.component.*;
import com.ywf.component.loading.BackgroundTaskKit;
import com.ywf.component.loading.LoadingBuild;
import com.ywf.component.toast.Toast;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.SvgIconFactory;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.constant.MessageConstant;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.LocationEnum;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.enums.TextConvertEnum;
import com.ywf.framework.enums.TextTypeEnum;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import com.ywf.framework.utils.*;
import com.ywf.view.PanelView;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;


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

    private final static Logger logger = LoggerFactory.getLogger(MenuEventService.class);

    private JTabbedSplitEditor tabbedSplitEditor;

    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    volatile private static MenuEventService instance = null;

    private MenuEventService() {
        tabbedSplitEditor = ObjectUtils.getBean(GlobalKEY.TABBED_SPLIT_EDITOR);
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
        JSONRSyntaxTextArea rSyntaxTextArea = tabbedSplitEditor.getFocusEditor();
        if ("".equals(rSyntaxTextArea.getText())) {
            //JOptionPane.showMessageDialog(frame, MessageConstant.SYSTEM_EMPTY_CONTENT_TIP, MessageConstant.SYSTEM_WARN_TIP, JOptionPane.INFORMATION_MESSAGE);
            Toast.error(frame, MessageConstant.SYSTEM_EMPTY_CONTENT_TIP);
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
                        // 1.先替换回车后面的空格
                        // 2.再替换回车和换行，
                        text = UnicodeUtil.toUnicode(text.replaceAll("(?<=\\n)[ \\t]+", "").replaceAll("[\\t\\n\\r]", ""));
                        formatAfterText = JsonUtil.contentFormat(textType, text);
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
        JSONRSyntaxTextArea rSyntaxTextArea = tabbedSplitEditor.getFocusEditor();
        rSyntaxTextArea.setText("");
        // 保持光标的焦点
        rSyntaxTextArea.requestFocusInWindow();
    }

    /**
     * 压缩内容
     */
    public void compressionJsonActionPerformed() {
        SwingUtilities.invokeLater(() -> {
            JSONRSyntaxTextArea rSyntaxTextArea = tabbedSplitEditor.getFocusEditor();
            String sourceText = rSyntaxTextArea.getText();
            rSyntaxTextArea.setText(JsonUtil.compressingStr(sourceText));
        });
    }

    /**
     * 转义
     */
    public void escapeJsonActionPerformed() {
        JSONRSyntaxTextArea rSyntaxTextArea = tabbedSplitEditor.getFocusEditor();
        String sourceText = rSyntaxTextArea.getText();
        SwingUtilities.invokeLater(() -> {
            rSyntaxTextArea.setText(JsonUtil.escapeJSON(sourceText));
        });
    }

    /**
     * 去除转义
     */
    public void unEscapeJsonActionPerformed() {
        JSONRSyntaxTextArea rSyntaxTextArea = tabbedSplitEditor.getFocusEditor();
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
        JSONRSyntaxTextArea rSyntaxTextArea = tabbedSplitEditor.getFocusEditor();
        if ("".equals(rSyntaxTextArea.getText())) {
            //JOptionPane.showMessageDialog(frame, MessageConstant.SYSTEM_EMPTY_CONTENT_TIP, MessageConstant.SYSTEM_WARN_TIP, JOptionPane.INFORMATION_MESSAGE);
            Toast.error(frame, MessageConstant.SYSTEM_EMPTY_CONTENT_TIP);
            return;
        }
        try {
            LoadingBuild.create(frame, new BackgroundTaskKit.CopyJsonAction(rSyntaxTextArea)).showModal();
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, MessageConstant.SYSTEM_COPY_JSON_FAIL_TIP + e.getMessage(), MessageConstant.SYSTEM_ERROR_TIP, JOptionPane.ERROR_MESSAGE);
            Toast.error(frame, MessageConstant.SYSTEM_COPY_JSON_FAIL_TIP);
            throw new RuntimeException("内容复制失败: " + e.getMessage());
        }
    }


    /**
     * json复制为图片
     *
     * @param frame
     */
    public void copyJsonToPictActionPerformed(JFrame frame) {
        JSONRSyntaxTextArea rSyntaxTextArea = tabbedSplitEditor.getFocusEditor();
        if ("".equals(rSyntaxTextArea.getText())) {
            //JOptionPane.showMessageDialog(frame, MessageConstant.SYSTEM_EMPTY_CONTENT_TIP, MessageConstant.SYSTEM_WARN_TIP, JOptionPane.INFORMATION_MESSAGE);
            Toast.error(WindowUtils.getFrame(), MessageConstant.SYSTEM_EMPTY_CONTENT_TIP);
            return;
        }
        LoadingBuild.create(frame, new BackgroundTaskKit.CopyJsonToPictAction(rSyntaxTextArea)).showModal();
    }

    /**
     * 保存文件
     *
     * @param frame
     */
    public void saveJsonToFileActionPerformed(JFrame frame) {
        JSONRSyntaxTextArea rSyntaxTextArea = tabbedSplitEditor.getFocusEditor();
        if ("".equals(rSyntaxTextArea.getText())) {
            //JOptionPane.showMessageDialog(frame, MessageConstant.SYSTEM_EMPTY_CONTENT_TIP, MessageConstant.SYSTEM_WARN_TIP, JOptionPane.INFORMATION_MESSAGE);
            Toast.error(WindowUtils.getFrame(), MessageConstant.SYSTEM_EMPTY_CONTENT_TIP);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        FileFilter fileFilter = new FileNameExtensionFilter(MessageConstant.SYSTEM_JSON_FILE_TYPE, "json");
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setDialogTitle(MessageConstant.SYSTEM_SAVE_FILE_TAG);
        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                FileWriter fileWriter = new FileWriter(fileToSave + SystemConstant.SAVE_JSON_EXTENSION);
                fileWriter.write(rSyntaxTextArea.getText());
                fileWriter.close();
                //JOptionPane.showMessageDialog(frame, MessageConstant.SYSTEM_FILE_SAVE_SUCCESS + fileToSave.getAbsolutePath() + SystemConstant.SAVE_JSON_EXTENSION, MessageConstant.SYSTEM_WARN_TIP, JOptionPane.INFORMATION_MESSAGE);
                Toast.success(frame, MessageConstant.SYSTEM_FILE_SAVE_SUCCESS + fileToSave.getAbsolutePath() + SystemConstant.SAVE_JSON_EXTENSION);
            } catch (IOException e) {
                //JOptionPane.showMessageDialog(null, MessageConstant.SYSTEM_FILE_SAVE_FAIL + e.getMessage(), MessageConstant.SYSTEM_ERROR_TIP, JOptionPane.ERROR_MESSAGE);
                Toast.error(frame, MessageConstant.SYSTEM_FILE_SAVE_FAIL + e.getMessage());
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
        JSONRSyntaxTextArea rSyntaxTextArea = tabbedSplitEditor.getFocusEditor();
        if ("".equals(rSyntaxTextArea.getText())) {
            //JOptionPane.showMessageDialog(frame, MessageConstant.SYSTEM_EMPTY_CONTENT_TIP, MessageConstant.SYSTEM_WARN_TIP, JOptionPane.INFORMATION_MESSAGE);
            Toast.error(frame, MessageConstant.SYSTEM_EMPTY_CONTENT_TIP);
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        FileFilter fileFilter = new FileNameExtensionFilter(MessageConstant.SYSTEM_IMAGE_FILE_TYPE, "png");
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setDialogTitle(MessageConstant.SYSTEM_SAVE_FILE_TAG);
        int userSelection = fileChooser.showSaveDialog(frame);
        int pictureScale = applicationContext.getPictureQualityState();
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            BufferedImage image = new BufferedImage(rSyntaxTextArea.getWidth() * pictureScale, rSyntaxTextArea.getHeight() * pictureScale, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            g2d.scale(pictureScale, pictureScale); // 根据画布大小调整缩放比例
            rSyntaxTextArea.print(g2d);
            g2d.dispose();
            try {
                ImageIO.write(image, "png", new File(fileToSave.getPath() + SystemConstant.SAVE_IMAGE_EXTENSION));
                //JOptionPane.showMessageDialog(frame, MessageConstant.SYSTEM_IMAGE_SAVE_SUCCESS + fileToSave.getAbsolutePath() + SystemConstant.SAVE_IMAGE_EXTENSION, MessageConstant.SYSTEM_WARN_TIP, JOptionPane.INFORMATION_MESSAGE);
                Toast.success(frame, MessageConstant.SYSTEM_IMAGE_SAVE_SUCCESS + fileToSave.getAbsolutePath() + SystemConstant.SAVE_IMAGE_EXTENSION);
            } catch (IOException e) {
                //JOptionPane.showMessageDialog(null, MessageConstant.SYSTEM_IMAGE_SAVE_FAIL + e.getMessage(), MessageConstant.SYSTEM_ERROR_TIP, JOptionPane.ERROR_MESSAGE);
                Toast.error(frame, MessageConstant.SYSTEM_IMAGE_SAVE_FAIL + e.getMessage());
                throw new RuntimeException("Error saving image: " + e.getMessage());
            }
        }
    }

    /**
     * 关于对话框
     */
    public void aboutActionPerformed() {
        JLabel titleLabel = new JLabel(MessageConstant.SYSTEM_TITLE + SystemConstant.SYSTEM_VERSION);
        titleLabel.setIcon(SvgIconFactory.icon(SvgIconFactory.SystemIcon.about, 50, 50));
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
     * 功能介绍
     */
    public void updateLogActionPerformed() {
        String message = "<html><body><center><h3>JSON工具功能介绍</h3></center><ol><li>支持JSON，XML报文的格式化显示，压缩，转义，去除转义，复制报文内容。</li><li>支持JSON，XML报文内容的中中文和Unicode互转。</li><li>支持报文内容复制到剪贴板。</li><li>支持L报文内容生成图片复制到剪贴板。</li><li>支持报文内容分享为二维码图片，实现任何收集扫码器的内容识别。</li><li>支持报文内容的查找，高亮显示功能。</li><li>支持一键清空报文内容。</li><li>支持报文内容导出为文件。</li><li>支持报文内容导出为图片，图片带有水印，图片可选择清晰度。</li><li>编辑器可识别JSON，XML,Properties,Sql,JavaScript,Java,Yaml的内容类型。</li><li>编辑器可设置行号是否显示。</li><li>编辑器可设置是否自动换行。</li><li>编辑器可设置内容是否可编辑。</li><li>可支持并排显示四个编辑器,方便实现代码对比。</li><li>软件支持八种主题的设置。</li><li>软件支持字体字号的设置。</li></ol><p style=\"text-align:right;font-weight:blod\">2024年3月5日</p><p style=\"text-align:right;font-weight:blod\">莫斐鱼</p></body></html>";
        JScrollPane scrollPane = TextAreaBuilder.createScrollEditorPane(message, 600, 500);
        final Dialog dialog = DialogBuilder.showDialog(WindowUtils.getFrame(), "功能介绍", scrollPane, 30);
        dialog.setVisible(true);
    }

    /**
     * 隐私协议
     */
    public void privacyPolicyActionPerformed() {
        String message = ("<html><body><h3>本软件为JSON工具，由莫斐鱼开发并发布。用户在安装、使用本软件时，需遵守以下条款和条件：</h3><ol><li>许可范围：本软件的许可范围仅限于个人使用，禁止用于商业用途或任何形式的盈利活动。</li><li>版权保护：本软件的所有知识产权归莫斐鱼所有，用户不得以任何形式复制、修改、传播、分发或出售本软件的任何部分。</li><li>免责声明：本软件按“原样”提供，不提供任何明示或暗示的保证，包括但不限于适销性、特定目的适用性和非侵权性的保证。对于因使用本软件而造成的任何损失、损害或法律纠纷，莫斐鱼概不负责。</li><li>更新和维护：莫斐鱼有权随时对本软件进行更新和改进，并在必要时发布补丁程序或新版本。用户应定期检查并下载最新版本的软件，以确保其功能和安全性。</li><li>终止：如果用户违反了本许可协议的任何条款，莫斐鱼有权立即终止用户的使用权，并保留追究法律责任的权利。</li><li>法律适用和管辖：本许可协议受中华人民共和国法律管辖。如发生争议，双方应友好协商解决；协商不成的，任何一方均有权向有管辖权的人民法院提起诉讼。</li></ol><p style=\"text-indent: 2em\">请在使用本软件之前认真阅读并理解本许可协议的所有内容，感谢您的理解和支持！</p><p style=\"text-align:right;font-weight:blod\">发布者：莫斐鱼</p><p style=\"text-align:right;font-weight:blod\">日期：2024年1月1日</p></body></html>");
        JScrollPane scrollPane = TextAreaBuilder.createScrollEditorPane(message, 600, 500);
        final Dialog dialog = DialogBuilder.showDialog(WindowUtils.getFrame(), "隐私协议", scrollPane, 10);
        dialog.setVisible(true);
    }

    /**
     * 感谢捐赠
     */
    public void expressThanksActionPerformed() {
        ImageIcon icon = IconUtils.getIcon("/images/pay.png");
        final JDialog dialog = DialogBuilder.showImageDialog(WindowUtils.getFrame(), "鸣谢反馈", icon, 20);
        dialog.setVisible(true);
    }


    /**
     * 修改主题按钮组事件注册
     *
     * @param frame
     * @param themesMenu
     * @date 2023/12/2 21:30
     */
    public void setupThemesActionPerformed(JFrame frame, JMenu themesMenu) {
        // 主题按钮选中
        SystemThemesEnum themesCss = SystemThemesEnum.findThemesBykey(applicationContext.getLastSystemThemes());
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
                    // 保存上一次选定的主题
                    applicationContext.setLastSystemThemes(themesStyles.getThemesKey());
                });
            }
        }
    }

    /**
     * 对多文本框进行是否可编辑设置
     *
     * @date 2023/12/2 21:44
     */
    public void editSwitchActionPerformed() {
        LinkedList<JScrollPane> scrollPaneList = tabbedSplitEditor.getPages();
        for (int i = 0; i < scrollPaneList.size(); i++) {
            JSONRSyntaxTextArea rSyntaxTextArea = ComponentUtils.convertEditor(scrollPaneList.get(i));
            boolean isEditable = rSyntaxTextArea.isEditable();
            rSyntaxTextArea.setEditable(!isEditable);
            if (i == scrollPaneList.size() - 1) {
                applicationContext.setTextAreaEditState(!isEditable);
            }
        }
    }

    /**
     * 对多文本框进行换行设置
     *
     * @date 2023/12/2 21:44
     */
    public void lineSetupActionPerformed() {
        LinkedList<JScrollPane> scrollPaneList = tabbedSplitEditor.getPages();
        for (int i = 0; i < scrollPaneList.size(); i++) {
            JSONRSyntaxTextArea rSyntaxTextArea = ComponentUtils.convertEditor(scrollPaneList.get(i));
            boolean breakLine = rSyntaxTextArea.getLineWrap();
            rSyntaxTextArea.setLineWrap(!breakLine);
            if (i == scrollPaneList.size() - 1) {
                applicationContext.setTextAreaBreakLineState(!breakLine);
            }
        }
    }

    /**
     * 显示行号
     *
     * @param
     */
    public void showLineNumActionPerformed() {
        LinkedList<JScrollPane> scrollPaneList = tabbedSplitEditor.getPages();
        for (int i = 0; i < scrollPaneList.size(); i++) {
            RTextScrollPane rTextScrollPane = (RTextScrollPane) scrollPaneList.get(i);
            boolean lineNumbersEnabled = rTextScrollPane.getLineNumbersEnabled();
            rTextScrollPane.setLineNumbersEnabled(!lineNumbersEnabled);
            if (i == scrollPaneList.size() - 1) {
                applicationContext.setTextAreaShowlineNumState(!lineNumbersEnabled);
            }
        }
    }

    /**
     * 是否显示工具栏
     *
     * @date 2023/12/9 21:40
     */
    public void showToolBarActionPerformed() {
        JToolBar toolBar = ToolBarBuilder.getInstance().getToolBar();
        boolean showToolBar = toolBar.isVisible();
        toolBar.setVisible(!showToolBar);
        // 菜单栏和工具按钮联动修改状态
        MenuBarBuilder.getInstance().getShowToolBarMenuItem().setSelected(!showToolBar);
        PopupMenuBuilder.getInstance().getToolBarShowState().setSelected(!showToolBar);
        applicationContext.setShowToolBarState(!showToolBar);
    }

    /**
     * 是否显示菜单栏
     *
     * @date 2023/12/9 21:40
     */
    public void showMenuBarActionPerformed() {
        JMenuBar menuBar = MenuBarBuilder.getInstance().getMenuBar();
        boolean showMenuBar = menuBar.isVisible();
        menuBar.setVisible(!showMenuBar);
        // 菜单栏和工具按钮联动修改状态
        MenuBarBuilder.getInstance().getShowMenuBarMenuItem().setSelected(!showMenuBar);
        PopupMenuBuilder.getInstance().getMenuBarShowState().setSelected(!showMenuBar);
        applicationContext.setShowMenuBarState(!showMenuBar);
    }

    /**
     * 中文转码按钮组事件
     *
     * @param chineseConverMenu
     */
    public void chineseConverActionPerformed(JMenu chineseConverMenu) {
        int chineseConverState = applicationContext.getChineseConverState();
        chineseConverEvent(chineseConverMenu, chineseConverState);
    }

    /**
     * 中文转码按钮组事件注册
     *
     * @param chineseConverMenu
     * @param chineseConverState
     */
    private void chineseConverEvent(JMenu chineseConverMenu, int chineseConverState) {
        for (Component menuComponent : chineseConverMenu.getMenuComponents()) {
            if (menuComponent instanceof CHToCNRadioButtonMenuItem) {
                CHToCNRadioButtonMenuItem chineseConverMenuItem = (CHToCNRadioButtonMenuItem) menuComponent;
                if (chineseConverMenuItem.getChineseConverState() == chineseConverState) {
                    chineseConverMenuItem.setSelected(true);
                }
                chineseConverMenuItem.addActionListener(e -> addChineseConverMenuActionListener(chineseConverMenuItem));
            }
        }
    }

    /**
     * 设置中文转码状态
     *
     * @param chineseConverMenuItem
     */
    private void addChineseConverMenuActionListener(CHToCNRadioButtonMenuItem chineseConverMenuItem) {
        int btnConverState = chineseConverMenuItem.getChineseConverState();
        LinkedList<JScrollPane> scrollPaneList = tabbedSplitEditor.getPages();
        for (int i = 0; i < scrollPaneList.size(); i++) {
            JSONRSyntaxTextArea rSyntaxTextArea = ComponentUtils.convertEditor(scrollPaneList.get(i));
            rSyntaxTextArea.setChineseConverState(btnConverState);
            if (i == scrollPaneList.size() - 1) {
                applicationContext.setChineseConverState(btnConverState);
            }
        }
    }

    /**
     * 设置界面语言事件注册
     *
     * @param languageMenu
     * @date 2023/12/16 22:13
     */
    public void setupLanguageActionPerformed(JFrame frame, JMenu languageMenu) {
        String language = applicationContext.getSystemLanguage();
        for (Component menuComponent : languageMenu.getMenuComponents()) {
            if (menuComponent instanceof LanguageRadioButtonMenuItem) {
                LanguageRadioButtonMenuItem languageRadioMenuitem = (LanguageRadioButtonMenuItem) menuComponent;
                if (language.equals(languageRadioMenuitem.getLanguageKey())) {
                    languageRadioMenuitem.setSelected(true);
                }
                languageRadioMenuitem.addActionListener(e -> {
                    applicationContext.setSystemLanguage(languageRadioMenuitem.getLanguageKey());
                    //resartWindowFrameActionPerformed(frame);
                    FlatLabel tipLabel = PanelView.getTipMessage();
                    tipLabel.setText("<html><span color=\"#107C41\" style=\"font-size:10px\">" + "语言切换成功，等待下次重启生效！" + "</span></html>");
                    Toast.info(frame, "语言切换成功，下次重启后生效！");
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
    public void pictureQualityActionPerformed(JMenu pictureQualityMenu) {
        int pictureQuality = applicationContext.getPictureQualityState();
        for (Component menuComponent : pictureQualityMenu.getMenuComponents()) {
            if (menuComponent instanceof PictureQualityRadioButtonMenuItem) {
                PictureQualityRadioButtonMenuItem pictureQualityMenuitem = (PictureQualityRadioButtonMenuItem) menuComponent;
                if (pictureQualityMenuitem.getPictureQualityState() == pictureQuality) {
                    pictureQualityMenuitem.setSelected(true);
                }
                pictureQualityMenuitem.addActionListener(e -> {
                    applicationContext.setPictureQualityState(pictureQualityMenuitem.getPictureQualityState());
                });
            }
        }
    }

    /**
     * 打开查找对话框
     */
    public void showFindDialogActionPerformed() {
        FindPanelBuilder.getLayout().showHideActionPerformed();
    }

    /**
     * 设置系统界面字体
     *
     * @param fontStyleMenu
     * @param fontSizeMenu
     */
    public void applyFrameFontActionPerformed(JMenu fontStyleMenu, JMenu fontSizeMenu) {
        //字体样式事件批量扫描注册
        Component[] fontStyleMenuComponents = fontStyleMenu.getMenuComponents();
        for (Component fontStyleMenuComponent : fontStyleMenuComponents) {
            if (fontStyleMenuComponent instanceof FontNameRadioButtonMenuItem) {
                FontNameRadioButtonMenuItem fontMenuItem = (FontNameRadioButtonMenuItem) fontStyleMenuComponent;
                if (fontMenuItem.getFontName().equals(applicationContext.getFontStyle().getName())) {
                    fontMenuItem.setSelected(true);
                }
                fontMenuItem.addActionListener(e -> {
                    //此事件，解决修改字体后，搜索框界面布局混乱问题
                    FindPanelBuilder.getLayout().hideFindPanelActionPerformed();
                    ChangeUIUtils.changeGlobalFont(new Font(fontMenuItem.getFontName(), Font.PLAIN, applicationContext.getFontStyle().getSize()));
                    applicationContext.getFontStyle().setName(fontMenuItem.getFontName());
                });
            }
        }
        //字体大小事件批量扫描注册
        Component[] fontSizeMenuComponents = fontSizeMenu.getMenuComponents();
        for (Component fontSizeMenuComponent : fontSizeMenuComponents) {
            if (fontSizeMenuComponent instanceof FontSizeRadioButtonMenuItem) {
                FontSizeRadioButtonMenuItem fontSizeMenuItem = (FontSizeRadioButtonMenuItem) fontSizeMenuComponent;
                if (fontSizeMenuItem.getFontSize() == applicationContext.getFontStyle().getSize()) {
                    fontSizeMenuItem.setSelected(true);
                }
                fontSizeMenuItem.addActionListener(e -> {
                    //此事件，解决修改字体后，搜索框界面布局混乱问题
                    FindPanelBuilder.getLayout().hideFindPanelActionPerformed();
                    ChangeUIUtils.changeGlobalFont(new Font(applicationContext.getFontStyle().getName(), Font.PLAIN, fontSizeMenuItem.getFontSize()));
                    applicationContext.getFontStyle().setSize(fontSizeMenuItem.getFontSize());
                });
            }
        }
    }

    /**
     * 新建可调整宽度的文本编辑器
     */
    public void addTabbedSplitEditorActionPerformed() {
        tabbedSplitEditor.addTab();
    }

    /**
     * 关闭可调整宽度的文本编辑器
     */
    public void closeTabbedSplitEditorActionPerformed(RTextArea syntaxTextArea) {
        tabbedSplitEditor.closeAbleTabbed(syntaxTextArea);
    }

    /**
     * 工具栏位置改变事件
     *
     * @param toolBar
     */
    public void toolBarLocationChangedEventListener(JToolBar toolBar) {
        toolBar.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("orientation")) {
                int orientation = (Integer) evt.getNewValue();
                String layoutLocation = LocationEnum.convertLayoutLocation(orientation);
                applicationContext.setToolBarLocation(layoutLocation);
                toolBar.setBorder(LocationEnum.convertToolBarBorder(layoutLocation));
                toolBar.repaint();
            }
        });
    }

    /**
     * 关闭窗口
     *
     * @param frame
     */
    public void closeWindowsFrameActionPerformed(JFrame frame) {
        int confirmed = JOptionPane.showConfirmDialog(frame,
                MessageConstant.SYSTEM_EXIT_TIP, MessageConstant.SYSTEM_CONFIRM_CLOSE_TIP,
                JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            // 屏幕尺寸大小保存
            if ((frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH) {
                // 窗口最大换状态不记录屏幕大小
            } else {
                applicationContext.setScreenSize(new ConfigurableApplicationContext.ScreenSize(frame.getWidth(), frame.getHeight()));
                // 退出应用时，保存所有配置项到本地
                FrameWindowCloseEventService.saveApplicationConfiguration();
            }
            logger.info("应用程序退出，界面销毁~~~");
            StateLabel statusLabel = LabelBarBuilder.getLabel(GlobalKEY.STATE_BAR_RUN_TIME);
            logger.info("程序运行时长：{}", statusLabel.getValue());
            frame.dispose();
            System.exit(0); // 退出程序
        }
    }

    /**
     * 官网跳转
     */
    public void linkWebSiteActionPerformed() {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(applicationContext.getWebSiteUrl()));
            } catch (IOException | URISyntaxException exception) {
                throw new RuntimeException("官网跳转失败: " + exception.getMessage());
            }
        }
    }

    /**
     * 显示/隐藏工具栏文本
     *
     * @param frame
     */
    public void showToolBarTextActionPerformed(JFrame frame) {
        applicationContext.setShowToolBarText(!applicationContext.getShowToolBarText());
        ToolBarBuilder toolBarBuilder = ToolBarBuilder.getInstance();
        toolBarBuilder.refreshToolBar(frame);
    }

    /**
     * 系统重启
     */
    /*public void resartWindowFrameActionPerformed(JFrame frame) {
        frame.dispose();
        frame.getContentPane().removeAll(); // 移除所有组件
        frame.revalidate(); // 重新验证布局
        frame.repaint(); // 重绘界面
        applicationContext.setScreenSize(new ConfigurableApplicationContext.ScreenSize(frame.getWidth(), frame.getHeight()));
        // 退出应用时，保存所有配置项到本地
        FrameWindowCloseEventService.saveApplicationConfiguration();
        SwingUtilities.invokeLater(() -> {
            try {
                int num = 5;
                while (num-- > 0) {
                    System.out.println("正在重启中~~~" + num);
                    Thread.sleep(1000);
                }
                ((MainFrame) Window.getWindows()[0]).createAndShowGUI(MessageConstant.SYSTEM_TITLE + SystemConstant.SYSTEM_VERSION);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }*/
    public void resartWindowFrameActionPerformed(JFrame frame) {
        frame.dispose();
        frame.getContentPane().removeAll(); // 移除所有组件
        frame.revalidate(); // 重新验证布局
        frame.repaint(); // 重绘界面
        applicationContext.setScreenSize(new ConfigurableApplicationContext.ScreenSize(frame.getWidth(), frame.getHeight()));
        // 退出应用时，保存所有配置项到本地
        FrameWindowCloseEventService.saveApplicationConfiguration();
        ScheduledExecutorService schedulerExecutor = Executors.newScheduledThreadPool(2);
        Callable callable = () -> {
            String jarPath = AppMain.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String cmd = "cmd /c start /b java -jar " + jarPath + "\\ViewJSON.jar";
            System.out.println("当前可执行jar的路径：" + cmd);
            Process p = Runtime.getRuntime().exec(cmd);
            return p;
        };
        FutureTask futureTask = new FutureTask(callable);
        schedulerExecutor.submit(futureTask);
        System.exit(0);
    }

}
