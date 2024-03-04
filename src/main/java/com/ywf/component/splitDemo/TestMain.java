package com.ywf.component.splitDemo;


import com.ywf.framework.init.SysConfigInit;
import com.ywf.framework.ioc.ConfigurableApplicationContext;
import com.ywf.framework.utils.FileUtils;
import com.ywf.framework.utils.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;


/**
 * TODO
 *
 * @Author YWF
 * @Date 2024/2/22 11:50
 */
public class TestMain {
    private final static Logger logger = LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) {
        //test002();
        test003();
    }

    public static void test001() {

    }

    public static void test002() {

    }

    public static void test003() {
        ConfigurableApplicationContext applicationContextDefault = FileUtils.readJSONFile("config/application.json", ConfigurableApplicationContext.class, FileUtils.STREAM_TYPE);
        System.out.println("applicationContextDefault:" + applicationContextDefault.toString());
        String applicationRootPath = SysConfigInit.getSystemRootPath();


        ConfigurableApplicationContext applicationContext = FileUtils.readJSONFile(applicationRootPath + "/config/application.json", ConfigurableApplicationContext.class, FileUtils.FILE_TYPE);
        System.out.println("applicationContext:" + applicationContext.toString());

        int counts = ReflectUtils.copyObjectFiledValue(applicationContextDefault, applicationContext);
        if (counts>0){
            FileUtils.saveJSONFile(applicationContext, applicationRootPath + "/config/application.json");
        }
    }




}