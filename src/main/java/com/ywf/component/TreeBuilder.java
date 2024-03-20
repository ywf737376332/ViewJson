package com.ywf.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.ywf.framework.config.TreeKit;
import com.ywf.framework.utils.IconUtils;
import org.apache.commons.configuration.tree.TreeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 树组件
 *
 * @Author YWF
 * @Date 2024/3/20 14:45
 */
public class TreeBuilder {

    private final static Logger logger = LoggerFactory.getLogger(TreeUtils.class);

    private TreeBuilder() {
    }

    volatile private static TreeBuilder instance = null;

    public static TreeBuilder getInstance() {
        if (instance == null) {
            synchronized (TreeBuilder.class) {
                if (instance == null) {
                    instance = new TreeBuilder();
                }
            }
        }
        return instance;
    }


    public JTree initTree(String text) {
        Map jsonEleTreeMap = new HashMap();
        Object ret = JSON.parse(text, Feature.OrderedField);
        //创建树节点
        JTree tree = getTree();
        jsonEleTreeMap.put(tree.hashCode(), ret);
        DefaultMutableTreeNode root = TreeKit.objNode("JSON-Settings");
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        try {
            createJsonTree(ret, root);
            model.setRoot(root);
            setNodeIcon(tree);
        } catch (Exception ex) {
            root.removeAllChildren();
            model.setRoot(root);
            logger.error("创建json树失败！{}", ex.getMessage());
        }
        System.gc();
        return tree;
    }

    private JTree getTree() {
        JTree tree = createTree();
        return tree == null ? new JTree() : tree;
    }

    private JTree createTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("o-JSON");
        DefaultTreeModel model = new DefaultTreeModel(root);
        JTree tree = new JTree(model);
        setNodeIcon(tree);
        return tree;
    }

    /**
     * 构造json树结构.
     *
     * @param obj   JsonElement
     * @param pNode DefaultMutableTreeNode
     */
    private void createJsonTree(Object obj, DefaultMutableTreeNode pNode) {
        if (obj == null) {
            pNode.add(TreeKit.nullNode("NULL"));
        }
        if (obj instanceof JSONObject) {
            createJsonObject((JSONObject) obj, pNode);
        } else if (obj instanceof JSONArray) {
            createJsonArray((JSONArray) obj, pNode, "(" + ((JSONArray) obj).size() + ")");
        }
    }

    /**
     * 处理jsoon对象.
     *
     * @param obj
     * @param pNode
     */
    private void createJsonObject(JSONObject obj, DefaultMutableTreeNode pNode) {
        Set<Map.Entry<String, Object>> entries = obj.entrySet();
        for (Map.Entry<String, Object> el : entries) {
            String key = el.getKey();
            Object val = el.getValue();
            if (val instanceof JSONObject) {
                DefaultMutableTreeNode node = TreeKit.objNode(key);
                createJsonObject((JSONObject) val, node);
                pNode.add(node);
            } else if (val instanceof JSONArray) {
                createJsonArray((JSONArray) val, pNode, key);
            } else {
                formatJsonPrimitive(key, val, pNode);
            }
        }
    }

    /**
     * 处理json数组.
     *
     * @param arr
     * @param pNode
     * @param key
     */
    private void createJsonArray(JSONArray arr, DefaultMutableTreeNode pNode, String key) {
        if (arr == null || arr.isEmpty()) {
            return;
        }
        int index = 0;
        DefaultMutableTreeNode child = TreeKit.arrNode(key);

        for (int i = 0; i < arr.size(); ++i) {
            Object obj = arr.get(i);
            if (obj instanceof JSONArray) {
                createJsonArray((JSONArray) obj, child, TreeKit.fkey(index));
            } else if (obj instanceof JSONObject) {
                DefaultMutableTreeNode node = TreeKit.objNode(index);
                createJsonObject((JSONObject) obj, node);
                child.add(node);
            }
            ++index;
        }

        pNode.add(child);
    }

    private void formatJsonPrimitive(String key, Object pri, DefaultMutableTreeNode pNode) {
        if (pri == null) {
            pNode.add(TreeKit.nullNode(key));
            return;
        }
        if (pri instanceof Number) {
            pNode.add(TreeKit.numNode(key, String.valueOf(pri)));
        } else if (pri instanceof Boolean) {
            pNode.add(TreeKit.boolNode(key, (Boolean) pri));
        } else if (pri instanceof String) {
            pNode.add(TreeKit.strNode(key, String.valueOf(pri)));
        } else {
            pNode.add(TreeKit.numNode(key, String.valueOf(pri)));
        }
    }

    private void setNodeIcon(JTree tree) {
        tree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                String tmp = node.toString();
                if (tmp.startsWith(TreeKit.sArr)) {
                    this.setIcon(IconUtils.getImageIcon("/icons/tree/a.gif"));
                    this.setText(tmp.substring(2));
                } else if (tmp.startsWith(TreeKit.sStr)) {
                    this.setIcon(IconUtils.getImageIcon("/icons/tree/v.gif"));
                    this.setText(tmp.substring(2));
                } else if (tmp.startsWith(TreeKit.sObj)) {
                    this.setIcon(IconUtils.getImageIcon("/icons/tree/o.gif"));
                    this.setText(tmp.substring(2));
                } else if (tmp.startsWith(TreeKit.sNum)) {
                    this.setIcon(IconUtils.getImageIcon("/icons/tree/n.gif"));
                    this.setText(tmp.substring(2));
                } else if (tmp.startsWith(TreeKit.sNull)) {
                    this.setIcon(IconUtils.getImageIcon("/icons/tree/k.gif"));
                    this.setText(tmp.substring(2));
                } else if (tmp.startsWith(TreeKit.sBool)) {
                    this.setIcon(IconUtils.getImageIcon("/icons/tree/v.gif"));
                    this.setText(tmp.substring(2));
                } else {
                    this.setIcon(IconUtils.getImageIcon("/icons/tree/v.gif"));
                    this.setText(tmp.substring(2));
                }
                return this;
            }
        });
    }

}