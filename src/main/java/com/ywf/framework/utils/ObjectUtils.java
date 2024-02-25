package com.ywf.framework.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象操作工具类
 *
 * @Author YWF
 * @Date 2024/1/28 22:31
 */
public class ObjectUtils {

    private static final ConcurrentHashMap<String, Object> VIEW_SOURCES = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class<?>, Object> VIEW_COMPONENTS = new ConcurrentHashMap<>();

    public static <T> T getBean(String clazzKey) {
        return (T) VIEW_SOURCES.get(clazzKey);
    }

    public static void setBean(String clazzKey, Object value) {
        VIEW_SOURCES.put(clazzKey, value);
    }


    public static <T> T getBean(Class<?> clazz) {
        return (T) VIEW_COMPONENTS.get(clazz);
    }

    public static void setBean(Class<?> clazz, Object value) {
        Object val = VIEW_COMPONENTS.get(clazz);
        if (val == null) {
            VIEW_COMPONENTS.put(clazz, value);
        }
    }

}
