package com.ywf.framework.config;

import javax.swing.*;
import java.io.Serializable;

/**
 * 全局菜单注册KEY
 *
 * @Author YWF
 * @Date 2024/2/1 22:11
 */
public class GlobalKEY extends JFrame implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String PREFIX = "#global:";

    public static final String MAIN_FRAME = PREFIX + "mainFrame";
    public static final String TABBED_SPLIT_EDITOR = PREFIX + "tabbedSplitEditor";
    public static final String STATE_BAR_TEXT_TYPE = PREFIX + "stateBarTextType";
    public static final String STATE_BAR_TEXT_LENGTH = PREFIX + "stateBarTextLength";
    public static final String STATE_BAR_RUN_TIME = PREFIX + "stateBarRunTime";
    public static final String STATE_BAR_COST_TIME = PREFIX + "stateBarCostTime";
    public static final String COMPONENT_SCROLL_GROUP = PREFIX + "Group:ScrollPane";

    /**
     * 系统设置配置参数
     */
    public static final String USER_PRPPERTIES_CONFIG = PREFIX + "user:dir#config";
    public static final String DEFAULT_PRPPERTIES_CONFIG = PREFIX + "default:dir#config";
}
