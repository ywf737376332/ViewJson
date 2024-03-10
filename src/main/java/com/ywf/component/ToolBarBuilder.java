package com.ywf.component;

import com.ywf.action.MenuEventService;
import com.ywf.action.ResourceBundleService;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.config.MenuAction;
import com.ywf.framework.config.MenuBarKit;
import com.ywf.framework.enums.LocationEnum;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import com.ywf.framework.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.plaf.basic.BasicToolBarUI;
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
        JToolBar tool = toolBar != null ? toolBar : new JToolBar();
        tool.setUI(new BasicToolBarUI());
        // 需要绘制边框
        tool.setBorderPainted(true);
        // 可移动工具栏
        tool.setFloatable(true);
        // 鼠标悬停时高亮显示按钮
        tool.setOrientation(LocationEnum.convertToolbarLocation(applicationContext.getToolBarLocation()));
        tool.setRollover(true);
        tool.setBorder(LocationEnum.convertToolBarBorder(applicationContext.getToolBarLocation())); // 设置边框颜色和宽度
        tool.setVisible(applicationContext.getShowToolBarState());
        // 工具条位置监听
        MenuEventService.getInstance().toolBarLocationChangedEventListener(tool);
        return tool;
    }

    protected JButton createButton(Action a) {
        JButton button = new JButton(a);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setBorderPainted(false); // 去掉边框
        button.setFocusPainted(false); // 去掉焦点边框
        return button;
    }

    /**
     * 菜单事件初始化
     */
    private void createButtonActions() {
        MenuAction.IconSize iconSize = applicationContext.getShowToolBarText() == true ? MenuAction.IconSize.medium : MenuAction.IconSize.large;
        ResourceBundle msg = resourceBundle;
        boolean showText = applicationContext.getShowToolBarText();
        formatAction = new MenuBarKit.FormatAction();
        formatAction.setProperties(msg, "MenuItem.Format", iconSize, showText);
        compressAction = new MenuBarKit.CompressAction();
        compressAction.setProperties(msg, "MenuItem.Compress", iconSize, showText);
        escapeAction = new MenuBarKit.EscapeAction();
        escapeAction.setProperties(msg, "MenuItem.Escape", iconSize, showText);
        unescapeAction = new MenuBarKit.UnescapeAction();
        unescapeAction.setProperties(msg, "MenuItem.Unescape", iconSize, showText);

        copyCodeAction = new MenuBarKit.CopyCodeAction();
        copyCodeAction.setProperties(msg, "MenuItem.CopyCode", iconSize, showText);
        copyPictAction = new MenuBarKit.CopyPictAction();
        copyPictAction.setProperties(msg, "MenuItem.CopyPict", iconSize, showText);
        showQrcodeAction = new MenuBarKit.ShowQrcodeAction();
        showQrcodeAction.setProperties(msg, "MenuItem.ShowQrcode", iconSize, showText);

        findAction = new MenuBarKit.FindAction();
        findAction.setProperties(msg, "MenuItem.Find", iconSize, showText);
        cleanAction = new MenuBarKit.CleanAction();
        cleanAction.setProperties(msg, "MenuItem.Clean", iconSize, showText);
    }

    /**
     * 刷新工具条
     *
     * @param frame
     */
    public void refreshToolBar(JFrame frame) {
        JFrame mainFrame = ObjectUtils.getBean(GlobalKEY.MAIN_FRAME);
        mainFrame.remove(toolBar);
        toolBar.removeAll();
        createButtonActions();
        mainFrame.add(createToolBar(frame), applicationContext.getToolBarLocation());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public JToolBar getToolBar() {
        return toolBar;
    }
}
