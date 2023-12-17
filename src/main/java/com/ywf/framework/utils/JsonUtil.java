package com.ywf.framework.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;

import java.net.MalformedURLException;
import java.net.URL;

public class JsonUtil {
    private static String space = "    ";
    private static boolean existLeft = false;

    private JsonUtil() {
    }

    /**
     * 格式化json
     *
     * @param jsonStr
     * @date 2023/12/17 14:48
     */
    public static String formatJson(String jsonStr) {
        // 替换空格、制表符和换行符，并在':'后面加空格
        String json = compressingStr(jsonStr).replaceAll(":(?!\\s)", ":  ");
        StringBuffer result = new StringBuffer();
        int length = json.length();
        int number = 0;
        char key = 0;
        for (int i = 0; i < length; i++) {
            key = json.charAt(i);

            if (isEffectSpecChr(i, key, json)) {
                if ((key == '[') || (key == '{')) {
                    result.append(key);
                    result.append("\r\n");
                    number++;
                    result.append(indent(number));
                    continue;
                }

                if ((key == ']') || (key == '}')) {
                    result.append("\r\n");
                    number--;
                    result.append(indent(number));
                    result.append(key);
                    continue;
                }

                if ((key == ',')) {
                    result.append(key);
                    result.append("\r\n");
                    result.append(indent(number));
                    continue;
                }
            }
            result.append(key);
        }
        return result.toString();
    }

    /**
     * 过滤有效的特殊字符
     *
     * @param index
     * @param key
     * @param json
     * @date 2023/12/17 14:48
     */
    private static boolean isEffectSpecChr(int index, char key, String json) {
        boolean flag = false;

        if (isDouMark(index, String.valueOf(key), json)) {
            if (existLeft) {
                existLeft = false;
            } else {
                existLeft = true;
            }
        } else {
            if ((key == '[')
                    || (key == '{')
                    || (key == ']')
                    || (key == '}')
                    || (key == ',')) {
                if (!existLeft) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 判断当前双引号是否为特殊字符
     *
     * @param index
     * @param key
     * @param json
     * @date 2023/12/17 14:48
     */
    private static boolean isDouMark(int index, String key, String json) {
        if (key.equals("\"") && index >= 0) {
            if (index == 0) {
                return true;
            }

            char c = json.charAt(index - 1);
            if (c != '\\') {
                return true;
            }
        }
        return false;
    }

    /**
     * 补充tab空格
     *
     * @param number
     * @date 2023/12/17 14:48
     */
    private static String indent(int number) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < number; i++) {
            result.append(space);
        }
        return result.toString();
    }

    /**
     * 压缩字符串
     *
     * @param jsonStr
     * @return
     */
    public static String compressingStr(String jsonStr) {
        return jsonStr.replaceAll("\\s", "");
    }

    /**
     * 取出转义
     *
     * @param jsonStr
     * @return
     */
    public static String unescapeJSON(String jsonStr) {
        return StringEscapeUtils.unescapeJavaScript(jsonStr);
    }

    /**
     * 转义
     *
     * @param jsonStr
     * @return
     */
    public static String escapeJSON(String jsonStr) {
        return StringEscapeUtils.escapeJavaScript(jsonStr);
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
     *
     * @param urlStr
     * @date 2023/12/17 13:46
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

