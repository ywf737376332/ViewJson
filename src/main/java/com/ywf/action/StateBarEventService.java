package com.ywf.action;

import cn.hutool.core.util.ObjectUtil;
import com.formdev.flatlaf.extras.components.FlatLabel;
import com.ywf.component.JSONRSyntaxTextArea;
import com.ywf.component.LabelBarBuilder;
import com.ywf.component.StateLabel;
import com.ywf.framework.config.GlobalKEY;
import com.ywf.framework.constant.MessageConstant;
import com.ywf.framework.enums.TextTypeEnum;
import com.ywf.framework.init.SysConfigInit;
import com.ywf.framework.utils.ObjectUtils;
import com.ywf.framework.utils.StrUtils;
import com.ywf.framework.utils.TimeUtils;
import com.ywf.framework.utils.TypeUtils;
import com.ywf.pojo.StateBarEntity;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 状态栏事件监听
 *
 * @Author YWF
 * @Date 2023/12/16 23:45
 */
public class StateBarEventService {

    private ResourceBundle resourceBundle;
    private FlatLabel labelTypeValue;
    private FlatLabel fileLengthValue;
    private StateBarEntity stateBarEntity;

    volatile private static StateBarEventService instance = null;

    private StateBarEventService() {
        resourceBundle = ResourceBundleService.getInstance().getResourceBundle();
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
     * 文本框内容监听事件
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
                updateStateUI(rSyntaxTextArea);
            }
        });
    }

    /**
     * 监听鼠标的行和列位置
     *
     * @param rSyntaxTextArea
     */
    public void mouseLineAndColumnDocumentActionPerformed(JSONRSyntaxTextArea rSyntaxTextArea) {
        rSyntaxTextArea.addCaretListener(e -> {
            FlatLabel tipLabel = ObjectUtils.getBean(GlobalKEY.STATE_BAR_COST_TIME);
            int lineNumber = rSyntaxTextArea.getCaretLineNumber() + 1;
            int columnNumber = rSyntaxTextArea.getCaretOffsetFromLineStart() + 1;
            tipLabel.setText("<html><span color=\"#389FD6\" style=\"font-family:'Microsoft YaHei UI';font-size:10px\">" + "行:" + lineNumber + " 列:" + columnNumber + "</span></html>");
        });
    }

    public void updateStateUI(JSONRSyntaxTextArea rSyntaxTextArea) {
        SwingWorker<Boolean, StateBarEntity> swingWorker = new SwingWorker<Boolean, StateBarEntity>() {
            @Override
            protected Boolean doInBackground() {
                String text = rSyntaxTextArea.getText();
                TextTypeEnum contentType = TypeUtils.isType(text);
                stateBarEntity = new StateBarEntity(contentType, StrUtils.counts(text));
                publish(stateBarEntity);
                return true;
            }

            @Override
            protected void process(List<StateBarEntity> chunks) {
                StateBarEntity stateBarEntity = chunks.get(chunks.size() - 1);
                if (ObjectUtil.isNotNull(stateBarEntity)) {
                    // 首次获取状态栏组件,作为整个类的属性
                    getInstanceLabel();
                    TextTypeEnum contentType = stateBarEntity.getContentType();
                    labelTypeValue.setText("<html><span color=\"#389FD6\" style=\"font-family:'Microsoft YaHei UI';font-size:10px\">" + getMessage(contentType.getMessageKey()) + "</span></html>");
                    fileLengthValue.setText("<html><span color=\"#107C41\" style=\"font-family:'Microsoft YaHei UI';font-size:10px\">" + stateBarEntity.getTextLength() + MessageConstant.SYSTEM_STATE_BAR_WORDS + "</span></html>");
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
                publish(TimeUtils.viewTime(runTime));
                return true;
            }

            @Override
            protected void process(List<String> chunks) {
                String countTime = chunks.get(chunks.size() - 1);
                runTimeValue.setText("<html><span style=\"font-family:'Microsoft YaHei UI';font-size:10px\">" + countTime + "</span></html>");
                runTimeValue.setValue(countTime);
            }
        };
        swingWorker.execute();
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

    private String getMessage(String keyRoot) {
        return resourceBundle.getString(keyRoot + ".Name");
    }

    private void getInstanceLabel() {
        if (labelTypeValue == null) {
            labelTypeValue = LabelBarBuilder.getLabel(GlobalKEY.STATE_BAR_TEXT_TYPE);
        }
        if (fileLengthValue == null) {
            fileLengthValue = LabelBarBuilder.getLabel(GlobalKEY.STATE_BAR_TEXT_LENGTH);
        }
    }
}
