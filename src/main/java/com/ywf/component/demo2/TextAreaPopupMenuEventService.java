package com.ywf.component.demo2;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.formdev.flatlaf.FlatClientProperties;
import com.ywf.component.*;
import com.ywf.framework.constant.SystemConstant;
import com.ywf.framework.enums.SystemThemesEnum;
import com.ywf.framework.enums.TextConvertEnum;
import com.ywf.framework.enums.TextTypeEnum;
import com.ywf.framework.utils.*;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;
import java.util.List;


/**
 * 菜单事件
 * <p>
 * 懒汉模式：不安全的有读操作，也有写操作。如何保证懒汉模式的线程安全问题：
 * 加锁，把 if 和 new 变成原子操作。
 * 双重 if，减少不必要的加锁操作。
 * 使用 volatile 禁止指重排序，保证后续线程肯定拿到的是完整对象。
 * <p>
 * https://blog.csdn.net/wyd_333/article/details/130416315
 *
 * @Author YWF
 * @Date 2023/12/7 17:15
 */
public class TextAreaPopupMenuEventService {


    /**
     * 保存图片放大倍数
     */
    private static int pictureScale;

    volatile private static TextAreaPopupMenuEventService instance = null;


    private TextAreaPopupMenuEventService() {
    }

    public static TextAreaPopupMenuEventService getInstance() {
        if (instance == null) {
            synchronized (TextAreaPopupMenuEventService.class) {
                if (instance == null) {
                    instance = new TextAreaPopupMenuEventService();
                }
            }
        }
        return instance;
    }

    /**
     * 清空文本内容
     */
    public void closeAbleTabbedSplitPane() {
        System.out.println("关闭事件");
    }


}
