package com.ywf.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 *
 * @Author YWF
 * @Date 2024/2/27 22:36
 */
public class StrUtils {
    private final static Logger logger = LoggerFactory.getLogger(StrUtils.class);

    /**
     * 字符串长度
     *
     * @param str
     * @return
     */
    public static int counts(String str) {
        if (str == null) {
            return 0;
        }
        return JsonUtil.compressingStr(str).length();
    }

    /**
     * 字符串分割
     *
     * @param str
     * @return
     */
    public static String[] strSplit(String str) {
        return str.split("_");
    }

    /**
     * 判断字符串是否含有括号
     *
     * @param str
     * @return
     */
    public static boolean hasAnyBrackets(String str) {
        String pattern = "\\(|\\[|\\{|\\)|\\]|\\}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static boolean isValidBrackets(String s) {
        Stack<Integer> stack = new Stack<>();        //定义一个栈
        int len = s.length();
        for (int i = 0; i < len; ++i) {        //遍历每一个字符
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {        //左括号符合题述，入栈
                stack.push(i);
            } else if (c == ')' || c == ']' || c == '}') {       // 右括号分多种情况
                if (stack.isEmpty()) {
                    return false;
                }
                int j = stack.pop();
                char t = s.charAt(j);
                if (c == ')' && t != '(' || c == ']' && t != '[' || c == '}' && t != '{') {
                    logger.error("%d:%s%n", i, "无法匹配对应的左括号");
                    return false;
                }
            }
        }
        if (!stack.isEmpty()) {
            return false;
        }
        return true;
    }

}
