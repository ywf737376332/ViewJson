package com.ywf.framework.constant;

import com.ywf.action.ResourceBundleService;

import java.util.ResourceBundle;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/3/4 17:37
 */
public class MessageConstant {

    private final static String Separator = "：";

    private static ResourceBundle resourceBundle;

    static {
        resourceBundle = ResourceBundleService.getInstance().getResourceBundle();
    }

    /**
     * 系统标题
     */
    public static final String SYSTEM_TITLE = getMessage("System.Title");

    /**
     * 运行时长
     */
    public static final String RUNTIME_TAG = getSepMessage("StateBar.RunTime");

    /**
     * 作者标签
     */
    public static final String AUTHOR_TAG = getSepMessage("StateBar.AuthorTag");

    /**
     * 作者
     */
    public static final String AUTHOR = getSepMessage("StateBar.Author");

    /**
     * 内容类型
     */
    public static final String CONTEXT_TYPE_TAG = getSepMessage("StateBar.ContextType");

    /**
     * 字数统计
     */
    public static final String WORDS_NUMS_TAG = getSepMessage("StateBar.WordsNums");

    /**
     * 内容为空提示
     */
    public static final String SYSTEM_EMPTY_CONTENT_TIP = getMessage("System.EmptyContent.Tip");

    /**
     * 退出系统提示
     */
    public static final String SYSTEM_EXIT_TIP = getMessage("System.Exit.Tip");

    /**
     * 确认关闭提示
     */
    public static final String SYSTEM_CONFIRM_CLOSE_TIP = getMessage("System.Confirm.Close.Tip");



    private static String getSepMessage(String keyRoot) {
        return resourceBundle.getString(keyRoot + ".Name") + Separator;
    }

    private static String getMessage(String keyRoot) {
        return resourceBundle.getString(keyRoot + ".Name");
    }
}
