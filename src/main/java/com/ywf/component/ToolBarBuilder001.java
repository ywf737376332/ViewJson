package com.ywf.component;

import com.ywf.action.MenuEventService;
import com.ywf.action.QRCodeEventService;
import com.ywf.framework.annotation.Autowired;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicToolBarUI;
import java.awt.*;

/**
 * 工具条组件
 *
 * @Author YWF
 * @Date 2023/11/27 22:11
 */
public class ToolBarBuilder001 {

    @Autowired
    public static ConfigurableApplicationContext applicationContext;

    private static JToolBar toolBar;
    private static JButton btnFormat;
    private static JButton btnComp;
    private static JButton btnEscape;
    private static JButton btnUnescape;
    private static JButton btnCopy;
    private static JButton btnCopyPict;
    private static JButton btnShowQrcode;
    private static JButton btnFindRepl;
    private static JButton btnClean;

    public static JToolBar createToolBar(JFrame frame) {
        toolBar = new JToolBar("工具栏");
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
        btnFormat.addActionListener(e -> MenuEventService.getInstance().formatJsonActionPerformed(frame));
        btnComp = new JButton("压 缩");
        btnComp.addActionListener(e -> MenuEventService.getInstance().compressionJsonActionPerformed());
        btnEscape = new JButton("转 义");
        btnEscape.addActionListener(e -> MenuEventService.getInstance().escapeJsonActionPerformed());
        btnUnescape = new JButton("去除转义");
        btnUnescape.addActionListener(e -> MenuEventService.getInstance().unEscapeJsonActionPerformed());
        btnCopy = new JButton("复制结果");
        btnCopy.addActionListener(e -> MenuEventService.getInstance().copyJsonActionPerformed(frame));
        btnCopyPict = new JButton("复制图片");
        btnCopyPict.addActionListener(e -> MenuEventService.getInstance().copyJsonToPictActionPerformed(frame));
        btnShowQrcode = new JButton("分享");
        btnShowQrcode.addActionListener(e -> QRCodeEventService.getInstance().showQrcodeActionPerformed(frame));
        btnFindRepl = new JButton("查找");
        btnFindRepl.addActionListener(e -> MenuEventService.getInstance().showFindDialogActionPerformed());
        btnClean = new JButton("清空");
        btnClean.addActionListener(e -> MenuEventService.getInstance().cleanJsonActionPerformed());

        //JButton btnFullScreen = new JButton("全屏");
        //btnFullScreen.addActionListener(e -> FullScreenEventService.getInstance().fullScreenActionPerformed(frame,btnFullScreen));
        //btnFullScreen.setIcon(IconUtils.getSVGIcon("icons/fullScreen.svg"));

        btnFormat.setIcon(IconUtils.getSVGIcon("icons/formatCode.svg"));
        btnComp.setIcon(IconUtils.getSVGIcon("icons/comp.svg"));
        btnEscape.setIcon(IconUtils.getSVGIcon("icons/escapeCode.svg"));
        btnUnescape.setIcon(IconUtils.getSVGIcon("icons/unEscapeCode.svg"));
        btnCopy.setIcon(IconUtils.getSVGIcon("icons/copyContent.svg"));
        btnCopyPict.setIcon(IconUtils.getSVGIcon("icons/sharePict.svg"));
        btnShowQrcode.setIcon(IconUtils.getSVGIcon("icons/shareQrcode.svg"));
        btnFindRepl.setIcon(IconUtils.getSVGIcon("icons/find.svg"));
        btnClean.setIcon(IconUtils.getSVGIcon("icons/delete.svg"));
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

        toolBar.add(btnCopyPict);
        toolBar.addSeparator();

        toolBar.add(btnShowQrcode);
        toolBar.addSeparator();

        toolBar.add(btnFindRepl);
        // 将默认大小的分隔符添加到工具栏的末尾
        toolBar.addSeparator();
        toolBar.add(btnClean);
        //toolBar.addSeparator();
        //toolBar.add(btnFullScreen);
        toolBar.setVisible(applicationContext.getShowToolBarState());
        return toolBar;
    }

    public static JToolBar getToolBar() {
        return toolBar;
    }

    public static void setToolBar(JToolBar toolBar) {
        ToolBarBuilder001.toolBar = toolBar;
    }

    public static JButton getBtnFormat() {
        return btnFormat;
    }

    public static void setBtnFormat(JButton btnFormat) {
        ToolBarBuilder001.btnFormat = btnFormat;
    }

    public static JButton getBtnComp() {
        return btnComp;
    }

    public static void setBtnComp(JButton btnComp) {
        ToolBarBuilder001.btnComp = btnComp;
    }

    public static JButton getBtnEscape() {
        return btnEscape;
    }

    public static void setBtnEscape(JButton btnEscape) {
        ToolBarBuilder001.btnEscape = btnEscape;
    }

    public static JButton getBtnUnescape() {
        return btnUnescape;
    }

    public static void setBtnUnescape(JButton btnUnescape) {
        ToolBarBuilder001.btnUnescape = btnUnescape;
    }

    public static JButton getBtnCopy() {
        return btnCopy;
    }

    public static void setBtnCopy(JButton btnCopy) {
        ToolBarBuilder001.btnCopy = btnCopy;
    }

    public static JButton getBtnCopyPict() {
        return btnCopyPict;
    }

    public static void setBtnCopyPict(JButton btnCopyPict) {
        ToolBarBuilder001.btnCopyPict = btnCopyPict;
    }

    public static JButton getBtnShowQrcode() {
        return btnShowQrcode;
    }

    public static void setBtnShowQrcode(JButton btnShowQrcode) {
        ToolBarBuilder001.btnShowQrcode = btnShowQrcode;
    }

    public static JButton getBtnFindRepl() {
        return btnFindRepl;
    }

    public static void setBtnFindRepl(JButton btnFindRepl) {
        ToolBarBuilder001.btnFindRepl = btnFindRepl;
    }

    public static JButton getBtnClean() {
        return btnClean;
    }

    public static void setBtnClean(JButton btnClean) {
        ToolBarBuilder001.btnClean = btnClean;
    }
}