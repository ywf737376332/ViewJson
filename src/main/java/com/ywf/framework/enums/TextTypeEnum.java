package com.ywf.framework.enums;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

/**
 * 内容类型
 *
 * @Author YWF
 * @Date 2024/1/21 19:28
 */
public enum TextTypeEnum {
    JSON("Language.Type.Json", "JSON", SyntaxConstants.SYNTAX_STYLE_JSON, "JSON类型"),
    URL("Language.Type.Url", "URL", SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT, "网址类型"),
    XML("Language.Type.Xml", "XML", SyntaxConstants.SYNTAX_STYLE_XML, "Xml类型"),
    JAVA("Language.Type.Java", "JAVA", SyntaxConstants.SYNTAX_STYLE_JAVA, "Java类型"),
    SQL("Language.Type.Sql", "SQL", SyntaxConstants.SYNTAX_STYLE_SQL, "SQL类型"),
    JAVASCRIPT("Language.Type.JavaScript", "JAVA_SCRIPT", SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT, "JavaScript类型"),
    PROPERTIES("Language.Type.Properties", "PROPERTIES", SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE, "Properties类型"),
    YAML("Language.Type.Yaml", "YAML", SyntaxConstants.SYNTAX_STYLE_YAML, "Yaml类型"),
    TEXT("Language.Type.Text", "TEXT", SyntaxConstants.SYNTAX_STYLE_JSON, "文本类型");

    private String messageKey;
    private String type;
    private String syntaxStyle;
    private String discription;

    TextTypeEnum(String messageKey, String type, String syntaxStyle, String discription) {
        this.messageKey = messageKey;
        this.type = type;
        this.syntaxStyle = syntaxStyle;
        this.discription = discription;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSyntaxStyle() {
        return syntaxStyle;
    }

    public String getDiscription() {
        return discription;
    }

}
