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

        public String getMsgKey() {
            return msgKey;
        }

        public String getName() {
            return name;
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

}
