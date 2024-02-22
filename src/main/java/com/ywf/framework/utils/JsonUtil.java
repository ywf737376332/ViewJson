package com.ywf.framework.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ywf.framework.enums.TextTypeEnum;
import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
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
        String json = compressingStr(jsonStr).replaceAll(":(?!\\s)", ":");
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
        return jsonStr.replaceAll("\\s*\\n\\s*", " ").replaceAll(">\\s+<", "><").trim();
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
        return StringEscapeUtils.escapeJavaScript(compressingStr(jsonStr));
    }

    public static String contentFormat(TextTypeEnum type, String content) {
        String result = null;
        switch (type) {
            case JSON:
                result = formatJson(content);
                break;
            case XML:
                result = prettyPrintByTransformer(content, 4, false);
                break;
            case URL:
                result = compressingStr(content);
                break;
            case TEXT:
                result = formatJson(content);
                break;
            default:
        }
        return result;
    }

    /**
     * 格式化xml
     *
     * @param xmlString         xml内容
     * @param indent            向前缩进多少空格
     * @param ignoreDeclaration 是否忽略描述
     * @return 格式化后的xml
     */
    public static String prettyPrintByTransformer(String xmlString, int indent, boolean ignoreDeclaration) {
        xmlString = compressingStr(xmlString);
        try {
            InputSource src = new InputSource(new StringReader(xmlString));
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, ignoreDeclaration ? "yes" : "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            Writer out = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(out));
            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error occurs when pretty-printing xml:\n" + xmlString, e);
        }
    }


    /**
     * 判断内容类型
     *
     * @param content
     * @date 2024/1/21 19:55
     */
    public static TextTypeEnum isType(String content) {
        if (isJsonString(content)) {
            return TextTypeEnum.JSON;
        } else if (isURL(content)) {
            return TextTypeEnum.URL;
        } else if (isText(content)) {
            return TextTypeEnum.TEXT;
        } else if (isXML(content)) {
            return TextTypeEnum.XML;
        }
        return TextTypeEnum.TEXT;
    }

    /**
     * 判断是否为普通字符串
     *
     * @param content 字符串
     * @return 是否为json字符串
     */
    private static boolean isText(String content) {
        String text = compressingStr(content);
        if (StrUtil.isBlank(content)) {
            return true;
        }
        if (text.contains("\\")) {
            return true;
        }
        if (text.startsWith("<") && text.endsWith(">")) {
            return false;
        }
        if (text.startsWith("{") && text.endsWith("}")) {
            return false;
        }
        if (text.startsWith("http")) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否为json字符串
     *
     * @param content 字符串
     * @return 是否为json字符串
     */
    private static boolean isJsonString(String content) {
        if (StrUtil.isEmpty(content)) {
            return false;
        }
        try {
            JSONObject.parse(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 判断一个字符是否为网址
     *
     * @param content
     * @date 2023/12/17 13:46
     */
    private static boolean isURL(String content) {
        try {
            new URL(content);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * 判断一个字符是否为XML
     *
     * @param content
     * @date 2023/12/17 13:46
     */
    private static boolean isXML(String content) {
        String text = compressingStr(content);
        try {
            if (text.startsWith("<") && text.endsWith(">")) {
                // 防止XXE攻击，禁用DTD加载
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                dbFactory.setNamespaceAware(false);
                dbFactory.setValidating(false);
                // 尝试解析字符串为XML文档
                dbFactory.newDocumentBuilder().parse(new InputSource(new StringReader(text)));
                // 如果没有抛出异常，则字符串是有效的XML格式
                return true;
            }
            return false;
        } catch (Exception e) {
            // 解析过程中出现异常则认为不是有效的XML
            return false;
        }
    }


}

