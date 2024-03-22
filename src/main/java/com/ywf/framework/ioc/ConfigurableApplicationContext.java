package com.ywf.framework.ioc;

import com.ywf.framework.annotation.PropertySource;

import java.io.Serializable;
import java.util.Objects;

/**
 * 应用配置类
 *
 * @Author YWF
 * @Date 2024/1/27 15:13
 */
@PropertySource(value = "jsonView.properties")
public class ConfigurableApplicationContext extends ApplicationContext implements Serializable {

    private static final long serialVersionUID = 1L;

    // 文本编辑器是否可编辑状态
    private Boolean textAreaEditState;
    // 文本编辑器是否可换行
    private Boolean textAreaBreakLineState;
    // 文本编辑器是否可换行
    private Boolean textAreaShowlineNumState;
    // 是否显示工具栏
    private Boolean showToolBarState;
    // 是否显示菜单栏
    private Boolean showMenuBarState;
    // 最近使用的主题样式
    private String lastSystemThemes;
    // 中文转换状态
    private Integer chineseConverState;
    // 图片质量
    private Integer pictureQualityState;
    // 系统语言
    private String systemLanguage;
    // 工具栏方向
    private String toolBarLocation;
    // 是否显示工具栏文字
    private Boolean showToolBarText;
    // 官网
    private String webSiteUrl;
    // 是否显示空格符
    private Boolean showWhitespace;
    // 是否显示分割线
    private MarginLine marginLine;
    // 屏幕大小
    private ScreenSize screenSize;
    // 软件界面字体样式
    private FontStyle fontStyle;
    // 软件界面字体样式
    private EditorFontStyle editorFontStyle;

    public static class FontStyle {
        private String name;
        private Integer style;
        private Integer size;

        public FontStyle() {
        }

        public FontStyle(String name, Integer style, Integer size) {
            this.name = name;
            this.style = style;
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getStyle() {
            return style;
        }

        public void setStyle(Integer style) {
            this.style = style;
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
                    ", style=" + style +
                    ", size=" + size +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FontStyle fontStyle = (FontStyle) o;
            return Objects.equals(name, fontStyle.name) && Objects.equals(style, fontStyle.style) && Objects.equals(size, fontStyle.size);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, style, size);
        }
    }

    public static class ScreenSize {
        private Integer width;
        private Integer height;

        public ScreenSize() {
        }

        public ScreenSize(Integer width, Integer height) {
            this.width = width;
            this.height = height;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        @Override
        public String toString() {
            return "ScreenSize{" +
                    "width=" + width +
                    ", height=" + height +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ScreenSize that = (ScreenSize) o;
            return Objects.equals(width, that.width) && Objects.equals(height, that.height);
        }

        @Override
        public int hashCode() {
            return Objects.hash(width, height);
        }
    }

    public static class EditorFontStyle {
        private String name;
        private Integer style;
        private Float size;

        public EditorFontStyle() {
        }

        public EditorFontStyle(String name, Integer style, Float size) {
            this.name = name;
            this.style = style;
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getStyle() {
            return style;
        }

        public void setStyle(Integer style) {
            this.style = style;
        }

        public Float getSize() {
            return size;
        }

        public void setSize(Float size) {
            this.size = size;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EditorFontStyle that = (EditorFontStyle) o;
            return Objects.equals(name, that.name) && Objects.equals(style, that.style) && Objects.equals(size, that.size);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, style, size);
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MarginLine that = (MarginLine) o;
            return Objects.equals(showMarginLine, that.showMarginLine) && Objects.equals(marginWidth, that.marginWidth);
        }

        @Override
        public int hashCode() {
            return Objects.hash(showMarginLine, marginWidth);
        }
    }

    public ConfigurableApplicationContext() {
    }

    public Boolean getTextAreaEditState() {
        return textAreaEditState;
    }

    public void setTextAreaEditState(Boolean textAreaEditState) {
        this.textAreaEditState = textAreaEditState;
    }

    public Boolean getTextAreaBreakLineState() {
        return textAreaBreakLineState;
    }

    public void setTextAreaBreakLineState(Boolean textAreaBreakLineState) {
        this.textAreaBreakLineState = textAreaBreakLineState;
    }

    public Boolean getTextAreaShowlineNumState() {
        return textAreaShowlineNumState;
    }

    public void setTextAreaShowlineNumState(Boolean textAreaShowlineNumState) {
        this.textAreaShowlineNumState = textAreaShowlineNumState;
    }

    public Boolean getShowToolBarState() {
        return showToolBarState;
    }

    public void setShowToolBarState(Boolean showToolBarState) {
        this.showToolBarState = showToolBarState;
    }

    public Boolean getShowMenuBarState() {
        return showMenuBarState;
    }

    public void setShowMenuBarState(Boolean showMenuBarState) {
        this.showMenuBarState = showMenuBarState;
    }

    public String getLastSystemThemes() {
        return lastSystemThemes;
    }

    public void setLastSystemThemes(String lastSystemThemes) {
        this.lastSystemThemes = lastSystemThemes;
    }

    public Integer getChineseConverState() {
        return chineseConverState;
    }

    public void setChineseConverState(Integer chineseConverState) {
        this.chineseConverState = chineseConverState;
    }

    public Integer getPictureQualityState() {
        return pictureQualityState;
    }

    public void setPictureQualityState(Integer pictureQualityState) {
        this.pictureQualityState = pictureQualityState;
    }

    public String getSystemLanguage() {
        return systemLanguage;
    }

    public void setSystemLanguage(String systemLanguage) {
        this.systemLanguage = systemLanguage;
    }

    public ScreenSize getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(ScreenSize screenSize) {
        this.screenSize = screenSize;
    }

    public FontStyle getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(FontStyle fontStyle) {
        this.fontStyle = fontStyle;
    }

    public EditorFontStyle getEditorFontStyle() {
        return editorFontStyle;
    }

    public void setEditorFontStyle(EditorFontStyle editorFontStyle) {
        this.editorFontStyle = editorFontStyle;
    }

    public String getToolBarLocation() {
        return toolBarLocation;
    }

    public void setToolBarLocation(String toolBarLocation) {
        this.toolBarLocation = toolBarLocation;
    }

    public Boolean getShowToolBarText() {
        return showToolBarText;
    }

    public void setShowToolBarText(Boolean showToolBarText) {
        this.showToolBarText = showToolBarText;
    }

    public String getWebSiteUrl() {
        return webSiteUrl;
    }

    public void setWebSiteUrl(String webSiteUrl) {
        this.webSiteUrl = webSiteUrl;
    }

    public Boolean getShowWhitespace() {
        return showWhitespace;
    }

    public void setShowWhitespace(Boolean showWhitespace) {
        this.showWhitespace = showWhitespace;
    }

    public MarginLine getMarginLine() {
        return marginLine;
    }

    public void setMarginLine(MarginLine marginLine) {
        this.marginLine = marginLine;
    }

    @Override
    public String toString() {
        return "ConfigurableApplicationContext{" +
                "textAreaEditState=" + textAreaEditState +
                ", textAreaBreakLineState=" + textAreaBreakLineState +
                ", textAreaShowlineNumState=" + textAreaShowlineNumState +
                ", showToolBarState=" + showToolBarState +
                ", showMenuBarState=" + showMenuBarState +
                ", lastSystemThemes='" + lastSystemThemes + '\'' +
                ", chineseConverState=" + chineseConverState +
                ", pictureQualityState=" + pictureQualityState +
                ", systemLanguage='" + systemLanguage + '\'' +
                ", toolBarLocation='" + toolBarLocation + '\'' +
                ", showToolBarText=" + showToolBarText +
                ", webSiteUrl='" + webSiteUrl + '\'' +
                ", showWhitespace=" + showWhitespace +
                ", marginLine=" + marginLine +
                ", screenSize=" + screenSize +
                ", fontStyle=" + fontStyle +
                ", editorFontStyle=" + editorFontStyle +
                '}';
    }
}
