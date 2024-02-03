package com.ywf.action;

import com.ywf.framework.config.GlobalMenuKEY;
import com.ywf.framework.utils.ObjectUtils;
import org.fife.ui.rtextarea.RTextArea;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/7 22:37
 */
public class EditorFocusEventService {


    volatile private static EditorFocusEventService instance = null;

    private EditorFocusEventService() {
    }

    public static EditorFocusEventService getInstance() {
        if (instance == null) {
            synchronized (EditorFocusEventService.class) {
                if (instance == null) {
                    instance = new EditorFocusEventService();
                }
            }
        }
        return instance;
    }

    /**
     * 设置保存编辑框的焦点
     *
     * @param rSyntaxTextArea
     */
    public void getFocusOwnerActionPerformed(RTextArea rSyntaxTextArea) {
        rSyntaxTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                ObjectUtils.setBean(GlobalMenuKEY.EDITOR_FOCUS, rSyntaxTextArea);
            }
        });
    }

}
