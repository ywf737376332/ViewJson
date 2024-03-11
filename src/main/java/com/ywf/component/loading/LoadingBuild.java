package com.ywf.component.loading;

import javax.swing.*;
import java.awt.*;

/**
 * 加载中遮罩层
 *
 * @Author YWF
 * @Date 2024/3/10 19:35
 */
public class LoadingBuild {

    private JFrame frame;
    private BackgroundTask backgroundKit;
    private JDialog dialog;
    private final LoadingLabel loadingLabel = new LoadingLabel();

    public LoadingBuild(JFrame frame, BackgroundTask backgroundKit) {
        this.frame = frame;
        this.backgroundKit = backgroundKit;
    }

    public static LoadingBuild create(JFrame frame, BackgroundTask backgroundKit) {
        return new LoadingBuild(frame, backgroundKit);
    }


    public LoadingBuild showModal() {
        dialog = new JDialog(frame, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);
        dialog.setBounds(frame.getBounds());
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(loadingLabel);
        dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        int color = 0x22_FF_00_00;
        //dialog.setBackground(new Color(color, true));
        dialog.setBackground(new Color(123, 121, 121, 160));
        doBackground();
        return this;
    }

    public LoadingBuild doBackground() {
        loadingLabel.startAnimation();
        SwingWorker<Boolean, Void> swingWorker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                backgroundKit.actionPerformedImpl();
                return true;
            }

            @Override
            protected void done() {
                if (!frame.isDisplayable()) {
                    cancel(true);
                    return;
                }
                loadingLabel.stopAnimation();
                dialog.setVisible(false);
            }
        };
        swingWorker.execute();
        dialog.setVisible(true);
        return this;
    }

}

