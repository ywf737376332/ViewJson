package com.ywf.component.splitDemo;


import com.ywf.framework.init.SysConfigInit;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import com.ywf.framework.utils.FileUtils;


/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/2/22 11:50
 */
public class TestMain {

    public static void main(String[] args) {
        //test002();
        test003();
    }

    public static void test001() {

    }

    public static void test002() {

    }

    public static void test003() {
        ConfigurableApplicationContext applicationContext = FileUtils.readJSONFile("config/application.json", ConfigurableApplicationContext.class);

        String applicationRootPath = SysConfigInit.getSystemRootPath();
        FileUtils.saveJSONFile(applicationContext, applicationRootPath + "/config/application.json");
    }
}