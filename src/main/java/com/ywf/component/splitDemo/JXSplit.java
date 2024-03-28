package com.ywf.component.splitDemo;

import org.jdesktop.swingx.MultiSplitLayout;
import org.jdesktop.swingx.MultiSplitLayout.Divider;
import org.jdesktop.swingx.MultiSplitLayout.Leaf;
import org.jdesktop.swingx.MultiSplitLayout.Split;
/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/3/28 17:14
 */
public class JXSplit extends Split {

    public static final String LEFT = "left";
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom1";

    /** Creates a new instance of DefaultSplitPaneLayout */
    public JXSplit() {
        Split row = new Split();
        Split col = new Split();
        col.setRowLayout(false);
        setChildren(new Leaf(LEFT), new Divider(), col);
        col.setChildren(new Leaf(TOP), new Divider(), new Leaf(BOTTOM));
        System.out.println("col:"+col.toString());
    }

    public static void main(String[] args) {
        new JXSplit();
    }
}
