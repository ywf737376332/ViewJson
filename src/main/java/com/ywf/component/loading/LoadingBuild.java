package com.ywf.component.loading;

import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.component.DialogBuilder;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.utils.ObjectUtils;

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

    private LoadingBuild(JFrame frame, BackgroundTask backgroundKit) {
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
        dialog = DialogBuilder.showMoadlDialog(frame);
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
                FlatLabel tipLabel = ObjectUtils.getBean(GlobalKEY.STATE_BAR_COST_TIME);
                tipLabel.setText("<html><span color=\"#107C41\" style=\"font-size:10px\">" + "当前操作耗时"+loadingLabel.getCostTime() +"毫秒" + "</span></html>");
            }
        };
        swingWorker.execute();
        dialog.setVisible(true);
        return this;
    }

}

