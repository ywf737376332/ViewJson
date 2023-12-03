package com.ywf.framework.component;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalComponents {
    private static GlobalComponents instance;
    private final static ConcurrentHashMap<String, Component> COMPONENT_MAP = new ConcurrentHashMap<>();

    private GlobalComponents() {
    }

    public static synchronized GlobalComponents getInstance() {
        if (instance == null) {
            instance = new GlobalComponents();
        }
        return instance;
    }

    public void addComponent(String componentKey, JComponent component) {
        if (component == null) {
            throw new RuntimeException("组件：" + component + "为空，不能进行添加");
        }
        if (!COMPONENT_MAP.containsKey(componentKey)) {
            COMPONENT_MAP.put(componentKey, component);
        }
    }

    public Component getComponent(String componentKey) {
        for (Map.Entry<String, Component> entry : COMPONENT_MAP.entrySet()) {
            if (COMPONENT_MAP.containsKey(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
