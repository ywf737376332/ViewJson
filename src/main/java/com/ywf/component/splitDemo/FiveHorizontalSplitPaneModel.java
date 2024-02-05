package com.ywf.component.splitDemo;

import org.jdesktop.swingx.MultiSplitLayout;
import org.jdesktop.swingx.MultiSplitLayout.Split;
/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/2/5 11:07
 */
public class FiveHorizontalSplitPaneModel extends Split
{
    // 5 possible positions
    public  static  final String P1 = "1";
    public  static  final String P2 = "2";
    public  static  final String P3 = "3";
    public  static  final String P4 = "4";

    public MultiSplitLayout.Leaf p1;
    public MultiSplitLayout.Leaf p2;
    public MultiSplitLayout.Leaf p3;
    public MultiSplitLayout.Leaf p4;

    public FiveHorizontalSplitPaneModel()
    {
        this( false);
    }

    public FiveHorizontalSplitPaneModel( boolean isEqualyWeighted)
    {
        setRowLayout( true);
        p1 =  new MultiSplitLayout.Leaf(P1);
        p2 =  new MultiSplitLayout.Leaf(P2);
        p3 =  new MultiSplitLayout.Leaf(P3);
        p4 =  new MultiSplitLayout.Leaf(P4);
        if(isEqualyWeighted)
        {
            p1.setWeight(1);
            /*p2.setWeight(0.25);
            p3.setWeight(0.25);
            p4.setWeight(0.25);*/
        }
        setChildren(p1,  new MultiSplitLayout.Divider(), p2,  new MultiSplitLayout.Divider(),
                p3,  new MultiSplitLayout.Divider(), p4);
    }
}