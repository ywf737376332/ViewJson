package com.ywf.component;

import com.ywf.component.demo2.RsTextAreaEditorKit;
import com.ywf.framework.enums.TextTypeEnum;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextAreaEditorKit;
import org.fife.ui.rtextarea.RTextArea;
import org.fife.ui.rtextarea.RTextAreaEditorKit;
import org.fife.ui.rtextarea.RecordableTextAction;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * RSyntaxTextArea组件扩展重写
 *
 * @Author YWF
 * @Date 2023/12/8 11:32
 */
public class JSONRSyntaxTextArea extends RSyntaxTextArea {

    /**
     * 保存hashCode值，以确保每一个新的组件，hashCode都不相同
     */
    private static Set<Integer> hashCodes = new HashSet<Integer>();

    private static ResourceBundle resourceBundle;

    /**
     * 是否替换空白字符串
     */
    private boolean isReplaceSpaceBlank = false;

    /**
     * 是否中文转Unicode
     */
    private int chineseConverState;

    /**
     * 内容类型
     */
    private TextTypeEnum textType;

    /**
     * 右键菜单重写
     */
    private static final String MSG = "RsTextArea";
    private JMenuItem newEditorMenuItem, closeEditorMenuItem, undoMenuItem, redoMenuItem, cutMenuItem, copyMenuItem, pasteMenuItem, deleteMenuItem, selectAllMenuItem;
    protected static RecordableTextAction newEditorAction, closeEditorAction, undoAction, redoAction, cutAction, copyAction, pasteAction, deleteAction, selectAllAction;
    protected static JMenu foldingMenu;
    private static RecordableTextAction collapseAllFoldsAction, expandAllFoldsAction;

    public JSONRSyntaxTextArea() {
        super();
    }

    @Override
    protected JPopupMenu createPopupMenu() {
        JPopupMenu popup = initPopupMenu(this);
        appendFoldingMenu(popup);
        return popup;
    }

    @Override
    protected void init() {
        super.init();
        if (newEditorMenuItem == null) {
            createPopupMenuActions();
        }
        if (collapseAllFoldsAction == null) {
            createRstaPopupMenuActions();
        }

    }

    /**
     * 创建右键菜单
     *
     * @param syntaxTextArea
     * @return
     */
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
        return menu;
    }

    @Override
    protected JMenuItem createPopupMenuItem(Action a) {
        JMenuItem item = new JMenuItem(a) {
            @Override
            public void setToolTipText(String text) {

            }
        };
        // 不显示菜单的快捷键
        item.setAccelerator(null);
        return item;
    }

    /**
     * 菜单事件初始化
     */
    private static void createPopupMenuActions() {
        ResourceBundle msg = getResourceBundle();
        int mod = RTextArea.getDefaultModifier();

        newEditorAction = new RsTextAreaEditorKit.NewEditorAction();
        newEditorAction.setProperties(msg, "Action.New");

        closeEditorAction = new RsTextAreaEditorKit.CloseEditorAction();
        closeEditorAction.setProperties(msg, "Action.Close");

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

        if (collapseAllFoldsAction == null) {
            foldingMenu = new JMenu(msg.getString("ContextMenu.Folding"));
            collapseAllFoldsAction = new RSyntaxTextAreaEditorKit.CollapseAllFoldsAction(true);
            expandAllFoldsAction = new RSyntaxTextAreaEditorKit.ExpandAllFoldsAction(true);
        }
    }

    @Override
    protected void appendFoldingMenu(JPopupMenu popup) {
        ResourceBundle msg = getResourceBundle();
        popup.addSeparator();
        foldingMenu = new JMenu(msg.getString("ContextMenu.Folding"));
        foldingMenu.add(createPopupMenuItem(collapseAllFoldsAction));
        foldingMenu.add(createPopupMenuItem(expandAllFoldsAction));
        popup.add(foldingMenu);

    }

    /**
     * 创建可折叠子集菜单事件
     */
    private static void createRstaPopupMenuActions() {
        collapseAllFoldsAction = new RSyntaxTextAreaEditorKit.CollapseAllFoldsAction(true);
        expandAllFoldsAction = new RSyntaxTextAreaEditorKit.ExpandAllFoldsAction(true);
    }

    private static ResourceBundle getResourceBundle() {
        if (resourceBundle == null) {
            resourceBundle = ResourceBundle.getBundle(MSG);
        }
        return resourceBundle;
    }


    public int getChineseConverState() {
        return chineseConverState;
    }

    public void setChineseConverState(int chineseConverState) {
        this.chineseConverState = chineseConverState;
    }

    public TextTypeEnum getTextType() {
        return textType;
    }

    public void setTextType(TextTypeEnum textType) {
        this.textType = textType;
    }

    @Override
    public int hashCode() {
        int hashCode;
        do {
            hashCode = (int) (Math.random() * Integer.MAX_VALUE);
        } while (hashCodes.contains(hashCode));
        hashCodes.add(hashCode);
        return hashCode;
    }
}
