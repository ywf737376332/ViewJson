package com.ywf.framework.enums;

/**
 * 字体定义
 *
 * @Author YWF
 * @Date 2024/2/23 11:17
 */
public class FontEnum {

    public enum Name {
        micYaHei("微软雅黑"),
        christmasWorship("华文中宋"),
        arial("黑体"),
        blackLetter("等线");

        private String name;

        Name(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum Size {
        mini(8, "迷你"),
        small(10, "小号"),
        medium(12, "中等"),
        regular(14, "常规"),
        large(16, "大号"),
        tooLarge(18, "特大号");

        private int size;
        private String desc;

        Size(int size, String desc) {
            this.size = size;
            this.desc = desc;
        }

        public int getSize() {
            return size;
        }

        public String getDesc() {
            return desc;
        }
    }

}
