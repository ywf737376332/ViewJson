package com.ywf.framework.enums;

/**
 * 字体定义
 *
 * @Author YWF
 * @Date 2024/2/23 11:17
 */
public class FontEnum {

    public enum Name {
        MicYaHei("MenuItem.FontName.MicYaHei", "微软雅黑"),
        ChristmasWorship("MenuItem.FontName.ChristmasWorship", "华文中宋"),
        Arial("MenuItem.FontName.Arial", "黑体"),
        BlackLetter("MenuItem.FontName.BlackLetter", "等线"),
        StLiti("MenuItem.FontName.StLiti", "华文隶书"),
        YouYuan("MenuItem.FontName.YouYuan", "幼圆");

        private String msgKey;
        private String name;

        Name(String msgKey, String name) {
            this.msgKey = msgKey;
            this.name = name;
        }

        public static String getFontNameByKey(String msgKey) {
            for (Name value : Name.values()) {
                if (msgKey.equalsIgnoreCase(value.msgKey)){
                    return value.name;
                }
            }
            return null;
        }

        public String getMsgKey() {
            return msgKey;
        }

        public String getName() {
            return name;
        }
    }

    public enum Style {
        PLAIN("MenuItem.FontStyle.Plain", 0,"常规"),
        BOLD("MenuItem.FontStyle.Bold", 1,"粗体"),
        ITALIC("MenuItem.FontStyle.Italic", 2,"斜体");

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
        mini("MenuItem.FontSize.Mini", 10, "迷你"),
        small("MenuItem.FontSize.Small", 12, "小号"),
        medium("MenuItem.FontSize.Medium", 14, "中号"),
        regular("MenuItem.FontSize.Regular", 16, "常规"),
        large("MenuItem.FontSize.Large", 18, "大号"),
        tooLarge("MenuItem.FontSize.TooLarge", 20, "特大号");

        private String msgKey;
        private int size;
        private String desc;

        Size(String msgKey, int size, String desc) {
            this.msgKey = msgKey;
            this.size = size;
            this.desc = desc;
        }

        public String getMsgKey() {
            return msgKey;
        }

        public int getSize() {
            return size;
        }

        public String getDesc() {
            return desc;
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
