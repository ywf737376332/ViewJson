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
    private static final ConcurrentHashMap<String, Object> VIEW_COMPONENTS = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> VIEW_GROUPS = new ConcurrentHashMap<>();

    public static <T> T getBean(String clazzKey) {
        return (T) VIEW_SOURCES.get(clazzKey);
    }

    public static void setBean(String clazzKey, Object value) {
        VIEW_SOURCES.put(clazzKey, value);
    }

    public static void addGroupBean(String groupId, Object value) {
        if (VIEW_GROUPS.containsKey(groupId)) {
            ConcurrentHashMap<String, Object> SOURCES = VIEW_GROUPS.get(groupId);
            if (!SOURCES.containsKey(groupId + value.hashCode()) && !VIEW_COMPONENTS.containsValue(value)) {
                VIEW_COMPONENTS.put(groupId + value.hashCode(), value);
            }
        } else {
            VIEW_COMPONENTS.put(groupId + value.hashCode(), value);
            VIEW_GROUPS.put(groupId, VIEW_COMPONENTS);
        }
    }

    public static ConcurrentHashMap<String, Object> getGroupBean(String groupId) {
        return VIEW_GROUPS.get(groupId);
    }


}
