package com.ywf.framework.config;

import javax.swing.*;
import java.io.Serializable;

/**
 * 全局菜单注册KEY
 *
 * @Author YWF
 * @Date 2024/2/1 22:11
 */
public class GlobalMenuKEY extends JFrame implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String PREFIX = "#global:";

    public static final String EDITOR_FOCUS = PREFIX + "textArea:focus";
    public static final String TABBED_SPLIT_EDITOR = PREFIX + "tabbedSplitEditor";

}
