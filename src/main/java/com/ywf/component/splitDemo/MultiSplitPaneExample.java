package com.ywf.component.splitDemo;

import javax.swing.*;
import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout;

import java.awt.*;

public class MultiSplitPaneExample {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Multi-Pane Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout());
            JXMultiSplitPane multiSplitPaneDemo = createMultiSplitPaneDemo();
            frame.add(multiSplitPaneDemo);
            frame.setVisible(true);
        });
    }

    private static JXMultiSplitPane createMultiSplitPaneDemo() {
        JXMultiSplitPane msp = new JXMultiSplitPane();

        String layoutDef = "(COLUMN (ROW weight=0.8 (COLUMN weight=0.25 " +
                "(LEAF name=left.top weight=0.5) (LEAF name=left.middle weight=0.5))" +
                "(LEAF name=editor weight=0.75)) (LEAF name=bottom weight=0.2))";

        //MultiSplitLayout.Node modelRoot = MultiSplitLayout.parseModel( layoutDef );

        MultiSplitLayout.RowSplit modelRoot = new MultiSplitLayout.RowSplit();
        MultiSplitLayout.Leaf leaf1 = new MultiSplitLayout.Leaf("left.top weight=0.5");
        MultiSplitLayout.Leaf leaf2 = new MultiSplitLayout.Leaf("left.top1 weight=0.5");
        modelRoot.setChildren(leaf1, leaf2);
        msp.getMultiSplitLayout().setModel( modelRoot );


        msp.add( new JButton( "Left Top" ), "left.top" );
        msp.add( new JButton( "Left Middle" ), "left.top1" );
        msp.add( new JButton( "Editor" ), "editor" );
        msp.add( new JButton( "Bottom" ), "bottom" );

        // ADDING A BORDER TO THE MULTISPLITPANE CAUSES ALL SORTS OF ISSUES
        msp.setBorder( BorderFactory.createEmptyBorder( 4, 4, 4, 4 ) );

        return msp;
    }
}
