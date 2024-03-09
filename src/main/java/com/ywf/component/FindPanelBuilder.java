package com.ywf.component;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.formdev.flatlaf.extras.components.FlatTextField;
import com.ywf.framework.base.BorderBuilder;
import com.ywf.framework.base.SvgIconFactory;
import com.ywf.framework.base.ThemeColor;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.constant.MessageConstant;
import com.ywf.framework.layout.FindPanelLayout;
import com.ywf.framework.utils.ObjectUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 查找对话框绘制
 *
 * @Author YWF
 * @Date 2023/12/30 22:39
 */
public class FindPanelBuilder {

    private static FindPanelLayout layout;
    private static JLabel btnClose;
    private static FlatTextField fieldFind;
    private static FlatLabel searchResultLabel;
    private static JTabbedSplitEditor tabbedSplitEditor;

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
        JToggleButton matchCaseButton = new JToggleButton(SvgIconFactory.mediumIcon(SvgIconFactory.FindIcon.matchCase));
        matchCaseButton.setRolloverIcon(SvgIconFactory.mediumIcon(SvgIconFactory.FindIcon.matchCaseHovered));
        matchCaseButton.setSelectedIcon(SvgIconFactory.mediumIcon(SvgIconFactory.FindIcon.matchCaseSelected));
        matchCaseButton.setToolTipText("区分大小写");
        // regex button
        JToggleButton regexButton = new JToggleButton(SvgIconFactory.largeIcon(SvgIconFactory.FindIcon.regex));
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
        JSONRSyntaxTextArea syntaxTextArea = tabbedSplitEditor.getFocusEditor();
        startSegmentFindOrReplaceOperation(syntaxTextArea, fieldFind.getText(), true, true, false);
    }

    /**
     * 查找上一个
     */
    private static void prevFindActionPerformed() {
        JSONRSyntaxTextArea syntaxTextArea = tabbedSplitEditor.getFocusEditor();
        startSegmentFindOrReplaceOperation(syntaxTextArea, fieldFind.getText(), true, false, false);
    }

    /**
     * 文本内容查找定位
     *
     * @param key        要查找的字符串
     * @param ignoreCase 是否区分大小写
     * @param down       查找方向（向上false，向下true）
     * @param isFirst    是否从开头开始查找
     * @return
     */
    public static boolean startSegmentFindOrReplaceOperation(JTextArea textArea, String key, boolean ignoreCase, boolean down, boolean isFirst) {
        int length = key.length();
        Document doc = textArea.getDocument();
        int offset = textArea.getCaretPosition();
        int charsLeft = doc.getLength() - offset;
        if (charsLeft <= 0) {
            offset = 0;
            charsLeft = doc.getLength() - offset;
        }
        if (!down) {
            offset -= length;
            offset--;
            charsLeft = offset;
        }
        if (isFirst) {
            offset = 0;
            charsLeft = doc.getLength() - offset;
        }
        Segment text = new Segment();
        text.setPartialReturn(true);
        try {
            while (charsLeft > 0) {
                doc.getText(offset, length, text);
                if ((ignoreCase == true && text.toString().equalsIgnoreCase(key))
                        || (ignoreCase == false && text.toString().equals(key))) {
                    //textArea.requestFocus();////焦点,才能能看到效果
                    textArea.setForeground(ThemeColor.findSelectColor);
                    textArea.setSelectionStart(offset);
                    textArea.setSelectionEnd(offset + length);
                    return true;
                }
                charsLeft--;
                if (down) {
                    offset++;
                } else {
                    offset--;
                }
            }
        } catch (Exception e) {

        }
        return false;
    }

    public static FindPanelLayout getLayout() {
        tabbedSplitEditor = ObjectUtils.getBean(GlobalKEY.TABBED_SPLIT_EDITOR);
        return layout;
    }

    private static final Highlighter.HighlightPainter HIGHLIGHT = new DefaultHighlighter.DefaultHighlightPainter(ThemeColor.highLightColor);
    private static boolean isHighlight = false;


    /**
     * 查找内容高亮显示
     *
     * @date 2024/1/2 23:03
     */
    private static void setTextAreaContentHighlight() {
        SwingUtilities.invokeLater(() -> {
            JSONRSyntaxTextArea syntaxTextArea = tabbedSplitEditor.getFocusEditor();
            if (!isHighlight) {
                int result = setHighlight(syntaxTextArea, fieldFind.getText());
                searchResultLabel.setText(updateSearchResultCounts(result));
                syntaxTextArea.repaint();
                isHighlight = true;
            } else {
                syntaxTextArea.getHighlighter().removeAllHighlights();
                searchResultLabel.setText(updateSearchResultCounts(0));
                syntaxTextArea.repaint();
                isHighlight = false;
            }
        });
    }

    private static int setHighlight(JTextComponent jtc, String pattern) {
        Highlighter highlighter = jtc.getHighlighter();
        highlighter.removeAllHighlights();
        Document doc = jtc.getDocument();
        int counts = 0;
        try {
            String text = doc.getText(0, doc.getLength());
            Matcher matcher = Pattern.compile(pattern).matcher(text);
            int pos = 0;
            while (matcher.find(pos) && !matcher.group().isEmpty()) {
                int start = matcher.start();
                int end = matcher.end();
                highlighter.addHighlight(start, end, HIGHLIGHT);
                pos = end;
                counts++;
            }
        } catch (BadLocationException | PatternSyntaxException ex) {
            UIManager.getLookAndFeel().provideErrorFeedback(jtc);
        }
        return counts;
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
            setTextAreaContentHighlight();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            setTextAreaContentHighlight();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    }


}
