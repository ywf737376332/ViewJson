package com.ywf.component.splitDemo;

import com.sun.beans.decoder.ElementHandler;
import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.MultiSplitLayout;
import org.jdesktop.swingx.multisplitpane.DefaultSplitPaneModel;

import javax.swing.*;
import java.awt.*;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/2/5 11:39
 */
public class createSplitPane extends JFrame{

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Multi-Pane Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout());
            JXMultiSplitPane multiSplitPaneDemo = createSplitPane();
            frame.add(createContents());
            frame.setVisible(true);
        });
    }

    private static JXMultiSplitPane createSplitPane() {
        final JComponent demoContainer = new JXPanel();
        demoContainer.setLayout(new BorderLayout()); //BoxLayout(demoContainer, BoxLayout.LINE_AXIS));
        demoContainer.setBorder(BorderFactory.createLineBorder(Color.RED));

//      <snip> MultiSplit layout declaration
        String layout =
                "(ROW " +
                        "(LEAF name=selector weight=0.6)" +
                        "(COLUMN weight=0.4 " +
                        "(LEAF name= demo weight=0.7)" +
                        "(LEAF name=source weight=0.3)" +
                        ")" +
                        ")";
        MultiSplitLayout multiSplitLayout = new MultiSplitLayout(MultiSplitLayout.parseModel(layout));
//        </snip>
        JXMultiSplitPane splitPane = new JXMultiSplitPane();
        splitPane.setLayout(multiSplitLayout);
        splitPane.add("demo", demoContainer);
        splitPane.add(new JButton("哈哈"), "selector");
        splitPane.add(new JTextArea("哈发顺丰哈"), "source");
        return splitPane;
    }

    private static Component createContents() {
        JXMultiSplitPane sp = new JXMultiSplitPane();
        sp.setModel(new DefaultSplitPaneModel());
        sp.add(new JButton("left"), JXSplit.LEFT);
        sp.add(new JButton("top"), JXSplit.TOP);
        sp.add(new JButton("bottom"), JXSplit.BOTTOM);
        return sp;
    }
}
