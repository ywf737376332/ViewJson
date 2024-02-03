package com.ywf.framework.config;

import com.ywf.action.MenuEventService;
import com.ywf.component.JSONRSyntaxTextArea;
import com.ywf.component.demo2.DemoTabble002;
import com.ywf.framework.utils.IconUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextAreaEditorKit;
import org.fife.ui.rtextarea.RecordableTextAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/1/27 10:22
 */
public class RsTextAreaEditorKit extends RTextAreaEditorKit {

    public static final String newEditorAction = "new-editor";
    public static final String closeEditorAction = "close-editor";

    public RsTextAreaEditorKit() {
        super();
    }

    private static final RecordableTextAction[] defaultActions = {
            new NewEditorAction(),
            new CloseEditorAction()
    };

    @Override
    public Action[] getActions() {
        return defaultActions;
    }

    public static class NewEditorAction extends RecordableTextAction {
        private JSONRSyntaxTextArea syntaxTextArea;
        public NewEditorAction() {
            //super(RsTextAreaEditorKit.newEditorAction);
            super(RsTextAreaEditorKit.newEditorAction,IconUtils.getSVGIcon("icons/newEditer.svg"), null, null, null);
        }
        @Override
        public void actionPerformedImpl(ActionEvent e, RTextArea textArea) {
            MenuEventService.getInstance().addTabbedSplitEditorActionPerformed();
        }
        @Override
        public final String getMacroID() {
            return RsTextAreaEditorKit.newEditorAction;
        }

    }

    public static class CloseEditorAction extends RecordableTextAction {
        private JSONRSyntaxTextArea syntaxTextArea;
        public CloseEditorAction() {
            //super(RsTextAreaEditorKit.closeEditorAction);
            super(RsTextAreaEditorKit.closeEditorAction,IconUtils.getSVGIcon("icons/closeTab.svg"), null, null, null);
        }
        @Override
        public void actionPerformedImpl(ActionEvent e, RTextArea textArea) {
            MenuEventService.getInstance().closeTabbedSplitEditorActionPerformed(textArea);
        }
        @Override
        public final String getMacroID() {
            return RsTextAreaEditorKit.closeEditorAction;
        }

    }
}