package com.ywf.component.demo2;

import com.ywf.action.QRCodeEventService;
import com.ywf.component.JSONRSyntaxTextArea;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextAreaEditorKit;
import org.fife.ui.rtextarea.RecordableTextAction;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/1/27 8:45
 */
public class RsTextAreaPopopMenu {
    private static final String MSG = "RsTextArea";
    private JMenuItem newEditorMenuItem, closeEditorMenuItem, undoMenuItem, redoMenuItem, cutMenuItem, copyMenuItem, pasteMenuItem, deleteMenuItem, selectAllMenuItem, collapseMenuItem;
    protected static RecordableTextAction newEditorAction, closeEditorAction, undoAction, redoAction, cutAction, copyAction, pasteAction, deleteAction, selectAllAction, collapseAction;
    volatile private static RsTextAreaPopopMenu instance = null;

    public RsTextAreaPopopMenu() {
        createPopupMenuActions();
    }

    public static RsTextAreaPopopMenu getInstance() {
        if (instance == null) {
            synchronized (QRCodeEventService.class) {
                if (instance == null) {
                    instance = new RsTextAreaPopopMenu();
                }
            }
        }
        return instance;
    }

    public JPopupMenu createPopupMenu(JSONRSyntaxTextArea syntaxTextArea) {
        return initPopupMenu(syntaxTextArea);
    }

    private JPopupMenu initPopupMenu(JSONRSyntaxTextArea syntaxTextArea) {
        JPopupMenu menu = new JPopupMenu();
        menu.setPopupSize(150, 289);
        menu.add(newEditorMenuItem = createPopupMenuItem(newEditorAction));
        menu.add(closeEditorMenuItem = createPopupMenuItem(closeEditorAction));
        menu.addSeparator();
        menu.add(undoMenuItem = createPopupMenuItem(undoAction));
        menu.add(redoMenuItem = createPopupMenuItem(redoAction));
        menu.addSeparator();
        menu.add(cutMenuItem = createPopupMenuItem(cutAction));
        menu.add(copyMenuItem = createPopupMenuItem(copyAction));
        menu.add(pasteMenuItem = createPopupMenuItem(pasteAction));
        menu.add(deleteMenuItem = createPopupMenuItem(deleteAction));
        menu.addSeparator();
        menu.add(selectAllMenuItem = createPopupMenuItem(selectAllAction));
        menu.addSeparator();
        menu.add(collapseMenuItem = createPopupMenuItem(collapseAction));
        return menu;
    }

    protected JMenuItem createPopupMenuItem(Action a) {
        JMenuItem item = new JMenuItem(a) {
            @Override
            public void setToolTipText(String text) {
                // Ignore!  Actions (e.g. undo/redo) set this when changing
                // their text due to changing enabled state.
            }
        };
        item.setAccelerator(null);
        return item;
    }

    private static void createPopupMenuActions() {
        int mod = RTextArea.getDefaultModifier();
        ResourceBundle msg = ResourceBundle.getBundle(MSG);

        newEditorAction = new RsTextAreaEditorKit.NewEditorAction();
        newEditorAction.setProperties(msg, "Action.New");
        newEditorAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, mod));

        closeEditorAction = new RsTextAreaEditorKit.CloseEditorAction();
        closeEditorAction.setProperties(msg, "Action.Close");
        closeEditorAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ALT, mod));

        cutAction = new RTextAreaEditorKit.CutAction();
        cutAction.setProperties(msg, "Action.Cut");
        cutAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, mod));

        copyAction = new RTextAreaEditorKit.CopyAction();
        copyAction.setProperties(msg, "Action.Copy");
        copyAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, mod));

        pasteAction = new RTextAreaEditorKit.PasteAction();
        pasteAction.setProperties(msg, "Action.Paste");
        pasteAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, mod));

        deleteAction = new RTextAreaEditorKit.DeleteNextCharAction();
        deleteAction.setProperties(msg, "Action.Delete");
        deleteAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));

        undoAction = new RTextAreaEditorKit.UndoAction();
        undoAction.setProperties(msg, "Action.Undo");
        undoAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, mod));

        redoAction = new RTextAreaEditorKit.RedoAction();
        redoAction.setProperties(msg, "Action.Redo");
        redoAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, mod));

        selectAllAction = new RTextAreaEditorKit.SelectAllAction();
        selectAllAction.setProperties(msg, "Action.SelectAll");
        selectAllAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, mod));

        collapseAction = new RTextAreaEditorKit.SelectAllAction();
        collapseAction.setProperties(msg, "Action.Collapse");
        collapseAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK, mod));
    }
}
