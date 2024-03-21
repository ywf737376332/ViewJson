package com.ywf.framework.enums;

/**
 * 编辑器字体定义
 *
 * @Author YWF
 * @Date 2024/2/23 11:17
 */
public class EditorFontEnum {

    /**
     * DejaVuSansMono.ttf
     * JetBrainsMono.ttf
     * Monaco.ttf
     */

    public enum Name {
        DejaVuSansMono("Editor.FontName.DejaVuSansMono", "DejaVuSansMono"),
        JetBrainsMono("Editor.FontName.JetBrainsMono", "JetBrainsMono"),
        Monaco("Editor.FontName.Monaco", "Monaco");

        private String msgKey;
        private String name;

        Name(String msgKey, String name) {
            this.msgKey = msgKey;
            this.name = name;
        }

        public String getMsgKey() {
            return msgKey;
        }

        public String getName() {
            return name;
        }
    }

    public enum Style {
        PLAIN("Editor.FontStyle.Plain", 0, "常规"),
        BOLD("Editor.FontStyle.Bold", 1, "粗体"),
        ITALIC("Editor.FontStyle.Italic", 2, "斜体");

        private String msgKey;
        private int style;
        private String desc;

        Style(String msgKey, int style, String desc) {
            this.msgKey = msgKey;
            this.style = style;
            this.desc = desc;
        }

        public String getMsgKey() {
            return msgKey;
        }

        public void setMsgKey(String msgKey) {
            this.msgKey = msgKey;
        }

        public int getStyle() {
            return style;
        }

        public void setStyle(int style) {
            this.style = style;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum Size {
        mini("Editor.FontSize.Mini", 10.0f, "迷你"),
        small("Editor.FontSize.Small", 12.0f, "小号"),
        medium("Editor.FontSize.Medium", 14.0f, "中号"),
        regular("Editor.FontSize.Regular", 16.0f, "常规"),
        large("Editor.FontSize.Large", 18.0f, "大号"),
        tooLarge("Editor.FontSize.TooLarge", 20.0f, "特大号");

        private String msgKey;
        private float size;
        private String desc;

        Size(String msgKey, float size, String desc) {
            this.msgKey = msgKey;
            this.size = size;
            this.desc = desc;
        }

        public String getMsgKey() {
            return msgKey;
        }

        public void setMsgKey(String msgKey) {
            this.msgKey = msgKey;
        }

        public float getSize() {
            return size;
        }

        public void setSize(float size) {
            this.size = size;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum Type {
        FONT_NAME,
        FONT_STYLE,
        FONT_SIZE;

        Type() {
        }

    }
}
