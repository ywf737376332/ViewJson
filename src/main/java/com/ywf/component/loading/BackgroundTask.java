package com.ywf.component.loading;

import com.ywf.framework.utils.WindowUtils;

import javax.swing.*;

/**
 * 后台执行事件
 *
 * @Author YWF
 * @Date 2024/3/10 19:59
 */
public abstract class BackgroundTask extends SwingWorker<Boolean, Void> implements TaskBase {

    protected JFrame frame = WindowUtils.getFrame();

    @Override
    protected Boolean doInBackground() {
        actionPerformedImpl();
        return true;
    }

    public abstract void actionPerformedImpl();

    public abstract void successTips();

}
