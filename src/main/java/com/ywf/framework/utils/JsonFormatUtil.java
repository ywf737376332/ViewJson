package com.ywf.framework.utils;

import org.apache.commons.lang.StringEscapeUtils;

import java.io.*;

public class JsonFormatUtil {
    private static String space = "    ";
    private static boolean existLeft = false;

    //写入文件
    private void writeFile(String filePath, String text) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        writer.write(text);
        writer.close();
    }

    //读取文件
    private String readFile(String filePath) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));

        String temp;
        String text = "";
        while ((temp = reader.readLine()) != null) {
            text = text + temp.trim() + "\r\n";
        }
        reader.close();
        return text;
    }

    //格式化json
    public static String formatJson(String json) {
        // 替换空格、制表符和换行符，并在':'后面加空格
        json = json.replaceAll("\\s", "").replaceAll(":(?!\\s)", ":  ");
        json = StringEscapeUtils.unescapeJava(json);
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

    //过滤有效的特殊字符
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

    //判断当前双引号是否为特殊字符
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

    //补充tab空格
    private static String indent(int number) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < number; i++) {
            result.append(space);
        }
        return result.toString();
    }
}

