package com.ywf.component.textAreaEditor;

import javax.swing.text.*;
import java.awt.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlView extends PlainView {

    private static HashMap<Pattern, Color> patternColors;
    //private static String TAG_PATTERN = "(</?[A-Za-z\\-_0-9]*)\\s?>?";
    private static String TAG_PATTERN = "^[0-9]+$";
    private static String TAG_ATTRIBUTE_PATTERN = "\\{\"([a-zA-Z_]+)\":(.+)}";
    private static String TAG_ATTRIBUTE_VALUE = "[a-z\\-]*\\:(\"[^\"]*\")";
    private static String TAG_COMMENT = "(<\\!--[^-]*+(?>-(?!->))*+-->)";
    private static String TAG_COMMENT_JAVA = "(/\\*\\*\\s+[\\u4e00-\\u9fa5]+:[\\w\\d\\(\\),]+\\s+\\*/)";
    //private static String TAG_CDATA = "(<\\!\\[CDATA\\[)";
    private static String TAG_CDATA = "(\\{)";
    //private static String TAG_CDATA_END = "(\\]\\]>)";
    private static String TAG_CDATA_END = "(\\})";
    //private static String TAG_JAVA = "(public|private|return)";
    private static String TAG_JAVA = "(header|body|data)";


    static {
        // NOTE: the order is important!
        patternColors = new LinkedHashMap<Pattern, Color>();
        patternColors.put(Pattern.compile(TAG_PATTERN), new Color(0, 0, 128));
        patternColors.put(Pattern.compile(TAG_CDATA), Color.red);
        patternColors.put(Pattern.compile(TAG_CDATA_END), Color.red);
        patternColors.put(Pattern.compile(TAG_ATTRIBUTE_PATTERN), new Color(40, 0, 252));
        patternColors.put(Pattern.compile(TAG_ATTRIBUTE_VALUE), new Color(43, 128, 0));
        patternColors.put(Pattern.compile(TAG_COMMENT), new Color(0, 128, 0));
        patternColors.put(Pattern.compile(TAG_COMMENT_JAVA), new Color(0, 128, 0));
        patternColors.put(Pattern.compile(TAG_JAVA), new Color(127,0,85));
    }

    public XmlView(Element element) {

        super(element);
        getDocument().putProperty(PlainDocument.tabSizeAttribute, 4);
    }

    @Override
    protected int drawUnselectedText(Graphics graphics, int x, int y, int p0,
                                     int p1) throws BadLocationException {

        Document doc = getDocument();
        String text = doc.getText(p0, p1 - p0);

        Segment segment = getLineBuffer();

        SortedMap<Integer, Integer> startMap = new TreeMap<Integer, Integer>();
        SortedMap<Integer, Color> colorMap = new TreeMap<Integer, Color>();

        for (Map.Entry<Pattern, Color> entry : patternColors.entrySet()) {

            Matcher matcher = entry.getKey().matcher(text);

            while (matcher.find()) {
                startMap.put(matcher.start(1), matcher.end());
                colorMap.put(matcher.start(1), entry.getValue());
            }
        }
        int i = 0;

        for (Map.Entry<Integer, Integer> entry : startMap.entrySet()) {
            int start = entry.getKey();
            int end = entry.getValue();

            if (i < start) {
                graphics.setColor(Color.black);
                doc.getText(p0 + i, start - i, segment);
                x = Utilities.drawTabbedText(segment, x, y, graphics, this, i);
            }

            graphics.setColor(colorMap.get(start));
            i = end;
            doc.getText(p0 + start, i - start, segment);
            x = Utilities.drawTabbedText(segment, x, y, graphics, this, start);
        }
        if (i < text.length()) {
            graphics.setColor(Color.black);
            doc.getText(p0 + i, text.length() - i, segment);
            x = Utilities.drawTabbedText(segment, x, y, graphics, this, i);
        }

        return x;
    }

}
