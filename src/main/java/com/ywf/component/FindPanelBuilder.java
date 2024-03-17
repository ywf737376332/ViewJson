package com.ywf.component;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.formdev.flatlaf.extras.components.FlatTextField;
import com.ywf.component.toast.Toast;
import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.SvgIconFactory;
import com.ywf.framework.base.ThemeColor;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.constant.MessageConstant;
import com.ywf.framework.layout.FindPanelLayout;
import com.ywf.framework.utils.ObjectUtils;
import com.ywf.framework.utils.WindowUtils;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;
import org.fife.ui.rtextarea.SearchResult;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 查找对话框绘制
 *
 * @Author YWF
 * @Date 2023/12/30 22:39
 */
public final class FindPanelBuilder {

    private static FindPanelLayout layout;
    private static JLabel btnClose;
    private static FlatTextField fieldFind;
    private static FlatLabel searchResultLabel;
    private static JTabbedSplitEditor tabbedSplitEditor;
    private static JToggleButton matchCaseButton;
    private static JToggleButton regexButton;

    private FindPanelBuilder() {
    }

    public static JPanel createFindPanel() {
        JPanel rootFindPanel = new JPanel();
        layout = new FindPanelLayout(rootFindPanel, 0, 5);
        rootFindPanel.setLayout(layout);
        rootFindPanel.setBorder(BorderBuilder.topBorder(1, ThemeColor.themeColor));

        JPanel findPanel = new JPanel(new BorderLayout());
        findPanel.setBorder(BorderBuilder.leftEmptyBorder(10)); // 设置左边框为10像素的空白边框
        JLabel findLabel = new JLabel(MessageConstant.SYSTEM_FIND, SvgIconFactory.largeIcon(SvgIconFactory.FindIcon.find), SwingConstants.RIGHT);
        findPanel.add(findLabel, BorderLayout.WEST);
        fieldFind = new FlatTextField();
        fieldFind.setPlaceholderText(MessageConstant.SYSTEM_FIND_CONTENT_TIP);
        fieldFind.setPadding(new Insets(0, 5, 0, 5));
        fieldFind.setBorder(BorderBuilder.leftAndRightBorder(1, ThemeColor.themeColor));
        fieldFind.setShowClearButton(true);
        fieldFind.getDocument().addDocumentListener(new HighlightDocumentListener());

        // search toolbar
        JToolBar searchToolbar = new JToolBar();
        matchCaseButton = new JToggleButton(SvgIconFactory.mediumIcon(SvgIconFactory.FindIcon.matchCase));
        matchCaseButton.setRolloverIcon(SvgIconFactory.mediumIcon(SvgIconFactory.FindIcon.matchCaseHovered));
        matchCaseButton.setSelectedIcon(SvgIconFactory.mediumIcon(SvgIconFactory.FindIcon.matchCaseSelected));
        matchCaseButton.setToolTipText("区分大小写");
        // regex button
        regexButton = new JToggleButton(SvgIconFactory.largeIcon(SvgIconFactory.FindIcon.regex));
        regexButton.setRolloverIcon(SvgIconFactory.mediumIcon(SvgIconFactory.FindIcon.regexHovered));
        regexButton.setSelectedIcon(SvgIconFactory.mediumIcon(SvgIconFactory.FindIcon.regexSelected));
        regexButton.setToolTipText("正则表达式");
        searchToolbar.addSeparator();
        searchToolbar.add(matchCaseButton);
        searchToolbar.add(regexButton);
        fieldFind.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, searchToolbar);
        findPanel.add(fieldFind, BorderLayout.CENTER);

        JPanel findBtnPanel = new JPanel(new FlowLayout(5, 5, FlowLayout.RIGHT));
        JButton buttonNext = new JButton(MessageConstant.SYSTEM_FIND_NEXT);
        buttonNext.addActionListener(e -> nextFindActionPerformed());
        JButton buttonUp = new JButton(MessageConstant.SYSTEM_FIND_PREVIOUS);
        buttonUp.addActionListener(e -> prevFindActionPerformed());
        searchResultLabel = new FlatLabel();
        searchResultLabel.setText(updateSearchResultCounts(0));
        searchResultLabel.setBorder(BorderBuilder.leftEmptyBorder(10));
        findBtnPanel.add(buttonNext);
        findBtnPanel.add(buttonUp);
        findBtnPanel.add(searchResultLabel);

        JPanel findRight = new JPanel(new BorderLayout());
        findRight.setPreferredSize(new Dimension(100, 20));
        //JCheckBox checkBox = new JCheckBox("替换");
        //findRight.add(checkBox, BorderLayout.EAST);
        findBtnPanel.add(findRight);

        btnClose = new JLabel(SvgIconFactory.largeIcon(SvgIconFactory.FindIcon.close));
        btnClose.setBorder(BorderBuilder.leftAndRightEmptyBorder(7)); // 设置边框为7像素的空白边框
        btnClose.setBounds(535, 5, 25, 25);
        btnClose.addMouseListener(new ClosePopupMouseListener());
        findRight.add(btnClose, BorderLayout.EAST);
        rootFindPanel.add(findPanel, BorderLayout.CENTER);
        rootFindPanel.add(findBtnPanel, BorderLayout.EAST);
        return rootFindPanel;
    }

    /**
     * 关闭事件
     */
    static class ClosePopupMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            layout.showHideActionPerformed();
            fieldFind.setText("");
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            btnClose.setIcon(SvgIconFactory.largeIcon(SvgIconFactory.FindIcon.closeHovered));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            btnClose.setIcon(SvgIconFactory.largeIcon(SvgIconFactory.FindIcon.close));
        }
    }

    /**
     * 查找下一个
     */
    private static void nextFindActionPerformed() {
        findContentHighlightAction(true);
    }

    /**
     * 查找上一个
     */
    private static void prevFindActionPerformed() {
        findContentHighlightAction(false);
    }

    public static FindPanelLayout getLayout() {
        tabbedSplitEditor = ObjectUtils.getBean(GlobalKEY.TABBED_SPLIT_EDITOR);
        return layout;
    }

    /**
     * 搜索结果状态栏显示文本
     */
    private static String updateSearchResultCounts(int searchResultCount) {
        return "<html><span color=\"#849BD9\">" + searchResultCount + MessageConstant.SYSTEM_FIND_RESULTS_TIP + "</span>";
    }

    /**
     * 文本框内容监听
     */
    static class HighlightDocumentListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            findAllContentHighlight();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            findAllContentHighlight();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            findAllContentHighlight();
        }
    }

    public static void findAllContentHighlight() {
        SwingUtilities.invokeLater(() -> {
            String text = fieldFind.getText();
            // 更新状态栏显示文本
            searchResultLabel.setText(updateSearchResultCounts(findContentHighlight(text, false).getMarkedCount()));
        });
    }

    private static void findContentHighlightAction(boolean isNext) {
        String text = fieldFind.getText();
        if (text.length() == 0) {
            return;
        }
        boolean found = findContentHighlight(text, isNext).wasFound();
        if (!found) {
            Toast.info(WindowUtils.getFrame(), isNext ? "已搜索到最底部，没有更多内容被找到!" : "已搜索到最顶部，没有更多内容被找到");
        }
    }

    private static SearchResult findContentHighlight(String keyWord, boolean isNext) {
        if (tabbedSplitEditor == null) {
            return new SearchResult();
        }
        JSONRSyntaxTextArea syntaxTextArea = tabbedSplitEditor.getFocusEditor();
        SearchContext context = new SearchContext();
        context.setSearchFor(keyWord);
        context.setMatchCase(matchCaseButton.isSelected());
        context.setRegularExpression(regexButton.isSelected());
        context.setSearchForward(isNext);
        context.setWholeWord(false);
        return SearchEngine.find(syntaxTextArea, context);
    }

}
