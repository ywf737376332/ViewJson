package com.ywf.framework.constant;

import com.ywf.action.ResourceBundleService;

import java.util.ResourceBundle;

/**
 * 国际化常量
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
     * 正在加载中... 当前耗时: " + nowTime++ + " 毫秒
     */
    public static final String SYSTEM_LOADING_TIP = getMessage("System.Loading.Tip");

    /**
     * 操作耗时: "+costTime +" 毫秒
     */
    public static final String SYSTEM_LOADING_COST_TIME_TIP = getMessage("System.LoadingCostTime.Tip");

    /**
     * 词
     */
    public static final String SYSTEM_STATE_BAR_WORDS = getMessage("System.StateBar.Words");

    /**
     * 文本类型
     */
    public static final String SYSTEM_DEFAULT_LANGUAGE_TYPE = getMessage("Language.Type.Text");

    /**
     * 天
     */
    public static final String SYSTEM_STATE_BAR_DAYS = getMessage("System.StateBar.Days");

    /**
     * 小时
     */
    public static final String SYSTEM_STATE_BAR_HOURS = getMessage("System.StateBar.Hours");

    /**
     * 分钟
     */
    public static final String SYSTEM_STATE_BAR_MINUTES = getMessage("System.StateBar.Minutes");

    /**
     * 秒
     */
    public static final String SYSTEM_STATE_BAR_SECONDS = getMessage("System.StateBar.Seconds");

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
    public static final String AUTHOR = getMessage("StateBar.Author");

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

    /**
     * 保存文件提示
     */
    public static final String SYSTEM_SAVE_FILE_TAG = getMessage("System.SaveFile.Tag");

    /**
     * JSON文件
     * 图片文件
     */
    public static final String SYSTEM_JSON_FILE_TYPE = getMessage("System.FileType.Json");
    public static final String SYSTEM_IMAGE_FILE_TYPE = getMessage("System.FileType.Image");

    /**
     * 已将格式化后的JSON结果复制到剪贴板！
     */
    public static final String SYSTEM_COPY_JSON_SUCCESS_TIP = getMessage("System.CopyJsonSuccess.Tip");

    /**
     * 内容复制失败！
     */
    public static final String SYSTEM_COPY_JSON_FAIL_TIP = getMessage("System.CopyJsonFail.Tip");

    /**
     * 图片已复制到剪贴板！
     */
    public static final String SYSTEM_COPY_IMAGE_SUCCESS_TIP = getMessage("System.CopyImageSuccess.Tip");

    /**
     * 图片复制失败！
     */
    public static final String SYSTEM_COPY_IMAGE_FAIL_TIP = getMessage("System.CopyImageFail.Tip");

    /**
     * 图片已保存
     */
    public static final String SYSTEM_IMAGE_SAVE_SUCCESS = getSepMessage("System.Image.SaveSuccess");

    /**
     * 图片保存失败
     */
    public static final String SYSTEM_IMAGE_SAVE_FAIL = getMessage("System.Image.SaveFail");

    /**
     * 文件已保存
     */
    public static final String SYSTEM_FILE_SAVE_SUCCESS = getSepMessage("System.File.SaveSuccess");

    /**
     * 文件保存失败
     */
    public static final String SYSTEM_FILE_SAVE_FAIL = getMessage("System.File.SaveFail");

    /**
     * 二维码展示
     */
    public static final String SYSTEM_SHOW_QRCODE = getMessage("System.Show.QRcode");

    /**
     * 二维码生成失败
     */
    public static final String SYSTEM_GRENT_QRCODE_FAIL = getMessage("System.GenerateQRcode.Fail");

    /**
     * 错误
     */
    public static final String SYSTEM_ERROR_TIP = getMessage("System.Error.Tip");

    /**
     * 提示
     */
    public static final String SYSTEM_WARN_TIP = getMessage("System.Warn.Tip");

    /**
     * 功能介绍
     */
    public static final String SYSTEM_VERSION_LOG = getMessage("MenuItem.FeatureIntroduction");

    /**
     * 查找
     */
    public static final String SYSTEM_FIND = getMessage("System.Find");

    /**
     * 请输入您要查找的内容...
     */
    public static final String SYSTEM_FIND_CONTENT_TIP = getMessage("System.FindContent.Tip");

    /**
     * 下一个
     */
    public static final String SYSTEM_FIND_NEXT = getMessage("System.Find.Next");

    /**
     * 上一个
     */
    public static final String SYSTEM_FIND_PREVIOUS = getMessage("System.Find.Previous");

    /**
     * 0个匹配项
     */
    public static final String SYSTEM_FIND_RESULTS_TIP = getMessage("System.FindResults.Tip");

    /**
     * 已搜索到最底部，没有更多内容被找到
     */
    public static final String SYSTEM_NEXT_FIND_RESULTS_TIP = getMessage("System.NextFindResults.Tip");

    /**
     * 已搜索到最顶部，没有更多内容被找到
     */
    public static final String SYSTEM_PREV_FIND_RESULTS_TIP = getMessage("System.PrevFindResults.Tip");

    /**
     * 系统设置
     */
    public static final String TAB_TITLE_SYSTEM_SETTINGS = getMessage("Tab.Title.SystemSettings");

    /**
     * 系统主题
     */
    public static final String TAB_TITLE_SYSTEM_THEMES = getMessage("Tab.Title.SystemThemes");

    /**
     * 界面字体
     */
    public static final String TAB_TITLE_SETTINGS_FONT = getMessage("Tab.Title.SystemFont");

    /**
     * 行
     */
    public static final String SYSTEM_STATE_BAR_LINE = getMessage("System.StateBar.Line");

    /**
     * 列
     */
    public static final String SYSTEM_STATE_BAR_COLUMN = getMessage("System.StateBar.Column");

    /**
     * 关闭倒计时：
     */
    public static final String SYSTEM_DIALOG_CLOSE_TIP = getMessage("System.Dialog.CloseTime");

    /**
     * 语言切换
     */
    public static final String SYSTEM_SET_LANGUAGE_SUCCESS = getMessage("System.SetLanguage.Success");

    private static String getSepMessage(String keyRoot) {
        return resourceBundle.getString(keyRoot + ".Name") + Separator;
    }

    private static String getMessage(String keyRoot) {
        return resourceBundle.getString(keyRoot + ".Name");
    }
}
