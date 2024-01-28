package com.ywf.framework.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/1/28 22:31
 */
public class ObjectUtils {

    private static final ConcurrentHashMap<String, Object> PROTOTYPE_MAP = new ConcurrentHashMap<>();

    public static <T> T getPrototype(Class<?> clazz) {
        return (T) PROTOTYPE_MAP.get(clazz.getName());
    }

    public static <T> T setPrototype(Class<?> clazz, T value) {
        return (T) PROTOTYPE_MAP.put(clazz.getName(), value);
    }

}
