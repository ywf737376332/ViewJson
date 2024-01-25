package com.ywf.component.demo;

import javax.swing.*;
import java.io.Serializable;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/1/25 11:18
 */
public class CashComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组件ID
     */
    private String id;

    /**
     * 容纳组件的容器面板,包含：JPanel JSplitPane 两种
     */
    private JComponent container;

    /**
     * 组件
     */
    private JScrollPane component;

    public CashComment() {
        this.id = String.valueOf(component.hashCode());
    }

    public CashComment(JComponent container, JScrollPane component) {
        this.id = String.valueOf(component.hashCode());
        this.container = container;
        this.component = component;
    }

    public String getId() {
        return id;
    }

    public JComponent getContainer() {
        return container;
    }

    public void setContainer(JComponent container) {
        this.container = container;
    }

    public JScrollPane getComponent() {
        return component;
    }

    public void setComponent(JScrollPane component) {
        this.component = component;
    }
}
