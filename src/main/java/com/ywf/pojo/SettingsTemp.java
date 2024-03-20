package com.ywf.pojo;

import java.io.Serializable;

/**
 * 设置界面配置信息
 *
 * @Author YWF
 * @Date 2024/1/27 15:13
 */
public class SettingsTemp implements Serializable {

    private static final long serialVersionUID = 1L;

    // 最近使用的主题样式
    private String lastSystemThemes;
    // 系统语言
    private String systemLanguage;
    // 文本编辑器是否可编辑状态
    private Boolean textAreaEditState;
    // 文本编辑器是否可换行
    private Boolean textAreaBreakLineState;
    // 文本编辑器是否可换行
    private Boolean textAreaShowlineNumState;
    // 中文转换状态
    private Integer chineseConverState;
    // 图片质量
    private Integer pictureQualityState;
    // 是否显示空格符
    private Boolean showWhitespace;
    // 是否显示分割线
    private MarginLine marginLine;
    // 是否显示工具栏文字
    private Boolean showToolBarText;
    // 软件界面字体样式
    private FontStyle fontStyle;
    // 软件界面字体样式
    private EditorFontStyle editorFontStyle;

    public SettingsTemp() {
    }


    public static class FontStyle {
        private String name;
        private Integer size;

        public FontStyle() {
        }

        public FontStyle(String name, Integer size) {
            this.name = name;
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return "FontStyle{" +
                    "name='" + name + '\'' +
                    ", size=" + size +
                    '}';
        }
    }

    public static class EditorFontStyle {
        private String name;
        private Float size;

        public EditorFontStyle() {
        }

        public EditorFontStyle(String name, Float size) {
            this.name = name;
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Float getSize() {
            return size;
        }

        public void setSize(Float size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return "EditorFontStyle{" +
                    "name='" + name + '\'' +
                    ", size=" + size +
                    '}';
        }
    }

    public static class MarginLine {

        private Boolean showMarginLine;
        private Integer marginWidth;

        public MarginLine() {
        }

        public Boolean getShowMarginLine() {
            return showMarginLine;
        }

        public void setShowMarginLine(Boolean showMarginLine) {
            this.showMarginLine = showMarginLine;
        }

        public Integer getMarginWidth() {
            return marginWidth;
        }

        public void setMarginWidth(Integer marginWidth) {
            this.marginWidth = marginWidth;
        }

        @Override
        public String toString() {
            return "MarginLine{" +
                    "showMarginLine=" + showMarginLine +
                    ", marginWidth=" + marginWidth +
                    '}';
        }
    }


}
