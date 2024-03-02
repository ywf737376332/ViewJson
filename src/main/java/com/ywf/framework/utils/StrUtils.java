package com.ywf.framework.utils;

/**
 * 字符串处理工具类
 *
 * @Author YWF
 * @Date 2024/2/27 22:36
 */
public class StrUtils {

    public static int counts(String str) {
        if (str == null){
            return 0;
        }
        return JsonUtil.compressingStr(str).length();
    }

    public static String[] strSplit(String str) {
        return str.split("_");
    }

}
