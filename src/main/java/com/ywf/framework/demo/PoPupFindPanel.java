package com.ywf.framework.demo;

import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 查找弹框
 *
 * @Author YWF
 * @Date 2023/12/26 10:56
 */
public class PoPupFindPanel extends JPanel {

    private JPanel _this = this;
    private Popup popup;
    private JTextField textFieldFind;
    private JButton btnFind;
    private JButton btnNext;
    private JButton btnPrev;
    private JLabel btnClose;
    private boolean isVisible = false;

    volatile private static PoPupFindPanel instance = null;

    static {
    }

    public static PoPupFindPanel getInstance() {
        if (instance == null) {
            synchronized (PoPupFindPanel.class) {
                if (instance == null) {
                    instance = new PoPupFindPanel();
                }
            }
        }
        return instance;
    }

    /**
     * 主面板布局
     */
    private PoPupFindPanel() {
        this.setLayout(null);
        this.setBorder(new MatteBorder(1, 1, 1, 1, new Color(130, 128, 128, 130))); // 设置立体边框
        this.setPreferredSize(new Dimension(565, 35));
        this.setOpaque(true);
        //this.setBackground(new Color(0xFFFFE1));
        textFieldFind = new JTextField();
        textFieldFind.setBounds(5, 5, 230, 25);
        btnFind = new JButton("查找");
        btnFind.setBounds(240, 5, 80, 25);
        btnFind.addActionListener(e -> {

        });
        btnNext = new JButton("下一个");
        btnNext.setBounds(325, 5, 100, 25);
        btnPrev = new JButton("上一个");
        btnPrev.setBounds(430, 5, 100, 25);

        btnClose = new JLabel(IconUtils.getSVGIcon("icons/close.svg"));
        btnClose.setBounds(535, 5, 25, 25);
        btnClose.addMouseListener(new ClosePopupMouseListener());
        this.add(textFieldFind);
        this.add(btnFind);
        this.add(btnNext);
        this.add(btnPrev);
        this.add(btnClose);
    }

    /**
     * 查找弹框创建
     *
     * @param owner
     */
    public void showPopup(Component owner) {
        if (!isShow()) {
            // 如果弹框没打开，就创建，打开了就不需要重复创建
            createPopup(owner);
        }
    }

    private void createPopup(Component owner) {
        //Point point = new Point(0, owner.getHeight());
        Point point = new Point(0, 0);
        //Point point = new Point(owner.getX(), owner.getY());
        SwingUtilities.convertPointToScreen(point, owner);
        PopupFactory sharedInstance = PopupFactory.getSharedInstance();
        PoPupFindPanel popupContent = PoPupFindPanel.getInstance();
        System.out.println("对象的引用地址（哈希码）： " + System.identityHashCode(popupContent));
        this.popup = sharedInstance.getPopup(owner, popupContent, point.x, point.y);
        popupContent.popup = popup;
        this.setShow(true);
    }

    public boolean isShow() {
        return isVisible;
    }

    public void setShow(boolean visible) {
        if (visible) {
            popup.show();
        } else {
            popup.hide();
            popup = null;
        }
        isVisible = visible;
    }

    /**
     * 关闭事件
     */
    class ClosePopupMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            setShow(false);
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
    }

}
