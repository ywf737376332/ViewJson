package com.ywf.component;

import com.ywf.framework.utils.IconUtils;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.text.Document;
import javax.swing.text.Segment;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * 图片组件工厂
 * 窗口最大设为比Frame大小小一点，里面的图片可进行放大和缩小，预览窗口的图片不能进行放大2倍
 * 图片可以进行上下滑动查看
 *
 * @Author YWF
 * @Date 2023/12/12 23:26
 */
public class DialogBuilder {

    /**
     * 图片组件创建
     *
     * @param parentFrame
     * @param title
     * @param image
     * @date 2023/12/12 23:54
     */
    public static JDialog ShowImageDialog(JFrame parentFrame, String title, ImageIcon image) {
        return createDialog(parentFrame, title, image);
    }

    /**
     * 图片组件创建
     *
     * @param parentFrame
     * @param title
     * @param imagePath
     * @date 2023/12/12 23:54
     */
    public static JDialog ShowImageDialog(JFrame parentFrame, String title, String imagePath) {
        ImageIcon image = IconUtils.getImageIcon(imagePath);
        return createDialog(parentFrame, title, image);
    }

    private static JDialog createDialog(JFrame parentFrame, String title, ImageIcon image) {
        final JDialog dialog = new JDialog(parentFrame, title, true);
        JLabel imageLabel = new JLabel(image);
        // 设置标签的首选大小为图片的大小
        dialog.setSize(image.getIconWidth() + 50, image.getIconHeight() + 50);
        dialog.setLayout(new BorderLayout());
        dialog.add(imageLabel, BorderLayout.CENTER);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setResizable(false);
        // 窗口监听
        return dialog;
    }

    /**
     * 查找对话框绘制
     *
     * @param frame
     * @param rSyntaxTextArea
     * @param title
     * @return
     */
    public static JDialog showFindDialog(JFrame frame, JSONRSyntaxTextArea rSyntaxTextArea, String title) {

        if (findDialog == null) {
            findDialog = createFindDialog(frame, rSyntaxTextArea, title);
        }
        //findDialog.setAlwaysOnTop(true);
        // 不管是否是新的对话框，都重新设置位置，避免父窗口移动后，子窗口位置不变
        // 居中贴上边
        // 贴右边贴上边
        RTextScrollPane rTextScrollPane = TextAreaBuilder.getrTextScrollPane();
        Point tslPoint = rTextScrollPane.getLocationOnScreen();
        findDialog.setLocation(tslPoint.x + rTextScrollPane.getWidth() - findDialog.getWidth() - 11, tslPoint.y + 1);
        //从头开始查找
        btnFind.addActionListener(e -> findActionPerformed(rSyntaxTextArea));
        //向下查找
        btnNext.addActionListener(e -> nextFindActionPerformed(rSyntaxTextArea));
        //向上查找
        btnPrev.addActionListener(e -> prevFindActionPerformed(rSyntaxTextArea));
        // 窗口监听
        //findDialog.addWindowListener(new FindDialogListener());
        return findDialog;
    }

    private static JDialog createFindDialog(JFrame frame, JSONRSyntaxTextArea rSyntaxTextArea, String title) {
        final JDialog openDlg = new JDialog(frame);
        JPanel panelRoot = new JPanel();
        openDlg.setUndecorated(true);
        openDlg.setTitle(title);
        openDlg.setModal(false);
        openDlg.setSize(565, 35);
        openDlg.setResizable(false);
        panelRoot.setLayout(null);
        panelRoot.setBorder(new MatteBorder(0, 0, 0, 0, new Color(130, 128, 128, 130))); // 设置立体边框
        textFieldFind = new JTextField();
        textFieldFind.setBounds(5, 5, 230, 25);
        btnFind = new JButton("查找");
        btnFind.setBounds(240, 5, 80, 25);
        btnNext = new JButton("下一个");
        btnNext.setBounds(325, 5, 100, 25);
        btnPrev = new JButton("上一个");
        btnPrev.setBounds(430, 5, 100, 25);

        JLabel btnClose = new JLabel(IconUtils.getSVGIcon("icons/close.svg"));
        btnClose.setBounds(535, 5, 25, 25);
        btnClose.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                findDialog.setVisible(false);
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                btnClose.setIcon(IconUtils.getSVGIcon("icons/closeRed.svg"));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnClose.setIcon(IconUtils.getSVGIcon("icons/close.svg"));
            }
        });
        panelRoot.add(textFieldFind);
        panelRoot.add(btnFind);
        panelRoot.add(btnNext);
        panelRoot.add(btnPrev);
        panelRoot.add(btnClose);
        openDlg.getContentPane().add(panelRoot);
        return openDlg;
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

    private static void modifyDialgTitle(JDialog dlg, boolean flag, int n) {
        String[] tmp = dlg.getTitle().split("-");
        if (n == -1) {
            dlg.setTitle(tmp[0] + "-" + "  ==");
            return;
        }
        if (flag) {
            dlg.setTitle(tmp[0] + "-" + "  找到了^_^");
        } else {
            dlg.setTitle(tmp[0] + "-" + "  没找到╮(╯_╰)╭");
        }
    }

    private static void findActionPerformed(JSONRSyntaxTextArea rSyntaxTextArea) {
        boolean flag = false;
        modifyDialgTitle(findDialog, flag, -1);
        flag = startSegmentFindOrReplaceOperation(rSyntaxTextArea, textFieldFind.getText(), true, true, true);
        modifyDialgTitle(findDialog, flag, 1);
    }

    protected static void nextFindActionPerformed(JSONRSyntaxTextArea rSyntaxTextArea) {
        boolean flag = false;
        modifyDialgTitle(findDialog, flag, -1);
        flag = startSegmentFindOrReplaceOperation(rSyntaxTextArea, textFieldFind.getText(), true, true, false);
        modifyDialgTitle(findDialog, flag, 1);
    }

    private static void prevFindActionPerformed(JSONRSyntaxTextArea rSyntaxTextArea) {
        boolean flag = false;
        modifyDialgTitle(findDialog, flag, -1);
        flag = startSegmentFindOrReplaceOperation(rSyntaxTextArea, textFieldFind.getText(), true, false, false);
        modifyDialgTitle(findDialog, flag, 1);
    }


    static class FindDialogListener implements WindowListener {
        public void windowOpened(WindowEvent e) {
        }

        public void windowClosing(WindowEvent e) {
        }

        public void windowClosed(WindowEvent e) {
        }

        public void windowIconified(WindowEvent e) {
        }

        public void windowDeiconified(WindowEvent e) {
        }

        public void windowActivated(WindowEvent e) {
        }

        // 失去焦点时，隐藏对话框
        public void windowDeactivated(WindowEvent e) {
            findDialog.setVisible(false);
        }
    }


    private static JDialog findDialog;
    private static JButton btnFind;
    private static JButton btnNext;
    private static JButton btnPrev;
    private static JTextField textFieldFind;

    public static JDialog getFindDialog() {
        return findDialog;
    }

    public static void setFindDialog(JDialog findDialog) {
        DialogBuilder.findDialog = findDialog;
    }
}
