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
    // 屏幕大小
    private ScreenSize screenSize;
    // 字体样式名称
    private String fontStyle;
    // 字头大小
    private Integer fontSize;

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

    public ScreenSize getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(ScreenSize screenSize) {
        this.screenSize = screenSize;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
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
                ", screenSize=" + screenSize +
                ", fontStyle='" + fontStyle + '\'' +
                ", fontSize=" + fontSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigurableApplicationContext that = (ConfigurableApplicationContext) o;
        return Objects.equals(textAreaEditState, that.textAreaEditState) && Objects.equals(textAreaBreakLineState, that.textAreaBreakLineState) && Objects.equals(textAreaShowlineNumState, that.textAreaShowlineNumState) && Objects.equals(showToolBarState, that.showToolBarState) && Objects.equals(showMenuBarState, that.showMenuBarState) && Objects.equals(lastSystemThemes, that.lastSystemThemes) && Objects.equals(chineseConverState, that.chineseConverState) && Objects.equals(pictureQualityState, that.pictureQualityState) && Objects.equals(screenSize, that.screenSize) && Objects.equals(fontStyle, that.fontStyle) && Objects.equals(fontSize, that.fontSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textAreaEditState, textAreaBreakLineState, textAreaShowlineNumState, showToolBarState, showMenuBarState, lastSystemThemes, chineseConverState, pictureQualityState, screenSize, fontStyle, fontSize);
    }
}
