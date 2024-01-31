package com.ywf.framework.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/1/28 22:31
 */
public class ObjectUtils {

    private static final ConcurrentHashMap<String, Object> VIEW_SOURCES = new ConcurrentHashMap<>();

    public static <T> T getBean(String clazzKey) {
        return (T) VIEW_SOURCES.get(clazzKey);
    }

    public static void setBean(String clazzKey, Object value) {
        Object val = VIEW_SOURCES.get(clazzKey);
        if (val == null) {
            VIEW_SOURCES.put(clazzKey, value);
        }
    }


}
