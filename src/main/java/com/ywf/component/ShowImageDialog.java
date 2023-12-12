package com.ywf.component;

import com.ywf.framework.utils.IconUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/12 22:33
 */
public class ShowImageDialog extends JDialog {

    private Dialog _this = this;

    public ShowImageDialog(JFrame frame, String title, ImageIcon image) {
        super(frame, title, true);
        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);
        JLabel imageLabel = new JLabel(image);
        panel.add(imageLabel, BorderLayout.CENTER);
        this.setSize(image.getIconWidth() + 10, image.getIconHeight() + 50);
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.setLocationRelativeTo(frame);
        this.setVisible(true);
        this.closeDialog(_this);
    }

    public ShowImageDialog(JFrame frame, String title, String imagePath) {
        super(frame, title);
        ImageIcon imageIcon = IconUtils.getImageIcon(imagePath);
        JLabel imageLabel = new JLabel(imageIcon);
        this.getContentPane().add(imageLabel, BorderLayout.CENTER);
        this.setLocationRelativeTo(frame);
        this.setVisible(true);
        this.closeDialog(_this);
    }

    private void closeDialog(Dialog dialog) {
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 在这里处理关闭对话框时的操作，例如释放资源或保存数据等
                System.out.println("closeDialog");
                dialog.dispose();
            }
        });
    }
}
