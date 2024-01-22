package com.ywf.component;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.formdev.flatlaf.extras.components.FlatTextField;
import com.ywf.framework.layout.FindPanelLayout;
import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/30 22:39
 */
public class FindPanelBuilder {

    private static FindPanelLayout layout;
    private static JLabel btnClose;
    private static FlatTextField fieldFind;
    private static FlatLabel searchResultLabel;


    public static JPanel createFindPanel() {
        JPanel rootFindPanel = new JPanel();
        layout = new FindPanelLayout(rootFindPanel, 0, 5);
        rootFindPanel.setLayout(layout);
        rootFindPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(130, 128, 128, 130)));

        JPanel findPanel = new JPanel(new BorderLayout());
        findPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // 设置边框为10像素的空白边框
        JLabel findLabel = new JLabel("查找:  ", IconUtils.getSVGIcon("icons/find.svg", 16, 16), SwingConstants.RIGHT);
        findPanel.add(findLabel, BorderLayout.WEST);
        fieldFind = new FlatTextField();
        fieldFind.setPlaceholderText("请输入您要查找的内容...");
        fieldFind.setPadding(new Insets(0, 5, 0, 5));
        fieldFind.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, new Color(130, 128, 128, 130)));
        fieldFind.setShowClearButton(true);
        fieldFind.getDocument().addDocumentListener(new HighlightDocumentListener());

        // search toolbar
        JToolBar searchToolbar = new JToolBar();
        JToggleButton matchCaseButton = new JToggleButton(new FlatSVGIcon("icons/matchCase.svg"));
        matchCaseButton.setRolloverIcon(new FlatSVGIcon("icons/matchCaseHovered.svg"));
        matchCaseButton.setSelectedIcon(new FlatSVGIcon("icons/matchCaseSelected.svg"));
        matchCaseButton.setToolTipText("Match Case");
        // regex button
        JToggleButton regexButton = new JToggleButton(new FlatSVGIcon("icons/regex.svg"));
        regexButton.setRolloverIcon(new FlatSVGIcon("icons/regexHovered.svg"));
        regexButton.setSelectedIcon(new FlatSVGIcon("icons/regexSelected.svg"));
        regexButton.setToolTipText("Regular Expression");
        searchToolbar.add(matchCaseButton);
        searchToolbar.addSeparator();
        searchToolbar.add(regexButton);
        fieldFind.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, searchToolbar);
        findPanel.add(fieldFind, BorderLayout.CENTER);

        JPanel findBtnPanel = new JPanel(new FlowLayout(5, 5, FlowLayout.RIGHT));
        JButton buttonNext = new JButton("下一个");
        buttonNext.addActionListener(e -> nextFindActionPerformed());
        JButton buttonUp = new JButton("上一个");
        searchResultLabel = new FlatLabel();
        searchResultLabel.setText("<html><span color=\"#A7B3D3\">"+ 0 +" 个匹配项</span>");
        searchResultLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        findBtnPanel.add(buttonNext);
        findBtnPanel.add(buttonUp);
        findBtnPanel.add(searchResultLabel);

        JPanel findRight = new JPanel(new BorderLayout());
        findRight.setPreferredSize(new Dimension(100, 20));
        JCheckBox checkBox = new JCheckBox("替换");
        //findRight.add(checkBox, BorderLayout.EAST);
        findBtnPanel.add(findRight);

        btnClose = new JLabel(IconUtils.getSVGIcon("icons/close.svg", 16, 16));
        btnClose.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 7)); // 设置边框为10像素的空白边框
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
            btnClose.setIcon(IconUtils.getSVGIcon("icons/closeRed.svg", 16, 16));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            btnClose.setIcon(IconUtils.getSVGIcon("icons/close.svg", 16, 16));
        }
    }

    /**
     * 查找下一个
     */
    private static void nextFindActionPerformed() {
        startSegmentFindOrReplaceOperation(TextAreaBuilder.getSyntaxTextArea(), fieldFind.getText(), true, true, false);
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
                    textArea.setForeground(new Color(248, 201, 171));
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
        return layout;
    }

    public static void setLayout(FindPanelLayout layout) {
        FindPanelBuilder.layout = layout;
    }


    private static final Highlighter.HighlightPainter HIGHLIGHT = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
    private static boolean isHighlight = false;


    /**
     * 查找内容高亮显示
     *
     * @date 2024/1/2 23:03
     */
    private static void setTextAreaContentHighlight() {
        SwingUtilities.invokeLater(() -> {
            JSONRSyntaxTextArea syntaxTextArea = TextAreaBuilder.getSyntaxTextArea();
            if (!isHighlight) {
                int result = setHighlight(syntaxTextArea, fieldFind.getText());
                searchResultLabel.setText("<html><span color=\"#A7B3D3\">"+ result +" 个匹配项</span>");
                syntaxTextArea.repaint();
                isHighlight = true;
            } else {
                syntaxTextArea.getHighlighter().removeAllHighlights();
                searchResultLabel.setText("<html><span color=\"#A7B3D3\">"+ 0 +" 个匹配项</span>");
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
