package com.ywf.component;

import com.ywf.action.ResourceBundleService;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.config.MenuAction;
import com.ywf.framework.config.MenuBarKit;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.plaf.basic.BasicToolBarUI;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * 工具条组件
 *
 * @Author YWF
 * @Date 2023/11/27 22:11
 */
public class ToolBarBuilder {

    private final static Logger logger = LoggerFactory.getLogger(ToolBarBuilder.class);

    @Autowired
    public static ConfigurableApplicationContext applicationContext;
    private static ResourceBundle resourceBundle;
    private static final String MSG = "Message";

    private JToolBar toolBar;
    private JButton btnFormat, btnCompress, btnEscape, btnUnescape, btnCopyCode, btnCopyPict, btnShowQrcode, btnFind, btnClean;
    private MenuAction formatAction, compressAction, escapeAction, unescapeAction, copyCodeAction, copyPictAction, showQrcodeAction, findAction, cleanAction;


    volatile private static ToolBarBuilder instance = null;

    private ToolBarBuilder() {
        init();
    }

    public static ToolBarBuilder getInstance() {
        if (instance == null) {
            synchronized (ToolBarBuilder.class) {
                if (instance == null) {
                    instance = new ToolBarBuilder();
                }
            }
        }
        return instance;
    }

    private void init() {
        resourceBundle = ResourceBundleService.getInstance().getResourceBundle();
        createButtonActions();
    }

    public JToolBar createToolBar(JFrame frame) {
        toolBar = initToolBar();
        toolBar.add(btnFormat = createButton(formatAction));
        toolBar.addSeparator();
        toolBar.add(btnCompress = createButton(compressAction));
        toolBar.addSeparator();
        toolBar.add(btnEscape = createButton(escapeAction));
        toolBar.addSeparator();
        toolBar.add(btnUnescape = createButton(unescapeAction));
        toolBar.addSeparator();
        toolBar.add(btnCopyCode = createButton(copyCodeAction));
        toolBar.addSeparator();
        toolBar.add(btnCopyPict = createButton(copyPictAction));
        toolBar.addSeparator();
        toolBar.add(btnShowQrcode = createButton(showQrcodeAction));
        toolBar.addSeparator();
        toolBar.add(btnFind = createButton(findAction));
        toolBar.addSeparator();
        toolBar.add(btnClean = createButton(cleanAction));
        return toolBar;
    }

    private JToolBar initToolBar() {
        ResourceBundle msg = resourceBundle;
        JToolBar tool = new JToolBar(getMessage(msg, "ToolBar"));
        tool.setUI(new BasicToolBarUI());
        // 需要绘制边框
        tool.setBorderPainted(true);
        // 可移动工具栏
        tool.setFloatable(true);
        // 鼠标悬停时高亮显示按钮
        tool.setRollover(true);
        tool.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(130, 128, 128, 130))); // 设置边框颜色和宽度
        tool.setMargin(new Insets(2, 10, 2, 10));
        tool.setVisible(applicationContext.getShowToolBarState());
        return tool;
    }

    protected JButton createButton(Action a) {
        return new JButton(a);
    }

    /**
     * 菜单事件初始化
     */
    private void createButtonActions() {
        ResourceBundle msg = resourceBundle;
        formatAction = new MenuBarKit.FormatAction();
        formatAction.setProperties(msg, "MenuItem.Format", MenuAction.IconSize.medium);
        compressAction = new MenuBarKit.CompressAction();
        compressAction.setProperties(msg, "MenuItem.Compress", MenuAction.IconSize.medium);
        escapeAction = new MenuBarKit.EscapeAction();
        escapeAction.setProperties(msg, "MenuItem.Escape", MenuAction.IconSize.medium);
        unescapeAction = new MenuBarKit.UnescapeAction();
        unescapeAction.setProperties(msg, "MenuItem.Unescape", MenuAction.IconSize.medium);

        copyCodeAction = new MenuBarKit.CopyCodeAction();
        copyCodeAction.setProperties(msg, "MenuItem.CopyCode", MenuAction.IconSize.medium);
        copyPictAction = new MenuBarKit.CopyPictAction();
        copyPictAction.setProperties(msg, "MenuItem.CopyPict", MenuAction.IconSize.medium);
        showQrcodeAction = new MenuBarKit.ShowQrcodeAction();
        showQrcodeAction.setProperties(msg, "MenuItem.ShowQrcode", MenuAction.IconSize.medium);

        findAction = new MenuBarKit.FindAction();
        findAction.setProperties(msg, "MenuItem.Find", MenuAction.IconSize.medium);
        cleanAction = new MenuBarKit.CleanAction();
        cleanAction.setProperties(msg, "MenuItem.Clean", MenuAction.IconSize.medium);
    }

    private String getMessage(ResourceBundle msg, String keyRoot) {
        return msg.getString(keyRoot + ".Name");
    }

    public JToolBar getToolBar() {
        return toolBar;
    }
}
