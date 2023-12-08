package com.ywf.component;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/8 11:32
 */
public class JSONRSyntaxTextArea extends RSyntaxTextArea {

    private boolean isReplaceSpaceBlank = false;

    public JSONRSyntaxTextArea(){
        super();
    }

    public boolean isReplaceSpaceBlank() {
        return isReplaceSpaceBlank;
    }

    public void setReplaceSpaceBlank(boolean replaceSpaceBlank) {
        isReplaceSpaceBlank = replaceSpaceBlank;
    }
}
