package com.ywf.component.setting;

import com.ywf.component.ToolBarBuilder;
import com.ywf.pojo.ToolBarElement;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 工具栏设置面板
 *
 * @Author YWF
 * @Date 2024/3/26 21:56
 */
public class ToolBarPanel extends JPanel {

    public ToolBarPanel() {
        super(new BorderLayout());
        init();
        setPreferredSize(new Dimension(400, 500));
    }

    private void init() {
        add(makeUI2(),BorderLayout.CENTER);
    }

    private Component makeUI2() {
        ArrayListModel<ToolBarElement> model = new ArrayListModel<>();
        ToolBarBuilder toolBarBuilder = ToolBarBuilder.getInstance();
        JToolBar toolBar = toolBarBuilder.getToolBar();
        LinkedList<ToolBarElement> toolBarElementList = toolBarBuilder.getToolBarElementList();
        toolBarElementList.forEach(element -> {
            model.add(element);
        });
        JList<String> leftList = makeList(model);
        JList<String> rightList = makeList(new ArrayListModel<>());

        JButton button1 = makeButton(">");
        button1.addActionListener(e -> move2(leftList, rightList));

        JButton button2 = makeButton("<");
        button2.addActionListener(e -> move2(rightList, leftList));

        return SpringLayoutUtil.makePanel(leftList, rightList, button1, button2);
    }

    private static <E> void move2(JList<E> from, JList<E> to) {
        int[] selectedIndices = from.getSelectedIndices();
        if (selectedIndices.length > 0) {
            ((ArrayListModel<E>) to.getModel()).addAll(from.getSelectedValuesList());
            ((ArrayListModel<E>) from.getModel()).remove(selectedIndices);
        }
    }

    private static JButton makeButton(String title) {
        JButton button = new JButton(title);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        return button;
    }

    private static <E> JList<E> makeList(ListModel<E> model) {
        JList<E> list = new JList<>(model);
        JPopupMenu popup = new JPopupMenu();
        popup.add("reverse").addActionListener(e -> {
            ListSelectionModel sm = list.getSelectionModel();
            for (int i = 0; i < list.getModel().getSize(); i++) {
                if (sm.isSelectedIndex(i)) {
                    sm.removeSelectionInterval(i, i);
                } else {
                    sm.addSelectionInterval(i, i);
                }
            }
        });
        list.setComponentPopupMenu(popup);
        return list;
    }



}


class ArrayListModel<E> extends AbstractListModel<String> {
    private final List<E> delegate = new ArrayList<>();

    public void add(E element) {
        int index = delegate.size();
        delegate.add(element);
        fireIntervalAdded(this, index, index);
    }

    public void addAll(Collection<? extends E> c) {
        delegate.addAll(c);
        fireIntervalAdded(this, 0, delegate.size());
    }

    public E remove(int index) {
        E rv = delegate.get(index);
        delegate.remove(index);
        fireIntervalRemoved(this, index, index);
        return rv;
    }

    public void remove(int... selectedIndices) {
        if (selectedIndices.length > 0) {
            int max = selectedIndices.length - 1;
            for (int i = max; i >= 0; i--) {
                delegate.remove(selectedIndices[i]);
            }
            fireIntervalRemoved(this, selectedIndices[0], selectedIndices[max]);
        }
    }

    public List<E> getDelegate() {
        return delegate;
    }

    @Override
    public String getElementAt(int index) {
        return ((ToolBarElement)delegate.get(index)).getText();
    }

    @Override
    public int getSize() {
        return delegate.size();
    }
}


final class SpringLayoutUtil {
    private SpringLayoutUtil() {
        /* Singleton */
    }

    public static void setScaleAndAdd(Container parent, SpringLayout layout, Component child, Rectangle2D.Float r) {
        Spring panelw = layout.getConstraint(SpringLayout.WIDTH, parent);
        Spring panelh = layout.getConstraint(SpringLayout.HEIGHT, parent);

        SpringLayout.Constraints c = layout.getConstraints(child);
        c.setX(Spring.scale(panelw, r.x));
        c.setY(Spring.scale(panelh, r.y));
        c.setWidth(Spring.scale(panelw, r.width));
        c.setHeight(Spring.scale(panelh, r.height));

        parent.add(child);
    }

    public static Component makePanel(JList<?> leftList, JList<?> rightList, JButton l2rButton, JButton r2lButton) {
        Box box = Box.createVerticalBox();
        box.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
        box.add(Box.createVerticalGlue());
        box.add(l2rButton);
        box.add(Box.createVerticalStrut(20));
        box.add(r2lButton);
        box.add(Box.createVerticalGlue());

        JPanel cpn = new JPanel(new GridBagLayout());
        cpn.add(box);

        JScrollPane spl = new JScrollPane(leftList);
        JScrollPane spr = new JScrollPane(rightList);

        SpringLayout layout = new SpringLayout();
        JPanel p = new JPanel(layout);
        setScaleAndAdd(p, layout, spl, new Rectangle2D.Float(.05f, .05f, .40f, .90f));
        setScaleAndAdd(p, layout, cpn, new Rectangle2D.Float(.45f, .05f, .10f, .90f));
        setScaleAndAdd(p, layout, spr, new Rectangle2D.Float(.55f, .05f, .40f, .90f));

        /*=======================================================================================*/
        Box boxBtn = Box.createVerticalBox();
        boxBtn.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
        boxBtn.add(Box.createVerticalGlue());
        JButton button = new JButton("ok");
        boxBtn.add(button);
        boxBtn.add(Box.createVerticalStrut(20));
        boxBtn.add(new JButton("cancle"));
        boxBtn.add(Box.createVerticalGlue());

        JPanel cpnBtn = new JPanel(new GridBagLayout());
        cpnBtn.add(boxBtn);
        setScaleAndAdd(p, layout, cpnBtn, new Rectangle2D.Float(.95f, .05f, .05f, .90f));

        button.addActionListener(e -> {
            rightList.getModel();
        });

        return p;
    }
}