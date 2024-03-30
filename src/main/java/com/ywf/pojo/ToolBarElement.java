package com.ywf.pojo;

import javax.swing.*;
import java.io.Serializable;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/3/26 18:28
 */
public class ToolBarElement implements Serializable {
    private String text;
    private Boolean isShow;
    private JButton button;

    public ToolBarElement() {
    }

    public ToolBarElement(String text, Boolean isShow, JButton button) {
        this.text = text;
        this.isShow = isShow;
        this.button = button;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "ToolBarElement{" +
                "text='" + text + '\'' +
                ", isShow=" + isShow +
                ", button=" + button +
                '}';
    }
}
