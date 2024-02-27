package com.ywf.framework.enums;

import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

/**
 * 内容类型
 *
 * @Author YWF
 * @Date 2024/1/21 19:28
 */
public enum TextTypeEnum {
    JSON("JSON", SyntaxConstants.SYNTAX_STYLE_JSON, "JSON类型"),
    URL("URL", SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT, "网址类型"),
    XML("XML", SyntaxConstants.SYNTAX_STYLE_XML, "XML类型"),
    JAVA("JAVA", SyntaxConstants.SYNTAX_STYLE_JAVA, "JAVA类型"),
    SQL("SQL", SyntaxConstants.SYNTAX_STYLE_SQL, "SQL类型"),
    JAVASCRIPT("JAVA_SCRIPT", SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT, "JavaScript类型"),
    PROPERTIES("PROPERTIES", SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE, "Properties类型"),
    YAML("YAML", SyntaxConstants.SYNTAX_STYLE_YAML, "YAML类型"),
    HTML("HTML", SyntaxConstants.SYNTAX_STYLE_HTML, "HTML类型"),
    CSS("CSS", SyntaxConstants.SYNTAX_STYLE_CSS, "CSS类型"),
    TEXT("TEXT", SyntaxConstants.SYNTAX_STYLE_JSON, "文本类型");

    private String type;
    private String syntaxStyle;
    private String discription;

    TextTypeEnum(String type, String syntaxStyle, String discription) {
        this.type = type;
        this.syntaxStyle = syntaxStyle;
        this.discription = discription;
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

    public void setSyntaxStyle(String syntaxStyle) {
        this.syntaxStyle = syntaxStyle;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
