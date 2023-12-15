package com.ywf.component;

import com.ywf.framework.enums.TextConvertEnum;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/8 11:32
 */
public class JSONRSyntaxTextArea extends RSyntaxTextArea {

    // 是否替换空白字符串
    private boolean isReplaceSpaceBlank = false;

    // 是否中文转Unicode
    private int chineseConverState = TextConvertEnum.CONVERT_CLOSED.getConverType();

    public JSONRSyntaxTextArea(){
        super();
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
}
