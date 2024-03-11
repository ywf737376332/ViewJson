package com.ywf.component.loading;

import com.ywf.component.DialogBuilder;

import javax.swing.*;

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
    private final LoadingLabel loadingLabel;

    public LoadingBuild(JFrame frame, BackgroundTask backgroundKit) {
        this.frame = frame;
        loadingLabel = new LoadingLabel();
        this.backgroundKit = backgroundKit;
    }

    /**
     * 初始化遮罩层
     *
     * @param frame
     * @param backgroundKit
     * @return
     */
    public static LoadingBuild create(JFrame frame, BackgroundTask backgroundKit) {
        return new LoadingBuild(frame, backgroundKit);
    }

    /**
     * 展示模态遮罩层
     *
     * @return
     */
    public LoadingBuild showModal() {
        dialog = DialogBuilder.showMoadlDialog(frame, 380, 80);
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dialog.add(loadingLabel);
        //后台任务
        doBackground();
        return this;
    }

    private LoadingBuild doBackground() {
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
                backgroundKit.successTips();
            }
        };
        swingWorker.execute();
        dialog.setVisible(true);
        return this;
    }

}

