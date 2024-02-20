package com.ywf.framework.base;

import com.ywf.framework.utils.ObjectUtils;

import javax.swing.*;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/2/1 17:23
 */
public abstract class AbstractWindow extends JFrame {

    public JFrame _this = this;

    public AbstractWindow() {

    }

    /**
     * 根据组件名称获取对应组件，并自动转换为对应类型
     *
     * @param componentName 组件名称
     * @return 组件
     */
    @SuppressWarnings("unchecked")
    protected final <T extends Component> T getComponent(String componentName) {
        return ObjectUtils.getBean(componentName);
    }

    /**
     * 此方法是对指定的容器执行上述操作，用法相同。
     *
     * @param target      指定容器
     * @param name        组件的名称
     * @param component   待添加的组件
     * @param constraints 组件约束
     */
    protected <T extends Component> void addComponent(Container target, String name, T component, Object constraints) {
        ObjectUtils.setBean(name, component);
        target.add(component, constraints);
    }

    /**
     * 此方法是对指定的容器执行上述操作，用法相同。
     *
     * @param target    指定容器
     * @param name      组件的名称
     * @param component 待添加的组件
     */
    protected <T extends Component> void addComponent(Container target, String name, T component) {
        ObjectUtils.setBean(name, component);
        target.add(component);
    }
}
