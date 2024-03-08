package com.ywf.framework.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ywf.framework.enums.TextTypeEnum;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
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

    private final static Logger logger = LoggerFactory.getLogger(TypeUtils.class);

    public static TextTypeEnum isType(String content) {
        if (isJson(content)) {
            return TextTypeEnum.JSON;
        } else if (isXML(content)) {
            return TextTypeEnum.XML;
        } else if (isURL(content)) {
            return TextTypeEnum.URL;
        } else if (isSQL(content)) {
            return TextTypeEnum.SQL;
        } else if (isProperties(content)) {
            return TextTypeEnum.PROPERTIES;
        } else if (isYaml(content)) {
            return TextTypeEnum.YAML;
        } else if (isJAVA(content)) {
            return TextTypeEnum.JAVA;
        } else if (isJavaScript(content)) {
            return TextTypeEnum.JAVASCRIPT;
        }
        return TextTypeEnum.TEXT;
    }

    public static boolean isJAVA(String content) {
        //JAVA语法至少包含一对(),继续检查
        if (!StrUtils.hasAnyBrackets(content)) {
            return false;
        }
        // 括号闭合,继续检查
        if (!isBracketValid(content)) {
            return false;
        }
        try {
            String keywordsRegex = "\\b(?:abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|int|interface|long|native|new|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while)\\b";
            Pattern pattern = Pattern.compile(keywordsRegex);
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                logger.info("当前内容类型：【Java】");
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isJavaScript(String content) {
        // 括号闭合，继续检查
        if (!isBracketValid(content)) {
            return false;
        }
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
        String sql = JsonUtil.compressingStr(content);
        if (StrUtil.isBlank(sql)) {
            return false;
        }
        for (String parsedSql : sql.split(";")) {
            try {
                CCJSqlParserUtil.parse(parsedSql);
                logger.info("当前内容类型：【Sql】");
                return true;
            } catch (JSQLParserException e) {
                return false;
            }
        }
        return false;
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
        if (nProps > 0) {
            logger.info("当前内容类型：【Yaml】");
            return true;
        }
        return false;
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
            logger.info("当前内容类型：【Json】");
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
            logger.info("当前内容类型：【Url】");
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
                logger.info("当前内容类型：【Xml】");
                return true;
            }
            return false;
        } catch (Exception e) {
            // 解析过程中出现异常则认为不是有效的XML
            return false;
        }
    }

    /**
     * Yaml类型判断
     *
     * @param content
     * @return
     */
    private static boolean isYaml(String content) {
        try {
            Yaml yaml = new Yaml();
            String tmpText = content.replaceAll("^---.*\n", "---\n");
            tmpText = tmpText.replaceAll("!ruby.*\n", "\n");
            Map load = yaml.load(tmpText);
            if (load == null) {
                return false;
            }
            logger.info("当前内容类型：【Yaml】");
            return true;
        } catch (YAMLException e) {
            return false;
        }
    }

    /**
     * 检查代码括号是否闭合
     *
     * @param s
     * @return
     */
    private static boolean isBracketValid(String s) {
        if (s == null || s.length() == 0)
            return false;
        char[] stack = new char[s.length()];
        int head = 0;
        for (char c : s.toCharArray()) {
            switch (c) {
                case '{':
                case '[':
                case '(':
                    stack[head++] = c;
                    break;
                case '}':
                    if (head == 0 || stack[--head] != '{') {
                        return false;
                    }
                    break;
                case ')':
                    if (head == 0 || stack[--head] != '(') {
                        return false;
                    }
                    break;
                case ']':
                    if (head == 0 || stack[--head] != '[') {
                        return false;
                    }
                    break;
            }
        }
        return head == 0;
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
