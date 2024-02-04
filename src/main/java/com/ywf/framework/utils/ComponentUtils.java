package com.ywf.framework.utils;

import com.ywf.component.JSONRSyntaxTextArea;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * 容器组件工具类
 *
 * @Author YWF
 * @Date 2024/2/4 10:59
 */
public class ComponentUtils {

    /**
     * 通过滚动面板组件获取里面的编辑框组件
     *
     * @param scrollPane
     * @return
     */
    public static JSONRSyntaxTextArea convertEditor(JScrollPane scrollPane) {
        Component viewComponent = scrollPane.getViewport().getView();
        if (viewComponent instanceof JSONRSyntaxTextArea) {
            return (JSONRSyntaxTextArea) viewComponent;
        }
        throw new RuntimeException("当前滚动框中未获取到编辑框组件，请检查");
    }

    public static <T> boolean testComsumer(LinkedList<T> list, Consumer<T> consumer) {
        for (T item : list) {
            consumer.accept(item);
        }
        return true;
    }

}
