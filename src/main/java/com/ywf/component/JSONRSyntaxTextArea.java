package com.ywf.component;

import com.ywf.component.demo2.RsTextAreaPopopMenu;
import com.ywf.framework.enums.TextTypeEnum;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/8 11:32
 */
public class JSONRSyntaxTextArea extends RSyntaxTextArea {

    private static Set<Integer> hashCodes = new HashSet<Integer>();
    // 是否替换空白字符串
    private boolean isReplaceSpaceBlank = false;

    // 是否中文转Unicode
    private int chineseConverState;

    private TextTypeEnum textType;

    public JSONRSyntaxTextArea() {
        super();
    }

    @Override
    protected JPopupMenu createPopupMenu() {
        return RsTextAreaPopopMenu.getInstance().createPopupMenu(this);
    }

    public boolean isReplaceSpaceBlank() {
        return isReplaceSpaceBlank;
    }

    public void setReplaceSpaceBlank(boolean replaceSpaceBlank) {
        isReplaceSpaceBlank = replaceSpaceBlank;
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
