package com.ywf.component;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicMenuBarUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/11/30 16:07
 */
public class MenuBarBuilder{

    public static JMenuBar createMenuBar(){

        JMenuBar menuBar = new JMenuBar();
        menuBar.setUI(new BasicMenuBarUI());
        JMenu fileMenu = new JMenu("文件");
        JMenuItem newTabMenuItem = new JMenuItem("新建");
        JMenuItem newFrameMenuItem = new JMenuItem("新建窗口");
        JMenuItem copyMenuItem = new JMenuItem("复制内容");
        copyMenuItem.setIcon(IconUtils.getSVGIcon("icons/copyCode.svg"));
        JMenuItem savePictMenuItem = new JMenuItem("导出图片");
        savePictMenuItem.setIcon(IconUtils.getSVGIcon("icons/cutPict.svg"));
        JMenuItem saveFileMenuItem = new JMenuItem("导出文件");
        saveFileMenuItem.setIcon(IconUtils.getSVGIcon("icons/saveCode.svg"));
        fileMenu.add(newTabMenuItem);
        fileMenu.add(newFrameMenuItem);
        fileMenu.add(copyMenuItem);
        fileMenu.add(savePictMenuItem);
        fileMenu.add(saveFileMenuItem);

        JMenu editMenu = new JMenu("编辑");
        JMenuItem compMenuItem = new JMenuItem("压缩");
        compMenuItem.setIcon(IconUtils.getSVGIcon("icons/Layers.svg"));
        JMenuItem escapeTabMenuItem = new JMenuItem("转义");
        escapeTabMenuItem.setIcon(IconUtils.getSVGIcon("icons/escapeCode.svg"));
        JMenuItem unescapeMenuItem = new JMenuItem("去除转义");
        unescapeMenuItem.setIcon(IconUtils.getSVGIcon("icons/unEscapeCode.svg"));
        JMenuItem formatMenuItem = new JMenuItem("格式化");
        formatMenuItem.setIcon(IconUtils.getSVGIcon("icons/formatCode.svg"));
        JMenuItem cleanMenuItem = new JMenuItem("清空");
        cleanMenuItem.setIcon(IconUtils.getSVGIcon("icons/Basket.svg"));
        JMenuItem findRepMenuItem = new JMenuItem("查找替换");
        findRepMenuItem.setIcon(IconUtils.getSVGIcon("icons/findCode.svg"));
        editMenu.add(compMenuItem);
        editMenu.add(escapeTabMenuItem);
        editMenu.add(unescapeMenuItem);
        editMenu.add(formatMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(cleanMenuItem);
        editMenu.add(findRepMenuItem);


        JMenu setupMenu = new JMenu("设置");
        JCheckBoxMenuItem editSetupMenuItem = new JCheckBoxMenuItem("是否可编辑");
        JCheckBoxMenuItem lineSetupMenuItem = new JCheckBoxMenuItem("是否换行");
        JCheckBoxMenuItem replaceSpaceMenuItem = new JCheckBoxMenuItem("是否去除空格");
        setupMenu.add(editSetupMenuItem);
        setupMenu.add(lineSetupMenuItem);
        setupMenu.add(replaceSpaceMenuItem);

        JMenu themesMenu = new JMenu("主题");
        JMenuItem lightThemesMenuItem = new JMenuItem("FlatLaf Light");
        JMenuItem darkThemesMenuItem = new JMenuItem("FlatLaf Dark");
        JMenuItem intelliJThemesMenuItem = new JMenuItem("FlatLaf IntelliJ");
        JMenuItem darculaThemesMenuItem = new JMenuItem("FlatLaf Darcula");
        themesMenu.add(lightThemesMenuItem);
        themesMenu.add(darkThemesMenuItem);
        themesMenu.add(intelliJThemesMenuItem);
        themesMenu.add(darculaThemesMenuItem);

        JMenu helpMenu = new JMenu("帮助");
        JMenuItem updateVersionLogMenuItem = new JMenuItem("更新日志");
        JMenuItem privacyPolicyMenuItem = new JMenuItem("隐私条款");
        JMenuItem officialWebsiteMenuItem = new JMenuItem("官方网站");
        JMenuItem expressThanksMenuItem = new JMenuItem("鸣谢");
        JMenuItem feedbackMenuItem = new JMenuItem("反馈");
        JMenuItem checkUodateMenuItem = new JMenuItem("检查更新");
        JMenuItem aboutMenuItem = new JMenuItem("关于");
        aboutMenuItem.addActionListener(e -> aboutActionPerformed());
        helpMenu.add(updateVersionLogMenuItem);
        helpMenu.add(privacyPolicyMenuItem);
        helpMenu.add(officialWebsiteMenuItem);
        helpMenu.add(expressThanksMenuItem);
        helpMenu.add(feedbackMenuItem);
        helpMenu.add(checkUodateMenuItem);
        helpMenu.add(aboutMenuItem);

        JMenu viewMenu = new JMenu("Beat");
        JRadioButtonMenuItem radioButtonMenuItem1 = new JRadioButtonMenuItem("单选一");
        radioButtonMenuItem1.setSelected(true);
        radioButtonMenuItem1.setMnemonic('D');
        JRadioButtonMenuItem radioButtonMenuItem2 = new JRadioButtonMenuItem("单选二");
        JRadioButtonMenuItem radioButtonMenuItem3 = new JRadioButtonMenuItem("单选三");
        // 组装为单选
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(radioButtonMenuItem1);
        buttonGroup1.add(radioButtonMenuItem2);
        buttonGroup1.add(radioButtonMenuItem3);

        JMenuItem htmlMenuItem = new JMenuItem("<html>some <b color=\"red\">HTML</b> <i color=\"blue\">text</i></html>");
        htmlMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        htmlMenuItem.setMnemonic('O');
        htmlMenuItem.setIcon( new FlatSVGIcon("icons/menu-cut.svg") );
        JCheckBoxMenuItem checkBoxMenuItem1 = new JCheckBoxMenuItem("复选A");
        JCheckBoxMenuItem checkBoxMenuItem2 = new JCheckBoxMenuItem("复选B");

        //添加组件
        viewMenu.add(radioButtonMenuItem1);
        viewMenu.add(radioButtonMenuItem2);
        viewMenu.add(radioButtonMenuItem3);
        viewMenu.add(htmlMenuItem);
        viewMenu.add(checkBoxMenuItem1);
        viewMenu.add(checkBoxMenuItem2);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(setupMenu);
        menuBar.add(themesMenu);
        menuBar.add(helpMenu);
        menuBar.add(viewMenu);

        return menuBar;
    }



    private static void aboutActionPerformed() {
        JLabel titleLabel = new JLabel( "FlatLaf Demo" );
        titleLabel.putClientProperty( FlatClientProperties.STYLE_CLASS, "h1" );

        String link = "https://www.formdev.com/flatlaf/";
        JLabel linkLabel = new JLabel( "<html><a href=\"#\">" + link + "</a></html>" );
        linkLabel.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
        linkLabel.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                try {
                    Desktop.getDesktop().browse( new URI( link ) );
                } catch( IOException | URISyntaxException ex ) {
                    JOptionPane.showMessageDialog( linkLabel,
                            "Failed to open '" + link + "' in browser.",
                            "About", JOptionPane.PLAIN_MESSAGE );
                }
            }
        } );


        JOptionPane.showMessageDialog( null,
                new Object[] {
                        titleLabel,
                        "Demonstrates FlatLaf Swing look and feel",
                        " ",
                        "Copyright 2019-" + Year.now() + " FormDev Software GmbH",
                        linkLabel,
                },
                "About", JOptionPane.PLAIN_MESSAGE );
    }



}
