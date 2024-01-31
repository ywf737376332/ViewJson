package com.ywf.pojo;

import com.ywf.framework.annotation.PropertySource;
import com.ywf.framework.handle.ApplicationContext;

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

    @Override
    public String toString() {
        return "ApplicationConfig{" +
                "textAreaEditState=" + textAreaEditState +
                ", textAreaBreakLineState=" + textAreaBreakLineState +
                ", textAreaShowlineNumState=" + textAreaShowlineNumState +
                ", showToolBarState=" + showToolBarState +
                ", showMenuBarState=" + showMenuBarState +
                ", lastSystemThemes='" + lastSystemThemes + '\'' +
                ", chineseConverState=" + chineseConverState +
                ", pictureQualityState=" + pictureQualityState +
                ", screenSize=" + screenSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigurableApplicationContext user = (ConfigurableApplicationContext) o;
        return Objects.equals(textAreaEditState, user.textAreaEditState) && Objects.equals(textAreaBreakLineState, user.textAreaBreakLineState) && Objects.equals(textAreaShowlineNumState, user.textAreaShowlineNumState) && Objects.equals(showToolBarState, user.showToolBarState) && Objects.equals(showMenuBarState, user.showMenuBarState) && Objects.equals(lastSystemThemes, user.lastSystemThemes) && Objects.equals(chineseConverState, user.chineseConverState) && Objects.equals(pictureQualityState, user.pictureQualityState) && Objects.equals(screenSize, user.screenSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textAreaEditState, textAreaBreakLineState, textAreaShowlineNumState, showToolBarState, showMenuBarState, lastSystemThemes, chineseConverState, pictureQualityState, screenSize);
    }
}
