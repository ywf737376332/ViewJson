package com.ywf.framework.base;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/2/1 17:23
 */
public abstract class AbstractWindow extends JFrame{

    protected JFrame _this = this;

    private final Map<String, Component> componentMap = new ConcurrentHashMap<>();

    /**
     * 根据组件名称获取对应组件，并自动转换为对应类型
     * @param componentName 组件名称
     * @return 组件
     */
    @SuppressWarnings("unchecked")
    protected final <T extends Component> T getComponent(String componentName){
        return (T) componentMap.get(componentName);
    }

    /**
     * 此方法是对指定的容器执行上述操作，用法相同。
     * @param target 指定容器
     * @param name 组件的名称
     * @param component 待添加的组件
     * @param constraints 组件约束
     * @param consumer 组件配置在这里写
     */
    protected <T extends Component> void addComponent(Container target, String name, T component, Object constraints/*, Consumer<T> consumer*/){
        /*if(consumer != null)
            consumer.accept(component);*/
        this.componentMap.put(name, component);
        target.add(component,constraints);
    }
}
