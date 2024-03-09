package com.ywf.framework.constant;

import com.ywf.framework.enums.FontEnum;

import java.awt.*;
import java.io.Serializable;

/**
 * 系统常量
 *
 * @Author YWF
 * @Date 2023/12/2 18:23
 */
public class SystemConstant implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 全局主要字体
     */
    public static final Font SYSTEM_DEFAULT_FONT = new Font(FontEnum.Name.MicYaHei.getName(), Font.PLAIN, FontEnum.Size.regular.getSize());
    public static final Font SYSTEM_STATE_BAR_FONT = new Font(FontEnum.Name.MicYaHei.getName(), Font.PLAIN, FontEnum.Size.medium.getSize());
    public static final Font SYSTEM_WATERMARK_FONT = new Font(FontEnum.Name.MicYaHei.getName(), Font.PLAIN, FontEnum.Size.large.getSize());

    /**
     * 窗口标题及版本号
     */
    public static final String SYSTEM_VERSION = "V5.0";   // 版本号

    /**
     * 窗口大小最小值
     */
    public static final int WINDOWS_MIN_WIDTH = 930;   // 窗口最小宽度
    public static final int WINDOWS_MIN_HEIGHT = 680;    // 窗口最小高度

    /**
     * 保存文件扩展名
     */
    public static final String SAVE_JSON_EXTENSION = ".json";
    public static final String SAVE_IMAGE_EXTENSION = ".png";

    /**
     * 主题类型常量
     */
    public static final int THEMES_TYPE_SYSTEM = 1;   // 系统主题
    public static final int THEMES_TYPE_OTHER = 2;    // 其他第三方自定义主题

    /**
     * 国密SM2密钥
     */
    public final static String publicKey = "0489f68e0e9a4ff7cc5a664ff842813132d09931d80a4d7e40390b6a9754f5d5413cca075ac2d0a37613c151cd1ee82c81b20c6d9106f5f449a9333a02aa95b303";
    public final static String privateKey = "514762ec15c4f0d61970a213501c3cb31ad2efa13d00f4615fe55f9b2dc10d36";


}
