package com.ywf.component.splitDemo;

import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.MultiSplitLayout;

import javax.swing.*;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/2/5 11:39
 */
public class createSplitPane {
    private JComponent createSplitPane() {
        final JComponent demoContainer = new JXPanel();
        demoContainer.setLayout(new BorderLayout()); //BoxLayout(demoContainer, BoxLayout.LINE_AXIS));
        demoContainer.setBorder(BorderFactory.createLineBorder(Color.RED));

//      <snip> MultiSplit layout declaration
        String layout =
                "(ROW " +
                        "(LEAF name=selector weight=0.3)" +
                        "(COLUMN weight=0.7 " +
                        "(LEAF name= demo weight=0.7)" +
                        "(LEAF name=source weight=0.3)" +
                        ")" +
                        ")";
        MultiSplitLayout multiSplitLayout = new MultiSplitLayout(MultiSplitLayout.parseModel(layout));
//        </snip>
        JXMultiSplitPane splitPane = new JXMultiSplitPane();
        splitPane.setLayout(multiSplitLayout);
        splitPane.add("demo", demoContainer);
        return splitPane;
    }
}
