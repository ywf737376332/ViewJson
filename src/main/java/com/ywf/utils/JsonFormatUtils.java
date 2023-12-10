package com.ywf.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;

public class JsonFormatUtils {
    private String space = "    ";
    private boolean existLeft = false;
    private final static String TAG = "\r\n";

    public static void main(String[] args) {
//        JsonFormatTool tool = new JsonFormatTool();
//        String path = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()+"\\json.txt";
//        String text = tool.readFile(path).replaceAll("\r\n","");
//        String json = tool.formatJson(text);
//        tool.writeFile(path,json);

        JsonFormatUtils tool = new JsonFormatUtils();
        String text = "{\"info\":[{\"code\":\"C\",\"key\":\"028\",\"nearest\":\"NO\",\"value\":\"好冷\"},{\"code\":\"N\",\"key\":\"0771\",\"nearest\":\"NO,\",\"value\":\"好{冷}\"},{\"code\":\"L\",\"key\":\"07[72\",\"nearest\":\"N]O\",\"value\":\"好冷\"},{\"code\":\"G\",\"key\":\"0773\",\"nearest\":\"NO\",\"value\":\"好冷\"}],\"resultCode\":\"0\",\"resultInfo\":\"SUCCESS\"}";
        String json = tool.formatJson(2, text);
        System.out.println("json" + json);
    }

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
    public String formatJson(String json) {
        json = json.replaceAll("\\r?\\n", "").replace(" ", "");
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

    private String formatJson(int formatType, String text) {
        //格式化字符串
        Object ret = null;
        JSONArray retArr = null;
        String jsonStr = null;
        if (StringUtils.isBlank(text)) {
            return "";
        }
        try {

            if (formatType == 1) {
                ret = JSON.parse(text);
            } else if (formatType == 2 || formatType == 3) {
                ret = JSON.parse(text, Feature.OrderedField);
            }
            if (formatType == 1) {
                jsonStr = JSON.toJSONString(ret, SerializerFeature.PrettyFormat, SerializerFeature.MapSortField);
            } else if (formatType == 2) {
                jsonStr = JSON.toJSONString(ret, SerializerFeature.PrettyFormat);
            } else if (formatType == 3) {
                jsonStr = JSON.toJSONString(ret);
            }

            if (jsonStr != null) {
                jsonStr = StringEscapeUtils.unescapeJava(jsonStr);
                if (formatType == 3) {
                    jsonStr = "";
                }
            }

        } catch (Exception ex) {
            System.out.println("非法JSON字符串！" + ex.getMessage());
        }
        //System.gc();
        System.gc();
        return jsonStr;
    }

    //过滤有效的特殊字符
    private boolean isEffectSpecChr(int index, char key, String json) {
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
    private boolean isDouMark(int index, String key, String json) {
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
    private String indent(int number) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < number; i++) {
            result.append(space);
        }
        return result.toString();
    }
}

