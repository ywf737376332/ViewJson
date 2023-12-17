package com.ywf.framework.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author lyf
 * @description:
 * @version: v1.0
 * @since 2022-05-06 21:12
 */
public class JsonUtil {

    private JsonUtil() {
    }

    /**
     * 判断是否为String类型
     *
     * @param obj obj
     * @return obj
     */
    private static boolean isStringType(Object obj) {
        return obj instanceof String;
    }


    /**
     * 判断是否为json字符串
     *
     * @param str 字符串
     * @return 是否为json字符串
     */
    public static boolean isJsonString(String str) {
        if (StrUtil.isEmpty(str)) {
            return false;
        }

        try {
            JSONObject.parse(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 判断一个字符是否为网址
     * @date 2023/12/17 13:46
     *
     * @param urlStr
     */
    public static boolean isURL(String urlStr) {
        try {
            new URL(urlStr);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

}
