package com.ywf.framework.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/1/28 22:31
 */
public class ObjectUtils {

    private static final ConcurrentHashMap<Class<?>, Object> VIEW_SOURCES = new ConcurrentHashMap<>();

    public static <T> T getBean(Class<?> clazz) {
        return (T) VIEW_SOURCES.get(clazz);
    }

    public static void setBean(Class<?> clazz, Object value) {
        Object val = VIEW_SOURCES.get(clazz);
        if (val == null) {
            VIEW_SOURCES.put(clazz, value);
        }
    }


}
