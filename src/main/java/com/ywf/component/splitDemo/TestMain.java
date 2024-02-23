package com.ywf.component.splitDemo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/2/22 11:50
 */
public class TestMain {
    public static void main(String[] args) {
        String code1 = "public static TextTypeEnum isType(String content) {if (isJsonString(content)) {return TextTypeEnum.JSON;} else if (isURL(content)) {return TextTypeEnum.URL;} else if (isText(content)) {return TextTypeEnum.TEXT;} else if (isXML(content)) {return TextTypeEnum.XML;}return TextTypeEnum.TEXT;}";
        String code2 = "function hello() { console.log('Hello, World!'); }";
        String code3 = "<root><element>content</element></root>";
//Theme theme = Theme.load(RSyntaxTextAreaEditor.class.getResourceAsStream("/org/fife/ui/rsyntaxtextarea/themes/idea.xml"));
        System.out.println("Code 1 is: " + detectCodeType(code1));
        System.out.println("Code 2 is: " + detectCodeType(code2));
        System.out.println("Code 3 is: " + detectCodeType(code3));
    }

    public static String detectCodeType(String code) {
        // Java关键字正则表达式
        String javaKeywordsRegex = "\\b(?:abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|int|interface|long|native|new|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while)\\b";
        // JavaScript关键字正则表达式
        String jsKeywordsRegex = "\\b(?:break|case|catch|class|const|continue|debugger|default|delete|do|else|export|extends|finally|for|function|if|import|in|instanceof|new|return|super|switch|this|throw|try|typeof|var|void|while|with|yield)\\b";
        String xmlKeywordsRegex = "^<\\?xml.*?\\?>.*?<\\/.*?>$";

        Pattern javaPattern = Pattern.compile(javaKeywordsRegex);
        Pattern jsPattern = Pattern.compile(jsKeywordsRegex);

        Matcher javaMatcher = javaPattern.matcher(code);
        Matcher jsMatcher = jsPattern.matcher(code);

        Pattern xmlPattern = Pattern.compile(xmlKeywordsRegex);
        Matcher xmlMatcher = xmlPattern.matcher(code);

        if (javaMatcher.find()) {
            return "Java";
        } else if (jsMatcher.find()) {
            return "JavaScript";
        }else if (xmlMatcher.find()) {
            return "xml";
        } else {
            return "Unknown";
        }
    }
}