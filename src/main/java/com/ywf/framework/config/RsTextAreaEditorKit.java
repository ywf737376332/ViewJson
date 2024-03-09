package com.ywf.framework.config;

import com.ywf.action.MenuEventService;
import com.ywf.component.JSONRSyntaxTextArea;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextAreaEditorKit;
import org.fife.ui.rtextarea.RecordableTextAction;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * 全局组建Key
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
            super(RsTextAreaEditorKit.newEditorAction, SvgIconFactory.mediumIcon(SvgIconFactory.TextAreaMenuIcon.newEditor), null, null, null);
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
            super(RsTextAreaEditorKit.closeEditorAction, SvgIconFactory.mediumIcon(SvgIconFactory.TextAreaMenuIcon.closeEditor), null, null, null);
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
