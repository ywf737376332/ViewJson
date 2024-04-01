package com.ywf.framework.utils;

import com.ywf.framework.config.GlobalKEY;

import javax.swing.*;

/**
 * window系统工具类
 *
 * @Author YWF
 * @Date 2024/3/11 22:35
 */
public class WindowUtils {

    public static JFrame getFrame() {
        return ObjectUtils.getBean(GlobalKEY.MAIN_FRAME);
    }

}
