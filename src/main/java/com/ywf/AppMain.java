package com.ywf;

import com.ywf.framework.ioc.ApplicationView;

/**
 * APP启动类
 */
public class AppMain {

    public static void main(String[] args) {
        ApplicationView.run(AppMain.class, args);
    }
}
