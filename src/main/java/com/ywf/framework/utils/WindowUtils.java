package com.ywf.framework.utils;

import javax.swing.*;
import java.awt.*;

/**
 * window系统工具类
 *
 * @Author YWF
 * @Date 2024/3/11 22:35
 */
public class WindowUtils {

    public static JFrame getFrame() {
        return (JFrame) Window.getWindows()[0];
    }

}
