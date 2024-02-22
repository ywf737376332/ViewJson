package com.ywf.framework.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ywf.framework.enums.TextTypeEnum;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类型判断
 *
 * @Author YWF
 * @Date 2024/2/22 14:47
 */
public class TypeUtils {

    public static TextTypeEnum isType(String content) {
        if (isJson(content)) {
            return TextTypeEnum.JSON;
        } else if (isXML(content)) {
            return TextTypeEnum.XML;
        } else if (isURL(content)) {
            return TextTypeEnum.URL;
        } else if (isJAVA(content)) {
            return TextTypeEnum.JAVA;
        } else if (isJavaScript(content)) {
            return TextTypeEnum.JAVASCRIPT;
        } else if (isSQL(content)) {
            return TextTypeEnum.SQL;
        } else if (isProperties(content)) {
            return TextTypeEnum.PROPERTIES;
        }
        return TextTypeEnum.TEXT;
    }

    private static boolean isJAVA(String content) {
        try {
            String keywordsRegex = "\\b(?:abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|int|interface|long|native|new|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while)\\b";
            Pattern pattern = Pattern.compile(keywordsRegex);
            Matcher matcher = pattern.matcher(content);
            return matcher.find();
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isJavaScript(String content) {
        try {
            String keywordsRegex = "\\b(?:break|case|catch|class|const|continue|debugger|default|delete|do|else|export|extends|finally|for|function|if|import|in|instanceof|new|return|super|switch|this|throw|try|typeof|var|void|while|with|yield)\\b";
            Pattern pattern = Pattern.compile(keywordsRegex);
            Matcher matcher = pattern.matcher(content);
            return matcher.find();
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isSQL(String content) {
        try {
            String keywordsRegex = "\\b(?:SELECT |INSERT |UPDATE |DELETE |CREATE TABLE |ALTER TABLE |TRUNCATE TABLE )\\b";
            Pattern pattern = Pattern.compile(keywordsRegex);
            Matcher matcher = pattern.matcher(content.toUpperCase());
            return matcher.find();
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isProperties(String content) {
        String text = content.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        Pattern p = Pattern.compile("\\w.*[=:] *.+");
        int nProps = 0;
        Scanner sc = new Scanner(text);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.startsWith("#")) {
                if (!p.matcher(line).matches()) {
                    sc.close();
                    return false;
                }
                nProps++;
            }
        }
        sc.close();
        return nProps > 0;
    }

    /**
     * 判断是否为json字符串
     *
     * @param content 字符串
     * @return 是否为json字符串
     */
    private static boolean isJson(String content) {
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
        String text = JsonUtil.compressingStr(content);
        try {
            if (text.startsWith("<") && text.endsWith(">")) {
                // 防止XXE攻击，禁用DTD加载 把dtd直接删除
                text = text.replaceAll("<!DOCTYPE.*?>", "");
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

    /**
     * 判断是否为普通字符串
     *
     * @param content 字符串
     * @return 是否为json字符串
     */
    private static boolean isText(String content) {
        String text = JsonUtil.compressingStr(content);
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

}
