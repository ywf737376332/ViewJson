package com.ywf.action;

import cn.hutool.core.util.ObjectUtil;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.component.JSONRSyntaxTextArea;
import com.ywf.component.LabelBarBuilder;
import com.ywf.component.StateLabel;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.enums.TextTypeEnum;
import com.ywf.framework.init.SysConfigInit;
import com.ywf.framework.utils.StrUtils;
import com.ywf.framework.utils.TypeUtils;
import com.ywf.pojo.StateBarEntity;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Date;
import java.util.List;

/**
 * 状态栏事件监听
 *
 * @Author YWF
 * @Date 2023/12/16 23:45
 */
public class StateBarEventService {


    volatile private static StateBarEventService instance = null;

    private StateBarEventService() {
    }

    public static StateBarEventService getInstance() {
        if (instance == null) {
            synchronized (StateBarEventService.class) {
                if (instance == null) {
                    instance = new StateBarEventService();
                }
            }
        }
        return instance;
    }

    /**
     * 文本框监听事件
     *
     * @param rSyntaxTextArea
     * @date 2023/12/17 14:35
     */
    public void textAreaDocumentActionPerformed(JSONRSyntaxTextArea rSyntaxTextArea) {
        rSyntaxTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateStateUI(rSyntaxTextArea);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateStateUI(rSyntaxTextArea);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // 不需要处理文档更改事件
            }
        });
    }

    public void updateStateUI(JSONRSyntaxTextArea rSyntaxTextArea) {
        SwingWorker<Boolean, StateBarEntity> swingWorker = new SwingWorker<Boolean, StateBarEntity>() {
            @Override
            protected Boolean doInBackground() {
                String text = rSyntaxTextArea.getText();
                TextTypeEnum contentType = TypeUtils.isType(text);
                StateBarEntity stateBarEntity = new StateBarEntity(contentType, StrUtils.counts(text));
                publish(stateBarEntity);
                return true;
            }

            @Override
            protected void process(List<StateBarEntity> chunks) {
                StateBarEntity stateBarEntity = chunks.get(chunks.size() - 1);
                if (ObjectUtil.isNotNull(stateBarEntity)) {
                    FlatLabel labelTypeLabel = LabelBarBuilder.getLabel(GlobalKEY.STATE_BAR_TEXT_TYPE);
                    FlatLabel fileLengthLabel = LabelBarBuilder.getLabel(GlobalKEY.STATE_BAR_TEXT_LENGTH);
                    TextTypeEnum contentType = stateBarEntity.getContentType();
                    labelTypeLabel.setText("<html><span color=\"#389FD6\" style=\"font-family:'Microsoft YaHei UI';font-size:9px\">" + contentType.getDiscription() + "</span></html>");
                    fileLengthLabel.setText("<html><span color=\"#107C41\" style=\"font-family:'Microsoft YaHei UI';font-size:9px\">" + stateBarEntity.getTextLength() + "词" + "</span></html>");
                    rSyntaxTextArea.setSyntaxEditingStyle(contentType.getSyntaxStyle());
                    rSyntaxTextArea.setTextType(contentType);
                }
            }
        };
        swingWorker.execute();
    }

    /**
     * 状态栏时间统计监听事件
     *
     * @date 2023/12/17 14:38
     */
    public void stateBarTimeActionPerformed(StateLabel runTimeValue) {
        SwingWorker<Boolean, String> swingWorker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground() {
                long nowTime = new Date().getTime();
                long nowStart = SysConfigInit.startTime.getTime();
                long runTime = nowTime - nowStart;
                publish(viewTime(runTime));
                return true;
            }

            @Override
            protected void process(List<String> chunks) {
                String countTime = chunks.get(chunks.size() - 1);
                runTimeValue.setText("<html><span style=\"font-family:'Microsoft YaHei UI';font-size:9px\">" + countTime + "</span></html>");
                runTimeValue.setValue(countTime);
            }
        };
        swingWorker.execute();
    }

    private String viewTime(long millseconds) {
        // millseconds = millseconds + 50000 + 3540000 + 82800000L + 1036800000L * 10;
        long seconds, minutes, hours, days;
        StringBuilder sb = new StringBuilder();
        seconds = millseconds / 1000;
        minutes = seconds / 60;
        hours = minutes / 60;
        days = hours / 24;
        // 计算显示的具体时间组合
        seconds = seconds - minutes * 60;
        minutes = minutes - hours * 60;
        hours = hours - days * 24;
        if (days != 0)
            return sb.append(days).append(" 天 ").append(hours).append(" 小时 ").append(minutes).append(" 分钟 ").append(seconds).append(" 秒").toString();
        if (hours != 0)
            return sb.append(hours).append(" 小时 ").append(minutes).append(" 分钟 ").append(seconds).append(" 秒").toString();
        if (minutes != 0) return sb.append(minutes).append(" 分钟 ").append(seconds).append(" 秒").toString();
        return seconds + " 秒";
        //return sb.append(days).append(" 天 ").append(hours).append(" 小时 ").append(minutes).append(" 分钟 ").append(seconds).append(" 秒").toString();
    }

    /**
     * JFrame窗口焦点事件
     *
     * @param frame
     * @date 2023/12/17 16:19
     */
    public void frameFocusActionPerformed(JFrame frame, Timer timer) {
        // 添加窗口失去焦点监听器
        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                // 窗口获得焦点时的操作
                timer.start();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                timer.stop();
            }
        });
    }

}
