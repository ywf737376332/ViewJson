package com.ywf.component.demo2;

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
        public NewEditorAction() {
            //super(RsTextAreaEditorKit.newEditorAction);
            super(RsTextAreaEditorKit.newEditorAction,IconUtils.getSVGIcon("icons/layoutOne.svg"), null, null, null);
        }
        @Override
        public void actionPerformedImpl(ActionEvent e, RTextArea textArea) {
            DemoTabble002.createNewTabActionPerformed();
        }
        @Override
        public final String getMacroID() {
            return RsTextAreaEditorKit.newEditorAction;
        }

    }

    public static class CloseEditorAction extends RecordableTextAction {
        public CloseEditorAction() {
            //super(RsTextAreaEditorKit.newEditorAction);
            super(RsTextAreaEditorKit.newEditorAction,IconUtils.getSVGIcon("icons/closeTab.svg"), null, null, null);
        }
        @Override
        public void actionPerformedImpl(ActionEvent e, RTextArea textArea) {
            DemoTabble002.closeAbleTabbedSplitPane((RSyntaxTextArea) textArea);
        }
        @Override
        public final String getMacroID() {
            return RsTextAreaEditorKit.closeEditorAction;
        }

    }
}
