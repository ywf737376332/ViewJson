package com.ywf.framework.utils;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.ywf.component.toast.Toast;
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
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;

public class JsonUtil {
    private static final String FOUR_SPACE = "    ";
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
            result.append(FOUR_SPACE);
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
            case SQL:
                result = sqlFormatter(content);
                break;
            case URL:
                result = compressingStr(content);
                break;
            case TEXT:
                result = formatJson(content);
                break;
            case PROPERTIES:
                Toast.info(WindowUtils.getFrame(), "当前内容，不支持格式化操作！");
                break;
            case YAML:
                Toast.info(WindowUtils.getFrame(), "当前内容，不支持格式化操作！");
                break;
            case JAVA:
                Toast.info(WindowUtils.getFrame(), "当前内容，不支持格式化操作！");
                break;
            case JAVASCRIPT:
                Toast.info(WindowUtils.getFrame(), "当前内容，不支持格式化操作！");
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
     * 将对象按以格式化json的方式写出
     * 使用的json为Jackson
     *
     * @param obj obj
     * @return 格式化的json字符串
     * @throws JsonProcessingException JsonProcessingException
     */
    public static String formatJSONObject(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // 配置输出时汉字转Unicode字符
            mapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
            // 配置四个空格的缩进
            DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter(FOUR_SPACE, DefaultIndenter.SYS_LF);
            DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
            printer.indentObjectsWith(indenter); // 缩进JSON对象
            printer.indentArraysWith(indenter);  // 缩进JSON数组
            return mapper.writer(printer).writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON格式化失败：" + e.getMessage());
        }
    }

    private static String sqlFormatter(String jsonStr) {
        return SqlFormatter.format(jsonStr).replace(";", ";\n");
    }

    /**
     * 对URL进行编码
     *
     * @param jsonStr
     * @return
     */
    private static String urlEncoder(String jsonStr) {
        String content = compressingStr(jsonStr);
        try {
            return URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 给分号后面加换行
     *
     * @param sourceText
     * @return
     */
    public static String replaceSemicolonWithNewlineAndTrim(String sourceText) {
        String replaced = compressingStr(sourceText).replaceAll(";", ";\n\n");
        return replaced.trim();
    }
}

