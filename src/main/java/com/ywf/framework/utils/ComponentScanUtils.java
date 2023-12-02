package com.ywf.framework.utils;

import java.awt.*;

/**
 * 通过父组件骚猫子组件
 *
 * @Author YWF
 * @Date 2023/12/2 15:24
 */
public class ComponentScanUtils {
    public static <T extends Component> T getComponentByType(Container container, Class<T> type) {
        for (Component component : container.getComponents()) {
            if (type.isInstance(component)) {
                return type.cast(component);
            }
            if (component instanceof Container) {
                T found = getComponentByType((Container) component, type);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
